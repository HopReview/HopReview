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

import com.example.teame_hopreview.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private ActivityMainBinding binding;
    private Fragment course;
    private Fragment home;
    private Fragment professors;
    private Fragment profile;
    public BottomNavigationView bottomNavigationView;
    private FirebaseDatabase mydbase;
    private DatabaseReference dbRef;

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