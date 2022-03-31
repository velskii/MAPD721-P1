package com.zfl.mapd721_p1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    float x1, x2, y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Initialize firebaseStore
        db = FirebaseFirestore.getInstance();

        TextView linkLogin = findViewById(R.id.linkLogin);
        linkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        Button btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                EditText edit_username = (EditText) findViewById(R.id.user_name_register);
                String username = edit_username.getText().toString();

                EditText edit_email = (EditText) findViewById(R.id.user_email_register);
                String email = edit_email.getText().toString();

                EditText edit_password = (EditText) findViewById(R.id.user_password_register);
                String pwd = edit_password.getText().toString();

                EditText edit_repassword = (EditText) findViewById(R.id.user_repassword_register);
                String rpwd = edit_repassword.getText().toString();

                if ( username.isEmpty() ) {
                    edit_username.setError("Please Enter the username");
                } else if ( !Patterns.EMAIL_ADDRESS.matcher( email ).matches() ) {
                    edit_email.setError("Please Enter the Email with right format");
                } else if ( pwd.length() < 6 ) {
                    edit_password.setError("Please Enter password more than 6 digits");
                } else if ( !rpwd.contentEquals(pwd) ) {
                    edit_repassword.setError("Please Enter the same password");
                } else {
                    createAccount( email, pwd, username );
                }
            }

        });
    }

    private void createAccount( String email, String password, String username) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI( user, username );
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null, null );
                        }
                    }
                });
    }

    private void updateUI( FirebaseUser user, String username ) {

        if(user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            boolean emailVerified = user.isEmailVerified();
            String uid = user.getUid();
        }

        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
    }

    public boolean onTouchEvent(MotionEvent touchEvent) {
        switch (touchEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if (x1 + 10 < x2) {
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                } else if (x1 > x2) {
                    // swipe left
                }
                break;

        }
        return false;
    }

}
