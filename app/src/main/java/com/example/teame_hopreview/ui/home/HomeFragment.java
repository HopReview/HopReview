package com.example.teame_hopreview.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.teame_hopreview.MainActivity;
import com.example.teame_hopreview.R;
import com.example.teame_hopreview.ReviewItem;
import com.example.teame_hopreview.User;
import com.example.teame_hopreview.databinding.FragmentHomeBinding;
import com.example.teame_hopreview.ui.course.CourseItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private static final String TAG = "dbref: ";

    private CardView myCard;
    private ReviewItem reviewItem;
    DatabaseReference dbref;
    private MainActivity myAct;
    private CourseItem courseItem;
    private Context context;
    private CardView[] courseCards = new CardView[3];
    private CardView[] reviewCards = new CardView[3];

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_home, container, false);
        myAct = (MainActivity) getActivity();
        context = myAct.getApplicationContext();
        myAct.getSupportActionBar().setTitle("Home");
        dbref = FirebaseDatabase.getInstance().getReference();
        courseItem = new CourseItem();

        ImageView nothingCourses = (ImageView) myView.findViewById(R.id.nothing_1);
        ImageView nothingReviews = (ImageView) myView.findViewById(R.id.nothing_2);
        TextView notCrsTxt = (TextView) myView.findViewById(R.id.course_stub);
        TextView notRevTxt = (TextView) myView.findViewById(R.id.course_stub1);


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
        ArrayList<String> recentlyViewed = myAct.user.getRecentlyViewedList();
        if (recentlyViewed != null) { System.out.println("SIZE: " + recentlyViewed.size()); }

        if (recentlyViewed != null && recentlyViewed.size() != 0) {
            nothingCourses.setVisibility(View.GONE);
            notCrsTxt.setVisibility(View.GONE);

            if (recentlyViewed.size() == 1) {
                courseCards[0].setVisibility(View.VISIBLE);

                firstCourse = recentlyViewed.get(0);
                findCourse(firstCourse);

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



                designation1.setText(courseItem.getDesignation());
                name1.setText(courseItem.getName());
                StringBuilder strBldr = new StringBuilder();
                int counter = 1;
                ArrayList<String> profsss = courseItem.getProfessors();
                for (String prof : courseItem.getProfessors()) {
                    if (counter == 1) {
                        strBldr.append(prof);
                    } else {
                        strBldr.append(" / ").append(prof);
                    }
                    counter++;
                }
                profs1.setText(strBldr.toString());
                int avg = courseItem.getAverageRating();
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
            } else if (recentlyViewed.size() == 2) {
                courseCards[0].setVisibility(View.VISIBLE);
                courseCards[1].setVisibility(View.VISIBLE);

                firstCourse = recentlyViewed.get(1);
                findCourse(firstCourse);

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



                designation1.setText(courseItem.getDesignation());
                name1.setText(courseItem.getName());
                StringBuilder strBldr = new StringBuilder();
                int counter = 1;
                for (String prof : courseItem.getProfessors()) {
                    if (counter == 1) {
                        strBldr.append(prof);
                    } else {
                        strBldr.append(" / ").append(prof);
                    }
                    counter++;
                }
                profs1.setText(strBldr.toString());
                int avg = courseItem.getAverageRating();
                for (int i = 0; i < 5; i++) {
                    if (i < avg) {
                        stars1[i].setImageDrawable(getResources().getDrawable(R.drawable.star_filled));
                    } else {
                        stars1[i].setImageDrawable(getResources().getDrawable(R.drawable.star_unfilled));;
                    }
                }


                secondCourse = recentlyViewed.get(0);
                findCourse(secondCourse);

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



                designation2.setText(courseItem.getDesignation());
                name2.setText(courseItem.getName());
                StringBuilder strBldr2 = new StringBuilder();
                int counter2 = 1;
                for (String prof : courseItem.getProfessors()) {
                    if (counter2 == 1) {
                        strBldr2.append(prof);
                    } else {
                        strBldr2.append(" / ").append(prof);
                    }
                    counter2++;
                }
                profs2.setText(strBldr2.toString());
                int avg2 = courseItem.getAverageRating();
                for (int i = 0; i < 5; i++) {
                    if (i < avg2) {
                        stars2[i].setImageDrawable(getResources().getDrawable(R.drawable.star_filled));
                    } else {
                        stars2[i].setImageDrawable(getResources().getDrawable(R.drawable.star_unfilled));;
                    }
                }

                courseCards[2].setVisibility(View.GONE);
                // set the course card details
            } else if (recentlyViewed.size() == 3) {
                courseCards[0].setVisibility(View.VISIBLE);
                courseCards[1].setVisibility(View.VISIBLE);
                courseCards[2].setVisibility(View.VISIBLE);

                firstCourse = recentlyViewed.get(2);
                findCourse(firstCourse);

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



                designation1.setText(courseItem.getDesignation());
                name1.setText(courseItem.getName());
                StringBuilder strBldr = new StringBuilder();
                int counter = 1;
                for (String prof : courseItem.getProfessors()) {
                    if (counter == 1) {
                        strBldr.append(prof);
                    } else {
                        strBldr.append(" / ").append(prof);
                    }
                    counter++;
                }
                profs1.setText(strBldr.toString());
                int avg = courseItem.getAverageRating();
                for (int i = 0; i < 5; i++) {
                    if (i < avg) {
                        stars1[i].setImageDrawable(getResources().getDrawable(R.drawable.star_filled));
                    } else {
                        stars1[i].setImageDrawable(getResources().getDrawable(R.drawable.star_unfilled));;
                    }
                }

                secondCourse = recentlyViewed.get(1);
                findCourse(secondCourse);

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



                designation2.setText(courseItem.getDesignation());
                name2.setText(courseItem.getName());
                StringBuilder strBldr2 = new StringBuilder();
                int counter2 = 1;
                for (String prof : courseItem.getProfessors()) {
                    if (counter2 == 1) {
                        strBldr2.append(prof);
                    } else {
                        strBldr2.append(" / ").append(prof);
                    }
                    counter2++;
                }
                profs2.setText(strBldr2.toString());
                int avg2 = courseItem.getAverageRating();
                for (int i = 0; i < 5; i++) {
                    if (i < avg2) {
                        stars2[i].setImageDrawable(getResources().getDrawable(R.drawable.star_filled));
                    } else {
                        stars2[i].setImageDrawable(getResources().getDrawable(R.drawable.star_unfilled));;
                    }
                }


                thirdCourse = recentlyViewed.get(0);
                findCourse(thirdCourse);

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



                designation3.setText(courseItem.getDesignation());
                name3.setText(courseItem.getName());
                StringBuilder strBldr3 = new StringBuilder();
                int counter3 = 1;
                for (String prof : courseItem.getProfessors()) {
                    if (counter3 == 1) {
                        strBldr3.append(prof);
                    } else {
                        strBldr3.append(" / ").append(prof);
                    }
                    counter3++;
                }
                profs3.setText(strBldr3.toString());
                int avg3 = courseItem.getAverageRating();
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
            System.out.println("REACHED HERE");
            courseCards[0].setVisibility(View.GONE);
            courseCards[1].setVisibility(View.GONE);
            courseCards[2].setVisibility(View.GONE);
        }





        // For now, otherwise have a shared preference for it
        // and create or initilize from database upon login/signup
        User currUser = new User("bluejay01", "bluejay01@jhu.edu");


        return myView;
    }

    public void findCourse(String courseName) {
        courseItem = new CourseItem();
        dbref.child("courses_data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> courses = snapshot.getChildren();
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

                    int counter = 1;
                    Iterable<DataSnapshot> list = crs.getChildren();
                    for (DataSnapshot item : list) {
                        if (counter == 1) {
                            avgRate = item.getValue(Integer.class);
                        } else if (counter == 2) {
                            designation = item.getValue(String.class);
                        } else if (counter == 3) {
                            name = item.getValue(String.class);
                            if (!name.equals(courseName)) {
                                break;
                            }
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
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}