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
import java.util.Map;
import java.util.UUID;

/**
 * Created by kholzinger on 10.03.2018.
 */

public class Database {
    private static final String TAG = "DatabaseOperation";
    static DatabaseReference database;
    static DatabaseReference databaseUser;
    static User user;
    static private String currentUserId;
    static private List<Recipe> recipeList;
    static private List<Plan> planList;
    static private List<Book> bookList;


    static public void newListener() {
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        DatabaseReference userRef = database.child("users");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot usersnapshot: snapshot.getChildren()
                     ) {
                    user= usersnapshot.getValue(User.class);
                    Log.d("ValueListener", usersnapshot.getValue().toString());
                }
                //user = snapshot.getValue(User.class);
                Log.d("ValueListener", snapshot.getValue().toString());
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

                final DatabaseReference recipesRef = database.child("recipes").child(currentUserId);
                recipeList = new ArrayList<>();
                recipesRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        //recipeList.clear();
                        // for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        //     Recipe recipe = postSnapshot.getValue(Recipe.class);
                        //    recipeList.add(recipe);
                        //}
                        int i= 0;
                        recipeList.clear();
                        for (DataSnapshot recipeSnapshot:  snapshot.getChildren()
                                ) {
                            i++;
                            //Map<String,Object> recipe = (Map<String,Object>) recipeSnapshot.getValue();
                            //Log.d("ValueListener", i+ ". :" + recipeSnapshot.getValue().toString());
                            //Log.d("ValueListener", i+ ". :" + recipe.values().toString());
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
        recipe.setID(id);
        FirebaseDatabase.getInstance().getReference().child("recipes").child(userID).child(id).setValue(recipe);
    }

    static  public void setNewPlan (String userID, Plan plan) {
        Log.d("Database", "Adding new plan ...");
        database.child("plans").child(userID).child(plan.getID()).setValue(plan);
    }

    static public void setNewMarkerInPlan (String userID, String planID, RecipeMarker marker) {
        Log.d("Database", "Adding new marker to plan xxx ...");
        database.child("plans").child(userID).child(planID).child("events").child(marker.getID()).push().setValue(marker);
    }

    static public List<Book> getBookList() {
        return bookList;
    }

    static public void logout() {
        FirebaseAuth.getInstance().signOut();
    }

    static public void setNewBook (String userID, Book book) {
        Log.d("Database", "Adding new book to user xxx ...");
        database.child("books").child(userID).child(book.getID()).push().setValue(book);
    }



    static public void addRecipeToBook (String userID, Book book, Recipe recipe) {
        Log.d("Database", "Adding new recipe to book xxx of user xxx ...");
        database.child("books").child(userID).child(book.getID()).push().setValue(recipe.getID());
    }

    static public Recipe findRecipe(String id) {
        for (Recipe r: recipeList) {
           if (r.getID() == id) {
               return r;
           }
        }
        return new Recipe();
    }

}
