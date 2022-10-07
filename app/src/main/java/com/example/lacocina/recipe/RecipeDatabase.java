package com.example.lacocina.recipe;



import android.content.Context;


import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.lacocina.grocerylist.GroceryList;
import com.example.lacocina.grocerylist.GroceryListDAO;
import com.example.lacocina.link.RecipeLink;
import com.example.lacocina.link.RecipeLinkDAO;

@Database(entities = {Recipe.class, RecipeLink.class, GroceryList.class}, version=7)
public abstract class RecipeDatabase extends RoomDatabase {

    private static RecipeDatabase instance;

    public abstract RecipeDAO recipeDAO();
    public abstract RecipeLinkDAO recipeLinkDAO();
    public abstract GroceryListDAO groceryListDAO();

    public static synchronized RecipeDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), RecipeDatabase.class, "recipe_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };


}
