package com.hwr.cookbook;
import android.content.Context;
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
import com.hwr.cookbook.UI.MainActivity;

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
    static  private ArrayList<Recipe> recipeList = new ArrayList<>();
    static  private Plan plan;
    private static ArrayList<Book> bookList = new ArrayList<>();
    private static ArrayList<Plan> planList = new ArrayList<>();

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
        String id= FirebaseDatabase.getInstance().getReference().child("plans").child(userID).push().getKey();
        plan.setID(id);
        FirebaseDatabase.getInstance().getReference().child("plans").child(userID).child(id).setValue(plan);
    }

    static public void setNewMarkerInPlan (String userID, String planID, RecipeMarker marker) {
        Log.d("Database", "Adding new marker to plan xxx ...");
        FirebaseDatabase.getInstance().getReference().child("plans").child(userID).child(planID).child("events").child(String.valueOf(marker.getID())).setValue(marker);

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
        book.recipes.add(recipe.id);
        FirebaseDatabase.getInstance().getReference().child("books").child(userID).child(book.id).setValue(book);
    }

    public static Recipe findRecipe(String recipeId) {
        for (Recipe r: recipeList
             ) {
            if (r.id.equals(recipeId)) {
                return r;
            }
        }
        return null;
    }

    public static Plan getPlan() {
        return plan;
    }

    public static void setPlan(Plan p) {
        plan = p;
    }

    public static void setBookList(ArrayList<Book> booklist) {
        Database.bookList = booklist;
    }

    public static void setRecipeList(ArrayList<Recipe> recipes) {
        recipeList = recipes;

    }

    public static ArrayList<Recipe> getRecipeList() {
        return recipeList;
    }

    public static ArrayList<Book> getBookList() {
        return bookList;
    }

    public static ArrayList<Plan> getPlanList() {
        return planList;
    }

    public static void setPlanList(ArrayList<Plan> planList) {
        Database.planList = planList;

        //Workarround: Nur einen Plan vorerst
        plan = planList.get(0);
    }

    public static Book findDefaultBook(Context ctx) {
        for (Book book: bookList
             ) {
            if (book.name.equals(ctx.getString(R.string.defaultBook))) {
                return book;
            }
        }
        return null;
    }
}
