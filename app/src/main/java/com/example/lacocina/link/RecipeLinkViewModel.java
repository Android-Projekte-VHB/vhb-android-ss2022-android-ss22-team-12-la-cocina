package com.example.lacocina.link;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.lacocina.recipe.RecipeLinkRepository;

import java.util.List;

public class RecipeLinkViewModel extends AndroidViewModel {

    private RecipeLinkRepository repository;
    private LiveData<List<RecipeLink>> allRecipeLinks;

    public RecipeLinkViewModel(@NonNull Application application) {
        super(application);
        repository = new RecipeLinkRepository(application);
        allRecipeLinks = repository.getAllRecipeLinks();
    }

    public void insert(RecipeLink recipeLink) {
        repository.insert(recipeLink);
    }

    public void update(RecipeLink recipeLink) {
        repository.update(recipeLink);
    }

    public void delete(RecipeLink recipeLink) {
        repository.delete(recipeLink);
    }

    public void deleteAllRecipeLinks() {
        repository.deleteAllRecipeLinks();
    }

    public LiveData<List<RecipeLink>> getAllRecipeLinks() {
        return allRecipeLinks;
    }
}