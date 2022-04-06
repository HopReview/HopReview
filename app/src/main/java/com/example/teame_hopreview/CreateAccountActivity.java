package com.example.teame_hopreview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateAccountActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    private FragmentTransaction transaction;
    private Fragment cFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        Button signUpBtn = (Button) findViewById(R.id.signup_btn);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        cFrag = new CourseFrag();

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
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
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
                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(CreateAccountActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                }
            });
    }

    private void updateUI(FirebaseUser user) {
        // TODO: go to new fragment and pass user!
//        transaction = getSupportFragmentManager().beginTransaction();
//        // Replace whatever is in the fragment_container view with this fragment,
//        // and add the transaction to the back stack so the user can navigate back
//        transaction.replace(R.id.text_home, this.cFrag);
//        transaction.addToBackStack(null);
//        transaction.commit();
    }

    private void checkSignUp(View view) {

        Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
        Context context = getApplicationContext();

        EditText usernameEt = (EditText) findViewById(R.id.userName_sign);
        EditText emailEt = (EditText) findViewById(R.id.email_sign);
        EditText emailConfirmEt = (EditText) findViewById(R.id.emailConfirm_sign);
        EditText passwordEt = (EditText) findViewById(R.id.password_sign);
        EditText passwordConfirmEt = (EditText) findViewById(R.id.passwordConfirm_sign);

        String username = usernameEt.getText().toString();
        String email = emailEt.getText().toString();
        String emailConfirm = emailConfirmEt.getText().toString();
        String password = passwordEt.getText().toString();
        String passwordConfirm = passwordConfirmEt.getText().toString();

        createAccount(email,password);

        /*
        if (!email.equals(emailConfirm) || password.equals(passwordConfirm)) {
            Toast.makeText(context, "Email and/or password couldn't be confirmed successfully", Toast.LENGTH_SHORT).show();
        } else if() {
            TO DO: check whether username or email already exists
            Toast.makeText(context, "Username already exists", Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "Email already registered", Toast.LENGTH_SHORT).show();
        } else if() {
            TO DO: check whether password is appropriate
            Toast.makeText(context, "Password is inadequate", Toast.LENGTH_SHORT).show();
        } else {
            TO DO: create account, update Firebase, direct to mainActivity
            startActivity(intent)
        }
        */

    }

    private void exitActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
