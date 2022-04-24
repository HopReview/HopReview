package com.example.teame_hopreview.ui.profile;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.teame_hopreview.MainActivity;
import com.example.teame_hopreview.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePasswordFragment extends Fragment {
    private MainActivity myAct;
    private FirebaseAuth mAuth;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View myView = inflater.inflate(R.layout.fragment_update_password, container, false);
        myAct = (MainActivity) getActivity();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        TextView currentPass = (TextView) myView.findViewById(R.id.currentPassword);
        TextView newPass = (TextView) myView.findViewById(R.id.newPassword);
        TextView confirmNewPass = (TextView) myView.findViewById(R.id.confirmNewPassword);

        currentPass.setText(currentPass.getText().toString());
        newPass.setText(newPass.getText().toString());
        confirmNewPass.setText(confirmNewPass.getText().toString());

        mAuth.sendPasswordResetEmail(currentUser.getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    // if isSuccessful then done message will be shown
                    // and you can change the password
                    Toast.makeText(myAct,"Done sent",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(myAct,"Error Occured",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(myAct,"Error Failed",Toast.LENGTH_LONG).show();
            }
        });

        return myView;
    }

}
