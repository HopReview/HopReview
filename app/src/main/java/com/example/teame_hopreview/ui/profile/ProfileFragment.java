package com.example.teame_hopreview.ui.profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.teame_hopreview.LoginActivity;
import com.example.teame_hopreview.MainActivity;
import com.example.teame_hopreview.R;
import com.example.teame_hopreview.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
                myAct.openMyCourses();
            }
        });

        TextView usernameTxt = myView.findViewById(R.id.userTxt);
        TextView emailTxt = myView.findViewById(R.id.emailTxt);

        usernameTxt.setText(myAct.user.getUserName());
        emailTxt.setText(myAct.user.getEmail());

        Button myReviewsBtn = myView.findViewById(R.id.my_reviews_btn);
        myReviewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAct.openMyReviews();
            }
        });

        Button updatePassBtn = myView.findViewById(R.id.change_pass_btn);
        updatePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Update Password");
                builder.setMessage("Are you sure that you want to update your password?" + "\n\n"
                        + "You will be sent an email to update your password shortly after");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAuth.sendPasswordResetEmail(currentUser.getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(myAct,"Reset Email Password Sent",Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(myAct,"Invalid Request",Toast.LENGTH_LONG).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(myAct,"Error Failed",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.md_theme_dark_inversePrimary));
                        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.md_theme_dark_inversePrimary));
                    }
                });
                alert.show();
            }
        });

        Button logoutBtn = myView.findViewById(R.id.logout_btn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Log Out");
                builder.setMessage("Are you sure you want to log out?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();
                        dialogInterface.dismiss();
                        Intent intent = new Intent(context, LoginActivity.class);
                        startActivity(intent);
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.md_theme_dark_inversePrimary));
                        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.md_theme_dark_inversePrimary));
                    }
                });
                alert.show();
            }
        });

        Button deleteBtn = myView.findViewById(R.id.delete_acct);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Delete Account");
                builder.setMessage("Are you sure that you want to delete your account?" + "\n\n"
                        + "Your account settings and saved preferences will be permanently gone." + "\n\n"
                        + "And more importantly we will miss you!");

                builder.setPositiveButton("Yes, delete my account", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "User account deleted.");
                                }
                            }
                        });

                        dialogInterface.dismiss();
                        Intent intent = new Intent(context, LoginActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.md_theme_dark_inversePrimary));
                        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.md_theme_dark_inversePrimary));
                    }
                });
                alert.show();

            }
        });

        return myView;
    }

}