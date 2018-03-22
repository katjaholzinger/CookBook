package com.hwr.cookbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class PlanActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private User userSnapshot;
    private List<Recipe> recipeList;
    private List<Plan> planList;
    private Book[] booksSnapshot;
    private String currentUserId;
    private static final String TAG = "LoginActivity";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        Intent intent = getIntent();
        currentUserId = intent.getStringExtra(LoginActivity.UID);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        DBNewListener();
    }

    private void DBNewListener() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        DatabaseReference currentUserRef = database.child("users").child(currentUserId);
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                userSnapshot = dataSnapshot.getValue(User.class);
                // [START_EXCLUDE]

                // [END_EXCLUDE]
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        currentUserRef.addValueEventListener(userListener);

        DatabaseReference plansRef = database.child("plans").child(currentUserId);
        planList = new ArrayList<>();
        plansRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                planList.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Plan plan = postSnapshot.getValue(Plan.class);
                    planList.add(plan);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });

        DatabaseReference recipesRef = database.child("recipes").child(currentUserId);
        recipeList = new ArrayList<>();
        recipesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                recipeList.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Recipe recipe = postSnapshot.getValue(Recipe.class);
                    recipeList.add(recipe);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });

        DatabaseReference booksRef = database.child("books").child(currentUserId);
        final Book[] books = new Book[] {};

        ValueEventListener booksListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI

                booksSnapshot = dataSnapshot.getValue(books.getClass());
                // [START_EXCLUDE]

                // [END_EXCLUDE]
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        booksRef.addValueEventListener(booksListener);
    }

}
