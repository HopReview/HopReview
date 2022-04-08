package com.example.teame_hopreview.ui.course;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teame_hopreview.CoursedbAdapter;
import com.example.teame_hopreview.MainActivity;
import com.example.teame_hopreview.R;

import java.util.ArrayList;

public class CourseFragment extends Fragment {

    private RecyclerView myList;
    private CardView myCard;
    private MainActivity myAct;
    private CourseItem courseItem;
    protected ArrayList<CourseItem> myCourses;
    Context context;
    protected static CoursedbAdapter dbAdapt;
    private CourseAdapter ca;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.frag_course, container, false);


        context = getActivity().getApplicationContext();

        // dbAdapt = CoursedbAdapter.getInstance(context);
        // dbAdapt.open();

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
