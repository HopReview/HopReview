package com.example.teame_hopreview.ui.course;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teame_hopreview.MainActivity;
import com.example.teame_hopreview.R;
import com.example.teame_hopreview.ReviewAdapter;
import com.example.teame_hopreview.ReviewItem;
import com.example.teame_hopreview.User;
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
    private int gradeRate;
    private int knowledgeRate;
    private ReviewItem ReviewItem;
    protected ArrayList<ReviewItem> myReviews;
    Context context;
    String courseName;
    private ReviewAdapter ra;
    private String currProfessor;
    DatabaseReference dbref;
    private ArrayList<String> reviewContents;
    private ArrayList<String> reviewUsers;
    private ArrayList<String> reviewDates;

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

        myAct = (MainActivity) getActivity();
        context = myAct.getApplicationContext();
        myAct.user.addRecentlyViewed(courseItem.getName());
        myAct.user.updateRecentlyViewedDatabase();

        TextView designation = (TextView) myView.findViewById(R.id.course_designation);
        TextView courseName = (TextView) myView.findViewById(R.id.course_name);
        TextView courseNum = (TextView) myView.findViewById(R.id.course_num);

        TextView[] professors = new TextView[4];
        professors[0] = (TextView) myView.findViewById(R.id.professor1);
        professors[1] = (TextView) myView.findViewById(R.id.professor2);
        professors[2] = (TextView) myView.findViewById(R.id.professor3);
        professors[3] = (TextView) myView.findViewById(R.id.professor4);
        ImageButton bookmark = (ImageButton) myView.findViewById(R.id.bookmark_det);


        myAct.user.retrieveUserData();
        if (myAct.user.getBookmarkedCourses() != null) {
            for (String crs : myAct.user.getBookmarkedCourses()) {
                if (crs.equals(courseItem.getName())) {
                    bookmark.setImageDrawable(getResources().getDrawable(R.drawable.bm_filled));
                    break;
                } else {
                    bookmark.setImageDrawable(getResources().getDrawable(R.drawable.bm_unfilled));
                }
            }
        }


        designation.setText(courseItem.getDesignation());
        courseName.setText(courseItem.getName());
        courseNum.setText(courseItem.getCourseNumber());

        ArrayList<String> professorsHolder = courseItem.getProfessors();
        currProfessor = professorsHolder.get(0);

        int counter = 0;
        int size = professorsHolder.size();
        while (counter < 4) {
            if (counter < size) {
                professors[counter].setText(professorsHolder.get(counter));
            } else {
                professors[counter].setVisibility(View.GONE);
            }
            counter++;
        }


        reviewContents = new ArrayList<>();
        reviewUsers = new ArrayList<>();
        reviewDates = new ArrayList<>();



        //
        setCourseProfessors(currProfessor);
        myDbHelper(myView, currProfessor);

        System.out.println(professorsHolder.size());

        if (professorsHolder.size() > 1) {
            professors[0].setBackground(myAct.getResources().getDrawable(R.drawable.selected_item_background));
            professors[0].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (professors[0].getVisibility() != View.GONE) {
                        professors[0].setBackground(myAct.getResources().
                                getDrawable(R.drawable.selected_item_background));
                        professors[1].setBackground(null);
                        professors[2].setBackground(null);
                        professors[3].setBackground(null);
                        currProfessor = professors[0].getText().toString();
                        setCourseProfessors(currProfessor);
                        myDbHelper(myView, currProfessor);
                    }
                }
            });
            professors[1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (professors[1].getVisibility() != View.GONE) {
                        professors[1].setBackground(myAct.getResources().
                                getDrawable(R.drawable.selected_item_background));
                        professors[0].setBackground(null);
                        professors[2].setBackground(null);
                        professors[3].setBackground(null);
                        currProfessor = professors[1].getText().toString();
                        setCourseProfessors(currProfessor);
                        myDbHelper(myView, currProfessor);
                    }
                }
            });
            professors[2].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (professors[2].getVisibility() != View.GONE) {
                        professors[2].setBackground(myAct.getResources().
                                getDrawable(R.drawable.selected_item_background));
                        professors[0].setBackground(null);
                        professors[1].setBackground(null);
                        professors[3].setBackground(null);
                        currProfessor = professors[2].getText().toString();
                        setCourseProfessors(currProfessor);
                        myDbHelper(myView, currProfessor);
                    }
                }
            });
            professors[3].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (professors[3].getVisibility() != View.GONE) {
                        professors[3].setBackground(myAct.getResources().
                                getDrawable(R.drawable.selected_item_background));
                        professors[0].setBackground(null);
                        professors[1].setBackground(null);
                        professors[2].setBackground(null);
                        currProfessor = professors[3].getText().toString();
                        setCourseProfessors(currProfessor);
                        myDbHelper(myView, currProfessor);
                    }
                }
            });

        }


        myFab = (FloatingActionButton) myView.findViewById(R.id.floatingActionButton);


        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAct.openCreateReview(courseItem);
            }
        });

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: IMPLEMENT, change bookmark icon so its filled, etc.
                if (myAct.user.getBookmarkedCourses().contains(courseItem.getName())) {
                    bookmark.setImageDrawable(getResources().getDrawable(R.drawable.bm_unfilled));
                    myAct.user.removeBookmarkedCourse(courseItem.getName());
                    myAct.user.updateBookmarkedCoursesDatabase();
                    Toast.makeText(myAct, "Course Removed", Toast.LENGTH_LONG).show();
                } else {
                    bookmark.setImageDrawable(getResources().getDrawable(R.drawable.bm_filled));
                    myAct.user.addBookmarkedCourse(courseItem.getName());
                    myAct.user.updateBookmarkedCoursesDatabase();
                    Toast.makeText(myAct, "Course Saved",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        return myView;
    }


    public void setCourseProfessors(String currProfessor) {
        for (ReviewItem rev : courseItem.getReviews()) {
            rev.setProfessorName(currProfessor);
        }
    }


    public void myDbHelper(View myView, String currProfessor) {
        reviewContents.clear();
        reviewUsers.clear();
        reviewDates.clear();

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long count = snapshot.getChildrenCount();
                Log.d(TAG, "Children count: " + count);
                Log.d(TAG, "Professor count: " + snapshot.child("professors_data").getChildrenCount());

                Iterable<DataSnapshot> professors = snapshot.child("professors_data").getChildren();


                for (DataSnapshot profs : professors) {
                    String professor = profs.getKey();
                    if (professor.equals(currProfessor)) {
                        Iterable<DataSnapshot> list = profs.getChildren();
                        int counter = 1;
                        for (DataSnapshot item : list) {
                            if (counter == 4) {
                                gradeRate = item.getValue(Integer.class);
                            } else if (counter == 5) {
                                knowledgeRate = item.getValue(Integer.class);
                            } else if (counter == 6) {
                                Iterable<DataSnapshot> revs = item.getChildren();
                                for (DataSnapshot rev : revs) {
                                    Iterable<DataSnapshot> revContent = rev.getChildren();
                                    int counter2 = 1;
                                    for (DataSnapshot content : revContent) {
                                        if (counter2 == 2) {
                                            reviewDates.add(content.getValue(String.class));
                                        } else if (counter2 == 5) {
                                            reviewContents.add(content.getValue(String.class));
                                        } else if (counter2 == 6) {
                                            reviewUsers.add(content.getValue(String.class));
                                        }
                                        counter2++;
                                    }
                                }
                            }
                            counter++;
                        }
                        break;
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
        myReviewsCopy.addAll(courseItem.getReviews());

        myReviews = new ArrayList<ReviewItem>();


        int len = reviewContents.size();
        for (ReviewItem rev : myReviewsCopy) {
            for (int i = 0; i < len; i++) {
                if (rev.getReviewContent().equals(reviewContents.get(i))) {
                    if (rev.getReviewerName().equals(reviewUsers.get(i))) {
                        if (rev.getDate().equals(reviewDates.get(i))) {
                            if (!myReviews.contains(rev)) {
                                myReviews.add(rev);
                            }
                        }
                    }
                }
            }
        }

        ra = new ReviewAdapter(myAct, context, myReviews);
        myList.setAdapter(ra);
        myList.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        ra.notifyDataSetChanged();
    }

    public void setRatesHelper(View myView) {
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
                avgStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_filled));
            } else {
                avgStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_unfilled));
            }

            if (i < gradeRate) {
                gradeStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_filled));
            } else {
                gradeStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_unfilled));
            }

            if (i < knowledgeRate) {
                knowStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_filled));
            } else {
                knowStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_unfilled));
            }

            if (i < courseItem.getWorkloadRating()) {
                workStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_filled));
            } else {
                workStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_unfilled));
            }

            if (i < courseItem.getFunRating()) {
                funStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_filled));
            } else {
                funStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_unfilled));
            }
        }
    }
}
