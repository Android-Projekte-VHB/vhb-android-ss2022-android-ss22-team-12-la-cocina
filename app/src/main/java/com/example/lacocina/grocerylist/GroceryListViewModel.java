package com.example.lacocina.grocerylist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.lacocina.recipe.GroceryListRepository;

import java.util.List;

public class GroceryListViewModel extends AndroidViewModel {
    private GroceryListRepository groceryListRepository;
    private LiveData<List<GroceryList>> allLists;

    public GroceryListViewModel(@NonNull Application application) {
        super(application);
        groceryListRepository = new GroceryListRepository(application);
        allLists = groceryListRepository.getAllLists();
    }

    public void insert(GroceryList groceryList) {
        groceryListRepository.insert(groceryList);
    }
    public void update(GroceryList groceryList) {
        groceryListRepository.update(groceryList);
    }
    public void delete(GroceryList groceryList) {
        groceryListRepository.delete(groceryList);
    }

    public LiveData<List<GroceryList>> getAllLists() {
        return allLists;
    }
}
