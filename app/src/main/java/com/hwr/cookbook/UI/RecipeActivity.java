package com.hwr.cookbook.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.hwr.cookbook.Book;
import com.hwr.cookbook.Database;
import com.hwr.cookbook.Ingredient;
import com.hwr.cookbook.R;
import com.hwr.cookbook.Recipe;
import com.hwr.cookbook.RecipeMarker;

import java.util.ArrayList;

/**
 * Created by Thomas on 29.03.2018.
 */

public class RecipeActivity extends AppCompatActivity implements View.OnClickListener {
    public static Recipe recipe = null;
    private boolean isEditAble;
    private boolean newRecipe;
    private boolean isFABOpen;


    public RecipeActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isEditAble = (recipe == null);
        newRecipe = (recipe == null);

        createLayouts();
    }

    private void createLayouts() {

        if (isEditAble) {
            recipe = new Recipe();
            setContentView(R.layout.activity_recipe_editable);
            addEditButtons();
        } else {
            setContentView(R.layout.activity_recipe_show);
            setRecipe();
            addPortionsButtons();
            createFloatActionButton();
        }

        // set Toolbar
        Toolbar toolBar = findViewById(R.id.toolbar);
        this.setSupportActionBar(toolBar);
    }

    private void addEditButtons() {
        ImageButton addIngredient = findViewById(R.id.ButtonAddIngredient);
        FloatingActionButton saveButton = findViewById(R.id.fabSave);

        saveButton.setOnClickListener(this);
        addIngredient.setOnClickListener(this);
    }

    private void setRecipe() {
        //set title
        TextView recipeTitle = findViewById(R.id.RecipeTitle);
        recipeTitle.setText(recipe.name);

        //set rating
        RatingBar ratingBar = findViewById(R.id.RecipeRatingBar);
        ratingBar.setEnabled(false);
        ratingBar.setRating(recipe.rating);
        updateIngredientsView();


        //set description
        TextView descriptionText = findViewById(R.id.Description);
        descriptionText.setText(recipe.description);

    }

    private void addPortionsButtons() {
        Button buttonDec = findViewById(R.id.buttonDecrement);
        Button buttonInc = findViewById(R.id.buttonIncrement);
        FloatingActionButton toCalendarButton = findViewById(R.id.fabToCalendar);


        TextView textViewPortions = this.findViewById(R.id.Portions);
        textViewPortions.setText(String.valueOf(recipe.defaultPortions));

        toCalendarButton.setOnClickListener(this);
        buttonDec.setOnClickListener(this);
        buttonInc.setOnClickListener(this);
    }

    private void changePortions(int portions) {
        if (portions >= 1) {
            recipe.defaultPortions = portions;
            TextView viewNumber = this.findViewById(R.id.Portions);
            viewNumber.setText(String.valueOf(recipe.defaultPortions));

            updateIngredientsView();
        }
    }


    public void updateIngredientsView() {
        // clean Layout
        LinearLayout linearLayout = this.findViewById(R.id.Ingredients);
        linearLayout.removeAllViews();

        //set ingredients
        ArrayList ingredients = recipe.ingredients;


        for (final Ingredient ingredient : recipe.toIngredientArray()) {
            TextView textView = new TextView(this);

            String text = String.format("%s (%s %s)", ingredient.name, ingredient.amount * recipe.defaultPortions, ingredient.unit);
            textView.setText(text);

            if (isEditAble) {

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DialogIngredient(RecipeActivity.this, ingredient);
                    }
                });
            }

            linearLayout.addView(textView);
        }
    }

    public void deleteIngredient(Ingredient ing){
       recipe.ingredients.remove(ing);
    }

    public void addToList(Ingredient ing) {
        recipe.ingredients.add(ing);
        updateIngredientsView();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonDecrement:
                changePortions(recipe.defaultPortions - 1);
                break;
            case R.id.buttonIncrement:
                changePortions(recipe.defaultPortions + 1);
                break;
            case R.id.fabSave:
                pushRecipe();
                break;
            case R.id.fabToCalendar:
                makeMarker();
                closeFABMenu();
                break;
            case R.id.ButtonAddIngredient:
                new DialogIngredient(this, null);
                break;
            case R.id.fab:
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
                break;
            case R.id.editRecipeFab:
                isEditAble = true;
                closeFABMenu();
                setContentView(R.layout.activity_recipe_editable);
                setRecipe();
                addEditButtons();
                break;
            default:
                break;
        }
    }

    private void makeMarker() {
        Intent intent = new Intent(this, EventActivity.class);
        EventActivity.marker = new RecipeMarker(recipe.id, 1, null);
        this.startActivity(intent);
    }

    private void pushRecipe() {

        EditText title = this.findViewById(R.id.RecipeTitle);
        recipe.name = title.getText().toString();

        EditText description = this.findViewById(R.id.Description);
        recipe.description = description.getText().toString();

        RatingBar ratingBar = this.findViewById(R.id.RecipeRatingBar);
        recipe.rating = ratingBar.getRating();

        recipe.normalizeIngredients(recipe.defaultPortions);

        if (newRecipe) {
            Database.setNewRecipe(FirebaseAuth.getInstance().getCurrentUser().getUid(), recipe);
            Book book = Database.findDefaultBook(this);
            Database.addRecipeToBook(FirebaseAuth.getInstance().getCurrentUser().getUid(), book, recipe.id);
        }else{
            Database.updateRecipe(recipe);
        }

        this.finish();
    }

    public void createFloatActionButton() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);

        FloatingActionButton fab1 = findViewById(R.id.editRecipeFab);
        fab1.setOnClickListener(this);

        FloatingActionButton fab2 = findViewById(R.id.fabToCalendar);
        fab2.setOnClickListener(this);
    }

    private void showFABMenu() {
        isFABOpen = true;
        this.findViewById(R.id.layoutToCalendar).animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        this.findViewById(R.id.editRecipeLayout).animate().translationY(-getResources().getDimension(R.dimen.standard_105));

        this.findViewById(R.id.editRecipeText).setVisibility(View.VISIBLE);
        this.findViewById(R.id.textToCalendar).setVisibility(View.VISIBLE);
    }

    private void closeFABMenu() {
        isFABOpen = false;
        this.findViewById(R.id.editRecipeLayout).animate().translationY(0);
        this.findViewById(R.id.layoutToCalendar).animate().translationY(0);

        this.findViewById(R.id.editRecipeText).setVisibility(View.GONE);
        this.findViewById(R.id.textToCalendar).setVisibility(View.GONE);
    }


    public int getPortions() {
        return recipe.defaultPortions;
    }
}


