package com.hwr.cookbook;

import java.util.List;
import java.util.UUID;

/**
 * Created by Sidney on 22.03.2018.
 */

public class Recipe {
    public String id;
    public String name;
    public List ingredients;
    public int defaultPortions;
    public String icon;
    public String description;
    public float rating;
    public int raters;

    public Recipe() {}

    public Recipe(String name, IngredientList ingredients, int defaultPortions, String icon, String description) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.ingredients = ingredients;
        this.defaultPortions = defaultPortions;
        this.icon = icon;
        this.description = description;
        this.raters = 0;
        this.rating = 0;
    }

    public String getName() {
        return name;
    }
}
