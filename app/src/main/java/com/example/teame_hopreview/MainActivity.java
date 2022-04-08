package com.example.teame_hopreview;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.teame_hopreview.ui.course.CourseFragment;
import com.example.teame_hopreview.ui.home.HomeFragment;
import com.example.teame_hopreview.ui.professors.ProfessorFragment;
import com.example.teame_hopreview.ui.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.teame_hopreview.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private ActivityMainBinding binding;
    private Fragment course;
    private Fragment home;
    private Fragment professors;
    private Fragment profile;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search");


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.navigation_home) {
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, this.home).commit();
            getSupportActionBar().show();
            searchView.clearFocus();
            transaction.addToBackStack(null);
            return true;
        } else if (id == R.id.navigation_courses) {
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, this.course).commit();
            getSupportActionBar().show();
            searchView.setQuery("", false);
            searchView.clearFocus();
            transaction.addToBackStack(null);
            return true;
        } else if (id == R.id.navigation_professors) {
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, this.professors).commit();
            getSupportActionBar().show();
            searchView.setQuery("", false);
            searchView.clearFocus();
            transaction.addToBackStack(null);
            return true;
        } else if (id == R.id.navigation_profile) {
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, this.profile).commit();
            getSupportActionBar().hide();
            searchView.setQuery("", false);
            searchView.clearFocus();
            transaction.addToBackStack(null);
            return true;
        }
        return false;
    }

}