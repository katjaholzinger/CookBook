package com.hwr.cookbook.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.hwr.cookbook.R;
import com.hwr.cookbook.RecipeMarker;

public class EventActivity extends AppCompatActivity implements View.OnClickListener {
    public static RecipeMarker event = null;

    public EventActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event);
    }

    @Override
    public void onClick(View view) {

    }
}
