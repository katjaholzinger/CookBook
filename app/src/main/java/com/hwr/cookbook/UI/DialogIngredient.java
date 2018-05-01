package com.hwr.cookbook.UI;


import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.hwr.cookbook.Ingredient;
import com.hwr.cookbook.R;

import java.util.Arrays;

/**
 * Created by Thomas on 26.04.2018.
 */

public class DialogIngredient extends AlertDialog.Builder {


    private boolean isModify;
    private EditText editTextAmount;
    private Spinner spinnerUnit;
    private EditText editTextName;
    private Ingredient ingredient;

    private RecipeActivity context;


    public DialogIngredient(@NonNull RecipeActivity context, Ingredient ingredient) {
        super(context);

        this.ingredient = ingredient;
        this.context = context;
        isModify = ingredient != null;

        View dialog_layout = createBuilder();

        editTextName = dialog_layout.findViewById(R.id.ingredientName);
        editTextAmount = dialog_layout.findViewById(R.id.amountInput);
        spinnerUnit = dialog_layout.findViewById(R.id.ingredientUnit);

        setValues();

        show();
    }

    private View createBuilder() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialog_layout = inflater.inflate(R.layout.dialog_add_ingredient, (ViewGroup) context.findViewById(R.id.addRecipeFab));

        setView(dialog_layout);
        setTitle("settings");

        setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                save();
            }
        });

        setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        if (isModify) {
            setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    context.deleteIngredient(ingredient);
                    context.updateIngredientsView();
                }
            });
        }

        return dialog_layout;
    }


    private void setValues() {
        if (isModify) {
            String[] units = getContext().getResources().getStringArray(R.array.units_short_array);
            spinnerUnit.setSelection(Arrays.asList(units).indexOf(ingredient.unit), true);
            editTextAmount.setText(String.valueOf(ingredient.amount*context.getPortions()));
            editTextName.setText(ingredient.name);
        } else {
            this.ingredient = new Ingredient();
        }
    }

    private void save() {
        try {
            ingredient.name = editTextName.getText().toString();
            ingredient.unit = spinnerUnit.getSelectedItem().toString();
            ingredient.amount = Float.parseFloat(editTextAmount.getText().toString().trim());

            ingredient.amount = Float.parseFloat(editTextAmount.getText().toString().trim())/context.getPortions();


            if (isModify) {
                context.updateIngredientsView();
            } else {
                context.addToList(DialogIngredient.this.ingredient);
            }
        } catch (Exception e) {
            Toast.makeText(context, R.string.Error,
                    Toast.LENGTH_SHORT).show();
        }

    }


}
