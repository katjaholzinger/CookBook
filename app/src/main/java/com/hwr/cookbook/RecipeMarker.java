package com.hwr.cookbook;

import java.util.Date;

/**
 * Created by Sidney on 22.03.2018.
 */

public class RecipeMarker {

    public Recipe recipe;
    public int persons;
    public Date date;

    public RecipeMarker () {

    }

    public RecipeMarker (Recipe recipe, int persons, Date date) {
        this.recipe = recipe;
        this.persons = persons;
        this.date = date;
    }

}
