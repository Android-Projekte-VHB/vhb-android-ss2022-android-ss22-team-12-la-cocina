package com.example.lacocina.activities;


import static com.example.lacocina.activities.RecipeActivity.SHARED_LIST_ITEMS;
import static com.example.lacocina.activities.RecipeActivity.SHARED_PREF_GROCERY_LIST;
import static com.example.lacocina.activities.RecipeActivity.SHARED_RECIPE_NAME;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.widget.Toast;

import com.example.lacocina.R;
import com.example.lacocina.grocerylist.GroceryList;
import com.example.lacocina.grocerylist.GroceryListViewModel;
import com.example.lacocina.recipe.GroceryListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

@SuppressWarnings("deprecation")
public class GroceryListActivity extends AppCompatActivity {

    // Field for differentiating between adding new list and editing old ones
    public static final int ADD_LIST_REQUEST = 1;
    public static final int EDIT_LIST_REQUEST = 2;

    private GroceryListViewModel groceryListViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Grocery Lists");

        // setting up button w/ click listener
        FloatingActionButton buttonAddGrocery = findViewById(R.id.button_add_grocery_list);
        buttonAddGrocery.setOnClickListener(view -> {
            Intent intent = new Intent(GroceryListActivity.this, AddGroceryListActivity.class);
            startActivityForResult(intent, ADD_LIST_REQUEST);
        });

        // Init of recyclerview, adapter and view model
        RecyclerView recyclerView = findViewById(R.id.recycler_view_grocery_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        GroceryListAdapter adapter = new GroceryListAdapter();
        recyclerView.setAdapter(adapter);

        groceryListViewModel = new ViewModelProvider(this).get(GroceryListViewModel.class);
        groceryListViewModel.getAllLists().observe(this, adapter::setLists);

        // Handling touch/swipe gestures for items in recycler view
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                // Alert if User wants to delete item permanently
                new AlertDialog.Builder(GroceryListActivity.this)

                        .setIcon(R.drawable.ic_delete)
                        .setMessage("Do you want to delete this List?")
                        .setTitle("Delete List")
                        // Delete item and instant update of recyclerview
                        .setPositiveButton("Yes, delete", (dialogInterface, i) -> {
                            groceryListViewModel.delete(adapter.getListAt(viewHolder.getAdapterPosition()));
                            Toast.makeText(GroceryListActivity.this, "List deleted", Toast.LENGTH_SHORT).show();
                        })
                        // Not deleting item
                        .setNegativeButton("No, cancel", (dialogInterface, i) -> {
                            Toast.makeText(GroceryListActivity.this, "List not deleted", Toast.LENGTH_SHORT).show();
                            // Put item back to its original position
                            adapter.notifyItemChanged(viewHolder.getAdapterPosition());

                        })
                        .show();

            }
            // Item will be dragged to a quarter of the screen; method also slows down swipe animation
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX / 2, dY, actionState, isCurrentlyActive);
            }

        }).attachToRecyclerView(recyclerView);

        // Implementing interface of adapter
        adapter.setOnItemClickListener(groceryList -> {
            Intent intent = new Intent(GroceryListActivity.this, AddGroceryListActivity.class);
            intent.putExtra(AddGroceryListActivity.EXTRA_GROCERY_ID, groceryList.getId());
            intent.putExtra(AddGroceryListActivity.EXTRA_NAME_GROCERY, groceryList.getListName());
            intent.putExtra(AddGroceryListActivity.EXTRA_ITEMS_GROCERY, groceryList.getListItems());
            intent.putExtra(AddGroceryListActivity.EXTRA_FAV_GROCERY, groceryList.getIsFavourite());
            startActivityForResult(intent, EDIT_LIST_REQUEST);
        });

        insertPreferences();

    }

    private void insertPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_GROCERY_LIST, MODE_PRIVATE);
        String title = sharedPreferences.getString(SHARED_RECIPE_NAME, "");
        String items = sharedPreferences.getString(SHARED_LIST_ITEMS, "");

        if(!title.equals("")) {
            GroceryList groceryList = new GroceryList(title, items, "0");
            groceryListViewModel.insert(groceryList);
        }
        sharedPreferences.edit().clear().apply();
    }

    // Handling Intent data from AddGroceryListActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Checking if correct intent received
        if(requestCode == ADD_LIST_REQUEST && resultCode == RESULT_OK) {
            assert data != null;
            String listName = data.getStringExtra(AddGroceryListActivity.EXTRA_NAME_GROCERY);
            String groceryItems = data.getStringExtra(AddGroceryListActivity.EXTRA_ITEMS_GROCERY);
            String isFavourite = data.getStringExtra(AddGroceryListActivity.EXTRA_FAV_GROCERY);

            GroceryList groceryList = new GroceryList(listName, groceryItems, isFavourite);
            groceryListViewModel.insert(groceryList);

            // User Feedback
            Toast.makeText(this, "Grocery List saved!", Toast.LENGTH_SHORT).show();

        } else if(requestCode == EDIT_LIST_REQUEST && resultCode == RESULT_OK) {

            // Retrieve ID for update
            assert data != null;
            int id = data.getIntExtra(AddGroceryListActivity.EXTRA_GROCERY_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Grocery List cannot be updated!", Toast.LENGTH_SHORT).show();
                return;
            }

            String listName = data.getStringExtra(AddGroceryListActivity.EXTRA_NAME_GROCERY);
            String groceryItems = data.getStringExtra(AddGroceryListActivity.EXTRA_ITEMS_GROCERY);
            String isFavourite = data.getStringExtra(AddGroceryListActivity.EXTRA_FAV_GROCERY);

            GroceryList groceryList = new GroceryList(listName, groceryItems, isFavourite);
            groceryList.setId(id);
            groceryListViewModel.update(groceryList);

            // User Feedback
            Toast.makeText(this, "Grocery List saved!", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Grocery List not saved!", Toast.LENGTH_SHORT).show();
        }
    }

}