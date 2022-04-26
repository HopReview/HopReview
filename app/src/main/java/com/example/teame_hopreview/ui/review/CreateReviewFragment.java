package com.example.teame_hopreview.ui.review;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.teame_hopreview.MainActivity;
import com.example.teame_hopreview.R;
import com.example.teame_hopreview.ReviewItem;
import com.example.teame_hopreview.ui.course.CourseItem;
import com.example.teame_hopreview.ui.professors.Professor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateReviewFragment} factory method to
 * create an instance of this fragment.
 */
public class CreateReviewFragment extends Fragment {

    private MainActivity mainActivity;

    private ListManager listManager;

    private ArrayList<CourseItem> masterCourseCopy = new ArrayList<>();

    private ArrayList<String> masterProfessorCopy = new ArrayList<>();

    private ArrayAdapter<CourseWrapper> courseAdapter;

    private AutoCompleteTextView courseDropdown;

    private ArrayAdapter<String> professorAdapter;

    private AutoCompleteTextView professorDropdown;

    private DatabaseReference dbref;

    private TextInputEditText commentEditText;

    private CourseItem selectedCourse;

    private String selectedProfessor;

    private int funRating = 0;

    private int workloadRating = 0;

    private int knowledgeRating = 0;

    private int gradingRating = 0;

    // if 0, coming from a course; if 1, coming from a professor
    private int setValue = 0;

    public String getDefaultCourseNumber() {
        return defaultCourseNumber;
    }

    public void setDefaultCourseNumber(String defaultCourseNumber) {
        this.defaultCourseNumber = defaultCourseNumber;
    }

    private String defaultCourseNumber;

    private String defaultProfessorName;


