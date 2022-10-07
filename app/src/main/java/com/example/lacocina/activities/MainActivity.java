package com.example.lacocina.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.lacocina.R;

import java.util.Objects;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity {

    // View Fields
    private CardView customRecipes;
    private CardView linkRecipes;
    private CardView groceryList;
    private CardView settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    // Init of Buttons and their click listeners
    private void initUI() {

        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        customRecipes = findViewById(R.id.custom_recipes_button);
        linkRecipes = findViewById(R.id.internet_recipes_button);
        groceryList = findViewById(R.id.grocery_list_button);
        settings = findViewById(R.id.settings_button);

        // Setting up click listener to all buttons
        customRecipes.setOnClickListener(view -> openRecipeListActivity());

        linkRecipes.setOnClickListener(view -> openRecipeLinkListActivity());

        groceryList.setOnClickListener(view -> openGroceryListActivity());

        settings.setOnClickListener(view -> notImplementedToast());

    }
    // Methods for Opening Activities

    private void openRecipeListActivity() {
        Intent recipeListIntent = new Intent(this, RecipeListActivity.class);
        startActivity(recipeListIntent);
    }

    private void openRecipeLinkListActivity() {
        Intent recipeLinkIntent = new Intent(this, RecipeLinkListActivity.class);
        startActivity(recipeLinkIntent);
    }

    private void openGroceryListActivity() {
        Intent groceryListIntent = new Intent(this, GroceryListActivity.class);
        startActivity(groceryListIntent);
    }

    // Method showing Toast if activity has not been implemented or added to android manifest
    private void notImplementedToast() {
        Toast.makeText(this, "Not implemented yet!", Toast.LENGTH_SHORT).show();
    }
}