package com.hwr.cookbook.UI;


import android.app.Activity;
import android.content.Context;
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



    private Spinner spinnerBooks;
    private Book oldBook;
    private Recipe recipe;

    private FragmentBookmarks context;


    public DialogEditRecipeGeneral(@NonNull FragmentBookmarks context, ViewGroup parentView, Book oldBook, Recipe recipe) {
        super(context.getActivity());

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
        setTitle(context.getResources().getString(R.string.RecipeGeneralSettings));

        setPositiveButton(R.string.move, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Book newBook = (Book) spinnerBooks.getSelectedItem();
                Database.moveToOtherBook(oldBook, newBook, recipe);
                context.updateExpandableList(Database.getBookList());
            }
        });

        setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Database.deleteRecipeFromBook(recipe,oldBook);
                context.updateExpandableList(Database.getBookList());
            }
        });


        setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        return dialog_layout;
    }


    private void setValues() {
        List <Book> books = Database.getBookList();

        SpinnerAdapter adapter;
        adapter = new ArrayAdapter<>(context.getActivity(), android.R.layout.simple_spinner_item, books);
        spinnerBooks.setAdapter(adapter);
    }

}
