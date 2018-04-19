package com.hwr.cookbook;

import java.util.List;

/**
 * Created by Sidney on 22.03.2018.
 */

public class Recipe extends IdentifiableElement{

    public IngredientList ingredients = new IngredientList();
    public int defaultPortions = 2;
    public String icon = "";
    public String description ="";
    public float rating = 3;
    public int raters = 0;

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
        this.rating = 3;
    }

}
