package com.example.teame_hopreview.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teame_hopreview.MainActivity;
import com.example.teame_hopreview.R;
import com.example.teame_hopreview.ReviewAdapter;
import com.example.teame_hopreview.ReviewItem;
import com.example.teame_hopreview.User;
import com.example.teame_hopreview.databinding.FragmentHomeBinding;
import com.example.teame_hopreview.ui.course.CourseItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private ArrayList<String> recentlyViewed;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_home, container, false);
        myAct = (MainActivity) getActivity();
        context = myAct.getApplicationContext();
        myAct.getSupportActionBar().setTitle("Home");
        dbref = FirebaseDatabase.getInstance().getReference();

        recentlyViewed = new ArrayList<>();


        courseCards[0] = myView.findViewById(R.id.course1);
        courseCards[1] = myView.findViewById(R.id.course2);
        courseCards[2] = myView.findViewById(R.id.course3);

        // For now, otherwise have a shared preference for it
        // and create or initilize from database upon login/signup
        User currUser = new User("bluejay01", "bluejay01@jhu.edu");


        return myView;
    }

}