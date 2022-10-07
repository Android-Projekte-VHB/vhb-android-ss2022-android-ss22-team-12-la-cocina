package com.example.lacocina.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lacocina.R;
import com.example.lacocina.bottom_sheets.DietBottomSheet;
import com.example.lacocina.bottom_sheets.NotesBottomSheet;
import com.example.lacocina.bottom_sheets.BottomSheet;
import com.example.lacocina.bottom_sheets.TimeBottomSheet;
import com.example.lacocina.bottom_sheets.VideoBottomSheet;
import com.example.lacocina.dialogs.AddYoutubeLinkDialog;
import com.squareup.picasso.Picasso;

import java.util.Objects;

@SuppressWarnings({"FieldCanBeLocal","deprecation"})
public class RecipeActivity extends AppCompatActivity implements BottomSheet.BottomSheetListener, NotesBottomSheet.NoteSheetListener, DietBottomSheet.dietListener, TimeBottomSheet.TimeListener, AddYoutubeLinkDialog.AddYoutubeLinkDialogListener, VideoBottomSheet.VideoListener {

    // Intent Keys
    public static final String EXTRA_ID =
            "com.example.lacocina.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.example.lacocina.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.example.lacocina.EXTRA_DESCRIPTION";
    public static final String EXTRA_INGREDIENTS =
            "com.example.lacocina.EXTRA_INGREDIENTS";
    public static final String EXTRA_INSTRUCTION =
            "com.example.lacocina.EXTRA_INSTRUCTION";
    public static final String EXTRA_LINK =
            "com.example.lacocina.EXTRA_LINK";
    public static final String EXTRA_IMG =
            "com.example.lacocina.EXTRA_IMG";
    public static final String EXTRA_INSTRUCTION_NOTE =
            "com.example.lacocina.EXTRA_INSTRUCTION_NOTE";
    public static final String EXTRA_INGREDIENTS_NOTE =
            "com.example.lacocina.EXTRA_INGREDIENTS_NOTE";
    public static final String EXTRA_DIET =
            "com.example.lacocina.EXTRA_DIET";
    public static final String EXTRA_FAV =
            "com.example.lacocina.EXTRA_FAV";
    public static final String EXTRA_TIME =
            "com.example.lacocina.EXTRA_TIME";

    // String constants for shared preferences
    public static final String SHARED_PREF_GROCERY_LIST = "groceryList";
    public static final String SHARED_RECIPE_NAME = "recipeName";
    public static final String SHARED_LIST_ITEMS = "listItems";



    //Fragment Bundle
    private Bundle fragmentBundle;

    // Fields for Picking Image
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;

    //Fields for EditText and Buttons
    private EditText recipeName;
    private ImageButton videoButton;
    private ImageButton descriptionButton;
    private ImageButton ingredientsButton;
    private ImageButton instructionButton;
    private ImageView recipeImage;
    private ImageButton shareRecipeButton;
    private Button addYoutubeLinkButton;

    // New Buttons for additional Recipe-Information
    private ImageButton dietButton;
    private ImageButton favButton;
    private ImageButton timeButton;


    // Fields for fragmentData
    private String descriptionText;
    private String instructionText;
    private String ingredientsText;

    private String youtubeLink;
    private String recipeTitle;
    private String imgURI;

    private String ingredientsNote;
    private String instructionNote;

    private String recipeDiet;
    private String isFavourite;
    private String cookingTime;

    //TextView
    private TextView dietText;
    private TextView timerNumber;

