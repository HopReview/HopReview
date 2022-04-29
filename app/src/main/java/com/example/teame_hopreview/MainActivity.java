package com.example.teame_hopreview;


import android.app.Activity;
import android.content.ComponentName;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.teame_hopreview.ui.course.CourseDetailFragment;
import com.example.teame_hopreview.ui.course.CourseFragment;
import com.example.teame_hopreview.ui.course.CourseItem;
import com.example.teame_hopreview.ui.profile.MyCoursesFragment;
import com.example.teame_hopreview.ui.home.HomeFragment;
import com.example.teame_hopreview.ui.professors.Professor;
import com.example.teame_hopreview.ui.professors.ProfessorDetailFragment;
import com.example.teame_hopreview.ui.professors.ProfessorFragment;
import com.example.teame_hopreview.ui.profile.MyReviewsFrag;
import com.example.teame_hopreview.ui.profile.ProfileFragment;
import com.example.teame_hopreview.ui.profile.UpdatePasswordFragment;
import com.example.teame_hopreview.ui.review.CreateReviewFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.teame_hopreview.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    public User user;
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

        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnItemSelectedListener(this);
        retrieveUserData();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, this.home).addToBackStack(null).commit();


    }

    public void retrieveUserData() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        user = new User(currentUser.getEmail());
        user.retrieveUserData();
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
        transaction.addToBackStack("createCourseDetailStack");
    }

    public void openProfessorDetailFragment(Professor profName) {
        transaction = getSupportFragmentManager().beginTransaction();
        ProfessorDetailFragment professorDetail = new ProfessorDetailFragment(profName);
        transaction.replace(R.id.fragment_container, professorDetail).commit();
        transaction.addToBackStack("createProfessorDetailStack");
    }

    public void openReviewDetailFragment(ReviewItem review) {
        transaction = getSupportFragmentManager().beginTransaction();
        ReviewDetailFragment reviewDetail = new ReviewDetailFragment(review);
        transaction.replace(R.id.fragment_container, reviewDetail).commit();
        transaction.addToBackStack("createReviewDetailStack");
    }

    public void openCreateReview(CourseItem course) {
        createReview = new CreateReviewFragment();
        createReview.setDefaultCourseNumber(course.getCourseNumber());
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, this.createReview).commit();
        transaction.addToBackStack("createCourseReviewStack");
    }

    public void openCreateReview(Professor prof) {
        createReview = new CreateReviewFragment();
        createReview.setDefaultProfessorName(prof.getProfessorName());
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, this.createReview).commit();
        transaction.addToBackStack("createProfessorReviewStack");
    }

    public void openMyCourses() {
        transaction = getSupportFragmentManager().beginTransaction();
        MyCoursesFragment myCourses = new MyCoursesFragment();
        transaction.replace(R.id.fragment_container, myCourses).commit();
        transaction.addToBackStack("myCoursesStack");
    }

    public void openMyReviews() {
        transaction = getSupportFragmentManager().beginTransaction();
        MyReviewsFrag myReviews = new MyReviewsFrag();
        transaction.replace(R.id.fragment_container, myReviews).commit();
        transaction.addToBackStack("myReviewsStack");
    }

    public void openUpdatePass() {
        transaction = getSupportFragmentManager().beginTransaction();
        UpdatePasswordFragment updatePass = new UpdatePasswordFragment();
        transaction.replace(R.id.fragment_container, updatePass).commit();
        transaction.addToBackStack("updatePassStack");
    }

    public void returnToHome() {
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().popBackStack();
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, this.home).addToBackStack(null).commit();
    }

}