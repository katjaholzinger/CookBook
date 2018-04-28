package com.hwr.cookbook;

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
import com.hwr.cookbook.UI.MainActivity;
import com.hwr.cookbook.UI.RegisterActivity;


public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener {

    private FirebaseAuth mAuth;

    private static final String TAG = "LoginActivity";
    public static final String MAIL = "com.hwr.kochbuch.MESSAGE_MAIL";
    public static final String PASSWORD = "com.hwr.kochbuch.MESSAGE_PASSWORD";
    public static final String UID = "com.hwr.kochbuch.MESSAGE_UID";


    private EditText textEmail;
    private EditText textPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Views
        textEmail = findViewById(R.id.textMail);
        textPassword = findViewById(R.id.textPassword);

        // Buttons
        findViewById(R.id.user_login).setOnClickListener(this);
        findViewById(R.id.user_register).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            login(currentUser.getUid());
        }
    }

    private void login(String uid) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(UID, uid);
        startActivity(intent);
    }


    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);

        //showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            login(user.getUid());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            //
                        }
                        // hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    private void signOut() {
        mAuth.signOut();
    }
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.user_register) {
            Intent intent = new Intent(this, RegisterActivity.class);
            intent.putExtra(MAIL, textEmail.getText().toString());
            intent.putExtra(PASSWORD, textPassword.getText().toString());
            startActivity(intent);
        } else if (i == R.id.user_login) {
            signIn(textEmail.getText().toString(), textPassword.getText().toString());
        }
    }
}
