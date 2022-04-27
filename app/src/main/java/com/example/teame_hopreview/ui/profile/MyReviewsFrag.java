package com.example.teame_hopreview.ui.profile;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teame_hopreview.MainActivity;
import com.example.teame_hopreview.R;
import com.example.teame_hopreview.ReviewAdapter;
import com.example.teame_hopreview.ReviewItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyReviewsFrag extends Fragment {

    private static final String TAG = "dbref: ";

    private MainActivity myAct;
    private RecyclerView myList;
    private CardView myCard;
    private ReviewItem reviewItem;
    private ArrayList<ReviewItem> myReviews;
    private DatabaseReference dbref;
    private ReviewUserAdapter rua;
    Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.frag_my_reviews, container, false);

        myAct = (MainActivity) getActivity();
        myAct.getSupportActionBar().setTitle("My Reviews");
        context = myAct.getApplicationContext();
        dbref = FirebaseDatabase.getInstance().getReference();
        myList = (RecyclerView) myView.findViewById(R.id.myReviewsList);
        myReviews = new ArrayList<>();
        setHasOptionsMenu(true);
        myAct.user.retrieveUserData();


        myReviews.addAll(myAct.user.getUserReviews());
        rua = new ReviewUserAdapter(myAct, context, myReviews);
        myList.setAdapter(rua);
        myList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rua.notifyDataSetChanged();
        return myView;
    }

}
