package com.hwr.cookbook.UI;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.hwr.cookbook.Book;
import com.hwr.cookbook.Database;
import com.hwr.cookbook.LoginActivity;
import com.hwr.cookbook.Plan;
import com.hwr.cookbook.R;
import com.hwr.cookbook.Recipe;
import com.hwr.cookbook.RecipeMarker;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity implements
        View.OnClickListener{

    private FirebaseAuth mAuth;

    private static final String TAG = "EmailPassword";
    private EditText textEmail;
    private EditText textPassword;
    private EditText textName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String mail = intent.getStringExtra(LoginActivity.MAIL);
        String password = intent.getStringExtra(LoginActivity.PASSWORD);


        textEmail = findViewById(R.id.textMail);
        textPassword = findViewById(R.id.textPassword);
        textName = findViewById(R.id.textName);

        textEmail.setText(mail);
        textPassword.setText(password);

        findViewById(R.id.user_register).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }
    private void createAccount(final String email, final String password, final String name) {
        Log.d(TAG, "createAccount:" + email);

        //showProgressDialog();

        // [START create_user_with_email]
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            Database.setNewUser(user.getUid(), name, email);
                            Database.setNewBook(user.getUid(), new Book(getString(R.string.defaultBook), new ArrayList<String>()));
                            Database.setNewPlan(user.getUid(), new Plan(new ArrayList<RecipeMarker>()));
                            Toast.makeText(RegisterActivity.this, "Deine Registrierung war erfolgreich.",
                                    Toast.LENGTH_SHORT).show();
                            goToLogin(email, password);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.user_register) {
            createAccount(textEmail.getText().toString(), textPassword.getText().toString(),textName.getText().toString());
        }
    }

    private  void goToLogin(String mail, String password) {

        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("MAIL", mail);
        intent.putExtra("PASSWORD", password);
        startActivity(intent);
    }

}
