package com.example.teame_hopreview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        Button signUpBtn = (Button) findViewById(R.id.signup_btn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkSignUp(view);
            }
        });

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
}
