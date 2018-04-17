package com.hwr.cookbook.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.gms.common.data.DataBufferObserverSet;
import com.hwr.cookbook.Book;
import com.hwr.cookbook.Database;
import com.hwr.cookbook.Ingredient;
import com.hwr.cookbook.IngredientList;
import com.hwr.cookbook.R;
import com.hwr.cookbook.Recipe;
import com.hwr.cookbook.User;

import java.util.ArrayList;


/**
 * Created by Thomas on 18.03.2018.
 */

public class FragmentBookmarks extends Fragment {
    private LinearLayout linearLayout;
    private User user;

    public FragmentBookmarks() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmarks, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //find Linear Layout
        linearLayout = getActivity().findViewById(R.id.linearLayoutBookmarks);

        //       Database db = new Database();

//        ArrayList<Book> books =  db.getBooks();


//        for (Book book: books){
//           addBook(book);
   //     }


        //add Recipes
        // /**
        //  * Just for testing
        IngredientList ingredients = new IngredientList();
        ingredients.add(new Ingredient("Salz", 5, "Teelöffel" ));
        ingredients.add(new Ingredient("Wasser", 3, "Liter" ));
        ingredients.add(new Ingredient("Spaghetti", 500, "Gramm" ));
        ingredients.normalize(4);
        Recipe r1 = new Recipe("Spaghetti", ingredients, 4, "pasta", "Wasser mit Salz zum kochen bringen. Wenn das Wasser kocht, die Spaghettis dazugeben. Nach 8 Minuten das Wasser abgießen und die Nudeln abschrecken.");
        r1.rating = 4;
        Recipe r2 = new Recipe("Test2", ingredients, 1, null, "");

        for(int i = 0; i <= 10; i++) {
            Book book = new Book(new Recipe[]{r1, r2});
            book.setName("Title");
            addBook(book);
        }
        //*/

        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), RecipeActivity.class);
                RecipeActivity.recipe=null;
                getContext().startActivity(intent);
                            }
        });

    }

    public void addBook(Book book) {
        LayoutBooks layoutBooks = new LayoutBooks(getContext(), book);
        linearLayout.addView(layoutBooks);
    }

}
