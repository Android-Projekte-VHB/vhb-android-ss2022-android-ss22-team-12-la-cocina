package com.example.lacocina.grocerylist;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface GroceryListDAO {

    @Insert
    void insert(GroceryList groceryList);

    @Delete
    void delete(GroceryList groceryList);

    @Update
    void update(GroceryList groceryList);

    @Query("SELECT * FROM grocerylist_table ORDER BY isFavourite DESC")
    LiveData<List<GroceryList>> getAllGroceryLists();
}
