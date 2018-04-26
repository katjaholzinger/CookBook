package com.hwr.cookbook;

import android.util.Log;

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
 public ArrayList<Recipe> getFullRecipes(ArrayList<Recipe> recipeList) {
        ArrayList<Recipe> recipeArrayList = new ArrayList<>();
        Recipe recipe = null;
     for (String rId: this.recipes
             ) {
         for (Recipe r: recipeList) {
             if (r.id.equals(rId)) {
                 recipe = r;
             }
         }

        if (recipe != null) {
            recipeArrayList.add(recipe);
            Log.d("Book", "Add recipe");
            recipe = null;
        }
     }
     return recipeArrayList;
 }

}
