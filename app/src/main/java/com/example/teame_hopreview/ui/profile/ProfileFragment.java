package com.example.teame_hopreview.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.teame_hopreview.LoginActivity;
import com.example.teame_hopreview.MainActivity;
import com.example.teame_hopreview.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    private MainActivity myAct;
    private Context context;
    private static final String TAG = "dbref: ";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View myView = inflater.inflate(R.layout.fragment_profile, container, false);
        context = getActivity().getApplicationContext();
        myAct = (MainActivity) getActivity();
        myAct.getSupportActionBar().setTitle("Profile");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Button myCoursesBtn = myView.findViewById(R.id.my_courses_btn);
        myCoursesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                Intent intent = new Intent(context, LoginActivity.class);
//                startActivity(intent);
            }
        });

        Button myReviewsBtn = myView.findViewById(R.id.my_reviews_btn);
        myReviewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                Intent intent = new Intent(context, LoginActivity.class);
//                startActivity(intent);
            }
        });

        SwitchMaterial toggleThemeBtn = myView.findViewById(R.id.toggle_theme);
        toggleThemeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                Intent intent = new Intent(context, LoginActivity.class);
//                startActivity(intent);
            }
        });



        Button logoutBtn = myView.findViewById(R.id.logout_btn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
            }
        });

        Button deleteBtn = myView.findViewById(R.id.delete_acct);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: add "Are you sure?" pop-up before performing action

                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User account deleted.");
                        }
                    }
                });

                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
            }
        });

        return myView;
    }

}