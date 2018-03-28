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

    private User user;
    private List<Recipe> recipeList;
    private List<Plan> planList;
    private List<Book> bookList;
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

        //DBNewListener();

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Log.d("Database", "Database erstellen...");
        Database db = new Database();
        mTextMessage.setText(db.getUserName(user));
        IngredientList ingredients = new IngredientList();
        ingredients.add(new Ingredient("Salz", 5, "Teelöffel" ));
        ingredients.add(new Ingredient("Wasser", 3, "Liter" ));
        ingredients.add(new Ingredient("Spaghetti", 500, "Gramm" ));
        ingredients.normalize(4);
        Recipe recipe = new Recipe("Spaghetti", ingredients, 4, "pasta", "Wasser mit Salz zum kochen bringen. Wenn das Wasser kocht, die Spaghettis dazugeben. Nach 8 Minuten das Wasser abgießen und die Nudeln abschrecken.");
        db.setNewRecipe(currentUserId, recipe);

        ausloggen();

    }

    private void ausloggen() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void DBNewListener() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        DatabaseReference userRef = database.child("users").child(currentUserId);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });

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

        final DatabaseReference booksRef = database.child("books").child(currentUserId);
        bookList = new ArrayList<>();
        booksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                bookList.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Book book = postSnapshot.getValue(Book.class);
                    bookList.add(book);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });
    }

}
