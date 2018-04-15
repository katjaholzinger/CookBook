package com.hwr.cookbook;

import java.util.Date;

/**
 * Created by Sidney on 22.03.2018.
 */

public class RecipeMarker extends IdentifiableElement {

    public Recipe recipe;
    public int persons;
    public Date date;

    public RecipeMarker () {
        super();
    }

    public RecipeMarker (Recipe recipe, int persons, Date date) {
        super();
        this.recipe = recipe;
        this.persons = persons;
        this.date = date;
    }

}
