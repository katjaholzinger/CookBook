package com.hwr.cookbook;

/**
 * Created by Sidney on 22.03.2018.
 */

public class Recipe {

    public String name;
    public Ingredient[] ingredients;
    public int defaultPortions;
    public String icon;
    public String description;

    public Recipe() {

    }

    public Recipe(String name, Ingredient[] ingredients, int defaultPortions, String icon, String description) {
        this.name = name;
        this.ingredients = ingredients;
        this.defaultPortions = defaultPortions;
        this.icon = icon;
        this.description = description;
    }

    public String getName() {
        return name;
    }
}
