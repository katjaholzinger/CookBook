package com.hwr.cookbook.UI;

import android.content.Context;
import android.text.Layout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hwr.cookbook.Book;
import com.hwr.cookbook.Recipe;

/**
 * Created by Thomas on 27.03.2018.
 */

public class LayoutBooks extends LinearLayout {
    private Book book;


    public LayoutBooks(Context context) {
        super(context);
    }

    public LayoutBooks(Context context, Book book){
        super(context);

        //set Attributes
        this.setOrientation(LinearLayout.VERTICAL);

        setTitle(book);

        addRecipes(book);
    }

    private void addRecipes(Book book) {
        //add recipes
        for (Recipe recipe: book.getRecipes()) {
            LayoutBookmarksRecipe layout = new LayoutBookmarksRecipe(getContext(), recipe);
            this.addView(layout);
        }
    }

    private void setTitle(Book book) {
        //set Title
        TextView title = new TextView(getContext());
        title.setText(book.getName());
        title.setGravity(TextView.TEXT_ALIGNMENT_CENTER);
        this.addView(title);
    }
}
