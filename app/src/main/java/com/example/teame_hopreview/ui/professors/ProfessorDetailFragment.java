package com.example.teame_hopreview.ui.professors;
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
import com.example.teame_hopreview.ui.course.CourseItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfessorDetailFragment extends Fragment {
    private static final String TAG = "dbref: ";

    private RecyclerView myList;
    private CardView myCard;
    private MainActivity myAct;
    private FloatingActionButton myFab;
    private Professor professor;
    private ReviewItem ReviewItem;
    protected ArrayList<ReviewItem> myReviews;
    Context context;
    String professorName;
    private ReviewAdapter ra;
    DatabaseReference dbref;

    public ProfessorDetailFragment(Professor prof) {
        this.professor = prof;
        this.professorName = prof.getProfessorName();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbref = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.prof_detail_frag, container, false);


        TextView initials = (TextView) myView.findViewById(R.id.initialsDet);
        TextView courseName = (TextView) myView.findViewById(R.id.prof_nameDet);
        TextView department = (TextView) myView.findViewById(R.id.prof_departmentDet);
        TextView professorView = (TextView) myView.findViewById(R.id.taught_coursesDet);

        initials.setText(professor.getInitials());
        courseName.setText(professor.getProfessorName());
        department.setText(professor.getDepartment());
        professorView.setText("Taught by: " + professor.getCourse());

        ImageView[] avgStars = new ImageView[5];
        ImageView[] workStars = new ImageView[5];
        ImageView[] funStars = new ImageView[5];
        ImageView[] gradeStars = new ImageView[5];
        ImageView[] knowStars = new ImageView[5];

        avgStars[0] = (ImageView) myView.findViewById(R.id.det_avg_1Prof);
        avgStars[1] = (ImageView) myView.findViewById(R.id.det_avg_2Prof);
        avgStars[2] = (ImageView) myView.findViewById(R.id.det_avg_3Prof);
        avgStars[3] = (ImageView) myView.findViewById(R.id.det_avg_4Prof);
        avgStars[4] = (ImageView) myView.findViewById(R.id.det_avg_5Prof);

        workStars[0] = (ImageView) myView.findViewById(R.id.det_work_1Prof);
        workStars[1] = (ImageView) myView.findViewById(R.id.det_work_2Prof);
        workStars[2] = (ImageView) myView.findViewById(R.id.det_work_3Prof);
        workStars[3] = (ImageView) myView.findViewById(R.id.det_work_4Prof);
        workStars[4] = (ImageView) myView.findViewById(R.id.det_work_5Prof);

        funStars[0] = (ImageView) myView.findViewById(R.id.det_fun_1Prof);
        funStars[1] = (ImageView) myView.findViewById(R.id.det_fun_2Prof);
        funStars[2] = (ImageView) myView.findViewById(R.id.det_fun_3Prof);
        funStars[3] = (ImageView) myView.findViewById(R.id.det_fun_4Prof);
        funStars[4] = (ImageView) myView.findViewById(R.id.det_fun_5Prof);

        gradeStars[0] = (ImageView) myView.findViewById(R.id.det_gr_1Prof);
        gradeStars[1] = (ImageView) myView.findViewById(R.id.det_gr_2Prof);
        gradeStars[2] = (ImageView) myView.findViewById(R.id.det_gr_3Prof);
        gradeStars[3] = (ImageView) myView.findViewById(R.id.det_gr_4Prof);
        gradeStars[4] = (ImageView) myView.findViewById(R.id.det_gr_5Prof);

        knowStars[0] = (ImageView) myView.findViewById(R.id.det_know_1Prof);
        knowStars[1] = (ImageView) myView.findViewById(R.id.det_know_2Prof);
        knowStars[2] = (ImageView) myView.findViewById(R.id.det_know_3Prof);
        knowStars[3] = (ImageView) myView.findViewById(R.id.det_know_4Prof);
        knowStars[4] = (ImageView) myView.findViewById(R.id.det_know_5Prof);

        for (int i = 0; i < 5; i++) {
            if (i < professor.getAverageRating()) {
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

            if (i < professor.getKnowledgeRating()) {
                workStars[i].setVisibility(View.VISIBLE);
            } else {
                workStars[i].setVisibility(View.INVISIBLE);
            }

            if (i < professor.getGradeRating()) {
                workStars[i].setVisibility(View.VISIBLE);
            } else {
                workStars[i].setVisibility(View.INVISIBLE);
            }
        }

        context = getActivity().getApplicationContext();

        myAct = (MainActivity) getActivity();
        myList = (RecyclerView) myView.findViewById(R.id.recyclerViewProf);
        myCard = (CardView) myView.findViewById(R.id.review_card);
        myFab = (FloatingActionButton) myView.findViewById(R.id.floatingActionButton2);
        myReviews = new ArrayList<ReviewItem>();

        ra = new ReviewAdapter(myAct, context, myReviews);

        myList.setAdapter(ra);
        myList.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        myReviews.addAll(professor.getReviews());

        ra.notifyDataSetChanged();



        /*dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                long count = snapshot.getChildrenCount();
                Log.d(TAG, "Children count: " + count);
                Log.d(TAG, "Courses count: " + snapshot.child("courses_data").getChildrenCount());

                myReviews.clear();
                Iterable<DataSnapshot> courses = snapshot.child("courses_data").getChildren();
                System.out.println("NAME VALUE " + professor.getProfessorName());

                if (professor != null && professor.getReviews() != null && !professor.getReviews().isEmpty()) {
                    myReviews = professor.getReviews();
                    System.out.println("DATE VALUE " + professor.getReviews().get(0).getReviewContent());
                }

                ra.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });*/

        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(myAct, CreateReview.class);
                //intent.putExtra("course_name", courseName);
                //CourseItem course = (CourseItem) view.getTag();
                myAct.openMyReview(professor.getProfessorName());
            }
        });



        return myView;
    }
}
