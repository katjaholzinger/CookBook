package com.hwr.cookbook;

/**
 * Created by Sidney on 22.03.2018.
 */

public class Book {

    public Recipe[] recipes;
    public String name;

    public Book () {

    }

    public Book (Recipe[] recipes) {
        this.recipes=recipes;
    }

    public Recipe[] getRecipes(){
        return recipes;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}
