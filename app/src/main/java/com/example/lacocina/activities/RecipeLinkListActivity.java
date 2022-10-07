package com.example.lacocina.activities;

import static com.example.lacocina.activities.AddReceivedLinkActivity.RECIPE_DESCRIPTION;
import static com.example.lacocina.activities.AddReceivedLinkActivity.RECIPE_FAV;
import static com.example.lacocina.activities.AddReceivedLinkActivity.RECIPE_LINK;
import static com.example.lacocina.activities.AddReceivedLinkActivity.RECIPE_NAME;
import static com.example.lacocina.activities.AddReceivedLinkActivity.SHARED_PREFS_LINK;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lacocina.R;
import com.example.lacocina.link.RecipeLink;
import com.example.lacocina.link.RecipeLinkViewModel;
import com.example.lacocina.recipe.RecipeLinkAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

@SuppressWarnings("deprecation")
public class RecipeLinkListActivity extends AppCompatActivity {

    // Fields for Request distinguishing between adding and editing
    public static final int ADD_RECIPE_LINK_REQUEST = 1;
    public static final int EDIT_RECIPE_LINK_REQUEST = 2;

    private RecipeLinkViewModel recipeLinkViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_link_list);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_filter_list);
        setTitle("Recipe Links");

        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_recipe_link);
        buttonAddNote.setOnClickListener(v -> {
            Intent intent = new Intent(RecipeLinkListActivity.this, AddReceivedLinkActivity.class);
            startActivityForResult(intent, ADD_RECIPE_LINK_REQUEST);
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view_link);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        RecipeLinkAdapter linkAdapter = new RecipeLinkAdapter();
        recyclerView.setAdapter(linkAdapter);

        recipeLinkViewModel = new ViewModelProvider(this).get(RecipeLinkViewModel.class);
        // update RecyclerView
        recipeLinkViewModel.getAllRecipeLinks().observe(this, linkAdapter::setRecipeLinks);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                // Alert if User wants to delete item permanently
                new AlertDialog.Builder(RecipeLinkListActivity.this)

                        .setIcon(R.drawable.ic_delete)
                        .setMessage("Do you want to delete this Recipe?")
                        .setTitle("Delete Recipe")
                        // Delete item and instant update of recyclerview
                        .setPositiveButton("Yes, delete", (dialogInterface, i) -> {
                            recipeLinkViewModel.delete(linkAdapter.getRecipeLinkAt(viewHolder.getAdapterPosition()));
                            Toast.makeText(RecipeLinkListActivity.this, "Recipe deleted", Toast.LENGTH_SHORT).show();
                        })
                        // Not deleting item
                        .setNegativeButton("No, cancel", (dialogInterface, i) -> {
                            Toast.makeText(RecipeLinkListActivity.this, "List not deleted", Toast.LENGTH_SHORT).show();
                            // Put item back to its original position
                            linkAdapter.notifyItemChanged(viewHolder.getAdapterPosition());

                        })
                        .show();
            }
        }).attachToRecyclerView(recyclerView);

        linkAdapter.setOnItemClickListener(recipelink -> {
            Intent intent = new Intent(RecipeLinkListActivity.this, AddReceivedLinkActivity.class);
            intent.putExtra(AddReceivedLinkActivity.EXTRA_LINK_ID, recipelink.getId());
            intent.putExtra(AddReceivedLinkActivity.EXTRA_LINK_NAME, recipelink.getTitle());
            intent.putExtra(AddReceivedLinkActivity.EXTRA_LINK_LINK, recipelink.getLink());
            intent.putExtra(AddReceivedLinkActivity.EXTRA_LINK_DESCRIPTION, recipelink.getShortDescription());
            intent.putExtra(AddReceivedLinkActivity.EXTRA_LINK_FAV, recipelink.getIsFavourite());
            startActivityForResult(intent, EDIT_RECIPE_LINK_REQUEST);
        });

        insertPreferences();

    }

    @Override
    public boolean onCreateOptionsMenu (@NonNull Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.list_recipe_menu, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_RECIPE_LINK_REQUEST && resultCode == RESULT_OK) {
            assert data != null;
            String title = data.getStringExtra(AddReceivedLinkActivity.EXTRA_LINK_NAME);
            String link = data.getStringExtra(AddReceivedLinkActivity.EXTRA_LINK_LINK);
            String shortDescription = data.getStringExtra(AddReceivedLinkActivity.EXTRA_LINK_DESCRIPTION);
            String isFavourite = data.getStringExtra(AddReceivedLinkActivity.EXTRA_LINK_FAV);

            RecipeLink recipeLink = new RecipeLink(link, title, isFavourite, shortDescription);
            recipeLinkViewModel.insert(recipeLink);


        } else if (requestCode == EDIT_RECIPE_LINK_REQUEST && resultCode == RESULT_OK) {
            assert data != null;
            int id = data.getIntExtra(AddReceivedLinkActivity.EXTRA_LINK_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Link to Recipe can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(AddReceivedLinkActivity.EXTRA_LINK_NAME);
            String link = data.getStringExtra(AddReceivedLinkActivity.EXTRA_LINK_LINK);
            String shortDescription = data.getStringExtra(AddReceivedLinkActivity.EXTRA_LINK_DESCRIPTION);
            String isFavourite = data.getStringExtra(AddReceivedLinkActivity.EXTRA_LINK_FAV);

            RecipeLink recipeLink = new RecipeLink(link, title, isFavourite, shortDescription);
            recipeLink.setId(id);
            recipeLinkViewModel.update(recipeLink);

        }
    }

    private void insertPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_LINK, MODE_PRIVATE);
        String title = sharedPreferences.getString(RECIPE_NAME, "");
        String link = sharedPreferences.getString(RECIPE_LINK, "");
        String description = sharedPreferences.getString(RECIPE_DESCRIPTION, "");
        String isFavourite =  sharedPreferences.getString(RECIPE_FAV, "");


        if(!title.equals("")) {
            RecipeLink recipeLink = new RecipeLink(link ,title, isFavourite, description);
            recipeLinkViewModel.insert(recipeLink);
        }
        sharedPreferences.edit().clear().apply();
    }
}