    // Bottom Sheet


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        initUI();
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_home);
        setTitle("Add Recipe");

        // Intent for Receiving data from recipe database object
        Intent intent = getIntent();

        // Bundle for passing data to fragments
        fragmentBundle = new Bundle();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            recipeName.setText(intent.getStringExtra(EXTRA_TITLE));
            descriptionText = intent.getStringExtra(EXTRA_DESCRIPTION);
            ingredientsText = intent.getStringExtra(EXTRA_INGREDIENTS);
            instructionText = intent.getStringExtra(EXTRA_INSTRUCTION);
            youtubeLink = intent.getStringExtra(EXTRA_LINK);
            imgURI = intent.getStringExtra(EXTRA_IMG);
            ingredientsNote = intent.getStringExtra(EXTRA_INGREDIENTS_NOTE);
            instructionNote = intent.getStringExtra(EXTRA_INSTRUCTION_NOTE);
            recipeDiet = intent.getStringExtra(EXTRA_DIET);
            isFavourite = intent.getStringExtra(EXTRA_FAV);
            cookingTime = intent.getStringExtra(EXTRA_TIME);


            if(imgURI != null) {
                mImageUri = Uri.parse(imgURI);
                Picasso.get()
                        .load(mImageUri)
                        .into(recipeImage);
            }

            Picasso.get().setLoggingEnabled(true);

            timerNumber.setText(cookingTime);

            setDietValues(recipeDiet);

            // Setting for favourite
            setFavourite();


            //Setting time
            if(cookingTime == null) {
                cookingTime = String.valueOf(1);
            }






            fragmentBundle.putString("editInstructionText", instructionText);
            fragmentBundle.putString("editIngredientsText", ingredientsText);
            fragmentBundle.putString("editDescriptionText", descriptionText);
            fragmentBundle.putString("instructionNotes", instructionNote);
            fragmentBundle.putString("ingredientNotes", ingredientsNote);
            fragmentBundle.putString("link", youtubeLink);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_recipe_menu, menu);
        return true;
    }

    public void initUI() {
        setContentView(R.layout.activity_recipe);

        recipeName = findViewById(R.id.recipe_name);
        videoButton = findViewById(R.id.youtube_tutorial_button);
        descriptionButton = findViewById(R.id.description_button);
        ingredientsButton = findViewById(R.id.ingredients_button);
        instructionButton = findViewById(R.id.instruction_button);
        recipeImage = findViewById(R.id.recipe_image);
        shareRecipeButton = findViewById(R.id.share_button);
        timeButton = findViewById(R.id.timer_button);


        //Side Buttons
        dietButton = findViewById(R.id.diet_button);
        favButton = findViewById(R.id.favourite_button);

        //Link button
        addYoutubeLinkButton = findViewById(R.id.add_yt_link_button);

        //TextView
        dietText = findViewById(R.id.diet_button_text);
        timerNumber = findViewById(R.id.timer_number);


        // Setting click listener to every button
        favButton.setOnClickListener(view -> toggleFavourite());

        dietButton.setOnClickListener(view -> {
            DietBottomSheet dietBottomSheet = new DietBottomSheet();
            dietBottomSheet.show(getSupportFragmentManager(), "dietBottomSheet");
        });


        descriptionButton.setOnClickListener(view -> {

            BottomSheet bottomSheet = new BottomSheet();
            fragmentBundle.putString("title","Description");

            // Put temporarily saved description string after saving
            if(!Temp.tempDescription.isEmpty()) {
                fragmentBundle.putString("editDescriptionText",Temp.tempDescription);
            }
            bottomSheet.setArguments(fragmentBundle);
            bottomSheet.show(getSupportFragmentManager(), "testBottomSheet");
        });

        ingredientsButton.setOnClickListener(view -> {

            BottomSheet bottomSheet = new BottomSheet();
            fragmentBundle.putString("title","Ingredients");

            // Put temporarily saved description string after saving
            if(!Temp.tempIngredients.isEmpty()) {
                fragmentBundle.putString("editIngredientsText",Temp.tempIngredients);
            }
            // Put temporarily saved instruction note after saving
            if(!Temp.tempIngredientsNote.isEmpty()) {
                fragmentBundle.putString("ingredientNotes", Temp.tempIngredientsNote);
            }
            bottomSheet.setArguments(fragmentBundle);
            bottomSheet.show(getSupportFragmentManager(), "testBottomSheet");
        });

        instructionButton.setOnClickListener(view -> {
            BottomSheet bottomSheet = new BottomSheet();
            fragmentBundle.putString("title","Instruction");
            // Put temporarily saved description string after saving
            if(!Temp.tempInstruction.isEmpty()) {
                fragmentBundle.putString("editInstructionText",Temp.tempInstruction);
            }
            // Put temporarily saved instruction note after saving
            if(!Temp.tempInstructionNote.isEmpty()) {
                fragmentBundle.putString("instructionNotes", Temp.tempInstructionNote);
            }
            bottomSheet.setArguments(fragmentBundle);
            bottomSheet.show(getSupportFragmentManager(), "testBottomSheet");
        });

        videoButton.setOnClickListener(view -> {

            if(youtubeLink != null && !youtubeLink.isEmpty() && youtubeLink.length() > 17) {
                VideoBottomSheet videoBottomSheet = new VideoBottomSheet();
                videoBottomSheet.setArguments(fragmentBundle);
                videoBottomSheet.show(getSupportFragmentManager(), "videoBottomSheet");
            } else if(youtubeLink.length() < 17) {
                Toast.makeText(this, "Invalid YouTube-Link!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Please add a YouTube-Link to your recipe", Toast.LENGTH_LONG).show();
            }


        });


        shareRecipeButton.setOnClickListener(view -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT, youtubeLink + "\n\n" + recipeName.getText().toString() + "\n\n" + descriptionText + "\n\n" +
                    ingredientsText + "\n\n" + instructionText);
            startActivity(Intent.createChooser(sendIntent, "Share via"));
        });

        recipeImage.setOnClickListener(view -> openImageChoice());

        timeButton.setOnClickListener(view -> {
            TimeBottomSheet timeBottomSheet = new TimeBottomSheet();
            timeBottomSheet.show(getSupportFragmentManager(), "timeBottomSheet");
        });

        addYoutubeLinkButton.setOnClickListener(view -> openDialog());

        initFavouriteButton();

    }

    // Method for receiving text from description, ingredients and instruction
    @Override
    public void onButtonClicked(String title, String sheetText) {
        if (title.equals("Description")) {
            descriptionText = sheetText;
            Temp.tempDescription = sheetText;

        } else if(title.equals("Ingredients")) {
            ingredientsText = sheetText;
            Temp.tempIngredients = sheetText;

        } else if(title.equals("Instruction")) {
            instructionText = sheetText;
            Temp.tempInstruction = sheetText;

        }

    }

    // Save items in ingredients as shared preferences to grocery list
    @Override
    public void saveToGroceryList(String title, String text) {
        if(title.equals("Ingredients")) {
            String groceryListName = recipeName.getText().toString();
            String groceryItems = text;

            SharedPreferences listPreference = getSharedPreferences(SHARED_PREF_GROCERY_LIST, MODE_PRIVATE);
            SharedPreferences.Editor editor = listPreference.edit();
            editor.putString(SHARED_RECIPE_NAME, groceryListName);
            editor.putString(SHARED_LIST_ITEMS, groceryItems);

            editor.apply();
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();

        }
    }


    // Open Chooser, in which the User can choose the source to pick a suitable image from
    private void openImageChoice() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    // Get recipe Name
    private String getRecipeName() {
        recipeTitle = recipeName.getText().toString();
        return recipeTitle;
    }

    // Saving recipe and send intent to RecipeListActivity
    private void saveRecipe() {


        Intent data = new Intent();
        String recipeTitleFinal = getRecipeName();
        data.putExtra(EXTRA_TITLE, recipeTitleFinal);
        data.putExtra(EXTRA_DESCRIPTION, descriptionText);
        data.putExtra(EXTRA_INGREDIENTS, ingredientsText);
        data.putExtra(EXTRA_INSTRUCTION, instructionText);
        data.putExtra(EXTRA_LINK, youtubeLink);
        data.putExtra(EXTRA_INSTRUCTION_NOTE, instructionNote);
        data.putExtra(EXTRA_INGREDIENTS_NOTE, ingredientsNote);
        data.putExtra(EXTRA_DIET, recipeDiet);
        data.putExtra(EXTRA_FAV, isFavourite);

        if(cookingTime == null) {
            cookingTime = "0";
        }

        // Convert integer cooking value to String
        String finalCookingTime = cookingTime;
        Log.d("saveRecipe", finalCookingTime);
        data.putExtra(EXTRA_TIME, finalCookingTime);


        // Convert uri object to string object
        if(mImageUri != null) {
            String newURI = mImageUri.toString();
            Log.d("URI", newURI);
            data.putExtra(EXTRA_IMG, newURI);
        }


        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);

        // User feedback whether recipe is saved
        Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
        finish();
    }

    // Method for receiving the image uri and loading it into the ImageView
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                final ContentResolver resolver = getContentResolver();
                resolver.takePersistableUriPermission(mImageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }

            Picasso.get().load(mImageUri).into(recipeImage);
        }
    }

    // Saving add/edit recipe if note item is clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                Log.d("Save Recipe:","Saved!");
                saveRecipe();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Receiving notes, distinguishing between ingredient notes and instruction notes
    @Override
    public void saveNote(String note, String title) {
        if(title.equals("Ingredients")) {
            ingredientsNote = note;
            Temp.tempIngredientsNote = note;
            Log.d("Ingredients Note saved", ingredientsNote);
        }
        else if(title.equals("Instruction")) {
            instructionNote = note;
            Temp.tempInstructionNote = note;
            Log.d("Instruction Note saved", instructionNote);
        }

    }

    // Method for Updating UI: Text and Image
    @Override
    public void setDiet(String diet) {

        dietText.setText(diet);
        if(diet.equals("VEGAN"))
            dietButton.setImageResource(R.drawable.ic_plant);
        else if(diet.equals("VEGGIE")) {
            dietButton.setImageResource(R.drawable.ic_carrot_veggie);
        }
        else if(diet.equals("MEAT")) {
            dietButton.setImageResource(R.drawable.ic_meat);
        }
        else if(diet.equals("PESC")) {
            dietButton.setImageResource(R.drawable.ic_fish);
        }

        //Set diet
        recipeDiet = diet;
    }

    // Toggling fav button "on/off"
    private void toggleFavourite() {
        if(isFavourite == null) {
            isFavourite = "0";
        }
        if(isFavourite.equals("1")) {
            favButton.setImageResource(R.drawable.ic_star_border);
            isFavourite = "0";
        }
        else {
            isFavourite = "1";
            favButton.setImageResource(R.drawable.ic_star);
        }
    }
    // Init favouriteButton value and its image source
    private void initFavouriteButton() {
        if(isFavourite == null || isFavourite.equals("0")) {
            favButton.setImageResource(R.drawable.ic_star_border);
        } else {
            favButton.setImageResource(R.drawable.ic_star);
        }
    }

    // Receiving Time Values from TimeBottomSheet
    @Override
    public void setTime(int time) {
        cookingTime = String.valueOf(time);
        timerNumber.setText(cookingTime);

        if(cookingTime == null) {
            Log.d("cookingTime", "is Null");
        }
        else {
            Log.d("cookingTime", cookingTime);
        }
    }

    // Handling ACTION_OPEN_DOCUMENT Permission, e.g. handling permission to open gallery/folders to pick images for recipe
    @Override
    public void onRequestPermissionsResult (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //reload my activity with permission granted or use the features that required the permission

                } else {
                    Toast.makeText(this, "No Permission", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    // Methods for setting up values, strings and icons edit and adding an recipe
    private void setDietValues(String recipeDiet) {
        if(recipeDiet == null) {
            dietButton.setImageResource(R.drawable.ic_plant);
            dietText.setText("Diet");
        }
        else if(recipeDiet.equals("VEGAN")){
            dietText.setText(recipeDiet);
            dietButton.setImageResource(R.drawable.ic_plant);

        }
        else if(recipeDiet.equals("MEAT")) {
            dietText.setText(recipeDiet);
            dietButton.setImageResource(R.drawable.ic_meat);
        }
        else if(recipeDiet.equals("VEGGIE")) {
            dietText.setText(recipeDiet);
            dietButton.setImageResource(R.drawable.ic_carrot_veggie);
        }
        else if(recipeDiet.equals("PESCE")) {
            dietText.setText(recipeDiet);
            dietButton.setImageResource(R.drawable.ic_fish);
        }
    }

    // Setting favourite Button according to its current value
    private void setFavourite() {

        if(isFavourite == null) {
            isFavourite = "0";
        }

        if(isFavourite.equals("1")) {
            favButton.setImageResource(R.drawable.ic_star);
        }
    }

    // Dialog for Pasting Youtube Link to Tutorial
    public void openDialog() {
        AddYoutubeLinkDialog youtubeDialog = new AddYoutubeLinkDialog();
        youtubeDialog.show(getSupportFragmentManager(), "add link dialog");
    }


    // Sending Link to VideoBottomSheet to View Youtube Video
    @Override
    public void setLink(String dialogYoutubeLink) {
        youtubeLink = dialogYoutubeLink;
        if(!youtubeLink.isEmpty()) {
            fragmentBundle.putString("link", youtubeLink);
        } else {
            Toast.makeText(this, "Please add a YouTube-Link to your recipe", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void delete() {
        youtubeLink = "";
    }
}