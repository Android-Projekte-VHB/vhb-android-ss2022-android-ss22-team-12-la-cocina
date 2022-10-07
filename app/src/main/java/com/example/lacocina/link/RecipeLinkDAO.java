package com.example.lacocina.link;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;


@Dao
public interface RecipeLinkDAO {



    @Insert
    void insert(RecipeLink recipeLink);

    @Update
    void update(RecipeLink recipeLink);

    @Delete
    void delete(RecipeLink recipeLink);

    @Query("DELETE FROM recipelink_table")
    void deleteAllRecipeLinks();

    // Shows favourite Recipes on top of the list
    @Query("SELECT * FROM recipelink_table ORDER BY isFavourite DESC")
    LiveData<List<RecipeLink>> getAllRecipeLinks();
}