    public CreateReviewFragment() {
        listManager = new ListManager();
        selectedCourse = null;
        dbref = FirebaseDatabase.getInstance().getReference();
        dbref.addValueEventListener(new DatabaseChangeListener());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainActivity = (MainActivity) getActivity();
        mainActivity.getSupportActionBar().setTitle("Create Review");

        View view = inflater.inflate(R.layout.fragment_review, container, false);

        //Set views
        setupCourseDropdown(view);
        setupProfessorDropdown(view);
        setupReviews(view);

        Handler handler = new Handler(Looper.getMainLooper());

        Timer t = new java.util.Timer();
        t.schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                setDropdownDefaultValues();
                            }
                        });
                    }
                },
                100
        );

        // Inflate the layout for this fragment
        return view;
    }

    private void setDropdownDefaultValues() {
        courseDropdown.setText(null);
        courseDropdown.setFocusable(false);
        professorDropdown.setText(null);
        professorDropdown.setFocusable(false);
        if (defaultCourseNumber != null) {
            setValue = 0;
            for (CourseItem item: listManager.getCourses()) {
                if (defaultCourseNumber.equals(item.getCourseNumber())) {
                    selectedCourse = item;
                    courseDropdown.setText(selectedCourse.getName(), false);
                    fillProfessorDropdown(selectedCourse);
                    break;
                }
            }
        }
        Boolean foundProfessor = false;
        if (defaultCourseNumber == null && defaultProfessorName != null) {
            setValue = 1;
            for (CourseItem item: listManager.getCourses()) {
                for (String profName: item.getProfessors()) {
                    if (defaultProfessorName.equals(profName)) {
                        professorDropdown.setText(defaultProfessorName, false);
                        foundProfessor = true;
                        break;
                    }
                }
            }
        }
        if (foundProfessor) {
            selectedProfessor = defaultProfessorName;
            fillCourseDropdown(defaultProfessorName);
        }
    }

    private void setupCourseDropdown(View view) {
        courseDropdown = (AutoCompleteTextView) view.findViewById(R.id.create_review_course_dropdown);
        courseDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Get course item
                CourseItem selected = listManager.getCourses().get(position);
                selectedCourse = selected;
                if (setValue == 0) {
                    fillProfessorDropdown(selected);
                }
            }
        });

        courseAdapter = new ArrayAdapter<>(getContext(), R.layout.create_review_dropdown, listManager.getCourseWrappers());
        courseDropdown.setAdapter(courseAdapter);
    }

    private void setupProfessorDropdown(View view) {
        professorDropdown = (AutoCompleteTextView) view.findViewById(R.id.create_review_professor_dropdown);
        professorDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String professor = listManager.getProfessors().get(position);
                if (setValue == 1) {
                    selectedProfessor = professor;
                    fillCourseDropdown(professor);
                }
            }
        });

        professorAdapter = new ArrayAdapter<>(getContext(), R.layout.create_review_dropdown, listManager.getProfessors());
        professorDropdown.setAdapter(professorAdapter);
    }

    private void fillProfessorDropdown(CourseItem selectedCourse) {
        //Aim: based on current selected course, fill the professor dropdown
        //ArrayList<String> professors = new ArrayList<>();
        //professors.add(selectedCourse.getProfessors().get(0));
        listManager.setProfessors(selectedCourse.getProfessors());
        professorAdapter.notifyDataSetChanged();
        professorDropdown.setText(selectedCourse.getProfessors().get(0));
        selectedProfessor = selectedCourse.getProfessors().get(0);
        professorAdapter = new ArrayAdapter<>(getContext(), R.layout.create_review_dropdown, listManager.getProfessors());
        professorDropdown.setAdapter(professorAdapter);
    }

    private void fillCourseDropdown(String selectedProfessor) {
        //Aim: based on current selected professor, fill the course dropdown
        ArrayList<CourseItem> courses = new ArrayList<>();
        for (CourseItem course: masterCourseCopy) {
            for (String profName: course.getProfessors()) {
                if (selectedProfessor.equals(profName)) {
                    courses.add(course.Copy());
                }
            }
        }
        listManager.setCourses(courses);
        courseAdapter.notifyDataSetChanged();
        courseAdapter = new ArrayAdapter<>(getContext(), R.layout.create_review_dropdown, listManager.getCourseWrappers());
        selectedCourse = courses.get(0);
        courseDropdown.setText(selectedCourse.getName(), false);
        courseDropdown.setAdapter(courseAdapter);
    }

    private void setupReviews(View view) {
        RatingsView funRatings = view.findViewById(R.id.create_review_fun_rating);
        funRatings.setOnRatingChangedListener(new OnRatingChangedListener() {
            @Override
            public void onRatingChanged(int newRating) {
                funRating = newRating;
            }
        });

        RatingsView workloadRatings = view.findViewById(R.id.create_review_workload_rating);
        workloadRatings.setOnRatingChangedListener(new OnRatingChangedListener() {
            @Override
            public void onRatingChanged(int newRating) {
                workloadRating = newRating;
            }
        });

        RatingsView gradingRatings = view.findViewById(R.id.create_review_grading_rating);
        gradingRatings.setOnRatingChangedListener(new OnRatingChangedListener() {
            @Override
            public void onRatingChanged(int newRating) {
                gradingRating = newRating;
            }
        });

        RatingsView knowledgeRatings = view.findViewById(R.id.create_review_knowledge_rating);
        knowledgeRatings.setOnRatingChangedListener(new OnRatingChangedListener() {
            @Override
            public void onRatingChanged(int newRating) {
                knowledgeRating = newRating;
            }
        });

        Button submitButton = (Button) view.findViewById(R.id.create_review_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitReview();
            }
        });

        commentEditText = (TextInputEditText) view.findViewById(R.id.create_review_comment);
    }

    public void submitReview() {
        //Get course
        if (selectedCourse == null) {
            //Display toast
            showToast("Please select a course");
            return;
        }

        if (funRating == 0 || workloadRating == 0 || gradingRating == 0 || knowledgeRating == 0) {
            //Display toast
            showToast("Ratings incomplete");
            return;
        }

        String reviewContent = commentEditText.getText().toString();
        if (reviewContent.trim().equals("")) {
            //Display toast
            showToast("Please write a comment");
            return;
        }


        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String date = dateFormat.format(Calendar.getInstance().getTime());

        int firstRating = funRating;
        int secondRating = gradingRating;
        int avgRating = (funRating + gradingRating) /2;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String reviewerName = user.getEmail();

        Integer reviewId = selectedCourse.getReviews().size()+1;
        /*Task<Void> task1 = dbref.child("courses_data").child(selectedCourse.getId()).child("reviews").child(reviewId.toString()).child("reviewContent").setValue(reviewContent);
        Task<Void> task2 = dbref.child("courses_data").child(selectedCourse.getId()).child("reviews").child(reviewId.toString()).child("date").setValue(date);
        Task<Void> task3 = dbref.child("courses_data").child(selectedCourse.getId()).child("reviews").child(reviewId.toString()).child("avgRating").setValue(avgRating);
        Task<Void> task4 = dbref.child("courses_data").child(selectedCourse.getId()).child("reviews").child(reviewId.toString()).child("firstRating").setValue(firstRating);
        Task<Void> task5 = dbref.child("courses_data").child(selectedCourse.getId()).child("reviews").child(reviewId.toString()).child("reviewerName").setValue(reviewerName);
        Task<Void> task6 = dbref.child("courses_data").child(selectedCourse.getId()).child("reviews").child(reviewId.toString()).child("secondRating").setValue(secondRating);*/

        //DatabaseReference profReviews = dbref.child("professors_data").child(selectedProfessor).child("reviews").child(reviewId.toString());
        /*Task<Void> task7 = profReviews.child("reviewContent").setValue(reviewContent);
        Task<Void> task8 = profReviews.child("date").setValue(date);
        Task<Void> task9 = profReviews.child("avgRating").setValue(avgRating);
        Task<Void> task10 = profReviews.child("firstRating").setValue(firstRating);
        Task<Void> task11 = profReviews.child("reviewerName").setValue(reviewerName);
        Task<Void> task12 = profReviews.child("secondRating").setValue(secondRating);*/

        DbReview review = new DbReview(reviewContent, date, avgRating, firstRating, reviewerName, secondRating);
        DbProfReview profReview = new DbProfReview(reviewContent, date, avgRating, firstRating, reviewerName, secondRating);
        createReview(review, profReview, selectedCourse.getId(), selectedProfessor);

        MainActivity myAct = (MainActivity) getActivity();
        ReviewItem toAdd = new ReviewItem(avgRating, date, firstRating, reviewContent, reviewerName, secondRating);
        myAct.user.addUserReview(toAdd);

        /*Task<Void> mainTask = Tasks.whenAll(task1, task2, task3, task4, task5, task6, task7, task8, task9, task10, task11, task12);
        mainTask.addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                showToast("Review published!");
            }
        });*/
    }

    private void createReview(DbReview review, DbProfReview profReview, String courseKey, String professorKey) {
        String courseReviewKey = dbref.child("courses_data").child(courseKey).child("reviews").push().getKey();
        Map<String, Object> courseReviewValues = review.toMap();

        String professorReviewKey = dbref.child("professors_data").child(professorKey).child("reviews").push().getKey();
        Map<String, Object> professorReviewValues = profReview.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/courses_data/" + courseKey + "/reviews/" + courseReviewKey, courseReviewValues);
        childUpdates.put("/professors_data/" + professorKey + "/reviews/" + professorReviewKey, professorReviewValues);

        dbref.updateChildren(childUpdates);
    }

    public void reset() {
        selectedCourse = null;
        workloadRating = 0;
        funRating = 0;
        gradingRating = 0;
        knowledgeRating = 0;
        if (professorDropdown != null) professorDropdown.setText(null);
        if (courseDropdown != null) {
            courseDropdown.setText(null);
            courseDropdown.setFocusable(false);
        }
    }

    private void showToast(String message) {
        Context context = getContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public String getDefaultProfessorName() {
        return defaultProfessorName;
    }

    public void setDefaultProfessorName(String defaultProfessorName) {
        this.defaultProfessorName = defaultProfessorName;
    }

    class DatabaseChangeListener implements ValueEventListener {

        private static final String TAG = "dbref: ";

        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            long count = snapshot.getChildrenCount();
            Log.d(TAG, "Children count: " + count);
            Log.d(TAG, "Course count: " + snapshot.child("courses_data").getChildrenCount());

            listManager.getCourses().clear();
            listManager.getCourseWrappers().clear();
            Iterable<DataSnapshot> courses = snapshot.child("courses_data").getChildren();
            ArrayList<ReviewItem> reviewsHolder = new ArrayList<ReviewItem>();

            for (DataSnapshot crs : courses) {
                reviewsHolder.clear();
                //System.out.println("Avg: " + crs.getValue(CourseItem.class).getAverageRating());
                //System.out.println("Fun: " + crs.getValue(CourseItem.class).getFunRating());
                //System.out.println("Work: " + crs.getValue(CourseItem.class).getWorkloadRating());
                //System.out.println("CourseNum: " + crs.getValue(CourseItem.class).getCourseNumber());
                //System.out.println("CourseDes: " + crs.getValue(CourseItem.class).getDesignation());
                String id = (String) crs.getKey();
                Iterable<DataSnapshot> list = crs.getChildren();
                String name = " ";
                String num = " ";
                String prof = new String();
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

                ArrayList<String> professors = new ArrayList<>();

                int counter = 1;
                for (DataSnapshot item : list) {
                    if (counter == 1) {
                        avgRate = item.getValue(Integer.class);
                    } else if (counter == 2) {
                        designation = item.getValue(String.class);
                    } else if (counter == 3) {
                        name = ensureStringLengthIsLessThanMax(item.getValue(String.class));
                    } else if (counter == 4) {
                        num = item.getValue(String.class);
                    } else if (counter == 5) {
                        funRate = item.getValue(Integer.class);
                    } else if (counter == 6) {
                        Iterable<DataSnapshot> profData = item.getChildren();
                        for (DataSnapshot tprof : profData) {
                            professors.add(tprof.getValue(String.class));
                        }
                    } else if (counter == 7) {
                        Iterable<DataSnapshot> reviews = item.getChildren();
                        for (DataSnapshot rev : reviews) {
                            Iterable<DataSnapshot> rr = rev.getChildren();
                            int c2 = 1;
                            for (DataSnapshot r : rr) {
                                if (c2 == 1) {
                                    try {
                                        revAvgRate = r.getValue(Integer.class);
                                    } catch (Exception ex) {

                                    }

                                } else if (c2 == 2) {
                                    try {
                                        date = r.getValue(String.class);
                                    } catch (Exception ex) {

                                    }
                                } else if (c2 == 3) {
                                    try {
                                        firstRating = r.getValue(Integer.class);
                                    } catch (Exception ex) {

                                    }
                                } else if (c2 == 4) {
                                    try {
                                        reviewerContent = r.getValue(String.class);
                                    } catch (Exception ex) {

                                    }
                                } else if (c2 == 5) {
                                    try {
                                        reviewerName = r.getValue(String.class);
                                    } catch (Exception ex) {

                                    }
                                } else if (c2 == 6) {
                                    try {
                                        secondRating = r.getValue(Integer.class);
                                    } catch (Exception ex) {

                                    }
                                }
                                c2++;
                            }
                        }
                        ReviewItem reviewItem = new ReviewItem(revAvgRate,date,firstRating,reviewerContent,reviewerName,secondRating);
                        reviewsHolder.add(reviewItem);
                    } else if (counter == 8) {
                        workRate = item.getValue(Integer.class);
                    }
                    counter++;
                }
                //CourseItem course = new CourseItem(avgRate, designation, name, num, funRate, prof, workRate);
                CourseItem course = new CourseItem(avgRate, designation, name, num, funRate, professors, workRate);
                for (ReviewItem r : reviewsHolder) {
                    course.addReview(r);
                }
                course.setId(id);
                listManager.addCourse(course);
            }

            masterCourseCopy.addAll(listManager.getCourses());

            if (courseAdapter != null) courseAdapter.notifyDataSetChanged();

            listManager.getProfessors().clear();
            Iterable<DataSnapshot> professors = snapshot.child("professors_data").getChildren();
            for (DataSnapshot profs : professors) {
                listManager.addProfessor( profs.getKey());
            }

            masterProfessorCopy.addAll(listManager.getProfessors());

            if (professorAdapter != null) professorAdapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.w(TAG, "Failed to read value.", error.toException());
        }

        private String ensureStringLengthIsLessThanMax(String string) {
            int maxLength = 30;
            String result = new String(string);
            if (string.length() > maxLength) {
                result = string.substring(0, maxLength - 3 - 1);
                //Add three dots to end
                result = result + "...";
            }
            return result;
        }
    }


}