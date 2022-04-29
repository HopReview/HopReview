package com.example.teame_hopreview.ui.professors;
import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
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
    private String currCourse;
    private int workRate;
    private int funRate;
    private ArrayList<String> reviewContents;
    private ArrayList<String> reviewUsers;
    private ArrayList<String> reviewDates;

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

        TextView[] courses = new TextView[4];
        courses[0] = (TextView) myView.findViewById(R.id.course1);
        courses[1] = (TextView) myView.findViewById(R.id.course2);
        courses[2] = (TextView) myView.findViewById(R.id.course3);
        courses[3] = (TextView) myView.findViewById(R.id.course4);

        initials.setText(professor.getInitials());
        courseName.setText(professor.getProfessorName());
        department.setText(professor.getDepartment());

        // implement later with a spinner
        ArrayList<String> coursesHolder = professor.getCourseNames();
        currCourse = coursesHolder.get(0);

        int counter = 0;
        int size = coursesHolder.size();
        while (counter < 4) {
            if (counter < size) {
                courses[counter].setText(coursesHolder.get(counter));
            } else {
                courses[counter].setVisibility(View.GONE);
            }
            counter++;
        }

        reviewContents = new ArrayList<>();
        reviewUsers = new ArrayList<>();
        reviewDates = new ArrayList<>();

        myAct = (MainActivity) getActivity();
        context = myAct.getApplicationContext();
        myAct.getSupportActionBar().setTitle("Professor");

        courses[0].setBackground(myAct.getResources().getDrawable(R.drawable.selected_item_background));
        setProfessorCourses(currCourse);
        myDbHelper(myView, currCourse);

        courses[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (courses[0].getVisibility() != View.GONE) {
                    courses[0].setBackground(myAct.getResources().
                            getDrawable(R.drawable.selected_item_background));
                    courses[1].setBackground(null);
                    courses[2].setBackground(null);
                    courses[3].setBackground(null);
                    currCourse = courses[0].getText().toString();
                    setProfessorCourses(currCourse);
                    myDbHelper(myView, currCourse);
                }
            }
        });

        courses[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (courses[1].getVisibility() != View.GONE) {
                    courses[1].setBackground(myAct.getResources().
                            getDrawable(R.drawable.selected_item_background));
                    courses[0].setBackground(null);
                    courses[2].setBackground(null);
                    courses[3].setBackground(null);
                    currCourse = courses[1].getText().toString();
                    setProfessorCourses(currCourse);
                    myDbHelper(myView, currCourse);
                }
            }
        });

        courses[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (courses[2].getVisibility() != View.GONE) {
                    courses[2].setBackground(myAct.getResources().
                            getDrawable(R.drawable.selected_item_background));
                    courses[0].setBackground(null);
                    courses[1].setBackground(null);
                    courses[3].setBackground(null);
                    currCourse = courses[2].getText().toString();
                    setProfessorCourses(currCourse);
                    myDbHelper(myView, currCourse);
                }
            }
        });

        courses[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (courses[3].getVisibility() != View.GONE) {
                    courses[3].setBackground(myAct.getResources().
                            getDrawable(R.drawable.selected_item_background));
                    courses[0].setBackground(null);
                    courses[1].setBackground(null);
                    courses[2].setBackground(null);
                    currCourse = courses[3].getText().toString();
                    setProfessorCourses(currCourse);
                    myDbHelper(myView, currCourse);
                }
            }
        });



        myFab = (FloatingActionButton) myView.findViewById(R.id.floatingActionButton2);


        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAct.openCreateReview(professor);
            }
        });

        return myView;
    }

    public void setProfessorCourses(String currCourse) {
        for (ReviewItem rev : professor.getReviews()) {
            rev.setCourseName(currCourse);
        }
    }

    public void myDbHelper(View myView, String currCourse) {
        reviewContents.clear();
        reviewUsers.clear();
        reviewDates.clear();

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long count = snapshot.getChildrenCount();

                Iterable<DataSnapshot> courses = snapshot.child("courses_data").getChildren();

                for (DataSnapshot crs : courses) {
                    Iterable<DataSnapshot> list = crs.getChildren();
                    int counter = 1;
                    boolean isCourse = false;
                    for (DataSnapshot item : list) {
                        if (isCourse) {
                            if (counter == 5) {
                                funRate = item.getValue(Integer.class);
                            } else if (counter == 7) {
                                Iterable<DataSnapshot> revs = item.getChildren();
                                for (DataSnapshot rev : revs) {
                                    Iterable<DataSnapshot> revContet = rev.getChildren();
                                    int counter2 = 1;
                                    for (DataSnapshot content : revContet) {
                                        if (counter2 == 2) {
                                            reviewDates.add(content.getValue(String.class));
                                        } else if (counter2 == 4) {
                                            reviewContents.add(content.getValue(String.class));
                                        } else if (counter2 == 5) {
                                            reviewUsers.add(content.getValue(String.class));
                                        }
                                        counter2++;
                                    }
                                }
                            } else if (counter == 8) {
                                workRate = item.getValue(Integer.class);
                            }
                        } else if (counter == 3) {
                            if (item.getValue(String.class).equals(currCourse)) {
                                isCourse = true;
                            }
                        } else if (counter > 3) {
                            break;
                        }
                        counter++;
                    }

                }
                setRatesHelper(myView);
                updateRecycler(myView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    public void updateRecycler(View myView) {
        myList = (RecyclerView) myView.findViewById(R.id.recyclerViewProf);
        myCard = (CardView) myView.findViewById(R.id.review_card);
        ArrayList<ReviewItem> myReviewsCopy = new ArrayList<>();
        myReviewsCopy.addAll(professor.getReviews());

        myReviews = new ArrayList<ReviewItem>();
        ra = new ReviewAdapter(myAct, context, myReviews);
        myList.setAdapter(ra);
        myList.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));

        int len = reviewContents.size();
        for (ReviewItem rev : myReviewsCopy) {
            for (int i = 0; i < len; i++) {
                if (rev.getReviewContent().equals(reviewContents.get(i))) {
                    if (rev.getReviewerName().equals(reviewUsers.get(i))) {
                        if (rev.getDate().equals(reviewDates.get(i))) {
                            myReviews.add(rev);
                        }
                    }
                }
            }
        }

        ra.notifyDataSetChanged();
    }


    public void setRatesHelper(View myView) {
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
                avgStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_filled));
            } else {
                avgStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_unfilled));
            }

            if (i < funRate) {
                funStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_filled));
            } else {
                funStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_unfilled));
            }

            if (i < workRate) {
                workStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_filled));
            } else {
                workStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_unfilled));
            }

            if (i < professor.getKnowledgeRating()) {
                knowStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_filled));
            } else {
                knowStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_unfilled));
            }

            if (i < professor.getGradeRating()) {
                gradeStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_filled));
            } else {
                gradeStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_unfilled));
            }
        }
    }
}
