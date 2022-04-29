package com.example.teame_hopreview.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.teame_hopreview.MainActivity;
import com.example.teame_hopreview.R;
import com.example.teame_hopreview.ReviewItem;
import com.example.teame_hopreview.User;
// import com.example.teame_hopreview.databinding.FragmentHomeBinding;
import com.example.teame_hopreview.ui.course.CourseItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class HomeFragment extends Fragment {
    private static final String TAG = "dbref: ";

    private CardView myCard;
    private ReviewItem reviewItem;
    private ReviewItem reviewItem1;
    private ReviewItem reviewItem2;
    private ReviewItem reviewItem3;
    DatabaseReference dbref;
    private MainActivity myAct;
    private CourseItem courseItem;
    private CourseItem courseItem1;
    private CourseItem courseItem2;
    private CourseItem courseItem3;
    private ArrayList<CourseItem> myCourses;
    private ArrayList<ReviewItem> recentReviews;
    private Context context;
    private CardView[] courseCards = new CardView[3];
    private CardView[] reviewCards = new CardView[3];


    // review related
    private int avgRating = 0;
    private String date = "";
    private int firstRating = 0;
    private String reviewContent = "";
    private String reviewerName = "";
    private int secondRating = 0;
    private String courseName = "";
    private String professorName = "";
    private int workRating = 0;
    private int funRating = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_home, container, false);
        myAct = (MainActivity) getActivity();
        context = myAct.getApplicationContext();
        myAct.getSupportActionBar().setTitle("Home");
        dbref = FirebaseDatabase.getInstance().getReference();
        courseItem = new CourseItem();
        courseItem1 = new CourseItem();
        courseItem2 = new CourseItem();
        courseItem3 = new CourseItem();
        reviewItem = new ReviewItem();
        reviewItem1 = new ReviewItem();
        reviewItem2 = new ReviewItem();
        reviewItem3 = new ReviewItem();

        myCourses = new ArrayList<>();
        recentReviews = new ArrayList<>();

        ImageView nothingCourses = (ImageView) myView.findViewById(R.id.nothing_1);
        TextView notCrsTxt = (TextView) myView.findViewById(R.id.course_stub);


        courseCards[0] = myView.findViewById(R.id.course1);
        courseCards[1] = myView.findViewById(R.id.course2);
        courseCards[2] = myView.findViewById(R.id.course3);
        reviewCards[0] = myView.findViewById(R.id.review1);
        reviewCards[1] = myView.findViewById(R.id.review2);
        reviewCards[2] = myView.findViewById(R.id.review3);

        courseCards[0].setVisibility(View.GONE);
        courseCards[1].setVisibility(View.GONE);
        courseCards[2].setVisibility(View.GONE);
        reviewCards[0].setVisibility(View.GONE);
        reviewCards[1].setVisibility(View.GONE);
        reviewCards[2].setVisibility(View.GONE);

        String firstCourse = "";
        String secondCourse = "";
        String thirdCourse = "";

        findUser(myAct.user.getUserId(), myView, notCrsTxt, nothingCourses);
        fillReviews(myView);




        // For now, otherwise have a shared preference for it
        // and create or initialize from database upon login/signup

        courseCards[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAct.openCourseDetailFragment(courseItem1);
            }
        });

        courseCards[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAct.openCourseDetailFragment(courseItem2);
            }
        });

        courseCards[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAct.openCourseDetailFragment(courseItem3);
            }
        });

        reviewCards[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAct.openReviewDetailFragment(reviewItem1);
            }
        });
        reviewCards[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAct.openReviewDetailFragment(reviewItem2);
            }
        });
        reviewCards[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAct.openReviewDetailFragment(reviewItem3);
            }
        });

        return myView;
    }

    public void findUser(String userId, View myView, TextView notCrsTxt, ImageView nothingCourses) {
        dbref.child("user_data").child(userId).child("recentlyViewed").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> recent = snapshot.getChildren();
                for (DataSnapshot course : recent) {
                    myAct.user.addRecentlyViewed(course.getValue(String.class));
                }
                ArrayList<String> courseNames = new ArrayList<>();
                courseNames.addAll(myAct.user.getRecentlyViewedList());
                int size = courseNames.size();
                findCourse(courseNames, size, myView, nothingCourses, notCrsTxt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void findCourse(ArrayList<String >courseNames, int size, View myView, ImageView nothingCourses, TextView notCrsTxt) {
        if (courseNames == null || size == 0) {
            return;
        }
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Collections.reverse(courseNames);
                myCourses.clear();
                for (String str : courseNames) {
                    Iterable<DataSnapshot> courses = snapshot.child("courses_data").getChildren();
                    for (DataSnapshot crs : courses) {
                        String name = "";
                        String num = "";
                        String designation = "";
                        ArrayList<String> professors = new ArrayList<>();
                        ArrayList<ReviewItem> myReviews = new ArrayList<>();
                        int avgRate = 0;
                        int funRate = 0;
                        int workRate = 0;
                        int revAvgRate = 0;
                        String date = "";
                        int firstRating = 0;
                        String reviewerContent = "";
                        String reviewerName = "";
                        int secondRating = 0;
                        boolean toAdd = false;

                        int counter = 1;
                        Iterable<DataSnapshot> list = crs.getChildren();
                        for (DataSnapshot item : list) {
                            if (counter == 1) {
                                avgRate = item.getValue(Integer.class);
                            } else if (counter == 2) {
                                designation = item.getValue(String.class);
                            } else if (counter == 3) {
                                name = item.getValue(String.class);
                                toAdd = name.equals(str);
                            } else if (counter == 4) {
                                num = item.getValue(String.class);
                            } else if (counter == 5) {
                                funRate = item.getValue(Integer.class);
                            } else if (counter == 6) {
                                Iterable<DataSnapshot> profs = item.getChildren();
                                for (DataSnapshot prof : profs) {
                                    professors.add(prof.getValue(String.class));
                                }
                            } else if (counter == 7) {
                                Iterable<DataSnapshot> reviews = item.getChildren();
                                for (DataSnapshot rev : reviews) {
                                    Iterable<DataSnapshot> rr = rev.getChildren();
                                    int c2 = 1;
                                    for (DataSnapshot r : rr) {
                                        if (c2 == 1) {
                                            revAvgRate = r.getValue(Integer.class);
                                        } else if (c2 == 2) {
                                            date = r.getValue(String.class);
                                        } else if (c2 == 3) {
                                            firstRating = r.getValue(Integer.class);
                                        } else if (c2 == 4) {
                                            reviewerContent = r.getValue(String.class);
                                        } else if (c2 == 5) {
                                            reviewerName = r.getValue(String.class);
                                        } else if (c2 == 6) {
                                            secondRating = r.getValue(Integer.class);
                                        }
                                        c2++;
                                    }
                                    ReviewItem reviewItem = new ReviewItem(revAvgRate, date, firstRating, reviewerContent, reviewerName, secondRating);
                                    reviewItem.setCourseName(name);
                                    myReviews.add(reviewItem);
                                }
                            } else if (counter == 8) {
                                workRate = item.getValue(Integer.class);
                            }
                            counter++;
                        }

                        courseItem = new CourseItem(avgRate, designation, name, num, funRate, professors, workRate);
                        for (ReviewItem r : myReviews) {
                            courseItem.addReview(r);
                        }
                        if (toAdd) {
                            myCourses.add(courseItem);
                        }
                    }
                }
                setDetailsHelper(myCourses, size, myView, nothingCourses, notCrsTxt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setDetailsHelper(ArrayList<CourseItem> myCourses, int size, View myView, ImageView nothingCourses, TextView notCrsTxt) {
        if (size != 0) {
            nothingCourses.setVisibility(View.GONE);
            notCrsTxt.setVisibility(View.GONE);

            if (size == 1) {
                courseCards[0].setVisibility(View.VISIBLE);

                TextView designation1 = (TextView) myView.findViewById(R.id.designationHome);
                TextView name1 = (TextView) myView.findViewById(R.id.nameHome);
                TextView profs1 = (TextView) myView.findViewById(R.id.profsHome);
                ImageView[] stars1 = {
                        (ImageView) myView.findViewById(R.id.avg_star1Home),
                        (ImageView) myView.findViewById(R.id.avg_star2Home),
                        (ImageView) myView.findViewById(R.id.avg_star3Home),
                        (ImageView) myView.findViewById(R.id.avg_star4Home),
                        (ImageView) myView.findViewById(R.id.avg_star5Home),
                };
                courseItem1 = myCourses.get(0);
                designation1.setText(myCourses.get(0).getDesignation());
                StringBuilder myName = new StringBuilder();
                for (int i = 0; i < 21; i++) {
                    if (i < myCourses.get(0).getName().length()) {
                        myName.append(myCourses.get(0).getName().charAt(i));
                    } else {
                        myName.append(" ");
                    }
                }
                myName.append(" . .");
                name1.setText(myName.toString());
                StringBuilder strBldr = new StringBuilder();
                int counter = 1;
                for (String prof : myCourses.get(0).getProfessors()) {
                    if (counter == 1) {
                        strBldr.append(prof);
                    } else {
                        strBldr.append(" / ").append(prof);
                    }
                    counter++;
                }
                profs1.setText(strBldr.toString());
                int avg = myCourses.get(0).getAverageRating();
                for (int i = 0; i < 5; i++) {
                    if (i < avg) {
                        stars1[i].setImageDrawable(getResources().getDrawable(R.drawable.star_filled));
                    } else {
                        stars1[i].setImageDrawable(getResources().getDrawable(R.drawable.star_unfilled));;
                    }
                }

                courseCards[1].setVisibility(View.GONE);
                courseCards[2].setVisibility(View.GONE);
                // set the course card details
            } else if (size == 2) {
                courseCards[0].setVisibility(View.VISIBLE);
                courseCards[1].setVisibility(View.VISIBLE);

                TextView designation1 = (TextView) myView.findViewById(R.id.designationHome);
                TextView name1 = (TextView) myView.findViewById(R.id.nameHome);
                TextView profs1 = (TextView) myView.findViewById(R.id.profsHome);
                ImageView[] stars1 = {
                        (ImageView) myView.findViewById(R.id.avg_star1Home),
                        (ImageView) myView.findViewById(R.id.avg_star2Home),
                        (ImageView) myView.findViewById(R.id.avg_star3Home),
                        (ImageView) myView.findViewById(R.id.avg_star4Home),
                        (ImageView) myView.findViewById(R.id.avg_star5Home),
                };
                courseItem1 = myCourses.get(0);
                designation1.setText(myCourses.get(0).getDesignation());
                StringBuilder myName = new StringBuilder();
                for (int i = 0; i < 21; i++) {
                    if (i < myCourses.get(0).getName().length()) {
                        myName.append(myCourses.get(0).getName().charAt(i));
                    } else {
                        myName.append(" ");
                    }
                }
                myName.append(" . .");
                name1.setText(myName.toString());
                StringBuilder strBldr = new StringBuilder();
                int counter = 1;
                for (String prof : myCourses.get(0).getProfessors()) {
                    if (counter == 1) {
                        strBldr.append(prof);
                    } else {
                        strBldr.append(" / ").append(prof);
                    }
                    counter++;
                }
                profs1.setText(strBldr.toString());
                int avg = myCourses.get(0).getAverageRating();
                for (int i = 0; i < 5; i++) {
                    if (i < avg) {
                        stars1[i].setImageDrawable(getResources().getDrawable(R.drawable.star_filled));
                    } else {
                        stars1[i].setImageDrawable(getResources().getDrawable(R.drawable.star_unfilled));;
                    }
                }


                TextView designation2 = (TextView) myView.findViewById(R.id.designationHome2);
                TextView name2 = (TextView) myView.findViewById(R.id.nameHome2);
                TextView profs2 = (TextView) myView.findViewById(R.id.profsHome2);
                ImageView[] stars2 = {
                        (ImageView) myView.findViewById(R.id.avg_star1Home2),
                        (ImageView) myView.findViewById(R.id.avg_star2Home2),
                        (ImageView) myView.findViewById(R.id.avg_star3Home2),
                        (ImageView) myView.findViewById(R.id.avg_star4Home2),
                        (ImageView) myView.findViewById(R.id.avg_star5Home2),
                };
                courseItem2 = myCourses.get(1);
                designation2.setText(myCourses.get(1).getDesignation());
                StringBuilder myName2 = new StringBuilder();
                for (int i = 0; i < 21; i++) {
                    if (i < myCourses.get(1).getName().length()) {
                        myName2.append(myCourses.get(1).getName().charAt(i));
                    } else {
                        myName2.append(" ");
                    }
                }
                myName2.append(" . .");
                name2.setText(myName2.toString());
                StringBuilder strBldr2 = new StringBuilder();
                int counter2 = 1;
                for (String prof : myCourses.get(1).getProfessors()) {
                    if (counter2 == 1) {
                        strBldr2.append(prof);
                    } else {
                        strBldr2.append(" / ").append(prof);
                    }
                    counter2++;
                }
                profs2.setText(strBldr2.toString());
                int avg2 = myCourses.get(1).getAverageRating();
                for (int i = 0; i < 5; i++) {
                    if (i < avg2) {
                        stars2[i].setImageDrawable(getResources().getDrawable(R.drawable.star_filled));
                    } else {
                        stars2[i].setImageDrawable(getResources().getDrawable(R.drawable.star_unfilled));;
                    }
                }

                courseCards[2].setVisibility(View.GONE);
                // set the course card details
            } else if (size == 3) {
                courseCards[0].setVisibility(View.VISIBLE);
                courseCards[1].setVisibility(View.VISIBLE);
                courseCards[2].setVisibility(View.VISIBLE);

                TextView designation1 = (TextView) myView.findViewById(R.id.designationHome);
                TextView name1 = (TextView) myView.findViewById(R.id.nameHome);
                TextView profs1 = (TextView) myView.findViewById(R.id.profsHome);
                ImageView[] stars1 = {
                        (ImageView) myView.findViewById(R.id.avg_star1Home),
                        (ImageView) myView.findViewById(R.id.avg_star2Home),
                        (ImageView) myView.findViewById(R.id.avg_star3Home),
                        (ImageView) myView.findViewById(R.id.avg_star4Home),
                        (ImageView) myView.findViewById(R.id.avg_star5Home),
                };
                courseItem1 = myCourses.get(0);
                designation1.setText(myCourses.get(0).getDesignation());
                StringBuilder myName = new StringBuilder();
                for (int i = 0; i < 21; i++) {
                    if (i < myCourses.get(0).getName().length()) {
                        myName.append(myCourses.get(0).getName().charAt(i));
                    } else {
                        myName.append(" ");
                    }
                }
                myName.append(" . .");
                name1.setText(myName.toString());
                StringBuilder strBldr = new StringBuilder();
                int counter = 1;
                for (String prof : myCourses.get(0).getProfessors()) {
                    if (counter == 1) {
                        strBldr.append(prof);
                    } else {
                        strBldr.append(" / ").append(prof);
                    }
                    counter++;
                }
                profs1.setText(strBldr.toString());
                int avg = myCourses.get(0).getAverageRating();
                for (int i = 0; i < 5; i++) {
                    if (i < avg) {
                        stars1[i].setImageDrawable(getResources().getDrawable(R.drawable.star_filled));
                    } else {
                        stars1[i].setImageDrawable(getResources().getDrawable(R.drawable.star_unfilled));;
                    }
                }


                TextView designation2 = (TextView) myView.findViewById(R.id.designationHome2);
                TextView name2 = (TextView) myView.findViewById(R.id.nameHome2);
                TextView profs2 = (TextView) myView.findViewById(R.id.profsHome2);
                ImageView[] stars2 = {
                        (ImageView) myView.findViewById(R.id.avg_star1Home2),
                        (ImageView) myView.findViewById(R.id.avg_star2Home2),
                        (ImageView) myView.findViewById(R.id.avg_star3Home2),
                        (ImageView) myView.findViewById(R.id.avg_star4Home2),
                        (ImageView) myView.findViewById(R.id.avg_star5Home2),
                };
                courseItem2 = myCourses.get(1);
                designation2.setText(myCourses.get(1).getDesignation());
                StringBuilder myName2 = new StringBuilder();
                for (int i = 0; i < 21; i++) {
                    if (i < myCourses.get(1).getName().length()) {
                        myName2.append(myCourses.get(1).getName().charAt(i));
                    } else  {
                        myName2.append(" ");
                    }
                }
                myName2.append(" . .");
                name2.setText(myName2.toString());
                StringBuilder strBldr2 = new StringBuilder();
                int counter2 = 1;
                for (String prof : myCourses.get(1).getProfessors()) {
                    if (counter2 == 1) {
                        strBldr2.append(prof);
                    } else {
                        strBldr2.append(" / ").append(prof);
                    }
                    counter2++;
                }
                profs2.setText(strBldr2.toString());
                int avg2 = myCourses.get(1).getAverageRating();
                for (int i = 0; i < 5; i++) {
                    if (i < avg2) {
                        stars2[i].setImageDrawable(getResources().getDrawable(R.drawable.star_filled));
                    } else {
                        stars2[i].setImageDrawable(getResources().getDrawable(R.drawable.star_unfilled));;
                    }
                }


                TextView designation3 = (TextView) myView.findViewById(R.id.designationHome3);
                TextView name3 = (TextView) myView.findViewById(R.id.nameHome3);
                TextView profs3 = (TextView) myView.findViewById(R.id.profsHome3);
                ImageView[] stars3 = {
                        (ImageView) myView.findViewById(R.id.avg_star1Home3),
                        (ImageView) myView.findViewById(R.id.avg_star2Home3),
                        (ImageView) myView.findViewById(R.id.avg_star3Home3),
                        (ImageView) myView.findViewById(R.id.avg_star4Home3),
                        (ImageView) myView.findViewById(R.id.avg_star5Home3),
                };
                courseItem3 = myCourses.get(2);
                designation3.setText(myCourses.get(2).getDesignation());
                StringBuilder myName3 = new StringBuilder();
                for (int i = 0; i < 21; i++) {
                    if (i < myCourses.get(2).getName().length()) {
                        myName3.append(myCourses.get(2).getName().charAt(i));
                    } else {
                        myName3.append(" ");
                    }
                }
                myName3.append(" . .");
                name3.setText(myName3.toString());
                StringBuilder strBldr3 = new StringBuilder();
                int counter3 = 1;
                for (String prof : myCourses.get(2).getProfessors()) {
                    if (counter3 == 1) {
                        strBldr3.append(prof);
                    } else {
                        strBldr3.append(" / ").append(prof);
                    }
                    counter3++;
                }
                profs3.setText(strBldr3.toString());
                int avg3 = myCourses.get(2).getAverageRating();
                for (int i = 0; i < 5; i++) {
                    if (i < avg3) {
                        stars3[i].setImageDrawable(getResources().getDrawable(R.drawable.star_filled));
                    } else {
                        stars3[i].setImageDrawable(getResources().getDrawable(R.drawable.star_unfilled));;
                    }
                }

                // set the course card details

            }
        } else {
            courseCards[0].setVisibility(View.GONE);
            courseCards[1].setVisibility(View.GONE);
            courseCards[2].setVisibility(View.GONE);
        }
    }

    public void fillReviews(View myView) {
        dbref.child("app_data").child("recentReviews").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> reviews = snapshot.getChildren();
                for (DataSnapshot rev : reviews) {
                    Iterable<DataSnapshot> currRev = rev.getChildren();
                    for (DataSnapshot field : currRev) {
                        if (field.getKey().equals("avgRating")) {
                            avgRating = field.getValue(Integer.class);
                        } else if (field.getKey().equals("date")) {
                            date = field.getValue(String.class);
                        } else if (field.getKey().equals("firstRating")) {
                            firstRating = field.getValue(Integer.class);
                        } else if (field.getKey().equals("reviewContent")) {
                            reviewContent = field.getValue(String.class);
                        } else if (field.getKey().equals("reviewerName")) {
                            reviewerName = field.getValue(String.class);
                        } else if (field.getKey().equals("secondRating")) {
                            secondRating = field.getValue(Integer.class);
                        } else if (field.getKey().equals("courseName")) {
                            courseName = field.getValue(String.class);
                        } else if (field.getKey().equals("professorName")) {
                            professorName = field.getValue(String.class);
                        } else if (field.getKey().equals("funRating")) {
                            funRating = field.getValue(Integer.class);
                        } else if (field.getKey().equals("workRating")) {
                            workRating = field.getValue(Integer.class);
                        }
                    }
                    ReviewItem newReview = new ReviewItem(avgRating, date, firstRating, reviewContent, reviewerName, secondRating);
                    newReview.setProfessorName(professorName);
                    newReview.setCourseName(courseName);
                    newReview.setHelperRating1(funRating);
                    newReview.setHelperRating2(workRating);
                    newReview.setHome();
                    recentReviews.add(newReview);
                }

                fillReviewsHelper(myView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void fillReviewsHelper(View myView) {
        TextView nothing = (TextView) myView.findViewById(R.id.review_stub);
        ImageView nothingImage = (ImageView) myView.findViewById(R.id.nothing_2);
        int size = 0;
        int avg = 0;
        if (recentReviews == null || recentReviews.size() == 0) {
            nothing.setVisibility(View.VISIBLE);
            nothingImage.setVisibility(View.VISIBLE);
            reviewCards[0].setVisibility(View.GONE);
            reviewCards[1].setVisibility(View.GONE);
            reviewCards[2].setVisibility(View.GONE);
        } else {
            nothing.setVisibility(View.GONE);
            nothingImage.setVisibility(View.GONE);
            size = recentReviews.size();
            Collections.reverse(recentReviews);
            if (size == 1) {
                TextView username = (TextView) myView.findViewById(R.id.userName1);
                TextView date = (TextView) myView.findViewById(R.id.date1);
                TextView coursename = (TextView) myView.findViewById(R.id.nameHome4);
                TextView content = (TextView) myView.findViewById(R.id.contentHome1);

                ImageView[] avg_stars = new ImageView[5];
                avg_stars[0] = (ImageView) myView.findViewById(R.id.avg_star1Home4);
                avg_stars[1] = (ImageView) myView.findViewById(R.id.avg_star2Home4);
                avg_stars[2] = (ImageView) myView.findViewById(R.id.avg_star3Home4);
                avg_stars[3] = (ImageView) myView.findViewById(R.id.avg_star4Home4);
                avg_stars[4] = (ImageView) myView.findViewById(R.id.avg_star5Home4);

                username.setText(recentReviews.get(0).getReviewerName());
                date.setText(recentReviews.get(0).getDate());
                coursename.setText(recentReviews.get(0).getCourseName());
                content.setText(recentReviews.get(0).getReviewContent());

                avg = recentReviews.get(0).getAvgRating();
                for (int i = 0; i < 5; i++) {
                    if (i < avg) {
                        avg_stars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_filled));
                    } else {
                        avg_stars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_unfilled));
                    }
                }
                reviewItem1 = recentReviews.get(0);
                reviewCards[0].setVisibility(View.VISIBLE);
                reviewCards[1].setVisibility(View.GONE);
                reviewCards[2].setVisibility(View.GONE);
            } else if (size == 2) {
                TextView username = (TextView) myView.findViewById(R.id.userName1);
                TextView date = (TextView) myView.findViewById(R.id.date1);
                TextView coursename = (TextView) myView.findViewById(R.id.nameHome4);
                TextView content = (TextView) myView.findViewById(R.id.contentHome1);

                ImageView[] avg_stars = new ImageView[5];
                avg_stars[0] = (ImageView) myView.findViewById(R.id.avg_star1Home4);
                avg_stars[1] = (ImageView) myView.findViewById(R.id.avg_star2Home4);
                avg_stars[2] = (ImageView) myView.findViewById(R.id.avg_star3Home4);
                avg_stars[3] = (ImageView) myView.findViewById(R.id.avg_star4Home4);
                avg_stars[4] = (ImageView) myView.findViewById(R.id.avg_star5Home4);

                username.setText(recentReviews.get(0).getReviewerName());
                date.setText(recentReviews.get(0).getDate());
                coursename.setText(recentReviews.get(0).getCourseName());
                content.setText(recentReviews.get(0).getReviewContent());

                avg = recentReviews.get(0).getAvgRating();
                for (int i = 0; i < 5; i++) {
                    if (i < avg) {
                        avg_stars[i].setImageDrawable(getResources().getDrawable(R.drawable.star_filled));
                    } else {
                        avg_stars[i].setImageDrawable(getResources().getDrawable(R.drawable.star_unfilled));
                    }
                }


                username = (TextView) myView.findViewById(R.id.userName2);
                date = (TextView) myView.findViewById(R.id.date2);
                coursename = (TextView) myView.findViewById(R.id.nameHome5);
                content = (TextView) myView.findViewById(R.id.contentHome2);

                avg_stars[0] = (ImageView) myView.findViewById(R.id.avg_star1Home5);
                avg_stars[1] = (ImageView) myView.findViewById(R.id.avg_star2Home5);
                avg_stars[2] = (ImageView) myView.findViewById(R.id.avg_star3Home5);
                avg_stars[3] = (ImageView) myView.findViewById(R.id.avg_star4Home5);
                avg_stars[4] = (ImageView) myView.findViewById(R.id.avg_star5Home5);

                username.setText(recentReviews.get(1).getReviewerName());
                date.setText(recentReviews.get(1).getDate());
                coursename.setText(recentReviews.get(1).getCourseName());
                content.setText(recentReviews.get(1).getReviewContent());

                avg = recentReviews.get(1).getAvgRating();
                for (int i = 0; i < 5; i++) {
                    if (i < avg) {
                        avg_stars[i].setImageDrawable(getResources().getDrawable(R.drawable.star_filled));
                    } else {
                        avg_stars[i].setImageDrawable(getResources().getDrawable(R.drawable.star_unfilled));
                    }
                }
                reviewItem1 = recentReviews.get(0);
                reviewItem2 = recentReviews.get(1);


                reviewCards[0].setVisibility(View.VISIBLE);
                reviewCards[1].setVisibility(View.VISIBLE);
                reviewCards[2].setVisibility(View.GONE);
            } else {
                TextView username = (TextView) myView.findViewById(R.id.userName1);
                TextView date = (TextView) myView.findViewById(R.id.date1);
                TextView coursename = (TextView) myView.findViewById(R.id.nameHome4);
                TextView content = (TextView) myView.findViewById(R.id.contentHome1);

                ImageView[] avg_stars = new ImageView[5];
                avg_stars[0] = (ImageView) myView.findViewById(R.id.avg_star1Home4);
                avg_stars[1] = (ImageView) myView.findViewById(R.id.avg_star2Home4);
                avg_stars[2] = (ImageView) myView.findViewById(R.id.avg_star3Home4);
                avg_stars[3] = (ImageView) myView.findViewById(R.id.avg_star4Home4);
                avg_stars[4] = (ImageView) myView.findViewById(R.id.avg_star5Home4);

                username.setText(recentReviews.get(0).getReviewerName());
                date.setText(recentReviews.get(0).getDate());
                coursename.setText(recentReviews.get(0).getCourseName());
                content.setText(recentReviews.get(0).getReviewContent());

                avg = recentReviews.get(0).getAvgRating();
                for (int i = 0; i < 5; i++) {
                    if (i < avg) {
                        avg_stars[i].setImageDrawable(getResources().getDrawable(R.drawable.star_filled));
                    } else {
                        avg_stars[i].setImageDrawable(getResources().getDrawable(R.drawable.star_unfilled));
                    }
                }


                username = (TextView) myView.findViewById(R.id.userName2);
                date = (TextView) myView.findViewById(R.id.date2);
                coursename = (TextView) myView.findViewById(R.id.nameHome5);
                content = (TextView) myView.findViewById(R.id.contentHome2);

                avg_stars[0] = (ImageView) myView.findViewById(R.id.avg_star1Home5);
                avg_stars[1] = (ImageView) myView.findViewById(R.id.avg_star2Home5);
                avg_stars[2] = (ImageView) myView.findViewById(R.id.avg_star3Home5);
                avg_stars[3] = (ImageView) myView.findViewById(R.id.avg_star4Home5);
                avg_stars[4] = (ImageView) myView.findViewById(R.id.avg_star5Home5);

                username.setText(recentReviews.get(1).getReviewerName());
                date.setText(recentReviews.get(1).getDate());
                coursename.setText(recentReviews.get(1).getCourseName());
                content.setText(recentReviews.get(1).getReviewContent());

                avg = recentReviews.get(1).getAvgRating();
                for (int i = 0; i < 5; i++) {
                    if (i < avg) {
                        avg_stars[i].setImageDrawable(getResources().getDrawable(R.drawable.star_filled));
                    } else {
                        avg_stars[i].setImageDrawable(getResources().getDrawable(R.drawable.star_unfilled));
                    }
                }

                username = (TextView) myView.findViewById(R.id.userName3);
                date = (TextView) myView.findViewById(R.id.date3);
                coursename = (TextView) myView.findViewById(R.id.nameHome6);
                content = (TextView) myView.findViewById(R.id.contentHome3);

                avg_stars[0] = (ImageView) myView.findViewById(R.id.avg_star1Home6);
                avg_stars[1] = (ImageView) myView.findViewById(R.id.avg_star2Home6);
                avg_stars[2] = (ImageView) myView.findViewById(R.id.avg_star3Home6);
                avg_stars[3] = (ImageView) myView.findViewById(R.id.avg_star4Home6);
                avg_stars[4] = (ImageView) myView.findViewById(R.id.avg_star5Home6);

                username.setText(recentReviews.get(2).getReviewerName());
                date.setText(recentReviews.get(2).getDate());
                coursename.setText(recentReviews.get(2).getCourseName());
                content.setText(recentReviews.get(2).getReviewContent());

                avg = recentReviews.get(2).getAvgRating();
                for (int i = 0; i < 5; i++) {
                    if (i < avg) {
                        avg_stars[i].setImageDrawable(getResources().getDrawable(R.drawable.star_filled));
                    } else {
                        avg_stars[i].setImageDrawable(getResources().getDrawable(R.drawable.star_unfilled));
                    }
                }
                reviewItem1 = recentReviews.get(0);
                reviewItem2 = recentReviews.get(1);
                reviewItem3 = recentReviews.get(2);

                reviewCards[0].setVisibility(View.VISIBLE);
                reviewCards[1].setVisibility(View.VISIBLE);
                reviewCards[2].setVisibility(View.VISIBLE);
            }
        }

    }
}