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
    public ArrayList<String> recipes = new ArrayList<>();

    public Book() {

    }

    public Book(String name, ArrayList<String> recipes) {
        this.name = name;
        this.recipes = recipes;
    }

    public ArrayList<Recipe> getFullRecipes() {
        ArrayList<Recipe> recipeList = Database.getRecipeList();
        ArrayList<Recipe> recipeArrayList = new ArrayList<>();
        Recipe recipe = null;
        if (recipes.size() == 0) {
            recipeArrayList = null;
        } else {
            for (String rId : this.recipes) {

                for (Recipe r : recipeList) {
                    if (r.id != null) {
                        if (r.id.equals(rId)) {
                            recipe = r;
                            break;
                        }
                    }
                }

                if (recipe != null) {
                    recipeArrayList.add(recipe);
                    recipe = null;
                }
            }
        }

        return recipeArrayList;
    }

    public String toString() {
        return name;
    }

}
