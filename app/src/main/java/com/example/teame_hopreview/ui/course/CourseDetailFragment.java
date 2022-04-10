package com.example.teame_hopreview.ui.course;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
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
        professor.setText("Taught by: " + courseItem.getProfessors());

        ImageView[] avgStars = new ImageView[5];
        ImageView[] workStars = new ImageView[5];
        ImageView[] funStars = new ImageView[5];
        ImageView[] gradeStars = new ImageView[5];
        ImageView[] knowStars = new ImageView[5];

        avgStars[0] = (ImageView) myView.findViewById(R.id.det_avg_1);
        avgStars[1] = (ImageView) myView.findViewById(R.id.det_avg_2);
        avgStars[2] = (ImageView) myView.findViewById(R.id.det_avg_3);
        avgStars[3] = (ImageView) myView.findViewById(R.id.det_avg_4);
        avgStars[4] = (ImageView) myView.findViewById(R.id.det_avg_5);

        workStars[0] = (ImageView) myView.findViewById(R.id.det_work_1);
        workStars[1] = (ImageView) myView.findViewById(R.id.det_work_2);
        workStars[2] = (ImageView) myView.findViewById(R.id.det_work_3);
        workStars[3] = (ImageView) myView.findViewById(R.id.det_work_4);
        workStars[4] = (ImageView) myView.findViewById(R.id.det_work_5);

        funStars[0] = (ImageView) myView.findViewById(R.id.det_fun_1);
        funStars[1] = (ImageView) myView.findViewById(R.id.det_fun_2);
        funStars[2] = (ImageView) myView.findViewById(R.id.det_fun_3);
        funStars[3] = (ImageView) myView.findViewById(R.id.det_fun_4);
        funStars[4] = (ImageView) myView.findViewById(R.id.det_fun_5);

        gradeStars[0] = (ImageView) myView.findViewById(R.id.det_gr_1);
        gradeStars[1] = (ImageView) myView.findViewById(R.id.det_gr_2);
        gradeStars[2] = (ImageView) myView.findViewById(R.id.det_gr_3);
        gradeStars[3] = (ImageView) myView.findViewById(R.id.det_gr_4);
        gradeStars[4] = (ImageView) myView.findViewById(R.id.det_gr_5);

        knowStars[0] = (ImageView) myView.findViewById(R.id.det_know_1);
        knowStars[1] = (ImageView) myView.findViewById(R.id.det_know_2);
        knowStars[2] = (ImageView) myView.findViewById(R.id.det_know_3);
        knowStars[3] = (ImageView) myView.findViewById(R.id.det_know_4);
        knowStars[4] = (ImageView) myView.findViewById(R.id.det_know_5);

        for (int i = 0; i < 5; i++) {
            if (i < courseItem.getAverageRating()) {
                avgStars[i].setVisibility(View.VISIBLE);
                // just for now, until we implement professors
                gradeStars[i].setVisibility(View.VISIBLE);
                knowStars[i].setVisibility(View.VISIBLE);
            } else {
                avgStars[i].setVisibility(View.INVISIBLE);
                // just for now, until we implement professors
                gradeStars[i].setVisibility(View.INVISIBLE);
                knowStars[i].setVisibility(View.INVISIBLE);
            }

            if (i < courseItem.getWorkloadRating()) {
                workStars[i].setVisibility(View.VISIBLE);
            } else {
                workStars[i].setVisibility(View.INVISIBLE);
            }

            if (i < courseItem.getFunRating()) {
                workStars[i].setVisibility(View.VISIBLE);
            } else {
                workStars[i].setVisibility(View.INVISIBLE);
            }
        }

        context = getActivity().getApplicationContext();

        myAct = (MainActivity) getActivity();
        myList = (RecyclerView) myView.findViewById(R.id.recyclerView);
        myCard = (CardView) myView.findViewById(R.id.review_card);
        myFab = (FloatingActionButton) myView.findViewById(R.id.floatingActionButton);
        myReviews = new ArrayList<ReviewItem>();

        ra = new ReviewAdapter(myAct, context, myReviews);

        myList.setAdapter(ra);
        myList.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));


        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                long count = snapshot.getChildrenCount();
                Log.d(TAG, "Children count: " + count);
                Log.d(TAG, "Courses count: " + snapshot.child("courses_data").getChildrenCount());

                myReviews.clear();
                Iterable<DataSnapshot> courses = snapshot.child("courses_data").getChildren();
                System.out.println("NAME VALUE " + courseItem.getName());

                if (courseItem != null && courseItem.getReviews() != null && !courseItem.getReviews().isEmpty()) {
                    myReviews = courseItem.getReviews();
                    System.out.println("DATE VALUE " + courseItem.getReviews().get(0).getReviewContent());
                }

                ra.notifyDataSetChanged();
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
                //CourseItem course = (CourseItem) view.getTag();
                myAct.openMyReview();
            }
        });



        return myView;
    }
}
