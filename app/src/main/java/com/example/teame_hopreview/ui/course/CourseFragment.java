package com.example.teame_hopreview.ui.course;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teame_hopreview.CourseItem;
import com.example.teame_hopreview.MainActivity;
import com.example.teame_hopreview.R;

public class CourseFragment extends Fragment {

    private RecyclerView myList;
    private CardView myCard;
    private MainActivity myAct;
    private CourseItem courseItem;
    Context context;
    protected static CoursedbAdapter dbAdapt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate layout for fragment
        View myView = inflater.inflate(R.layout.frag_course, container, false);
        context = getActivity().getApplicationContext();
        myAct = (MainActivity) getActivity();


        // get the recycler view for the list

        myList = (RecyclerView) myView.findViewById(R.id.myList);


        return myView;
    }
}
