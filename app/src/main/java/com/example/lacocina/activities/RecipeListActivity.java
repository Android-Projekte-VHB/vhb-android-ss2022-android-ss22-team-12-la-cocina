package com.example.lacocina.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lacocina.R;
import com.example.lacocina.recipe.Recipe;
import com.example.lacocina.recipe.RecipeAdapter;
import com.example.lacocina.recipe.RecipeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.Objects;

@SuppressWarnings("deprecation")
public class RecipeListActivity extends AppCompatActivity {
    // Handling request whether recipe is edited or new added to list
    public static final int ADD_RECIPE_REQUEST = 1;
    public static final int EDIT_RECIPE_REQUEST = 2;

    private RecipeViewModel recipeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_home);
        setTitle("Recipe List");

        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_recipe);
        buttonAddNote.setOnClickListener(v -> {
            Intent intent = new Intent(RecipeListActivity.this, RecipeActivity.class);
            startActivityForResult(intent, ADD_RECIPE_REQUEST);
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        RecipeAdapter adapter = new RecipeAdapter();
        recyclerView.setAdapter(adapter);


        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        recipeViewModel.getAllRecipes().observe(this, recipes -> {
            // update RecyclerView
            adapter.setRecipes(recipes);
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                // Alert if User wants to delete item permanently
                new AlertDialog.Builder(RecipeListActivity.this)

                        .setIcon(R.drawable.ic_delete)
                        .setMessage("Do you want to delete this Recipe?")
                        .setTitle("Delete Recipe")
                        // Delete item and instant update of recyclerview
                        .setPositiveButton("Yes, delete", (dialogInterface, i) -> {
                            recipeViewModel.delete(adapter.getRecipeAt(viewHolder.getAdapterPosition()));
                            Toast.makeText(RecipeListActivity.this, "Recipe deleted", Toast.LENGTH_SHORT).show();
                        })
                        // Not deleting item
                        .setNegativeButton("No, cancel", (dialogInterface, i) -> {
                            Toast.makeText(RecipeListActivity.this, "Recipe not deleted", Toast.LENGTH_SHORT).show();
                            // Put item back to its original position
                            adapter.notifyItemChanged(viewHolder.getAdapterPosition());

                        })
                        .show();
            }
        }).attachToRecyclerView(recyclerView);

        // Sending intent to RecipeActivity
        adapter.setOnItemClickListener(recipe -> {
            Intent intent = new Intent(RecipeListActivity.this, RecipeActivity.class);
            intent.putExtra(RecipeActivity.EXTRA_ID, recipe.getId());
            intent.putExtra(RecipeActivity.EXTRA_TITLE, recipe.getRecipeTitle());
            intent.putExtra(RecipeActivity.EXTRA_DESCRIPTION, recipe.getDescription());
            intent.putExtra(RecipeActivity.EXTRA_INGREDIENTS, recipe.getIngredients());
            intent.putExtra(RecipeActivity.EXTRA_INSTRUCTION, recipe.getInstruction());
            intent.putExtra(RecipeActivity.EXTRA_LINK, recipe.getYoutubeTutorialURL());
            intent.putExtra(RecipeActivity.EXTRA_IMG, recipe.getImgURI());
            intent.putExtra(RecipeActivity.EXTRA_INGREDIENTS_NOTE, recipe.getIngredientsNote());
            intent.putExtra(RecipeActivity.EXTRA_INSTRUCTION_NOTE, recipe.getInstructionNote());
            intent.putExtra(RecipeActivity.EXTRA_DIET, recipe.getDiet());
            intent.putExtra(RecipeActivity.EXTRA_FAV, recipe.getIsFavourite());
            intent.putExtra(RecipeActivity.EXTRA_TIME, String.valueOf(recipe.getCookingTime()));
            startActivityForResult(intent, EDIT_RECIPE_REQUEST);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.list_recipe_menu, menu);
        return true;
    }

    // Receiving intent from recipe activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int addCookingTimeFinal;

        if (requestCode == ADD_RECIPE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(RecipeActivity.EXTRA_TITLE);
            String description = data.getStringExtra(RecipeActivity.EXTRA_DESCRIPTION);
            String ingredients = data.getStringExtra(RecipeActivity.EXTRA_INGREDIENTS);
            String instruction = data.getStringExtra(RecipeActivity.EXTRA_INSTRUCTION);
            String youtubeLink = data.getStringExtra(RecipeActivity.EXTRA_LINK);
            String imgURI = data.getStringExtra(RecipeActivity.EXTRA_IMG);
            String ingNote = data.getStringExtra(RecipeActivity.EXTRA_INGREDIENTS_NOTE);
            String insNote = data.getStringExtra(RecipeActivity.EXTRA_INSTRUCTION_NOTE);
            String diet = data.getStringExtra(RecipeActivity.EXTRA_DIET);
            String isFavourite = data.getStringExtra(RecipeActivity.EXTRA_FAV);
            String cookingTime = data.getStringExtra(RecipeActivity.EXTRA_TIME);
            Integer check = Integer.valueOf(cookingTime);
            if(check == null) {
                addCookingTimeFinal = 0;
            } else {
                addCookingTimeFinal = Integer.valueOf(cookingTime);
            }




            Recipe recipe = new Recipe(title, description, ingredients, instruction, youtubeLink, imgURI, ingNote, insNote, diet, isFavourite, addCookingTimeFinal );
            recipeViewModel.insert(recipe);

            Toast.makeText(this, "Recipe saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_RECIPE_REQUEST && resultCode == RESULT_OK) {
            // Get ID to identify the right recipe object
            int id = data.getIntExtra(RecipeActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(RecipeActivity.EXTRA_TITLE);
            String description = data.getStringExtra(RecipeActivity.EXTRA_DESCRIPTION);
            String ingredients = data.getStringExtra(RecipeActivity.EXTRA_INGREDIENTS);
            String instruction = data.getStringExtra(RecipeActivity.EXTRA_INSTRUCTION);
            String youtubeLink = data.getStringExtra(RecipeActivity.EXTRA_LINK);
            String imgURI = data.getStringExtra(RecipeActivity.EXTRA_IMG);
            String ingNote = data.getStringExtra(RecipeActivity.EXTRA_INGREDIENTS_NOTE);
            String insNote = data.getStringExtra(RecipeActivity.EXTRA_INSTRUCTION_NOTE);
            String diet = data.getStringExtra(RecipeActivity.EXTRA_DIET);
            String isFavourite = data.getStringExtra(RecipeActivity.EXTRA_FAV);
            String cookingTime = data.getStringExtra(RecipeActivity.EXTRA_TIME);
            int cookingTimeFinal = Integer.parseInt(cookingTime);



            Recipe recipe = new Recipe(title, description, ingredients, instruction, youtubeLink, imgURI, ingNote, insNote, diet, isFavourite, cookingTimeFinal);
            recipe.setId(id);
            recipeViewModel.update(recipe);

            // User Feedback
            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }

    }

}
