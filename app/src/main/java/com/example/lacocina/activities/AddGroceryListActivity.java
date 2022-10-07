package com.example.lacocina.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lacocina.R;

import java.util.Objects;

public class AddGroceryListActivity extends AppCompatActivity {

    // String tags as identifier when sending data via intent
    public static final String EXTRA_GROCERY_ID =
            "com.example.lacocina.EXTRA_GROCERY_ID";
    public static final String EXTRA_NAME_GROCERY =
            "com.example.lacocina.EXTRA_NAME_GROCERY";
    public static final String EXTRA_ITEMS_GROCERY =
            "com.example.lacocina.EXTRA_ITEMS_GROCERY";
    public static final String EXTRA_FAV_GROCERY =
            "com.example.lacocina.EXTRA_FAV_GROCERY";

    // Fields for Views
    private TextView recipeName;
    private TextView groceryItems;
    private ImageButton favouriteListButton;

    // Button Mode Field
    private String isFavourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();

    }

    /*
    Initializes UI
    - Layout
    - TextView
    - ImageButton
    - Setting up OnClickListener on ImageButton
    - Initialize favourite button image resource and button mode
     */
    private void initUI() {

        setContentView(R.layout.activity_add_grocery_list);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_close);

        recipeName = findViewById(R.id.grocery_list_recipe_name);
        groceryItems = findViewById(R.id.grocery_list_items);
        favouriteListButton = findViewById(R.id.favourite_button_grocery_list);

        favouriteListButton.setOnClickListener(view -> toggleButton());

        receivingIntent();
        initButtonMode();
    }

    // Saving Grocery List by sending data to GroceryListActivity where it is going to be saved persistently.
    private void saveGroceryList() {

        // Get Strings from Views
        String name = recipeName.getText().toString();
        String items = groceryItems.getText().toString();

        // Check if String is empty; if empty, no intent will be send to activity
        if (name.trim().isEmpty() || items.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a name and items for your grocery list", Toast.LENGTH_SHORT).show();
        }

        // Send Intent to GroceryListActivity
        Intent data = new Intent();
        data.putExtra(EXTRA_NAME_GROCERY, name);
        data.putExtra(EXTRA_ITEMS_GROCERY, items);
        Log.d("Saving isFavourite", isFavourite);
        data.putExtra(EXTRA_FAV_GROCERY, isFavourite);

        // Passing ID to intent so that it can be identified which item should be updated
        int id = getIntent().getIntExtra(EXTRA_GROCERY_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_GROCERY_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();

    }

    // Toggling favourite Button On and Off visually as well as its button mode
    // Button Mode: isFavourite; 0 = Off, 1 = On
    private void toggleButton() {

        if(isFavourite == null) {
            isFavourite = "0";
        }
        if(isFavourite.equals("1")) {
            favouriteListButton.setImageResource(R.drawable.ic_star_border_50);
            isFavourite = "0";
        }
        else {
            isFavourite = "1";
            favouriteListButton.setImageResource(R.drawable.ic_star_50_green);
        }
    }

    // Method initializing Button for toggling
    private void initButtonMode() {
        if(isFavourite == null) {
            isFavourite = "0";
            favouriteListButton.setImageResource(R.drawable.ic_star_border_50);
        } else if (isFavourite.equals("1")) {
            favouriteListButton.setImageResource(R.drawable.ic_star_50_green);
        }
    }

    // Receiving Intent for Editing/Adding grocery list
    private void receivingIntent() {

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_GROCERY_ID)) {
            setTitle("Edit Grocery List");
            recipeName.setText(intent.getStringExtra(EXTRA_NAME_GROCERY));
            groceryItems.setText(intent.getStringExtra(EXTRA_ITEMS_GROCERY));
            isFavourite = intent.getStringExtra(EXTRA_FAV_GROCERY);
            Log.d("Receiving isFavourite", isFavourite);

        } else {
            setTitle("Add Grocery List");
        }
    }

    // Init Layout for Menu
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_grocery_list_menu, menu);
        return true;
    }

    // Provide actions when clicking icons on menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_grocery_list:
                saveGroceryList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}