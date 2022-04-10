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

public class CourseFragment extends Fragment {

    private static final String TAG = "dbref: ";


    private RecyclerView myList;
    private CardView myCard;
    private MainActivity myAct;
    private CourseItem courseItem;
    protected ArrayList<CourseItem> myCourses;
    private CourseAdapter ca;
    private FirebaseDatabase mdbase;
    private DatabaseReference dbref;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View myView = inflater.inflate(R.layout.frag_course, container, false);


        context = getActivity().getApplicationContext();
        dbref = FirebaseDatabase.getInstance().getReference();

        myAct = (MainActivity) getActivity();
        myAct.getSupportActionBar().setTitle("Courses");
        System.out.println("Reached here");
        myList = (RecyclerView) myView.findViewById(R.id.myList);
        myCard = (CardView) myView.findViewById(R.id.course_card);
        myCourses = new ArrayList<CourseItem>();
        setHasOptionsMenu(true);

        ca = new CourseAdapter(myAct, context, myCourses);

        myList.setAdapter(ca);
        myList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));


        // TO DO: Firebase
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                long count = snapshot.getChildrenCount();
                Log.d(TAG, "Children count: " + count);
                Log.d(TAG, "Course count: " + snapshot.child("courses_data").getChildrenCount());

                myCourses.clear();
                Iterable<DataSnapshot> courses = snapshot.child("courses_data").getChildren();
                ArrayList<ReviewItem> reviewsHolder = new ArrayList<ReviewItem>();

                for (DataSnapshot crs : courses) {

                    System.out.println("Avg: " + crs.getValue(CourseItem.class).getAverageRating());
                    System.out.println("Fun: " + crs.getValue(CourseItem.class).getFunRating());
                    System.out.println("Work: " + crs.getValue(CourseItem.class).getWorkloadRating());
                    System.out.println("CourseNum: " + crs.getValue(CourseItem.class).getCourseNumber());
                    System.out.println("CourseDes: " + crs.getValue(CourseItem.class).getDesignation());
                    Iterable<DataSnapshot> list = crs.getChildren();
                    String name = " ";
                    String num = " ";
                    String prof = new String();
                    String designation = " ";
                    int avgRate = 0;
                    int funRate = 0;
                    int workRate = 0;

                    int counter = 1;
                    for (DataSnapshot item : list) {
                        if (counter == 1) {
                            avgRate = item.getValue(Integer.class);
                        } else if (counter == 2) {
                            designation = item.getValue(String.class);
                        } else if (counter == 3) {
                            name = item.getValue(String.class);
                        } else if (counter == 4) {
                            num = item.getValue(String.class);
                        } else if (counter == 5) {
                            funRate = item.getValue(Integer.class);
                        } else if (counter == 6) {
                            prof = item.getValue(String.class);
                        } else if (counter == 7) {
                            workRate = item.getValue(Integer.class);
                        }
                        counter++;
                    }
                    courseItem = new CourseItem(avgRate, designation, name, num, funRate, prof, workRate);
                    myCourses.add(courseItem);
                }

                ca.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

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