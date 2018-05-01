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
import java.util.Random;
import java.util.UUID;
import org.json.JSONObject;

import rx.internal.operators.OnSubscribeFromIterable;

/**
 * Created by kholzinger on 10.03.2018.
 */

public class Database {
    private static final String TAG = "DatabaseOperation";
    static DatabaseReference database;
    static DatabaseReference databaseUser;
    static User user;
    static private ArrayList<Recipe> recipeList = new ArrayList<>();
    static private Plan plan;
    private static ArrayList<Book> bookList = new ArrayList<>();
    private static ArrayList<Plan> planList = new ArrayList<>();


    private static ArrayList<Recipe> allRecipeList = new ArrayList<>();

    static public void setNewUser(String id, String name, String mail) {
        Log.d("Database", "Creating new user ...");
        User user = new User(name, mail);
        Log.d("Database", "Adding new user ...");
        FirebaseDatabase.getInstance().getReference().child("users").child(id).setValue(user);

    }

    static public String getUserName(User user) {
        if (user == null) {
            return "";
        }
        return user.username;
    }

    static public void setNewRecipe(String userID, Recipe recipe) {

        Log.d("Database", "Adding new recipe ...");
        String id = FirebaseDatabase.getInstance().getReference().child("recipes").child(userID).push().getKey();
        recipe.id = (id);
        FirebaseDatabase.getInstance().getReference().child("recipes").child(userID).child(id).setValue(recipe);
        recipeList.add(recipe);

    }

    static public void setNewPlan(String userID, Plan plan) {
        Log.d("Database", "Adding new plan ...");
        String id = FirebaseDatabase.getInstance().getReference().child("plans").child(userID).push().getKey();
        plan.id = (id);
        FirebaseDatabase.getInstance().getReference().child("plans").child(userID).child(id).setValue(plan);
    }

    static public void setNewMarkerInPlan(RecipeMarker marker) {
        Log.d("Database", "Adding new marker to plan " + plan.id + " ...");
        plan.markers.add(marker);
        FirebaseDatabase.getInstance().getReference().child("plans").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(plan.id).setValue(plan);
    }

    public static void updateMarker(RecipeMarker recipeMarker) {
        for (int i = 0; i<plan.markers.size(); i++) {

            if (plan.markers.get(i).id.equals(recipeMarker.id)) {

                plan.markers.set(i, recipeMarker);
            }
        }
        FirebaseDatabase.getInstance().getReference().child("plans").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(plan.id).setValue(plan);
    }

    static public void logout() {
        FirebaseAuth.getInstance().signOut();
    }

    static public void setNewBook(String userID, Book book) {
        Log.d("Database", "Adding new book to user xxx ...");

        String id = FirebaseDatabase.getInstance().getReference().child("books").child(userID).push().getKey();
        book.id = (id);
        FirebaseDatabase.getInstance().getReference().child("books").child(userID).child(id).setValue(book);
        bookList.add(book);
    }


    static public void addRecipeToBook(String userID, Book book, String recipeId) {
        Log.d("Database", "Adding new recipe to book xxx of user xxx ...");
        book.recipes.add(recipeId);
        FirebaseDatabase.getInstance().getReference().child("books").child(userID).child(book.id).setValue(book);
    }

    public static Recipe findRecipe(String recipeId) {
        for (Recipe r : recipeList
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

        try {
            //Workarround: Nur einen Plan vorerst
            setPlan(planList.get(0));
        } catch (Exception e) {
            Log.d("Database", e.getMessage());
        }
    }

    public static Book findDefaultBook(Context ctx) {
        for (Book book : bookList
                ) {
            if (book.name.equals(ctx.getString(R.string.defaultBook))) {
                return book;
            }
        }
        return null;
    }

    public static void updateRecipe(Recipe recipe) {
        for (int i = 0; i<recipeList.size(); i++) {

            if (recipeList.get(i).id.equals(recipe.id)) {

                recipeList.set(i, recipe);
            }
        }
        FirebaseDatabase.getInstance().getReference().child("recipes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(recipe.id).setValue(recipe);
    }

    public static void deleteRecipe(Recipe recipe) {
        recipeList.remove(recipe);
        FirebaseDatabase.getInstance().getReference().child("recipes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(recipe.id).removeValue();
    }

    public static void deleteRecipeFromBook (Recipe recipe, Book book) {
        book.recipes.remove(recipe.id);
        FirebaseDatabase.getInstance().getReference().child("books").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(book.id).child("recipes").child(recipe.id).removeValue();
    }

    public static void deleteBook(Book book) {

        for (String recipeId: book.recipes
             ) {
            addRecipeToBook(FirebaseAuth.getInstance().getCurrentUser().getUid(), findDefaultBook(null), recipeId);
        }
        bookList.remove(book);
        FirebaseDatabase.getInstance().getReference().child("books").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(book.id).removeValue();
    }

    public static void copForeignRecipeToBook(Context ctx, Recipe recipe) {
        String id = FirebaseDatabase.getInstance().getReference().child("recipes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push().getKey();
        recipe.id = (id);
        FirebaseDatabase.getInstance().getReference().child("recipes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(id).setValue(recipe);
        recipeList.add(recipe);

        Book defaultBook = findDefaultBook(ctx);
        addRecipeToBook(FirebaseAuth.getInstance().getCurrentUser().getUid(),defaultBook , recipe.id);
        defaultBook.recipes.add(recipe.id);
    }

    public static void moveToOtherBook(Book oldBook, Book newBook, Recipe r) {
    deleteRecipeFromBook(r, oldBook);
    addRecipeToBook(FirebaseAuth.getInstance().getCurrentUser().getUid(), newBook, r.id);
    }

    public static void getAllRecipes() {
    //Datenkritisch da alle Rezepte runter geladen werden. Besser wäre es zufällig einige Knoten zu selektieren.
        FirebaseDatabase.getInstance().getReference().child("recipes").orderByKey().
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<Recipe> recipeList = new ArrayList<>();
                        for (DataSnapshot child : dataSnapshot.getChildren()
                                ) {
                            for (DataSnapshot recipes : child.getChildren()
                                    ) {
                                allRecipeList.add(recipes.getValue(Recipe.class));
                                Log.d("AllRecipes", recipes.getValue(Recipe.class).name);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        }

    public static ArrayList<Recipe> getRandomRecipeList() {
        Log.d("Database", "getRandomRecipeList");
        if (allRecipeList.size() > 0) {
            Log.d("RandomRecipe", "größer als 0");
            Random r = new Random();
            ArrayList<Recipe> randomRecipeList = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                Recipe re = allRecipeList.get(r.nextInt(allRecipeList.size()));
                randomRecipeList.add(re);
                Log.d("RandomRecipe", re.name);
        }
            return randomRecipeList;
        }
        else {
            return null;
        }
    }
}
