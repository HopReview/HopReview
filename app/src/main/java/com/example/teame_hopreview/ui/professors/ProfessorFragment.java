package com.example.teame_hopreview.ui.professors;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfessorFragment extends Fragment {

    private static final String TAG = "dbref: ";

    private RecyclerView myList;
    private CardView myCard;
    private MainActivity myAct;
    private Professor professorItem;
    private ReviewItem reviewItem;
    protected ArrayList<Professor> myProfessors;
    protected ArrayList<Professor> myProfessorsCopy;
    protected ArrayList<ReviewItem> myReviews;
    private ProfessorAdapter pa;
    private FirebaseDatabase mdbase;
    private DatabaseReference dbref;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View myView = inflater.inflate(R.layout.fragment_professors, container, false);

        context = getActivity().getApplicationContext();
        dbref = FirebaseDatabase.getInstance().getReference();

        myAct = (MainActivity) getActivity();
        myAct.getSupportActionBar().setTitle("Professors");

        myList = (RecyclerView) myView.findViewById(R.id.myReviewsList);
        myCard = (CardView) myView.findViewById(R.id.professor_card);
        myProfessors = new ArrayList<Professor>();
        myProfessorsCopy = new ArrayList<Professor>();
        myReviews = new ArrayList<ReviewItem>();
        setHasOptionsMenu(true);

        pa = new ProfessorAdapter(myAct, context, myProfessors, myProfessorsCopy);

        myList.setAdapter(pa);
        myList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                long count = snapshot.getChildrenCount();
                Log.d(TAG, "Children count: " + count);
                Log.d(TAG, "Course count: " + snapshot.child("professors_data").getChildrenCount());

                myProfessors.clear();
                Iterable<DataSnapshot> professors = snapshot.child("professors_data").getChildren();


                for (DataSnapshot profs : professors) {
                    myReviews.clear();
                    String professor = profs.getKey();
                    String department = "";
                    ArrayList<String> courseNames = new ArrayList<String>();
                    int avgRate = 0;
                    int gradeRate = 0;
                    int knowledgeRate = 0;


                    String date = "";
                    int revAvgRate = 0;
                    int revGradeRate = 0;
                    int revKnowRate = 0;
                    String reviewerContent = "";
                    String reviewerName = "";

                    Iterable<DataSnapshot> list = profs.getChildren();
                    int counter = 1;
                    for (DataSnapshot item : list) {
                        if (counter == 1) {
                            avgRate = item.getValue(Integer.class);
                        } else if (counter == 2) {
                            Iterable<DataSnapshot> crs = item.getChildren();
                            for (DataSnapshot course : crs) {
                                courseNames.add(course.getValue(String.class));
                            }
                        } else if (counter == 3) {
                            department = item.getValue(String.class);
                        } else if (counter == 4) {
                            gradeRate = item.getValue(Integer.class);
                        } else if (counter == 5) {
                            knowledgeRate = item.getValue(Integer.class);
                        } else {
                            Iterable<DataSnapshot> rev = item.getChildren();
                            for (DataSnapshot review : rev) {
                                Iterable<DataSnapshot> currRev = review.getChildren();
                                int counter2 = 1;
                                for (DataSnapshot curr : currRev) {
                                    if (counter2 == 1) {
                                        revAvgRate = curr.getValue(Integer.class);
                                    } else if (counter2 == 2) {
                                        date = curr.getValue(String.class);
                                    } else if (counter2 == 3) {
                                        revGradeRate = curr.getValue(Integer.class);
                                    } else if (counter2 == 4) {
                                        revKnowRate = curr.getValue(Integer.class);
                                    } else if (counter2 == 5) {
                                        reviewerContent = curr.getValue(String.class);
                                    } else {
                                        reviewerName = curr.getValue(String.class);
                                    }

                                    counter2++;
                                }

                                reviewItem = new ReviewItem(revAvgRate, date, revGradeRate, reviewerContent, reviewerName, revKnowRate);
                                myReviews.add(reviewItem);
                            }
                        }

                        counter++;
                    }
                    professorItem = new Professor(professor, department, gradeRate, knowledgeRate, avgRate);

                    for (ReviewItem review : myReviews) {
                        professorItem.addReview(review);
                    }

                    for (String str : courseNames) {
                        professorItem.addCourseNames(str);
                    }

                    myProfessors.add(professorItem);
                    myProfessorsCopy.add(professorItem);
                }

                pa.notifyDataSetChanged();

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
                System.out.println("search query submit");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("tap");
                return false;
            }
        });

    }

}