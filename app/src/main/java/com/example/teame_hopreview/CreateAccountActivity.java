package com.example.teame_hopreview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.teame_hopreview.ui.course.CourseFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateAccountActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    private FragmentTransaction transaction;
    private Fragment cFrag;
    private Context context;
    private String email;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        Button signUpBtn = (Button) findViewById(R.id.signup_btn);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            updateUI(currentUser);
        }

        cFrag = new CourseFragment();

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkSignUp(view);
            }
        });

        ImageButton backBtn = (ImageButton) findViewById(R.id.back_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitActivity();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        createUserData(email, username);
                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(CreateAccountActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    private void updateUI(FirebaseUser user) {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }

    private void checkSignUp(View view) {

        Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
        Context context = getApplicationContext();

        TextInputEditText usernameEt = findViewById(R.id.userName_sign);
        TextInputEditText emailEt = findViewById(R.id.email_sign);
        TextInputEditText emailConfirmEt =  findViewById(R.id.emailConfirm_sign);
        TextInputEditText passwordEt =  findViewById(R.id.password_sign);
        TextInputEditText passwordConfirmEt = findViewById(R.id.passwordConfirm_sign);

        username = usernameEt.getText().toString();
        email = emailEt.getText().toString();
        String emailConfirm = emailConfirmEt.getText().toString();
        String password = passwordEt.getText().toString();
        String passwordConfirm = passwordConfirmEt.getText().toString();

        if (!email.equals(emailConfirm) || !password.equals(passwordConfirm)) {
            Toast.makeText(context, "Email and/or password could not be verified successfully", Toast.LENGTH_SHORT).show();
        }
        else {
            createAccount(email,password);
        }

    }

    private void exitActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void createUserData(String email, String username) {
        User user = new User(username, email);
        user.updateEmail();
        user.updateUsername();
    }
}
