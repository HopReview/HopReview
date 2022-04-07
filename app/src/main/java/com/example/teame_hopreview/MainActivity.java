package com.example.teame_hopreview;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.teame_hopreview.ui.course.CourseFragment;
import com.example.teame_hopreview.ui.home.HomeFragment;
import com.example.teame_hopreview.ui.professors.ProfessorFragment;
import com.example.teame_hopreview.ui.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teame_hopreview.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private ActivityMainBinding binding;
    private Fragment course;
    private Fragment home;
    private Fragment professors;
    private Fragment profile;
    public BottomNavigationView bottomNavigationView;
    private FirebaseDatabase mydbase;
    private DatabaseReference dbRef;
//    private SimpleItemRecyclerViewAdapter mAdapt;

    // TODO: for testing only, remove later
    private static final String TAG = "DB_REF: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

        getSupportFragmentManager().beginTransaction().add(R.id.container, this.home).commit();

        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnItemSelectedListener(this);

    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.navigation_home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, this.home).commit();
            return true;
        } else if (id == R.id.navigation_courses) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, this.course).commit();
            return true;
        } else if (id == R.id.navigation_professors) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, this.professors).commit();
            return true;
        } else if (id == R.id.navigation_profile) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, this.profile).commit();
            return true;
        }
        return false;
    }

}