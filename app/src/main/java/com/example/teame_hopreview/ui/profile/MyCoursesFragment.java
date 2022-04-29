package com.example.teame_hopreview.ui.profile;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teame_hopreview.MainActivity;
import com.example.teame_hopreview.R;
import com.example.teame_hopreview.ReviewItem;
import com.example.teame_hopreview.User;
import com.example.teame_hopreview.ui.course.CourseAdapter;
import com.example.teame_hopreview.ui.course.CourseItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyCoursesFragment extends Fragment {

    private static final String TAG = "dbref: ";

    private RecyclerView myList;
    private MainActivity myAct;
    private CourseItem courseItem;
    private ReviewItem reviewItem;
    protected ArrayList<CourseItem> myCourses;
    protected ArrayList<CourseItem> myCoursesCopy;
    protected ArrayList<ReviewItem> myReviews;
    private CourseBMAdapter ca;
    private ArrayList<String> professors;
    private DatabaseReference dbref;
    private User user;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View myView = inflater.inflate(R.layout.frag_my_courses, container, false);

        context = getActivity().getApplicationContext();
        dbref = FirebaseDatabase.getInstance().getReference();

        myAct = (MainActivity) getActivity();
        myAct.getSupportActionBar().setTitle("My Courses");
        myCourses = new ArrayList<CourseItem>();
        myCoursesCopy = new ArrayList<CourseItem>();
        myReviews = new ArrayList<ReviewItem>();
        professors = new ArrayList<>();
        setHasOptionsMenu(true);

        user = myAct.user;

        ca = new CourseBMAdapter(myAct, context, myCourses, myCoursesCopy);
        myList = (RecyclerView) myView.findViewById(R.id.myCoursesList);

        myList.setAdapter(ca);
        myList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                long count = snapshot.getChildrenCount();

                myCourses.clear();

                Iterable<DataSnapshot> courses = snapshot.child("courses_data").getChildren();

                for (DataSnapshot crs : courses) {
                    myReviews.clear();
                    professors.clear();

                    if (null == user.getBookmarkedCourses()) {
                        break;
                    }

                    Iterable<DataSnapshot> list = crs.getChildren();
                    String name = " ";
                    String num = " ";
                    String designation = " ";
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
                    for (DataSnapshot item : list) {
                        if (counter == 1) {
                            avgRate = item.getValue(Integer.class);
                        } else if (counter == 2) {
                            designation = item.getValue(String.class);
                        } else if (counter == 3) {
                            name = item.getValue(String.class);
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
                                reviewItem = new ReviewItem(revAvgRate, date, firstRating, reviewerContent, reviewerName, secondRating);
                                myReviews.add(reviewItem);
                            }
                        } else if (counter == 8) {
                            workRate = item.getValue(Integer.class);
                        }
                        counter++;
                    }

                    if (!user.getBookmarkedCourses().contains(name)) {
                        continue;
                    }

                    courseItem = new CourseItem(avgRate, designation, name, num, funRate, professors, workRate);
                    for (ReviewItem r : myReviews) {
                        courseItem.addReview(r);
                    }
                    myCourses.add(courseItem);
                    myCoursesCopy.add(courseItem);
                }

                ca.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        return myView;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.bar_menu, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView sv = new SearchView(((myAct.getSupportActionBar().getThemedContext())));
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        item.setActionView(sv);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ca.getFilter().filter(newText);
                return false;
            }
        });

    }


}