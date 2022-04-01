package com.example.teame_hopreview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {


    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Button logInBtn = (Button) findViewById(R.id.login_btn);
        TextView createAccountTxt = (TextView) findViewById(R.id.createAccount);

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogIn(view);
            }
        });

        createAccountTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });
    }

    public void checkLogIn(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        // get user entered details
        EditText emailEt = (EditText) findViewById(R.id.email);
        EditText passwordEt = (EditText) findViewById(R.id.password);

        Context context = getApplicationContext();

        email = emailEt.getText().toString();
        password = passwordEt.getText().toString();

        /*
        if (email.equals()) {
            startActivity(intent);
        } else {
            Toast toast = Toast.makeText(context, "Email and/or password does not match"
            , Toast.LENGTH_SHORT).show();
        }
        */

    }
}
