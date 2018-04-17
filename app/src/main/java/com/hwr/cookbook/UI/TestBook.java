package com.hwr.cookbook.UI;

import com.hwr.cookbook.Book;
import com.hwr.cookbook.Ingredient;
import com.hwr.cookbook.IngredientList;
import com.hwr.cookbook.Recipe;

import java.util.ArrayList;

/**
 * Created by Thomas on 17.04.2018.
 */

public class TestBook {
    public static ArrayList<Book> generateTestBook(){
        ArrayList<Book> books = new ArrayList<>();
        IngredientList ingredients = new IngredientList();
        ingredients.add(new Ingredient("Salz", 5, "Teelöffel" ));
        ingredients.add(new Ingredient("Wasser", 3, "Liter" ));
        ingredients.add(new Ingredient("Spaghetti", 500, "Gramm" ));
        ingredients.normalize(4);
        Recipe r1 = new Recipe("Spaghetti", ingredients, 4, "pasta", "Wasser mit Salz zum kochen bringen. Wenn das Wasser kocht, die Spaghettis dazugeben. Nach 8 Minuten das Wasser abgießen und die Nudeln abschrecken.");
        r1.rating = 4;
        Recipe r2 = new Recipe("Test2", ingredients, 1, null, "");

        for(int i = 0; i <= 3; i++) {
            Book book = new Book(new Recipe[]{r1, r2});
            book.setName("Book Title " + i);
            books.add(book);
        }

        return books;
    }
}
