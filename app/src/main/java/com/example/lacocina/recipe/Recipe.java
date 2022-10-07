package com.example.lacocina.recipe;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "recipe_table")
public class Recipe {

    private String recipeTitle;
    public String youtubeTutorialURL;
    public String description;
    public String ingredients;
    public String instruction;
    public String diet;
    public String isFavourite;
    public String imgURI;
    public int cookingTime;


    // Fields for Notes
    public String ingredientsNote;
    public String instructionNote;

    // Id
    @PrimaryKey(autoGenerate = true)
    private int id;

    public Recipe(String recipeTitle, String description, String ingredients, String instruction, String youtubeTutorialURL, String imgURI, String ingredientsNote, String instructionNote, String diet, String isFavourite, int cookingTime) {
        this.recipeTitle = recipeTitle;
        this.description = description;
        this.ingredients = ingredients;
        this.instruction = instruction;
        this.youtubeTutorialURL = youtubeTutorialURL;
        this.imgURI = imgURI;
        this.instructionNote = instructionNote;
        this.ingredientsNote = ingredientsNote;
        this.diet = diet;
        this.isFavourite = isFavourite;
        this.cookingTime = cookingTime;
    }



    @Ignore
    public Recipe(String recipeTitle, String description, String ingredients, String instruction, String youtubeTutorialURL, String imgURI) {
        this.recipeTitle = recipeTitle;
        this.description = description;
        this.ingredients = ingredients;
        this.instruction = instruction;
        this.youtubeTutorialURL = youtubeTutorialURL;
        this.imgURI = imgURI;
    }

    @Ignore
    public Recipe(String recipeTitle, String description) {
        this.recipeTitle = recipeTitle;
        this.description = description;
    }


    @Ignore
    public Recipe(String recipeTitle, String youtubeTutorialURL, String description, String ingredients, String instruction, String diet, String ingredientsNote, String instructionNote) {

        this.recipeTitle = recipeTitle;
        this.youtubeTutorialURL = youtubeTutorialURL;
        this.description = description;
        this.ingredients = ingredients;
        this.instruction = instruction;
        this.diet = diet;
        this.ingredientsNote = ingredientsNote;
        this.instructionNote = instructionNote;


    }
    public int getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(int time) {
        cookingTime = time;
    }

    public String getImgURI() {
        return imgURI;
    }

    public void setImgURI(String imgURI) {
        this.imgURI = imgURI;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() { return this.id; }

    public String getIsFavourite() {
        return isFavourite;
    }

    public void setFavourite(String favourite) {
        isFavourite = favourite;
    }

    public String getYoutubeTutorialURL() {
        return youtubeTutorialURL;
    }

    public void setYoutubeTutorialURL(String youtubeTutorialURL) {
        this.youtubeTutorialURL = youtubeTutorialURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getIngredientsNote() {
        return ingredientsNote;
    }

    public void setIngredientsNote(String ingredientsNote) {
        this.ingredientsNote = ingredientsNote;
    }

    public String getInstructionNote() {
        return instructionNote;
    }

    public void setInstructionNote(String instructionNote) {
        this.instructionNote = instructionNote;
    }

    public String getRecipeTitle() {
        return recipeTitle;
    }

    public void setRecipeTitle(String recipeTitle) {
        this.recipeTitle = recipeTitle;
    }
}
