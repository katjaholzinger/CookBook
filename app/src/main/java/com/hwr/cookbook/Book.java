package com.hwr.cookbook;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Sidney on 22.03.2018.
 */

public class Book {
    public String name;
    public String id;
    public ArrayList<String> recipes = new ArrayList<String>();

    public Book () {

    }

    public Book (String name, ArrayList<String> recipes) {
        this.name = name;
        this.recipes=recipes;
    }
 public ArrayList<Recipe> getFullRecipes() {
        ArrayList<Recipe> recipeArrayList = new ArrayList<>();
     for (String rId: recipes
             ) {
        Recipe r = Database.findRecipe(rId);
        if (r != null) {
            recipeArrayList.add(r);
        }
     }
     return recipeArrayList;
 }

}
