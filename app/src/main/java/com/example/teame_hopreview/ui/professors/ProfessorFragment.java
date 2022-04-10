package com.example.teame_hopreview.ui.professors;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teame_hopreview.MainActivity;
import com.example.teame_hopreview.R;
import com.example.teame_hopreview.ui.course.CourseAdapter;
import com.example.teame_hopreview.ui.course.CourseItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfessorFragment extends Fragment {

    private static final String TAG = "DB_REF: ";

    private RecyclerView myList;
    private CardView myCard;
    private MainActivity myAct;
    private CourseItem courseItem;
    protected ArrayList<Professor> myProfessors;
    private CourseAdapter ca;
    private FirebaseDatabase mdbase;
    private DatabaseReference dbref;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbref = FirebaseDatabase.getInstance().getReference();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_professors, container, false);
        context = getActivity().getApplicationContext();
        myAct = (MainActivity) getActivity();
        myAct.getSupportActionBar().setTitle("Professors");

        myList = (RecyclerView) myView.findViewById(R.id.myList);
        myCard = (CardView) myView.findViewById(R.id.course_card);
        myProfessors = new ArrayList<>();
        setHasOptionsMenu(true);

//        ca = new ProfessorAdapter(context, myProfessors);
//        myList.setAdapter(ca);

//        dbref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                long count = snapshot.getChildrenCount();
//                Log.d(TAG, "Children count: " + count);
//                Log.d(TAG, "Client count: " + snapshot.child("clients").getChildrenCount());
//
//                myProfessors.clear();
//                Iterable<DataSnapshot> professors = snapshot.child("professors").getChildren();
//
//                for (DataSnapshot prf : professors) {
//                    myProfessors.add(prf.getValue(Professor.class));
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });

        return myView;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.bar_menu, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView sv = new SearchView(((myAct.getSupportActionBar().getThemedContext())));
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        item.setActionView(sv);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("search query submit");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("tap");
                return false;
            }
        });

    }

}