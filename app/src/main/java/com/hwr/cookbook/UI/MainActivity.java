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
import com.hwr.cookbook.Plan;
import com.hwr.cookbook.R;
import com.hwr.cookbook.Recipe;

import java.util.ArrayList;

/**
 * Created by Thomas on 18.03.2018.
 */

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;

    private String uID;
    private static final String TAG = "LoginActivity";
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mTextMessage = findViewById(R.id.message);
        // creates TabLayout and Actionbar
        Log.d("MainAcitvity", "Wait for data...");
        //bookList.add(new Book("Test", new ArrayList<String>(){}));
        LoadData();
        createListener();

        mTextMessage = findViewById(R.id.message);

        createLayouts();

    }

    private void LoadData() {

        //Load Books at the first time
        FirebaseDatabase.getInstance().getReference().child("books").child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Book> bookList = new ArrayList<>();
                for (DataSnapshot child:dataSnapshot.getChildren()
                        ) {
                    Book book = child.getValue(Book.class);
                    Log.d("Book", book.name);
                    bookList.add(book);
                }
                Database.setBookList(bookList);

                FirebaseDatabase.getInstance().getReference().child("recipes").child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<Recipe> recipeList = new ArrayList<>();
                        for (DataSnapshot child:dataSnapshot.getChildren()
                                ) {
                            Recipe recipe = child.getValue(Recipe.class);
                            recipeList.add(recipe);
                        }
                        Database.setRecipeList(recipeList);
                        FirebaseDatabase.getInstance().getReference().child("plans").child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                ArrayList<Plan> planList = new ArrayList<>();
                                for (DataSnapshot child:dataSnapshot.getChildren()
                                        ) {
                                    Plan recipe = child.getValue(Plan.class);
                                    planList.add(recipe);
                                }
                                Database.setPlanList(planList);

                                FragmentBookmarks fb = (FragmentBookmarks) pagerAdapter.getItem(1);
                                fb.updateExpandableList(Database.getBookList());
                                createListener();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    public void createListener() {
        DatabaseReference plansRef = FirebaseDatabase.getInstance().getReference().child("plans").child(uID);
        plansRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Plan book = dataSnapshot.getValue(Plan.class);
                ArrayList<Plan> planArrayList = Database.getPlanList();
                planArrayList.add(book);
                Database.setPlanList(planArrayList);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        final DatabaseReference recipesRef = FirebaseDatabase.getInstance().getReference().child("recipes").child(uID);
        recipesRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String s) {
                ArrayList<Recipe> recipeList = Database.getRecipeList();
                Recipe recipe = snapshot.getValue(Recipe.class);
                recipeList.add(recipe);
                Database.setRecipeList(recipeList);

            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String s) {
                //Search Recipe in RecipeList
                //Change Attributes
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                ArrayList<Recipe> recipeList = Database.getRecipeList();
                Recipe recipe = dataSnapshot.getValue(Recipe.class);
                for (Recipe r: recipeList
                        ) {
                    if (r.id == recipe.id) {
                        recipeList.remove(r);
                    }
                }
                Database.setRecipeList(recipeList);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });

        final DatabaseReference booksRef = FirebaseDatabase.getInstance().getReference().child("books").child(uID);
        booksRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String s) {
                ArrayList<Book> bookList= Database.getBookList();
                Book book = snapshot.getValue(Book.class);
                Log.d("Bookname:", book.name);
                bookList.add(book);

                Database.setBookList(bookList);
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String s) {
                //Search Recipe in RecipeList
                //Change Attributes
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                ArrayList<Book> bookList= Database.getBookList();
                Book book = dataSnapshot.getValue(Book.class);
                bookList.remove(book);
                Database.setBookList(bookList);
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
        Toolbar toolBar = findViewById(R.id.toolbar);
        this.setSupportActionBar(toolBar);

        // Define the TabLayout and add Items
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_search_black_24dp)); //ICON TAB
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_collections_bookmark_black_24dp));  //TEXT TAB
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_date_range_black_24dp));  //TEXT TAB


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = findViewById(R.id.pager);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);


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
