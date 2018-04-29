package com.hwr.cookbook.UI;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.google.firebase.auth.FirebaseAuth;
import com.hwr.cookbook.Database;
import com.hwr.cookbook.Plan;
import com.hwr.cookbook.R;
import com.hwr.cookbook.Recipe;
import com.hwr.cookbook.RecipeMarker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EventActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    //public static Plan plan = null;
    public static RecipeMarker marker = null;
    private Spinner spinner;

    public EventActivity(){
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event);

        createSpinnerAdapter();

        DatePicker datePicker = findViewById(R.id.DatePicker);
        if (marker.getCalendar() != null) {
            datePicker.updateDate(
                    marker.getCalendar().get(Calendar.YEAR),
                    marker.getCalendar().get(Calendar.MONTH),
                    marker.getCalendar().get(Calendar.DAY_OF_MONTH
                    ));
        }

        EditText persons = findViewById(R.id.personsInput);
        persons.setText(String.valueOf(marker.persons));

        FloatingActionButton floatingButton = findViewById(R.id.addMarkerFab);
        floatingButton.setOnClickListener(this);

        // set Toolbar
        Toolbar toolBar = findViewById(R.id.toolbar);
        this.setSupportActionBar(toolBar);
    }

    private void createSpinnerAdapter() {

        spinner = findViewById(R.id.RecipeSpinner);

        // Create an ArrayAdapter using the recipe array and a default spinner layout

        ArrayList<Recipe> recipeList = Database.getRecipeList();
        Object[] recipes = recipeList.toArray();
        //Recipe[] recipes = new Recipe[] {new Recipe()};

        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, recipes);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        if (marker.recipeId != null) {
            for (int counter=0; counter<recipes.length; counter++) {
                if (((Recipe) recipes[counter]).id.equals(marker.recipeId)) {
                    spinner.setSelection(counter);
                    break;
                }
            }
        }

    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        // Get the currently selected State object from the spinner
        Recipe recipe = (Recipe)spinner.getSelectedItem();

        // Now do something with it.
        marker.recipeId = recipe.id;
        marker.setName(recipe.name);

    }

    public void onNothingSelected(AdapterView<?> parent )
    {
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.addMarkerFab:
                pushEvent();
                break;

            default:
                break;
        }
    }

    private void pushEvent() {



        DatePicker datePicker = this.findViewById(R.id.DatePicker);
        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth());
        marker.setCalendar(calendar);

        EditText persons = this.findViewById(R.id.personsInput);
        marker.persons = Integer.parseInt(persons.getText().toString());

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String planId = Database.getPlan().id;

        /*
        if (plan.Markers == null) {
            plan.Markers = new ArrayList<RecipeMarker>();
        }

        plan.Markers.add(marker);
        */

        // Database.setNewPlan(userId, plan);
        Database.setNewMarkerInPlan(userId, planId , marker);
        this.finish();
    }
}
