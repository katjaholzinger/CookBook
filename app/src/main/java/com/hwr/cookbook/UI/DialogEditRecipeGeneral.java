package com.hwr.cookbook.UI;


import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.hwr.cookbook.Book;
import com.hwr.cookbook.Database;
import com.hwr.cookbook.R;
import com.hwr.cookbook.Recipe;

import java.util.List;

/**
 * Created by Thomas on 26.04.2018.
 */

public class DialogEditRecipeGeneral extends AlertDialog.Builder {



    public Spinner spinnerBooks;
    private Book oldBook;
    private Recipe recipe;

    private Activity context;


    public DialogEditRecipeGeneral(@NonNull Activity context, ViewGroup parentView, Book oldBook, Recipe recipe) {
        super(context);

        this.oldBook = oldBook;
        this.recipe = recipe;
        this.context = context;

        View dialog_layout = createBuilder(parentView);

        spinnerBooks = dialog_layout.findViewById(R.id.bookSpinner);

        setValues();
    }

    private View createBuilder(final ViewGroup parentView) {
        LayoutInflater inflater = context.getLayoutInflater();
        View dialog_layout = inflater.inflate(R.layout.dialog_change_book, null);

        setView(dialog_layout);
        setTitle(context.getResources().getString(R.string.RecipeGeneralSettings));

        return dialog_layout;
    }


    private void setValues() {
        List <Book> books = Database.getBookList();

        SpinnerAdapter adapter;
        adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, books);
        spinnerBooks.setAdapter(adapter);
    }

}
