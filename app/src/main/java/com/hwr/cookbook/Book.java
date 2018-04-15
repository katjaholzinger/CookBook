package com.hwr.cookbook;

import java.util.UUID;

/**
 * Created by Sidney on 22.03.2018.
 */

public class Book extends IdentifiableElement {

    public Recipe[] recipes;

    public Book () {
        super();
    }

    public Book (Recipe[] recipes) {
        super();
        this.recipes=recipes;
    }

    public Recipe[] getRecipes(){
        return recipes;
    }

}
