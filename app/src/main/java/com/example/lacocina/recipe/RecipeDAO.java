package com.example.lacocina.recipe;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RecipeDAO {


    @Insert
    void insert(Recipe recipe);

    @Update
    void update(Recipe recipe);

    @Delete
    void delete(Recipe recipe);

    @Query("DELETE FROM recipe_table")
    void deleteAllRecipes();

    // Shows favourite Recipes on top of the list
    @Query("SELECT * FROM recipe_table ORDER BY isFavourite DESC")
    LiveData<List<Recipe>> getAllNotes();
}
