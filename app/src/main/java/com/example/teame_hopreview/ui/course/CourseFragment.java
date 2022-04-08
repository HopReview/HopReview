package com.example.teame_hopreview.ui.course;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teame_hopreview.MainActivity;
import com.example.teame_hopreview.R;

import java.util.ArrayList;

public class CourseFragment extends Fragment {

    private RecyclerView myList;
    private CardView myCard;
    private MainActivity myAct;
    private CourseItem courseItem;
    protected ArrayList<CourseItem> myCourses;
    private CourseAdapter ca;
    private DatabaseReference dbref;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbref = FirebaseDatabase.getInstance().getReference();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.frag_course, container, false);


        context = getActivity().getApplicationContext();

        myAct = (MainActivity) getActivity();
        myAct.getSupportActionBar().setTitle("Courses");
        myList = (RecyclerView) myView.findViewById(R.id.myList);
        myCard = (CardView) myView.findViewById(R.id.course_card);
        myCourses = new ArrayList<CourseItem>();

        ca = new CourseAdapter(context, myCourses);

        myList.setAdapter(ca);

        // TO DO: Firebase

        return myView;
    }
}
