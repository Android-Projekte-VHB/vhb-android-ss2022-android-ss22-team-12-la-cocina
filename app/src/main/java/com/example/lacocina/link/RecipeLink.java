package com.example.lacocina.link;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipelink_table")
public class RecipeLink {

    private String link;
    private String title;
    private String isFavourite;
    private String shortDescription;

    @PrimaryKey(autoGenerate = true)
    private int id;

    public RecipeLink(String link, String title, String isFavourite, String shortDescription) {
        this.link = link;
        this.title = title;
        this.isFavourite = isFavourite;
        this.shortDescription = shortDescription;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(String isFavourite) {
        this.isFavourite = isFavourite;
    }

    public String getShortDescription() {
        return shortDescription;
    }


}
