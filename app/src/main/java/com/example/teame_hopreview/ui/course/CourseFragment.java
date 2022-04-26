package com.example.teame_hopreview.ui.course;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.example.teame_hopreview.ReviewItem;
import com.example.teame_hopreview.database.Course;
import com.example.teame_hopreview.database.CoursesOnChangeListener;
import com.example.teame_hopreview.database.DbManager;
import com.example.teame_hopreview.database.Review;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teame_hopreview.MainActivity;
import com.example.teame_hopreview.R;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import kotlin.DslMarker;

public class CourseFragment extends Fragment {

    private static final String TAG = "dbref: ";

    private RecyclerView myList;
    private CardView myCard;
    private MainActivity myAct;
    protected ArrayList<Course> myCourses;
    protected ArrayList<Course> myCoursesCopy;
    protected ArrayList<Review> myReviews;
    private ArrayList<String> professors;

    private CourseAdapter ca;
    private DbManager dbManager;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View myView = inflater.inflate(R.layout.frag_course, container, false);

        context = getActivity().getApplicationContext();


        myAct = (MainActivity) getActivity();
        myAct.getSupportActionBar().setTitle("Courses");
        myList = (RecyclerView) myView.findViewById(R.id.myReviewsList);
        myCard = (CardView) myView.findViewById(R.id.course_card);

        setupData();

        setHasOptionsMenu(true);

        ca = new CourseAdapter(myAct, context, myCourses, myCoursesCopy);

        myList.setAdapter(ca);
        myList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

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
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ca.getFilter().filter(newText);
                return false;
            }
        });

    }

    private void setupData() {
        professors = new ArrayList<>();
        myCourses = new ArrayList<>();
        myCoursesCopy = new ArrayList<>();
        dbManager = DbManager.getDbManager();
        myCourses.addAll(dbManager.getCourses());
        myCoursesCopy.addAll(dbManager.getCourses());
        dbManager.addCoursesOnChangeListener(new CoursesOnChangeListener() {
            @Override
            public void onChange(List<Course> newCourses) {
                myCoursesCopy.clear();
                myCoursesCopy.clear();
                myCourses.addAll(dbManager.getCourses());
                myCoursesCopy.addAll(dbManager.getCourses());
                ca.notifyDataSetChanged();
            }
        });
    }


}