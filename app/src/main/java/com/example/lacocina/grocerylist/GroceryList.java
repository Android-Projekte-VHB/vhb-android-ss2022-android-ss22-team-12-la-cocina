package com.example.lacocina.grocerylist;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "grocerylist_table")
public class GroceryList {

    private String listName;
    private String listItems;
    private String isFavourite;

    @PrimaryKey(autoGenerate = true)
    private int id;


    public GroceryList(String listName, String listItems, String isFavourite) {
        this.listName = listName;
        this.listItems = listItems;
        this.isFavourite = isFavourite;
    }

    public String getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(String isFavourite) {
        this.isFavourite = isFavourite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getListItems() {
        return listItems;
    }

    public void setListItems(String listItems) {
        this.listItems = listItems;
    }
}
