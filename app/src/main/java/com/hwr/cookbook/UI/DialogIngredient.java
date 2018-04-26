package com.hwr.cookbook.UI;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.hwr.cookbook.Ingredient;
import com.hwr.cookbook.R;

import java.util.Arrays;


public class DialogIngredient extends Dialog implements
        android.view.View.OnClickListener {

    private RecipeActivity c;
    private Button yes, no;

    private Ingredient ingredient;
    private EditText editTextAmount;
    private Spinner spinnerUnit;
    private EditText editTextName;

    private boolean isModify;

    public DialogIngredient(RecipeActivity a, Ingredient ingredient) {
        super(a,R.style.CustomeDialog);
        // TODO Auto-generated constructor stub
        this.c = a;
        isModify = ingredient != null;

        if (isModify){
            this.ingredient = ingredient;
        }else{
            this.ingredient=new Ingredient();
        }



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(R.string.AddIngTitle);
        setContentView(R.layout.dialog_add_ingredient);
        yes = findViewById(R.id.btn_add);
        no = findViewById(R.id.btn_cancel);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);


        spinnerUnit = findViewById(R.id.ingredientUnit);

        Resources res = c.getResources();
        String[] units = res.getStringArray(R.array.units_short_array);

        spinnerUnit.setSelection( Arrays.asList(units).indexOf(ingredient.unit), true);
        editTextAmount = findViewById(R.id.amountInput);
        editTextAmount.setText(String.valueOf(ingredient.amount));
        editTextName = findViewById(R.id.ingredientName);
        editTextName.setText(ingredient.name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                ingredient.name = editTextName.getText().toString();
                ingredient.unit = spinnerUnit.getSelectedItem().toString();
                ingredient.amount = Float.parseFloat(editTextAmount.getText().toString().trim());

                if (isModify){
                    c.updateIngredientsView();
                }
                else{
                    c.addToList(ingredient);
                }

                break;
            case R.id.btn_cancel:

                break;
            default:
                break;
        }
        dismiss();
    }

}