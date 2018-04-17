package com.hwr.cookbook.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hwr.cookbook.Ingredient;
import com.hwr.cookbook.IngredientList;
import com.hwr.cookbook.R;
import com.hwr.cookbook.Recipe;

/**
 * Created by Thomas on 29.03.2018.
 *
 */

public class RecipeActivity extends AppCompatActivity {
    public static Recipe recipe = null;

    public RecipeActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createLayouts();
    }

    private void createLayouts() {

        if (recipe==null){
            setContentView(R.layout.activity_recipe_editable);
        } else {
            setContentView(R.layout.activity_recipe_show);
            setRecipe();
        }

        // set Toolbar
        Toolbar toolBar = findViewById(R.id.toolbar);
        this.setSupportActionBar(toolBar);

    }

    private void setRecipe() {
        //set title
        TextView recipeTitle = findViewById(R.id.RecipeTitle);
        recipeTitle.setText(recipe.name);

        //set rating
        RatingBar ratingBar = findViewById(R.id.RecipeRatingBar);
        ratingBar.setEnabled(false);
        ratingBar.setRating(recipe.rating);


        //set ingredients
        LinearLayout ingredientsLayout = findViewById(R.id.Ingredients);
        IngredientList ingredients = (IngredientList) recipe.ingredients;

        for(Object o: ingredients){
            TextView ingredientView = new TextView(this);
            Ingredient ingredient = (Ingredient) o;
            ingredientView.setText(ingredient.name);
            ingredientsLayout.addView(ingredientView);
        }

        //set description
        TextView descriptionText = findViewById(R.id.Description);
        descriptionText.setText(recipe.description);

    }



}


