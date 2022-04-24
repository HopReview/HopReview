package com.example.teame_hopreview.ui.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.teame_hopreview.MainActivity;
import com.example.teame_hopreview.R;
import com.example.teame_hopreview.ReviewAdapter;
import com.example.teame_hopreview.ReviewItem;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyReviewsFrag extends Fragment {

    private static final String TAG = "dbref: ";

    private MainActivity myAct;
    private ReviewItem reviewItem;
    private ReviewAdapter ra;
    private DatabaseReference dbref;
    Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.frag_my_reviews, container, false);

        myAct = (MainActivity) getActivity();
        myAct.getSupportActionBar().setTitle("My Reviews");
        context = myAct.getApplicationContext();
        dbref = FirebaseDatabase.getInstance().getReference();



        return myView;
    }

}
