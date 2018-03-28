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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hwr.cookbook.Book;
import com.hwr.cookbook.Database;
import com.hwr.cookbook.Ingredient;
import com.hwr.cookbook.IngredientList;
import com.hwr.cookbook.LoginActivity;
import com.hwr.cookbook.Plan;
import com.hwr.cookbook.R;
import com.hwr.cookbook.Recipe;
import com.hwr.cookbook.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 18.03.2018.
 */

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;

    private User user;
    private List<Recipe> recipeList;
    private List<Plan> planList;
    private List<Book> bookList;
    private String currentUserId;
    private static final String TAG = "LoginActivity";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = getIntent();
        currentUserId = intent.getStringExtra(LoginActivity.UID);

        //DBNewListener();

        mTextMessage = (TextView) findViewById(R.id.message);

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

        // creates TabLayout and Actionbar
        createLayouts();

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

        final ViewPager viewPager = findViewById(R.id.pager);

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

    public List<Book> getBookList(){
        return bookList;
    }

    public User getUser(){
        return user;
    }



}
