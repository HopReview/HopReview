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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.teame_hopreview.MainActivity;
import com.example.teame_hopreview.R;
import com.example.teame_hopreview.ReviewItem;
import com.example.teame_hopreview.database.Course;
import com.example.teame_hopreview.database.CoursesOnChangeListener;
import com.example.teame_hopreview.database.DbManager;
import com.example.teame_hopreview.database.Professor;
import com.example.teame_hopreview.database.ProfessorsOnChangeListener;
import com.example.teame_hopreview.database.Review;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateReviewFragment} factory method to
 * create an instance of this fragment.
 */
public class CreateReviewFragment extends Fragment {

    private MainActivity mainActivity;

    private ListManager listManager;

    private ArrayList<Course> masterCourseCopy = new ArrayList<Course>();

    private ArrayList<Professor> masterProfessorCopy = new ArrayList<Professor>();

    private ArrayAdapter<CourseWrapper> courseAdapter;

    private AutoCompleteTextView courseDropdown;

    private ArrayAdapter<ProfessorWrapper> professorAdapter;

    private AutoCompleteTextView professorDropdown;

    private DatabaseReference dbref;

    private TextInputEditText commentEditText;

    private Course selectedCourse;

    private Professor selectedProfessor;

    private int funRating = 0;

    private int workloadRating = 0;

    private int knowledgeRating = 0;

    private int gradingRating = 0;

    // if 0, coming from a course; if 1, coming from a professor
    private int setValue = 0;

    private DbManager manager;

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
        manager = DbManager.getDbManager();
        listManager.setCourses(manager.getCourses());

        manager.addCoursesOnChangeListener(new CoursesOnChangeListener() {
            @Override
            public void onChange(List<Course> newCourses) {
                listManager.setCourses(manager.getCourses());
            }
        });
        manager.addProfessorsOnChangeListener(new ProfessorsOnChangeListener() {
            @Override
            public void onChange(List<Professor> newProfessors) {
                listManager.setProfessors(manager.getProfessors());
            }
        });
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
            for (Course item: listManager.getCourses()) {
                if (defaultCourseNumber.equals(item.getCourseNumber())) {
                    selectedCourse = item;
                    courseDropdown.setText(selectedCourse.getDisplayName(), false);
                    fillProfessorDropdown(selectedCourse);
                    break;
                }
            }
        }
        Boolean foundProfessor = false;
        if (defaultCourseNumber == null && defaultProfessorName != null) {
            setValue = 1;
            for (Course item: listManager.getCourses()) {
                /*for (String profName: item.getProfessor()) {
                    if (defaultProfessorName.equals(profName)) {
                        professorDropdown.setText(defaultProfessorName, false);
                        foundProfessor = true;
                        break;
                    }
                }*/
            }
        }
        if (foundProfessor) {
            //fillCourseDropdown(defaultProfessorName);
        }
    }

    private void setupCourseDropdown(View view) {
        courseDropdown = (AutoCompleteTextView) view.findViewById(R.id.create_review_course_dropdown);
        courseDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Get course item
                Course selected = listManager.getCourses().get(position);
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
                Professor professor = listManager.getProfessors().get(position);
                selectedProfessor = professor;
                if (setValue == 1) {
                    fillCourseDropdown(professor);
                }
            }
        });

        professorAdapter = new ArrayAdapter<ProfessorWrapper>(getContext(), R.layout.create_review_dropdown, listManager.getProfessorWrappers());
        professorDropdown.setAdapter(professorAdapter);
    }

    private void fillProfessorDropdown(Course selectedCourse) {
        //Aim: based on current selected course, fill the professor dropdown
        ArrayList<Professor> professors = new ArrayList<>();
        for (String name: selectedCourse.getProfessor()) {
            Professor professor = manager.getProfessorByName(name);
            if (professor != null) professors.add(professor);
        }
        listManager.setProfessors(professors);
        professorAdapter.notifyDataSetChanged();
        professorDropdown.setText(selectedCourse.getProfessor().get(0));
        //professorAdapter = new ArrayAdapter<ProfessorWrapper>(getContext(), R.layout.create_review_dropdown, listManager.getProfessorWrappers());
        //professorDropdown.setAdapter(professorAdapter);
    }

    private void fillCourseDropdown(Professor selectedProfessor) {
        //Aim: based on current selected professor, fill the course dropdown
        ArrayList<Course> courses = new ArrayList<>();
        for (Course course: masterCourseCopy) {
            for (String profName: course.getProfessor()) {
                if (selectedProfessor.getName().equals(profName)) {
                    courses.add(course.copy());
                }
            }
        }
        listManager.setCourses(courses);
        courseAdapter.notifyDataSetChanged();
        courseAdapter = new ArrayAdapter<>(getContext(), R.layout.create_review_dropdown, listManager.getCourseWrappers());
        selectedCourse = courses.get(0);
        courseDropdown.setText(selectedCourse.getDisplayName(), false);
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

        if (selectedProfessor == null) {
            //Display toast
            showToast("Please select a professor");
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

        Review review = new Review(selectedCourse, selectedProfessor, avgRating, firstRating, secondRating, reviewContent, date, reviewerName);
        manager.createReview(review);
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


}