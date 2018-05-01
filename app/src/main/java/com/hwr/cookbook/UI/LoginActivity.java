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
import com.hwr.cookbook.R;


public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener {

    private FirebaseAuth mAuth;

    private static final String TAG = "LoginActivity";
    public static final String MAIL = "com.hwr.kochbuch.MESSAGE";
    public static final String UID = "com.hwr.kochbuch.MESSAGE";


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
        findViewById(R.id.forgetPassword).setOnClickListener(this);

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
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.user_register) {
            Intent intent = new Intent(this, RegisterActivity.class);
            intent.putExtra(MAIL, new String[]{textEmail.getText().toString(), textPassword.getText().toString()});
            startActivity(intent);
            finish();
        } else {
            if (i == R.id.user_login) {
                signIn(textEmail.getText().toString(), textPassword.getText().toString());
            } else
                {

                if (textEmail.getText().toString().length() > 0) {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(textEmail.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this, getText(R.string.checkMail), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(LoginActivity.this, getText(R.string.enterMail), Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    @Override
    public void onBackPressed(){
        finish();
    }

}
