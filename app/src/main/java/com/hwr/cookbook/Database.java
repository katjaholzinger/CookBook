package com.hwr.cookbook;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by kholzinger on 10.03.2018.
 */

public class Database {
    private static final String TAG = "DatabaseOperation";
    DatabaseReference database;
    DatabaseReference databaseUser;
    User user;
    private String currentUserId;
    private List<Recipe> recipeList;
    private List<Plan> planList;
    private List<Book> bookList;

    public Database() {
        database = FirebaseDatabase.getInstance().getReference();
    }

    public void newListener() {
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
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
    public void setNewUser(String id, String name, String mail) {
        Log.d("Database", "Creating new user ...");
        User user = new User(name, mail);
        Log.d("Database", "Adding new user ...");
        database.child("users").child(id).setValue(user);

    }

    public String getUserName (User user) {
        if (user == null) {
            return "";
        }
        return  user.username;
    }

    public void setNewRecipe (String userID, Recipe recipe) {

        Log.d("Database", "Adding new recipe ...");

        FirebaseDatabase.getInstance().getReference().child("recipes").child(userID).setValue(recipe);
    }

    public void setNewPlan (String userID, Plan plan) {
        Log.d("Database", "Adding new plan ...");
        database.child("plans").child(userID).child(plan.getID()).setValue(plan);
    }

    public void setNewMarkerInPlan (String userID, String planID, RecipeMarker marker) {
        Log.d("Database", "Adding new marker to plan xxx ...");
        database.child("plans").child(userID).child(planID).child("events").child(marker.getID()).setValue(marker);
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
    }

    public void setNewBook (String userID, Book book) {
        Log.d("Database", "Adding new book to user xxx ...");
        database.child("books").child(userID).child(book.getID()).setValue(book);
    }

    public void addRecipeToBook (String userID, Book book, Recipe recipe) {
        Log.d("Database", "Adding new recipe to book xxx of user xxx ...");
        database.child("books").child(userID).child(book.getID()).setValue(recipe.getID());
    }

    //public void

}
