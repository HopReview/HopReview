package com.example.teame_hopreview.ui.course;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
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
import com.example.teame_hopreview.database.Course;
import com.example.teame_hopreview.database.DbManager;
import com.example.teame_hopreview.database.Professor;
import com.example.teame_hopreview.database.Review;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CourseDetailFragment extends Fragment {
    private static final String TAG = "dbref: ";

    //Views
    private RecyclerView myList;
    private CardView myCard;
    private MainActivity myAct;
    private FloatingActionButton myFab;

    //Models
    private Course courseItem;
    protected ArrayList<Review> myReviews;
    private ArrayList<String> reviewContents;
    private ArrayList<String> reviewUsers;
    private ArrayList<String> reviewDates;
    private String currProfessor;
    int workLoadRating;
    int knowledgeRating;

    //Adapter
    private ReviewAdapter ra;

    //Other stuff
    Context context;
    private DbManager dbManager;


    public CourseDetailFragment(Course course) {
        this.courseItem = course;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.course_detail_frag, container, false);

        myAct = (MainActivity) getActivity();
        context = myAct.getApplicationContext();

        reviewContents = new ArrayList<>();
        reviewUsers = new ArrayList<>();
        reviewDates = new ArrayList<>();

        updateRecentlyViewedCourses();

        setUpViews(myView);
        setBookmark(myView);

        setupProfessorSelector(myView);


        return myView;
    }

    private void updateRecentlyViewedCourses() {
        myAct.user.addRecentlyViewed(courseItem.getCourseName());
        myAct.user.updateRecentlyViewedDatabase();
    }

    private void setUpViews(View myView) {
        TextView designation = (TextView) myView.findViewById(R.id.course_designation);
        TextView courseName = (TextView) myView.findViewById(R.id.course_name);
        TextView courseNum = (TextView) myView.findViewById(R.id.course_num);

        designation.setText(courseItem.getCourseDesignation());
        courseName.setText(courseItem.getCourseName());
        courseNum.setText(courseItem.getCourseNumber());

        //Set up fab

        myFab = (FloatingActionButton) myView.findViewById(R.id.floatingActionButton);

        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAct.openCreateReview(courseItem);
            }
        });
    }

    private void setupProfessorSelector(View myView) {
        TextView[] professors = new TextView[4];
        professors[0] = (TextView) myView.findViewById(R.id.professor1);
        professors[1] = (TextView) myView.findViewById(R.id.professor2);
        professors[2] = (TextView) myView.findViewById(R.id.professor3);
        professors[3] = (TextView) myView.findViewById(R.id.professor4);

        ArrayList<String> professorsHolder = courseItem.getProfessor();
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

        if (professorsHolder.size() > 1) {
            professors[0].setBackground(myAct.getResources().getDrawable(R.drawable.selected_item_background));
            professors[0].setOnClickListener(new ProfessorButtonOnClickListener(0, professors));
            professors[1].setOnClickListener(new ProfessorButtonOnClickListener(1, professors));
            professors[2].setOnClickListener(new ProfessorButtonOnClickListener(2, professors));
            professors[3].setOnClickListener(new ProfessorButtonOnClickListener(3, professors));
        }

    }

    private void setBookmark(View myView) {
        ImageButton bookmark = (ImageButton) myView.findViewById(R.id.bookmark_det);
        myAct.user.retrieveUserData();
        if (myAct.user.getBookmarkedCourses() != null) {
            for (String crs : myAct.user.getBookmarkedCourses()) {
                if (crs.equals(courseItem.getCourseName())) {
                    bookmark.setImageDrawable(getResources().getDrawable(R.drawable.bm_filled));
                    break;
                } else {
                    bookmark.setImageDrawable(getResources().getDrawable(R.drawable.bm_unfilled));
                }
            }
        }

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: IMPLEMENT, change bookmark icon so its filled, etc.
                if (myAct.user.getBookmarkedCourses().contains(courseItem.getCourseName())) {
                    bookmark.setImageDrawable(getResources().getDrawable(R.drawable.bm_unfilled));
                    myAct.user.removeBookmarkedCourse(courseItem.getCourseName());
                    myAct.user.updateBookmarkedCoursesDatabase();
                    Toast.makeText(myAct, "Course Removed", Toast.LENGTH_LONG).show();
                } else {
                    bookmark.setImageDrawable(getResources().getDrawable(R.drawable.bm_filled));
                    myAct.user.addBookmarkedCourse(courseItem.getCourseName());
                    myAct.user.updateBookmarkedCoursesDatabase();
                    Toast.makeText(myAct, "Course Saved",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void updateRecycler(View myView) {
        myList = (RecyclerView) myView.findViewById(R.id.recyclerViewProf);
        myCard = (CardView) myView.findViewById(R.id.review_card);
        ArrayList<Review> myReviewsCopy = new ArrayList<>();
        myReviewsCopy.addAll(courseItem.getReviews().values());

        myReviews = new ArrayList<Review>();

        int len = reviewContents.size();
        for (Review rev : myReviewsCopy) {
            for (int i = 0; i < len; i++) {
                if (rev.getReviewerContent().equals(reviewContents.get(i))) {
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

            if (i < 5) {
                gradeStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_filled));
            } else {
                gradeStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_unfilled));
            }

            if (i < 5) {
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


    class ProfessorButtonOnClickListener implements View.OnClickListener {

        private int index;
        private TextView[] textViews;

        ProfessorButtonOnClickListener(int index, TextView[] textViews) {
            this.index = index;
            this.textViews = textViews;
        }

        @Override
        public void onClick(View view) {
            if (textViews[index].getVisibility() != View.GONE) {
                textViews[index].setBackground(myAct.getResources().
                        getDrawable(R.drawable.selected_item_background));

                if (index != 0) textViews[0].setBackground(null);
                if (index != 1) textViews[1].setBackground(null);
                if (index != 2) textViews[2].setBackground(null);
                if (index != 3) textViews[3].setBackground(null);

                currProfessor = textViews[index].getText().toString();
                reviewDates.clear();
                reviewContents.clear();
                reviewUsers.clear();
                setRatesHelper(getView());
                updateRecycler(getView());
            }
        }
    }
}
