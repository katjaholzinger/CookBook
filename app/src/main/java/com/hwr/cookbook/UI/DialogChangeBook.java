package com.hwr.cookbook.UI;


import android.app.Activity;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.hwr.cookbook.Book;
import com.hwr.cookbook.Database;
import com.hwr.cookbook.Ingredient;
import com.hwr.cookbook.R;
import com.hwr.cookbook.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Thomas on 26.04.2018.
 */

public class DialogChangeBook extends AlertDialog.Builder {



    private Spinner spinnerBooks;
    private Book oldBook;
    private Recipe recipe;

    private Activity context;


    public DialogChangeBook(@NonNull Activity context, ViewGroup parentView, Book oldBook, Recipe recipe) {
        super(context);

        this.oldBook = oldBook;
        this.recipe = recipe;
        this.context = context;

        View dialog_layout = createBuilder(parentView);

        spinnerBooks = dialog_layout.findViewById(R.id.bookSpinner);

        setValues();
        show();
    }

    private View createBuilder(ViewGroup parentView) {
        LayoutInflater inflater = context.getLayoutInflater();
        View dialog_layout = inflater.inflate(R.layout.dialog_change_book, null);

        setView(dialog_layout);
        setTitle(context.getResources().getString(R.string.MoveBookTitle));

        setPositiveButton(R.string.move, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Book newBook = (Book) spinnerBooks.getSelectedItem();
                Database.moveToOtherBook(oldBook, newBook, recipe);
            }
        });

        setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        return dialog_layout;
    }


    private void setValues() {
        List <Book> books = Database.getBookList();

        SpinnerAdapter adapter;
        adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, books);
        spinnerBooks.setAdapter(adapter);
    }

}
