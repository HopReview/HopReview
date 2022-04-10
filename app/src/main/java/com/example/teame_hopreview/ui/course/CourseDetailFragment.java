package com.example.teame_hopreview.ui.course;

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
import androidx.recyclerview.widget.RecyclerView;

import com.example.teame_hopreview.MainActivity;
import com.example.teame_hopreview.R;
import com.example.teame_hopreview.ReviewAdapter;
import com.example.teame_hopreview.ReviewItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CourseDetailFragment extends Fragment {


    private static final String TAG = "dbref: ";



    private RecyclerView myList;
    private CardView myCard;
    private MainActivity myAct;
    private FloatingActionButton myFab;
    private CourseItem courseItem;
    private ReviewItem ReviewItem;
    protected ArrayList<ReviewItem> myReviews;
    Context context;
    String courseName;
    private ReviewAdapter ra;
    FirebaseDatabase mdbase;
    DatabaseReference dbref;

    public CourseDetailFragment(CourseItem course) {
        this.courseItem = course;
        this.courseName = course.getName();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbref = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.course_detail_frag, container, false);


        TextView designation = (TextView) myView.findViewById(R.id.course_designation);
        TextView courseName = (TextView) myView.findViewById(R.id.course_name);
        TextView courseNum = (TextView) myView.findViewById(R.id.course_num);
        TextView professor = (TextView) myView.findViewById(R.id.teaching_professor);

        designation.setText(courseItem.getDesignation());
        courseName.setText(courseItem.getName());
        courseNum.setText(courseItem.getCourseNumber());
        professor.setText(courseItem.getProfessors());



        context = getActivity().getApplicationContext();

        myAct = (MainActivity) getActivity();
        myList = (RecyclerView) myView.findViewById(R.id.myList);
        myCard = (CardView) myView.findViewById(R.id.review_card);
        myFab = (FloatingActionButton) myView.findViewById(R.id.floatingActionButton);
        myReviews = new ArrayList<ReviewItem>();

        ra = new ReviewAdapter(context, myReviews);

        if (ra.getItemCount() != 0) {
            myList.setAdapter(ra);
        }


        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                long count = snapshot.getChildrenCount();
                Log.d(TAG, "Children count: " + count);
                Log.d(TAG, "Courses count: " + snapshot.child("courses_data").getChildrenCount());

                myReviews.clear();
                Iterable<DataSnapshot> courses = snapshot.child("courses_data").getChildren();

                for (DataSnapshot crs : courses) {
                    Iterable<DataSnapshot> list = crs.getChildren();
                    int counter = 1;
                    for (DataSnapshot item : list) {
                        if (counter == 3 && item.getValue(String.class).equals(courseName)) {
                            courseItem = crs.getValue(CourseItem.class);
                        }
                        counter++;
                    }
                }

                if (courseItem != null && courseItem.getReviews() != null && !courseItem.getReviews().isEmpty()) {
                    myReviews = courseItem.getReviews();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(myAct, CreateReview.class);
                //intent.putExtra("course_name", courseName);

            }
        });



        return myView;
    }
}
