package com.hwr.cookbook.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hwr.cookbook.Ingredient;
import com.hwr.cookbook.IngredientList;
import com.hwr.cookbook.R;
import com.hwr.cookbook.Recipe;

import java.util.ArrayList;

/**
 * Created by Thomas on 29.03.2018.
 *
 */

public class RecipeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        createLayouts();
    }

    private void createLayouts() {
        // Set Toolbar
        Recipe recipe = new Recipe();

        Toolbar toolBar = findViewById(R.id.toolbar);
        this.setSupportActionBar(toolBar);

        LinearLayout linearLayout = findViewById(R.id.linearLayoutRecipe);

        //set title
        TextView titleView = new TextView(this);
        titleView.setText(recipe.name);

        //set rating
        RatingBar ratingBar = new RatingBar(this);

        //add all to linear layout
        linearLayout.addView(titleView);
        linearLayout.addView(ratingBar);

        //set ingredients
        IngredientList ingredients = (IngredientList) recipe.ingredients;
        TextView ingredientView = new TextView(this);
        for(Object o: ingredients){
            Ingredient ingredient = (Ingredient) o;
            ingredientView.setText(ingredient.name);
            linearLayout.addView(ingredientView);
        }

        //set image
        ImageView imageView = new ImageView(this);


        //set description
        TextView descriptionView = new TextView(this);
        descriptionView.setText(recipe.description);

        //add views
        linearLayout.addView(imageView);
        linearLayout.addView(descriptionView );
    }
}


