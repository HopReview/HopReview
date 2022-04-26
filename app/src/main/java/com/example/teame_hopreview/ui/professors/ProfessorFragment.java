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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teame_hopreview.MainActivity;
import com.example.teame_hopreview.R;
import com.example.teame_hopreview.database.DbManager;
import com.example.teame_hopreview.database.ProfessorsOnChangeListener;
import com.example.teame_hopreview.database.Review;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProfessorFragment extends Fragment {

    private static final String TAG = "dbref: ";

    //Views
    private RecyclerView myList;
    private CardView myCard;
    private MainActivity myAct;

    //Models
    private com.example.teame_hopreview.database.Professor professorItem;
    private Review reviewItem;
    protected ArrayList<com.example.teame_hopreview.database.Professor> myProfessors;
    protected ArrayList<com.example.teame_hopreview.database.Professor> myProfessorsCopy;
    protected ArrayList<Review> myReviews;

    //Adapter and misc
    private ProfessorAdapter pa;
    private DbManager dbManager;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View myView = inflater.inflate(R.layout.fragment_professors, container, false);

        context = getActivity().getApplicationContext();
        myAct = (MainActivity) getActivity();
        myAct.getSupportActionBar().setTitle("Professors");
        setHasOptionsMenu(true);

        setupData();
        setupViews(myView);
        setupAdapters();

        return myView;
    }

    private void setupData() {
        dbManager = DbManager.getDbManager();
        myProfessors = new ArrayList<com.example.teame_hopreview.database.Professor>();
        myProfessorsCopy = new ArrayList<com.example.teame_hopreview.database.Professor>();
        myReviews = new ArrayList<Review>();
        dbManager.addProfessorsOnChangeListener(new ProfessorsOnChangeListener() {
            @Override
            public void onChange(List<com.example.teame_hopreview.database.Professor> newProfessors) {
                myProfessors.clear();
                myProfessorsCopy.clear();
                myProfessors.addAll(dbManager.getProfessors());
                myProfessorsCopy.addAll(dbManager.getProfessors());
                pa.notifyDataSetChanged();
            }
        });
    }

    private void setupViews(View myView) {
        myList = (RecyclerView) myView.findViewById(R.id.myReviewsList);
        myCard = (CardView) myView.findViewById(R.id.professor_card);
    }

    private void setupAdapters() {
        pa = new ProfessorAdapter(myAct, context, myProfessors, myProfessorsCopy);
        myList.setAdapter(pa);
        myList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
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
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                pa.getFilter().filter(newText);
                return false;
            }
        });

    }

}