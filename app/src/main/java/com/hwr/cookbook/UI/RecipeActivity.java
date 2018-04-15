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
    private EditText descriptionText;
    private EditText recipeTitle;
    private RatingBar ratingBar;
    private LinearLayout ingredientsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        createLayouts();
    }

    private void createLayouts() {
        // set Toolbar
        Toolbar toolBar = findViewById(R.id.toolbar);
        this.setSupportActionBar(toolBar);

        // example Ingredient
        IngredientList ing = new IngredientList();
        ing.add(new Ingredient("Salz", 5, "Teelöffel" ));
        ing.add(new Ingredient("Wasser", 3, "Liter" ));
        ing.add(new Ingredient("Spaghetti", 500, "Gramm" ));
        ing.normalize(4);
        Recipe recipe = new Recipe("Spaghetti", ing, 4, "pasta", "Wasser mit Salz zum kochen bringen. Wenn das Wasser kocht, die Spaghettis dazugeben. Nach 8 Minuten das Wasser abgießen und die Nudeln abschrecken.");

        // find Views
        recipeTitle = findViewById(R.id.RecipeTitle);
        ratingBar = findViewById(R.id.RecipeRatingBar);
        ingredientsLayout = findViewById(R.id.Ingredients);
        descriptionText = findViewById(R.id.Description);

        setRecipe(recipe);
    }

    private void setRecipe(Recipe recipe) {
        //set title
        recipeTitle.setText(recipe.name);

        //set rating
        ratingBar.setRating(recipe.rating);

        //set ingredients
        IngredientList ingredients = (IngredientList) recipe.ingredients;

        for(Object o: ingredients){
            TextView ingredientView = new TextView(this);
            Ingredient ingredient = (Ingredient) o;
            ingredientView.setText(ingredient.name);
            ingredientsLayout.addView(ingredientView);
        }

        //set description
        descriptionText.setText(recipe.description);

        setEnable();
    }

    private void setEnable(){
        ratingBar.setEnabled(true);
        recipeTitle.setEnabled(true);
        descriptionText.setEnabled(true);
    }

    private void setDisable(){
        ratingBar.setEnabled(false);
        recipeTitle.setEnabled(false);
        descriptionText.setEnabled(false);
    }


}


