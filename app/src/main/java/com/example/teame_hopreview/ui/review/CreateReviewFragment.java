package com.example.teame_hopreview.ui.review;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.teame_hopreview.MainActivity;
import com.example.teame_hopreview.Professor;
import com.example.teame_hopreview.R;
import com.example.teame_hopreview.ui.course.CourseItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateReviewFragment extends Fragment {

    private MainActivity mainActivity;

    private ListManager listManager;

    private ArrayAdapter<CourseWrapper> courseAdapter;

    private AutoCompleteTextView courseDropdown;

    private ArrayAdapter<ProfessorWrapper> professorAdapter;

    private AutoCompleteTextView professorDropdown;

    public CreateReviewFragment() {
        // Required empty public constructor
        listManager = new ListManager();
        createFakeData();
    }

    private void createFakeData() {
        CourseItem calc1 = new CourseItem("Calculus I", "AS.", "Q", new ArrayList<Professor>());
        CourseItem calc2 = new CourseItem("Calculus II", "AS.", "Q", new ArrayList<Professor>());

        Professor prof1 = new Professor("Richard Brown", "Math", new ArrayList<CourseItem>());
        Professor prof2 = new Professor("Emily Riehl", "Math", new ArrayList<CourseItem>());
        Professor prof3 = new Professor("Joe Biden", "Math", new ArrayList<CourseItem>());

        calc1.getProfessors().add(prof1);
        calc1.getProfessors().add(prof2);

        calc2.getProfessors().add(prof1);
        calc2.getProfessors().add(prof2);
        calc2.getProfessors().add(prof3);

        prof1.addCourse(calc1);
        prof1.addCourse(calc2);

        prof2.addCourse(calc1);
        prof2.addCourse(calc2);

        prof3.addCourse(calc2);

        listManager.addCourse(calc1);
        listManager.addCourse(calc2);
        listManager.addProfessor(prof1);
        listManager.addProfessor(prof2);
        listManager.addProfessor(prof3);
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

        // Inflate the layout for this fragment
        return view;
    }

    private void setupCourseDropdown(View view) {
        courseDropdown = (AutoCompleteTextView) view.findViewById(R.id.create_review_course_dropdown);
        courseDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Get course item
                CourseItem selected = listManager.getCourses().get(position);
                fillProfessorDropdown(selected);
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
                fillCourseDropdown(professor);
            }
        });

        professorAdapter = new ArrayAdapter<>(getContext(), R.layout.create_review_dropdown, listManager.getProfessorWrappers());

        professorDropdown.setAdapter(professorAdapter);
    }

    private void fillProfessorDropdown(CourseItem selectedCourse) {
        //Aim: based on current selected course, fill the professor dropdown
        listManager.setProfessors(selectedCourse.getProfessors());
        professorAdapter.notifyDataSetChanged();
    }

    private void fillCourseDropdown(Professor selectedProfessor) {
        //Aim: based on current selected professor, fill the course dropdown
        listManager.setCourses(selectedProfessor.getCourses());
        courseAdapter.notifyDataSetChanged();
    }


}