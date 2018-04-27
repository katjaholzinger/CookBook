package com.hwr.cookbook.UI;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.hwr.cookbook.Database;
import com.hwr.cookbook.R;
import com.hwr.cookbook.Recipe;
import com.hwr.cookbook.RecipeMarker;

public class EventActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    public static RecipeMarker marker = null;
    private Spinner spinner;

    public EventActivity(){
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event);

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        createSpinnerAdapter();

        // set Toolbar
        Toolbar toolBar = findViewById(R.id.toolbar);
        this.setSupportActionBar(toolBar);
    }

    @Override
    public void onClick(View view) {

    }

    private void createSpinnerAdapter() {

        spinner = (Spinner) findViewById(R.id.RecipeSpinner);

        // Create an ArrayAdapter using the recipe array and a default spinner layout

        //Recipe[] recipes = (Recipe[]) Database.getRecipeList().toArray();
        Recipe[] recipes = new Recipe[] {new Recipe()};

        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, recipes);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        // Get the currently selected State object from the spinner
        Recipe recipe = (Recipe)spinner.getSelectedItem();

        // Now do something with it.
    }

    public void onNothingSelected(AdapterView<?> parent )
    {
    }
}
