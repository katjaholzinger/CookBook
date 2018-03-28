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
import com.google.firebase.database.DatabaseReference;

import java.util.UUID;

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


        textEmail = findViewById(R.id.textmail);
        textPassword = findViewById(R.id.textPassword);
        textName = findViewById(R.id.textName);

        textEmail.setText(mail);
        textPassword.setText(password);

        findViewById(R.id.user_register).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }
    private void createAccount(final String email, String password, final String name) {
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

                            Database database = new Database();
                            database.setNewUser(user.getUid(), name, email);
                            Toast.makeText(RegisterActivity.this, "Deine Registrierung war erfolgreich.",
                                    Toast.LENGTH_SHORT).show();
                            setContentView(R.layout.activity_login);
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

}
