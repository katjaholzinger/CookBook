package com.hwr.cookbook;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.json.JSONObject;

/**
 * Created by kholzinger on 10.03.2018.
 */

public class Database {
    private static final String TAG = "DatabaseOperation";
    static DatabaseReference database;
    static DatabaseReference databaseUser;
    static User user;
    static private String currentUserId;
    static private ArrayList<Recipe> recipeList;
    static private ArrayList<Plan> planList;
    static public ArrayList<Book> bookList;


    static public void newListener() {
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

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


                final DatabaseReference recipesRef = database.child("recipes").child(currentUserId);
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

        final DatabaseReference booksRef = database.child("books").child(currentUserId);
        bookList = new ArrayList<>();
        booksRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String s) {
                int i= 0;
                Book book = snapshot.getValue(Book.class);
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
    static public void setNewUser(String id, String name, String mail) {
        Log.d("Database", "Creating new user ...");
        User user = new User(name, mail);
        Log.d("Database", "Adding new user ...");
        FirebaseDatabase.getInstance().getReference().child("users").child(id).setValue(user);

    }

    static public String getUserName (User user) {
        if (user == null) {
            return "";
        }
        return  user.username;
    }

    static public void setNewRecipe (String userID, Recipe recipe) {

        Log.d("Database", "Adding new recipe ...");

        String id= FirebaseDatabase.getInstance().getReference().child("recipes").child(userID).push().getKey();
        recipe.id = (id);
        FirebaseDatabase.getInstance().getReference().child("recipes").child(userID).child(id).setValue(recipe);
    }

    static  public void setNewPlan (String userID, Plan plan) {
        Log.d("Database", "Adding new plan ...");
        FirebaseDatabase.getInstance().getReference().child("plans").child(userID).child(plan.getID()).setValue(plan);
    }

    static public void setNewMarkerInPlan (String userID, String planID, RecipeMarker marker) {
        Log.d("Database", "Adding new marker to plan xxx ...");
        FirebaseDatabase.getInstance().getReference().child("plans").child(userID).child(planID).child("events").child(marker.getID()).push().setValue(marker);
    }

    static public ArrayList<Book> getBookList() {
        return bookList;
    }

    static public void logout() {
        FirebaseAuth.getInstance().signOut();
    }

    static public void setNewBook (String userID, Book book) {
        Log.d("Database", "Adding new book to user xxx ...");

        String id= FirebaseDatabase.getInstance().getReference().child("books").child(userID).push().getKey();
        book.id = (id);
        FirebaseDatabase.getInstance().getReference().child("books").child(userID).child(id).setValue(book);
    }



    static public void addRecipeToBook (String userID, Book book, Recipe recipe) {
        Log.d("Database", "Adding new recipe to book xxx of user xxx ...");
        FirebaseDatabase.getInstance().getReference().child("books").child(userID).child(book.id).push().setValue(recipe.id);
    }

    static public Recipe findRecipe(String id) {
        for (Recipe r: recipeList) {
           if (r.id == id) {
               return r;
           }
        }
        return null;
    }

    public static ArrayList<Recipe> getRecipeList() {
        return recipeList;
    }
}
