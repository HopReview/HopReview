package com.example.teame_hopreview;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.teame_hopreview.ui.review.OnRatingChangedListener;
import com.example.teame_hopreview.ui.review.RatingsView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReviewDetailFragment extends Fragment {
    private static final String TAG = "dbref: ";
    private MainActivity myAct;
    private ReviewItem reviewItem;
    private String reviewerName;
    private String reviewContent;
    private String course;
    private String professor;
    private int gradeRate;
    private int knowRate;
    private int funRate;
    private int workRate;
    DatabaseReference dbref;
    Context context;

    RatingsView funRatings;
    RatingsView workloadRatings;
    RatingsView gradingRatings;
    RatingsView knowledgeRatings;


    public ReviewDetailFragment(ReviewItem review) {
        this.reviewItem = review;
        this.reviewerName = review.getReviewerName();
        this.reviewContent = review.getReviewContent();
        this.course = review.getCourseName();
        this.professor = review.getProfessorName();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbref = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_review_detail, container, false);

        myAct = (MainActivity) getActivity();
        myAct.getSupportActionBar().setTitle("Review");
        context = myAct.getApplicationContext();

        TextView userName = (TextView) myView.findViewById(R.id.review_by);
        userName.setText("Review by: " + reviewerName);

        TextView course = (TextView) myView.findViewById(R.id.ReviewDetail_CourseText);
        course.setText(reviewItem.getCourseName());

        TextView professor = (TextView) myView.findViewById(R.id.ReviewDetail_ProfessorText);
        professor.setText(reviewItem.getProfessorName());

        TextInputEditText comment = (TextInputEditText) myView.findViewById(R.id.create_review_comment);
        comment.setText(reviewContent);

        setupReviews(myView);

        myDbHelper(myView);
        return myView;
    }

    private void setupReviews(View view) {
        funRatings = view.findViewById(R.id.review_detail_fun_rating);
        funRatings.setDisabled(true);
        workloadRatings = view.findViewById(R.id.review_detail_workload_rating);
        workloadRatings.setDisabled(true);
        gradingRatings = view.findViewById(R.id.review_detail_grading_rating);
        gradingRatings.setDisabled(true);
        knowledgeRatings = view.findViewById(R.id.review_detail_knowledge_rating);
        knowledgeRatings.setDisabled(true);
    }

    private void setRatings() {
        funRatings.setRating(funRate);
        workloadRatings.setRating(workRate);
        gradingRatings.setRating(gradeRate);
        knowledgeRatings.setRating(knowRate);
    }

    public void myDbHelper(View myView) {
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> professors = snapshot.child("professors_data").getChildren();
                for (DataSnapshot prof : professors) {
                    if (prof.getKey().equals(professor)) {
                        Iterable<DataSnapshot> list = prof.getChildren();
                        int counter = 1;
                        for (DataSnapshot item : list) {
                            if (counter == 6) {
                                Iterable<DataSnapshot> reviews = item.getChildren();
                                for (DataSnapshot rev : reviews) {
                                    Iterable<DataSnapshot> revContent = rev.getChildren();
                                    int counter2 = 1;
                                    boolean isIt = false;
                                    for (DataSnapshot content : revContent) {
                                        if (counter2 == 2 &&
                                                !content.getValue(String.class).equals(reviewItem.getDate())) {
                                            break;
                                        } else if (counter2 == 3) {
                                            gradeRate = content.getValue(Integer.class);
                                        } else if (counter2 == 4) {
                                            knowRate = content.getValue(Integer.class);
                                        } else if (counter2 == 5 &&
                                                !content.getValue(String.class).equals(reviewItem.getReviewContent())) {
                                            break;
                                        } else if (counter2 == 6 &&
                                                !content.getValue(String.class).equals(reviewItem.getReviewerName())) {
                                            break;
                                        } else if (counter2 == 6) {
                                            isIt = true;
                                        }
                                        counter2++;
                                    }
                                    if (isIt) {
                                        break;
                                    }
                                }
                            }
                            counter++;
                        }
                    }
                }

                Iterable<DataSnapshot> courses = snapshot.child("courses_data").getChildren();
                for (DataSnapshot crs : courses) {
                    Iterable<DataSnapshot> list = crs.getChildren();
                    int counter = 1;
                    boolean isCourse = false;
                    for (DataSnapshot item : list) {
                        if (counter == 3 && item.getValue(String.class).equals(course)) {
                            isCourse = true;
                        } else if (isCourse && counter == 7) {
                            Iterable<DataSnapshot> reviews = item.getChildren();
                            for (DataSnapshot rev : reviews) {
                                Iterable<DataSnapshot> revContent = rev.getChildren();
                                int counter2 = 1;
                                boolean isIt = false;

                                for (DataSnapshot content : revContent) {
                                    if (counter2 == 2 &&
                                            !content.getValue(String.class).equals(reviewItem.getDate())) {
                                        break;
                                    } else if (counter2 == 3) {
                                        funRate = content.getValue(Integer.class);
                                    } else if (counter2 == 4 &&
                                            !content.getValue(String.class).equals(reviewItem.getReviewContent())) {
                                        break;
                                    } else if (counter2 == 5 &&
                                            !content.getValue(String.class).equals(reviewItem.getReviewerName())) {
                                        break;
                                    } else if (counter2 == 6) {
                                        workRate = content.getValue(Integer.class);
                                        isIt = true;
                                    }
                                    counter2++;
                                }

                                if (isIt) {
                                    break;
                                }
                            }
                        }
                        counter++;
                    }
                }
                setRatings();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }
}
