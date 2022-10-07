package com.example.lacocina.recipe;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.lacocina.grocerylist.GroceryList;
import com.example.lacocina.grocerylist.GroceryListDAO;

import java.util.List;
@SuppressWarnings("deprecation")
public class GroceryListRepository {

    private GroceryListDAO groceryListDAO;
    private LiveData<List<GroceryList>> allLists;


    public GroceryListRepository(Application application) {
        RecipeDatabase recipeDatabase = RecipeDatabase.getInstance(application);
        groceryListDAO = recipeDatabase.groceryListDAO();
        allLists = groceryListDAO.getAllGroceryLists();
    }

    public void insert(GroceryList groceryList) {
        new InsertGroceryListAsyncTask(groceryListDAO).execute(groceryList);

    }

    public void delete(GroceryList groceryList) {
        new DeleteGroceryListAsyncTask(groceryListDAO).execute(groceryList);
    }

    public void update(GroceryList groceryList) {
        new UpdateGroceryListAsyncTask(groceryListDAO).execute(groceryList);
    }

    public LiveData<List<GroceryList>> getAllLists() {
        return allLists;
    }

    private static class InsertGroceryListAsyncTask extends AsyncTask<GroceryList, Void, Void> {
        private GroceryListDAO groceryListDAO;

        private InsertGroceryListAsyncTask(GroceryListDAO groceryListDAO) {
            this.groceryListDAO = groceryListDAO;
        }

        @Override
        protected Void doInBackground(GroceryList... groceryLists) {
            groceryListDAO.insert(groceryLists[0]);
            return null;
        }
    }

    private static class DeleteGroceryListAsyncTask extends AsyncTask<GroceryList, Void, Void> {
        private GroceryListDAO groceryListDAO;

        private DeleteGroceryListAsyncTask(GroceryListDAO groceryListDAO) {
            this.groceryListDAO = groceryListDAO;
        }

        @Override
        protected Void doInBackground(GroceryList... groceryLists) {
            groceryListDAO.delete(groceryLists[0]);
            return null;
        }
    }

    private static class UpdateGroceryListAsyncTask extends AsyncTask<GroceryList, Void, Void> {
        private GroceryListDAO groceryListDAO;

        private UpdateGroceryListAsyncTask(GroceryListDAO groceryListDAO) {
            this.groceryListDAO = groceryListDAO;
        }

        @Override
        protected Void doInBackground(GroceryList... groceryLists) {
            groceryListDAO.update(groceryLists[0]);
            return null;
        }
    }
}
