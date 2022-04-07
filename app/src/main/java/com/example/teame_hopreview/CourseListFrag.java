package com.example.teame_hopreview;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CourseListFrag extends Fragment {

    private RecyclerView myList;
    private CardView myCard;
    private MainActivity myAct;
    private Professor profItem;
    private DatabaseReference dbref;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbref = FirebaseDatabase.getInstance().getReference();


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.frag_course, container, false);

        context = getActivity().getApplicationContext();

        myAct = (MainActivity) getActivity();
        myAct.getSupportActionBar().setTitle("Courses");
        myList = (RecyclerView) myView.findViewById(R.id.myList);
        myCard = (CardView) myView.findViewById(R.id.course_card);

        return myView;
    }
}
