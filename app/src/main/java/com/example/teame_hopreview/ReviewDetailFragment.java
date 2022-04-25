package com.example.teame_hopreview;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.teame_hopreview.database.Professor;
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
        context = myAct.getApplicationContext();

        TextView userName = (TextView) myView.findViewById(R.id.userName);
        userName.setText(reviewerName);

        TextView course = (TextView) myView.findViewById(R.id.course_box);
        course.setText(reviewItem.getCourseName());

        TextView professor = (TextView) myView.findViewById(R.id.professor_box);
        professor.setText(reviewItem.getProfessorName());

        TextView comment = (TextView) myView.findViewById(R.id.comment_box);
        comment.setText(reviewContent);

        myDbHelper(myView);
        return myView;
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
                setRatesHelper(myView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    public void setRatesHelper(View myView) {
        // course related stars
        ImageView[] workloadStars = new ImageView[5];
        workloadStars[0] = (ImageView) myView.findViewById(R.id.rev_work_1);
        workloadStars[1] = (ImageView) myView.findViewById(R.id.rev_work_2);
        workloadStars[2] = (ImageView) myView.findViewById(R.id.rev_work_3);
        workloadStars[3] = (ImageView) myView.findViewById(R.id.rev_work_4);
        workloadStars[4] = (ImageView) myView.findViewById(R.id.rev_work_5);

        ImageView[] funStars = new ImageView[5];
        funStars[0] = (ImageView) myView.findViewById(R.id.rev_fun_1);
        funStars[1] = (ImageView) myView.findViewById(R.id.rev_fun_2);
        funStars[2] = (ImageView) myView.findViewById(R.id.rev_fun_3);
        funStars[3] = (ImageView) myView.findViewById(R.id.rev_fun_4);
        funStars[4] = (ImageView) myView.findViewById(R.id.rev_fun_5);

        // professor related stars
        ImageView[] gradeStars = new ImageView[5];
        gradeStars[0] = (ImageView) myView.findViewById(R.id.rev_gr_1);
        gradeStars[1] = (ImageView) myView.findViewById(R.id.rev_gr_2);
        gradeStars[2] = (ImageView) myView.findViewById(R.id.rev_gr_3);
        gradeStars[3] = (ImageView) myView.findViewById(R.id.rev_gr_4);
        gradeStars[4] = (ImageView) myView.findViewById(R.id.rev_gr_5);

        ImageView[] knowledgeStars = new ImageView[5];
        knowledgeStars[0] = (ImageView) myView.findViewById(R.id.rev_know_1);
        knowledgeStars[1] = (ImageView) myView.findViewById(R.id.rev_know_2);
        knowledgeStars[2] = (ImageView) myView.findViewById(R.id.rev_know_3);
        knowledgeStars[3] = (ImageView) myView.findViewById(R.id.rev_know_4);
        knowledgeStars[4] = (ImageView) myView.findViewById(R.id.rev_know_5);

        for (int i = 0; i < 5; i++) {
            if (i < gradeRate) {
                gradeStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_filled));
            } else {
                gradeStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_unfilled));
            }

            if (i < knowRate) {
                knowledgeStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_filled));
            } else {
                knowledgeStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_unfilled));
            }

            if (i < workRate) {
                workloadStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_filled));
            } else {
                workloadStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_unfilled));
            }

            if (i < funRate) {
                funStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_filled));
            } else {
                funStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_unfilled));
            }
        }

    }
}
