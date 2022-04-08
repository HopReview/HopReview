package com.example.teame_hopreview.ui.course;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teame_hopreview.MainActivity;
import com.example.teame_hopreview.R;
import com.example.teame_hopreview.ReviewAdapter;
import com.example.teame_hopreview.ReviewItem;

import java.util.ArrayList;

public class CourseDetailFragment extends Fragment {

    private RecyclerView myList;
    private CardView myCard;
    private MainActivity myAct;
    private ReviewItem ReviewItem;
    protected ArrayList<ReviewItem> myReviews;
    Context context;
    private ReviewAdapter ra;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.course_detail_frag, container, false);


        context = getActivity().getApplicationContext();

        // dbAdapt = CoursedbAdapter.getInstance(context);
        // dbAdapt.open();

        myAct = (MainActivity) getActivity();
        myAct.getSupportActionBar().setTitle("Courses");
        myList = (RecyclerView) myView.findViewById(R.id.myList);
        myCard = (CardView) myView.findViewById(R.id.course_card);
        myReviews = new ArrayList<ReviewItem>();

        ra = new ReviewAdapter(context, myReviews);

        myList.setAdapter(ra);

        // TO DO: Firebase

        return myView;
    }
}
