package com.hwr.cookbook;

import java.util.List;

/**
 * Created by Sidney on 22.03.2018.
 */

public class Recipe extends IdentifiableElement{

    public List ingredients;
    public int defaultPortions;
    public String icon;
    public String description;
    public float rating;
    public int raters;

    public Recipe() {
        super();
    }

    public Recipe(String name, IngredientList ingredients, int defaultPortions, String icon, String description) {
        super();
        this.name = name;
        this.ingredients = ingredients;
        this.defaultPortions = defaultPortions;
        this.icon = icon;
        this.description = description;
        this.raters = 0;
        this.rating = 0;
    }

}
