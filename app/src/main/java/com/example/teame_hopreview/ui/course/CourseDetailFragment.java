package com.example.teame_hopreview.ui.course;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teame_hopreview.CreateReview;
import com.example.teame_hopreview.MainActivity;
import com.example.teame_hopreview.R;
import com.example.teame_hopreview.ReviewAdapter;
import com.example.teame_hopreview.ReviewItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CourseDetailFragment extends Fragment {


    private static final String TAG = "dbref: ";



    private RecyclerView myList;
    private CardView myCard;
    private MainActivity myAct;
    private FloatingActionButton myFab;
    private ReviewItem ReviewItem;
    protected ArrayList<ReviewItem> myReviews;
    Context context;
    private ReviewAdapter ra;
    FirebaseDatabase mdbase;
    DatabaseReference dbref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.course_detail_frag, container, false);


        context = getActivity().getApplicationContext();

        // dbAdapt = CoursedbAdapter.getInstance(context);
        // dbAdapt.open();

        myAct = (MainActivity) getActivity();
        myList = (RecyclerView) myView.findViewById(R.id.myList);
        myCard = (CardView) myView.findViewById(R.id.course_card);
        myFab = (FloatingActionButton) myView.findViewById(R.id.floatingActionButton);
        myReviews = new ArrayList<ReviewItem>();

        ra = new ReviewAdapter(context, myReviews);

        myList.setAdapter(ra);

        // TO DO: Firebase

        mdbase = FirebaseDatabase.getInstance();
        dbref = mdbase.getReference();





        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(myAct, CreateReview.class);
                intent.putExtra("isCourse", true);

            }
        });



        return myView;
    }
}
