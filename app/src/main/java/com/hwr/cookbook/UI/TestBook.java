package com.hwr.cookbook.UI;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.hwr.cookbook.Book;
import com.hwr.cookbook.Database;
import com.hwr.cookbook.Ingredient;
import com.hwr.cookbook.Recipe;

import java.util.ArrayList;

/**
 * Created by Thomas on 17.04.2018.
 */

public class TestBook {
    public static ArrayList<Book> generateTestBook(boolean safeToDB){
        ArrayList<Book> bookList = Database.getBookList();
        ArrayList<Recipe> recipeList = Database.getRecipeList();
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Salz", 5, "Teelöffel" ));
        ingredients.add(new Ingredient("Wasser", 3, "Liter" ));
        ingredients.add(new Ingredient("Spaghetti", 500, "Gramm" ));
        Recipe r1 = new Recipe("Spaghetti", ingredients, 4, "pasta", "Wasser mit Salz zum kochen bringen. Wenn das Wasser kocht, die Spaghettis dazugeben. Nach 8 Minuten das Wasser abgießen und die Nudeln abschrecken.");
        r1.rating = 4;
        r1.normalizeIngredients(4);
        if (safeToDB) {
            Database.setNewRecipe(FirebaseAuth.getInstance().getCurrentUser().getUid(), r1);
        }

        Recipe r2 = new Recipe("Test2", ingredients, 1, null, "");
        r2.normalizeIngredients(4);
        recipeList.add(r1);
        recipeList.add(r2);
        Database.setRecipeList(recipeList);

        for(int i = 0; i <= 3; i++) {
            ArrayList<String> recipeIdList = new ArrayList<>();
            recipeIdList.add(r1.id);
            recipeIdList.add(r2.id);
            Book book = new Book("Book Title " + i, recipeIdList);
            bookList.add(book);
        }
        for (Book book: bookList
             ) {
            if (safeToDB) {
                Database.setNewBook(FirebaseAuth.getInstance().getCurrentUser().getUid(), book);
            }
        }
        Database.setBookList(bookList);
        return bookList;
    }


    public static ArrayList<Recipe> generateRecipes(){
        ArrayList<Recipe> recipes = Database.getRecipeList();
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Salz", 5, "Teelöffel" ));
        ingredients.add(new Ingredient("Wasser", 3, "Liter" ));
        ingredients.add(new Ingredient("Spaghetti", 500, "Gramm" ));

        Recipe r1 = new Recipe("Spaghetti", ingredients, 4, "pasta", "Wasser mit Salz zum kochen bringen. Wenn das Wasser kocht, die Spaghettis dazugeben. Nach 8 Minuten das Wasser abgießen und die Nudeln abschrecken.");
        r1.rating = 4;
        Recipe r2 = new Recipe("Test2", ingredients, 1, null, "");

        recipes.add(r1);
        recipes.add(r2);
        Database.setRecipeList(recipes);
        return recipes;
    }

}
