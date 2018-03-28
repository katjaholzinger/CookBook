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

import java.util.UUID;

/**
 * Created by kholzinger on 10.03.2018.
 */

class Database {
    private static final String TAG = "DatabaseOperation";
    DatabaseReference database;
    DatabaseReference databaseUser;
    User user;
    public Database() {
        database = FirebaseDatabase.getInstance().getReference();
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
        String recipeID = UUID.randomUUID().toString();
        FirebaseDatabase.getInstance().getReference().setValue("recipes");
        FirebaseDatabase.getInstance().getReference().child("recipes").setValue(userID);
        FirebaseDatabase.getInstance().getReference().child("recipes").child(userID).child(recipeID).setValue(recipe);
    }

    public void setNewPlan (String userID, Plan plan) {
        Log.d("Database", "Adding new plan ...");
        String planID = UUID.randomUUID().toString();
        database.child("plans").child(userID).child(planID).setValue(plan);
    }

    public void setNewMarker (String userID, String planID, RecipeMarker marker) {
        Log.d("Database", "Adding new marker to plan xxx ...");
        String markerID = UUID.randomUUID().toString();
        database.child("plans").child(userID).child(planID).child("events").setValue(marker);
    }

}
