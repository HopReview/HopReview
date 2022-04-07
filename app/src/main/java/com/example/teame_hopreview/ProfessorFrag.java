package com.example.teame_hopreview;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfessorFrag extends Fragment {

    private ListView myList;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate layout for fragment
        View myView = inflater.inflate(R.layout.fragment_professors, container, false);
        context = getActivity().getApplicationContext();
        myAct = (MainActivity) getActivity();
        // set title
        myAct.getSupportActionBar().setTitle("Professors");

        // get the recycler view for the list
        // TODO: create separate xml from course
        myList = (ListView) myView.findViewById(R.id.myList);

        return myView;
    }
}
