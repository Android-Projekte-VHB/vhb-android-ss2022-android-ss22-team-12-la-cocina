package com.example.lacocina.recipe;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

@SuppressWarnings("deprecation")
public class RecipeRepository {

    private RecipeDAO recipeDAO;
    private LiveData<List<Recipe>> allRecipes;

    public RecipeRepository(Application application) {
        RecipeDatabase database = RecipeDatabase.getInstance(application);
        recipeDAO = database.recipeDAO();
        allRecipes = recipeDAO.getAllNotes();
    }

    public void insert(Recipe recipe) {
        new InsertRecipeAsyncTask(recipeDAO).execute(recipe);
    }
    public void update(Recipe recipe) {
        new UpdateRecipeAsyncTask(recipeDAO).execute(recipe);
    }
    public void delete(Recipe recipe) {
        new DeleteRecipeAsyncTask(recipeDAO).execute(recipe);
    }
    public void deleteAllRecipes() {
        new DeleteAllRecipesAsyncTask(recipeDAO).execute();

    }

    public LiveData<List<Recipe>> getAllRecipeLinks() {
        return allRecipes;
    }

    // Insert with AsyncTask
    private static class InsertRecipeAsyncTask extends AsyncTask<Recipe, Void, Void> {
        private RecipeDAO recipeDAO;

        private InsertRecipeAsyncTask(RecipeDAO recipeDAO) {
            this.recipeDAO = recipeDAO;
        }

        @Override
        protected Void doInBackground(Recipe... recipes) {
            recipeDAO.insert(recipes[0]);
            return null;
        }
    }

    private static class UpdateRecipeAsyncTask extends AsyncTask<Recipe, Void, Void> {
        private RecipeDAO recipeDAO;

        private UpdateRecipeAsyncTask(RecipeDAO recipeDAO) {
            this.recipeDAO = recipeDAO;
        }

        @Override
        protected Void doInBackground(Recipe... recipes) {
            recipeDAO.update(recipes[0]);
            return null;
        }
    }

    private static class DeleteRecipeAsyncTask extends AsyncTask<Recipe, Void, Void> {
        private RecipeDAO recipeDAO;

        private DeleteRecipeAsyncTask(RecipeDAO recipeDAO) {
            this.recipeDAO = recipeDAO;
        }

        @Override
        protected Void doInBackground(Recipe... recipes) {
            recipeDAO.delete(recipes[0]);
            return null;
        }
    }

    private static class DeleteAllRecipesAsyncTask extends AsyncTask<Void, Void, Void> {
        private RecipeDAO recipeDAO;

        private DeleteAllRecipesAsyncTask(RecipeDAO recipeDAO) {
            this.recipeDAO = recipeDAO;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            recipeDAO.deleteAllRecipes();
            return null;
        }
    }
}

