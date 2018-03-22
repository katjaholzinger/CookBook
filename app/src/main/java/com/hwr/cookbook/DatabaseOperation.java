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

    public String getUserName (String id) {
        databaseUser = database.child(id);
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                user = dataSnapshot.getValue(User.class);
                // [START_EXCLUDE]

                // [END_EXCLUDE]
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseUser.addValueEventListener(userListener);
        return user.username;
    }

    public void setNewRecipe (String userID, String recipeID, Recipe recipe) {

        Log.d("Database", "Adding new recipe ...");
        database.child("recipes").child(userID).child(recipeID).setValue(recipe);
    }

    public void setNewPlan (String userID, String planID, Plan plan) {
        Log.d("Database", "Adding new plan ...");
        database.child("plans").child(userID).child(planID).setValue(plan);
    }

    public void setNewMarker (String userID, String planID, String markerID, RecipeMarker marker) {
        Log.d("Database", "Adding new marker to plan xxx ...");
        database.child("plans").child(userID).child(planID).child("events").setValue(marker);
    }

}
