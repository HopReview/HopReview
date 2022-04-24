package com.example.teame_hopreview.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.teame_hopreview.MainActivity;
import com.example.teame_hopreview.R;
import com.example.teame_hopreview.ReviewItem;
import com.example.teame_hopreview.User;
import com.example.teame_hopreview.databinding.FragmentHomeBinding;
import com.example.teame_hopreview.ui.course.CourseItem;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private static final String TAG = "dbref: ";

    private CardView myCard;
    private CourseItem courseItem;
    private ReviewItem reviewItem;
    DatabaseReference dbref;
    private MainActivity myAct;
    private Context context;
    private CardView[] courseCards = new CardView[3];
    private CardView[] reviewCards = new CardView[3];

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_home, container, false);
        myAct = (MainActivity) getActivity();
        context = myAct.getApplicationContext();
        myAct.getSupportActionBar().setTitle("Home");
        dbref = FirebaseDatabase.getInstance().getReference();



        courseCards[0] = myView.findViewById(R.id.course1);
        courseCards[1] = myView.findViewById(R.id.course2);
        courseCards[2] = myView.findViewById(R.id.course3);
        reviewCards[0] = myView.findViewById(R.id.review1);
        reviewCards[1] = myView.findViewById(R.id.review2);
        reviewCards[2] = myView.findViewById(R.id.review3);

        String firstCourse = "";
        String secondCourse = "";
        String thirdCourse = "";
        ArrayList<String> recentlyViewed = myAct.user.getRecentlyViewedList();
        if (recentlyViewed != null) {
            if (recentlyViewed.size() == 1) {
                firstCourse = recentlyViewed.get(0);
                // set the course card details
            } else if (recentlyViewed.size() == 2) {
                firstCourse = recentlyViewed.get(1);
                secondCourse = recentlyViewed.get(0);
                // set the course card details
            } else if (recentlyViewed.size() == 3) {
                firstCourse = recentlyViewed.get(2);
                secondCourse = recentlyViewed.get(1);
                thirdCourse = recentlyViewed.get(0);
                // set the course card details

            }
        } else {
            courseCards[0].setVisibility(View.GONE);
            courseCards[1].setVisibility(View.GONE);
            courseCards[2].setVisibility(View.GONE);
        }





        // For now, otherwise have a shared preference for it
        // and create or initilize from database upon login/signup
        User currUser = new User("bluejay01", "bluejay01@jhu.edu");


        return myView;
    }

}