package com.hwr.cookbook.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hwr.cookbook.Book;
import com.hwr.cookbook.Database;
import com.hwr.cookbook.Ingredient;
import com.hwr.cookbook.LoginActivity;
import com.hwr.cookbook.Plan;
import com.hwr.cookbook.R;
import com.hwr.cookbook.Recipe;
import com.hwr.cookbook.User;

import java.util.ArrayList;

/**
 * Created by Thomas on 18.03.2018.
 */

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;

    private User user;
    private String currentUserId;
    private static final String TAG = "LoginActivity";
    private ViewPager viewPager;
    public ArrayList<Recipe> recipeList;
    public ArrayList<Plan> planList;
    public ArrayList<Book> bookList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = getIntent();
        currentUserId = intent.getStringExtra(LoginActivity.UID);


        mTextMessage = (TextView) findViewById(R.id.message);


        // creates TabLayout and Actionbar
        createLayouts();

    }

    public void createListener() {
        DatabaseReference plansRef = FirebaseDatabase.getInstance().getReference().child("plans").child(currentUserId);
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


        final DatabaseReference recipesRef = FirebaseDatabase.getInstance().getReference().child("recipes").child(currentUserId);
        recipeList = new ArrayList<>();
        recipesRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String s) {
                int i= 0;
                Log.d("ValueListener", i+ ". :" + snapshot + " : " + s);
                Recipe recipe = snapshot.getValue(Recipe.class);
                recipeList.add(recipe);

                for (Recipe r: recipeList
                        ) {
                    Log.d("RecipeListe", r.name);
                }
                viewPager.;

            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String s) {
                //Search Recipe in RecipeList
                //Change Attributes
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Recipe recipe = dataSnapshot.getValue(Recipe.class);
                for (Recipe r: recipeList
                        ) {
                    if (r.id == recipe.id) {
                        recipeList.remove(r);
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });

        final DatabaseReference booksRef = FirebaseDatabase.getInstance().getReference().child("books").child(currentUserId);
        bookList = new ArrayList<>();
        booksRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String s) {
                int i= 0;
                Log.d("ValueListener", i+ ". :" + snapshot + " : " + s);
                Book book = snapshot.getValue(Book.class);
                Log.d("Bookname:", book.name);
                bookList.add(book);

            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String s) {
                //Search Recipe in RecipeList
                //Change Attributes
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Book book = dataSnapshot.getValue(Book.class);
                bookList.remove(book);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });
    }


    private void createLayouts() {
        // Set Toolbar
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(toolBar);

        // Define the TabLayout and add Items
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_search_black_24dp)); //ICON TAB
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_collections_bookmark_black_24dp));  //TEXT TAB
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_date_range_black_24dp));  //TEXT TAB


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = findViewById(R.id.pager);

        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        Log.d("Database", "Listener erstellen...");
        createListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                // User chose the "logout" item
                logout();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
