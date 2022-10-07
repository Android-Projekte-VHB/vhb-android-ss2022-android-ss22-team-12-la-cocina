package com.example.lacocina.activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lacocina.R;

import java.util.Objects;

public class AddReceivedLinkActivity extends AppCompatActivity {

    // Intent Keys
    public static final String EXTRA_LINK_ID =
            "com.example.lacocina.EXTRA_LINK_ID";
    public static final String EXTRA_LINK_NAME =
            "com.example.lacocina.EXTRA_LINK_NAME";
    public static final String EXTRA_LINK_LINK =
            "com.example.lacocina.EXTRA_LINK_LINK";
    public static final String EXTRA_LINK_DESCRIPTION =
            "com.example.lacocina.EXTRA_LINK_NOTE";
    public static final String EXTRA_LINK_FAV =
            "com.example.lacocina.EXTRA_LINK_FAV";


    //
    public static final String SHARED_PREFS_LINK = "sharedLink";
    public static final String RECIPE_NAME = "linkName";
    public static final String RECIPE_DESCRIPTION = "linkDescription";
    public static final String RECIPE_LINK = "link";
    public static final String RECIPE_FAV = "favourite";

    // Boolean for distinguishing between receiving link from browser or saving it manually
    private boolean fromBrowser;

    // View Fields
    private EditText linkText;
    private EditText recipeLinkName;
    private ImageButton favouriteRecipe;
    private EditText shortDescription;

    // Field for Button
    private String isFavourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();

    }

    private void receiveIntent() {

        Intent intent = getIntent();

        // Setting apart intents
        // 1. Intent from browser
        // 2. Intent from AddReceivedLinkActivity

        // Intent from Browser
        if(intent!=null) {
            String action = intent.getAction();
            String type = intent.getType();
            if (Intent.ACTION_SEND.equals(action) && type != null) {
                if (type.equalsIgnoreCase("text/plain")) {
                    // Set Link to EditText
                    setLinkFromIntent(intent);
                    fromBrowser = true;

                }
            } else {
                if (intent.hasExtra(EXTRA_LINK_ID)) {
                    setTitle("Edit Recipe Link");
                    recipeLinkName.setText(intent.getStringExtra(EXTRA_LINK_NAME));
                    linkText.setText(intent.getStringExtra(EXTRA_LINK_LINK));
                    shortDescription.setText(intent.getStringExtra(EXTRA_LINK_DESCRIPTION));
                    isFavourite = intent.getStringExtra(EXTRA_LINK_FAV);
                    Log.d("Receiving isFav", isFavourite);
                }
            }
        }

    }
    // Setting Link from Intent to EditText
    private void setLinkFromIntent(Intent intent) {

        String link = intent.getStringExtra(Intent.EXTRA_TEXT);
        if(link!=null) {
            Log.d("Link", link);
            linkText.setText(link);
        }

    }
    // Init UI
    private void initUI() {

        setContentView(R.layout.activity_receive_link);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Recipe Link");

        linkText = findViewById(R.id.link_received);
        recipeLinkName = findViewById(R.id.recipe_name_link);
        favouriteRecipe = findViewById(R.id.favourite_button_link);
        shortDescription = findViewById(R.id.short_recipe_link_description);


        // Button OnClickListener
        favouriteRecipe.setOnClickListener(view -> toggleFavourite());
        // Init default value of button and default src image
        receiveIntent();
        setInitialFavouriteButton();

    }
    // Extra method for saving Recipe with SharedPreferences instead of Intent
    private void saveRecipeFromWeb() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_LINK, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(RECIPE_NAME, recipeLinkName.getText().toString());
        editor.putString(RECIPE_DESCRIPTION, shortDescription.getText().toString());
        editor.putString(RECIPE_LINK, linkText.getText().toString());
        editor.putString(RECIPE_FAV, isFavourite);

        // Saving shared preferences
        editor.apply();
        Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
        finish();
    }

    // Saving Link by sending Intent to RecipeLinkListActivity
    private void saveRecipe() {

        Intent data = new Intent();
        data.putExtra(EXTRA_LINK_NAME, recipeLinkName.getText().toString());
        data.putExtra(EXTRA_LINK_DESCRIPTION, shortDescription.getText().toString());
        data.putExtra(EXTRA_LINK_LINK, linkText.getText().toString());
        data.putExtra(EXTRA_LINK_FAV, isFavourite);
        Log.d("Saving isFavourite", isFavourite);

        int id = getIntent().getIntExtra(EXTRA_LINK_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_LINK_ID, id);
        }
        
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                if(fromBrowser) {
                    saveRecipeFromWeb();
                } else {
                    saveRecipe();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Creates Menu
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_recipe_menu, menu);
        return true;
    }

    // Toggling favourite button "on/off"
    private void toggleFavourite() {
        if(isFavourite == null) {
            isFavourite = "0";
        }
        if(isFavourite.equals("1")) {
            favouriteRecipe.setImageResource(R.drawable.ic_star_border_50);
            isFavourite = "0";
        }
        else {
            isFavourite = "1";
            favouriteRecipe.setImageResource(R.drawable.ic_star_50_green);
        }
    }

    // Initializes default value and image of button
    private void setInitialFavouriteButton() {
        if(isFavourite == null) {
            isFavourite = "0";
        } else if(isFavourite.equals("1")) {
            favouriteRecipe.setImageResource(R.drawable.ic_star_50_green);
        }
    }


}