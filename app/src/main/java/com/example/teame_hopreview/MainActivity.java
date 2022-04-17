package com.example.teame_hopreview;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.teame_hopreview.ui.course.CourseDetailFragment;
import com.example.teame_hopreview.ui.course.CourseFragment;
import com.example.teame_hopreview.ui.course.CourseItem;
import com.example.teame_hopreview.ui.course.MyCoursesFragment;
import com.example.teame_hopreview.ui.home.HomeFragment;
import com.example.teame_hopreview.ui.professors.Professor;
import com.example.teame_hopreview.ui.professors.ProfessorDetailFragment;
import com.example.teame_hopreview.ui.professors.ProfessorFragment;
import com.example.teame_hopreview.ui.profile.ProfileFragment;
import com.example.teame_hopreview.ui.review.CreateReviewFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.teame_hopreview.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private ActivityMainBinding binding;
    private Fragment course;
    private Fragment home;
    private Fragment professors;
    private Fragment profile;
    private CreateReviewFragment createReview;
    public BottomNavigationView bottomNavigationView;
    private FirebaseDatabase mydbase;
    private DatabaseReference dbRef;
    private FragmentTransaction transaction;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar toolbar = (Toolbar) findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        course = new CourseFragment();
        home = new HomeFragment();
        professors = new ProfessorFragment();
        profile = new ProfileFragment();
        createReview = new CreateReviewFragment();

        mydbase = FirebaseDatabase.getInstance();
        dbRef = mydbase.getReference();

//        RecyclerView recyclerView = findViewById(R.id.myList);
//        assert recyclerView != null;
//        mAdapt = new SimpleItemRecyclerViewAdapter(this, mItems, mTwoPane);
//        recyclerView.setAdapter(mAdapt);
//
//        // TODO: need to adjust for recycler CourseListFrag
//        dbRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                long count = snapshot.getChildrenCount();
//                Log.d(TAG, "Children count: " + count);
//                Log.d(TAG, "Client count: " + snapshot.child("clients").getChildrenCount());
//
//                // need to recreate the mItems list somehow
//                // another way is to use a FirebaseRecyclerView - see Sample Database code
//
//                mItems.clear();
//                Iterable<DataSnapshot> clients = snapshot.child("clients").getChildren();
//                for (DataSnapshot pair : clients) {
//                    mItems.add(pair.getValue(CourseItem.class));
//                }
//                mAdapt.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, this.home).addToBackStack(null).commit();


        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.navigation_home) {
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, this.home).commit();
            transaction.addToBackStack(null);
            return true;
        } else if (id == R.id.navigation_courses) {
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, this.course).commit();
            transaction.addToBackStack(null);
            return true;
        } else if (id == R.id.navigation_professors) {
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, this.professors).commit();
            transaction.addToBackStack(null);
            return true;
        } else if (id == R.id.navigation_profile) {
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, this.profile).commit();
            transaction.addToBackStack(null);
            return true;
        }
        return false;
    }

    public void openCourseDetailFragment(CourseItem courseName) {
        transaction = getSupportFragmentManager().beginTransaction();
        CourseDetailFragment courseDetail = new CourseDetailFragment(courseName);
        transaction.replace(R.id.fragment_container, courseDetail).commit();
        transaction.addToBackStack("createDetailStack");
    }

    public void openProfessorDetailFragment(Professor profName) {
        transaction = getSupportFragmentManager().beginTransaction();
        ProfessorDetailFragment professorDetail = new ProfessorDetailFragment(profName);
        transaction.replace(R.id.fragment_container, professorDetail).commit();
        transaction.addToBackStack("createDetailStack");
    }

    public void openCreateReview(String courseName) {
        createReview.reset();
        createReview.setDefaultCourseName(courseName);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, this.createReview).commit();
        transaction.addToBackStack("createReviewStack");
    }

    public void openMyCourses() {
        transaction = getSupportFragmentManager().beginTransaction();
        MyCoursesFragment myCourses = new MyCoursesFragment();
        transaction.replace(R.id.fragment_container, myCourses).commit();
        transaction.addToBackStack("myCoursesStack");
    }

}