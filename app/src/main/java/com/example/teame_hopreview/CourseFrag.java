package com.example.teame_hopreview;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class CourseFrag extends Fragment {

    private ListView myList;
    private CardView myCard;
    private MainActivity myAct;
    private CourseItem courseItem;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate layout for fragment
        View myView = inflater.inflate(R.layout.frag_course, container, false);
        context = getActivity().getApplicationContext();
        myAct = (MainActivity) getActivity();
        // set title
        myAct.getSupportActionBar().setTitle("Courses");

        // get the recycler view for the list
        myList = (ListView) myView.findViewById(R.id.myList);

        return myView;
    }
}
