package com.example.lacocina.recipe;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.lacocina.link.RecipeLink;
import com.example.lacocina.link.RecipeLinkDAO;

import java.util.List;

@SuppressWarnings("deprecation")
public class RecipeLinkRepository {

    private RecipeLinkDAO recipeLinkDAO;
    private LiveData<List<RecipeLink>> allRecipeLinks;

    public RecipeLinkRepository(Application application) {
        RecipeDatabase database = RecipeDatabase.getInstance(application);
        recipeLinkDAO = database.recipeLinkDAO();
        allRecipeLinks = recipeLinkDAO.getAllRecipeLinks();
    }

    public void insert(RecipeLink recipeLink) {
        new InsertRecipeLinkAsyncTask(recipeLinkDAO).execute(recipeLink);
    }
    public void update(RecipeLink recipeLink) {
        new UpdateRecipeLinkAsyncTask(recipeLinkDAO).execute(recipeLink);
    }
    public void delete(RecipeLink recipeLink) {
        new DeleteRecipeLinkAsyncTask(recipeLinkDAO).execute(recipeLink);
    }
    public void deleteAllRecipeLinks() {
        new DeleteAllRecipeLinkAsyncTask(recipeLinkDAO).execute();

    }

    public LiveData<List<RecipeLink>> getAllRecipeLinks() {
        return allRecipeLinks;
    }

    // Insert with AsyncTask
    private static class InsertRecipeLinkAsyncTask extends AsyncTask<RecipeLink, Void, Void> {
        private RecipeLinkDAO recipeLinkDAO;

        private InsertRecipeLinkAsyncTask(RecipeLinkDAO recipeLinkDAO) {
            this.recipeLinkDAO = recipeLinkDAO;
        }

        @Override
        protected Void doInBackground(RecipeLink...recipeLinks) {
            recipeLinkDAO.insert(recipeLinks[0]);
            return null;
        }
    }

    private static class UpdateRecipeLinkAsyncTask extends AsyncTask<RecipeLink, Void, Void> {
        private RecipeLinkDAO recipeLinkDAO;

        private UpdateRecipeLinkAsyncTask(RecipeLinkDAO recipeLinkDAO) {
            this.recipeLinkDAO = recipeLinkDAO;
        }

        @Override
        protected Void doInBackground(RecipeLink... recipeLinks) {
            recipeLinkDAO.update(recipeLinks[0]);
            return null;
        }
    }

    private static class DeleteRecipeLinkAsyncTask extends AsyncTask<RecipeLink, Void, Void> {
        private RecipeLinkDAO recipeLinkDAO;

        private DeleteRecipeLinkAsyncTask(RecipeLinkDAO recipeLinkDAO) {
            this.recipeLinkDAO = recipeLinkDAO;
        }

        @Override
        protected Void doInBackground(RecipeLink... recipeLinks) {
            recipeLinkDAO.delete(recipeLinks[0]);
            return null;
        }
    }

    private static class DeleteAllRecipeLinkAsyncTask extends AsyncTask<Void, Void, Void> {
        private RecipeLinkDAO recipeLinkDAO;

        private DeleteAllRecipeLinkAsyncTask(RecipeLinkDAO recipeLinkDAO) {
            this.recipeLinkDAO = recipeLinkDAO;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            recipeLinkDAO.deleteAllRecipeLinks();
            return null;
        }
    }

}

