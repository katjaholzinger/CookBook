package com.hwr.cookbook;

import android.util.Log;

import org.json.*;

import java.util.ArrayList;
import java.util.List;

import static com.hwr.cookbook.UI.RecipeActivity.recipe;

/**
 * Created by Sidney on 22.03.2018.
 */

public class Recipe{

    public ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
    public String id;
    public String name;
    public int defaultPortions = 2;
    public String icon = "";
    public String description ="";
    public float rating = 3;
    public int raters = 0;

    public Recipe() {

    }

    public Recipe(String name, String id, ArrayList<Ingredient> ingredients, int defaultPortions, String icon, String description) {
        this.name = name;
        this.id = id;
        this.ingredients = ingredients;
        this.defaultPortions = defaultPortions;
        this.icon = icon;
        this.description = description;
        this.raters = 0;
        this.rating = 3;
    }
    public Recipe(String name, ArrayList ingredients, int defaultPortions, String icon, String description) {
        this.name = name;
        this.ingredients = ingredients;
        this.defaultPortions = defaultPortions;
        this.icon = icon;
        this.description = description;
        this.raters = 0;
        this.rating = 3;
    }


    public ArrayList normalizeIngredients(int defaultPortions) {
        //TODO normalize Ingredients without Ingredientlist
        return this.ingredients;
    }

    public static Recipe ObjectToRecipe(Object recipeString) {
        Log.d("Recipe", recipeString.toString());
        JSONObject json = null;
        try {
            json = new JSONObject(recipeString.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            json = new JSONObject();
        }
        Log.d("Recipe", json.toString());

        return new Recipe();
    }

    public Ingredient[] toIngredientArray() {
        Ingredient[] arrayIngredient = new Ingredient[recipe.ingredients.size()];
        int i = 0;
        for (Object o: this.ingredients) {
            Ingredient ingredient = (Ingredient)o;
            arrayIngredient[i] = ingredient;
            i++;
        }
        return arrayIngredient;
    }
}
