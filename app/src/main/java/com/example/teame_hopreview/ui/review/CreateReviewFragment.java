package com.example.teame_hopreview.ui.review;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.teame_hopreview.MainActivity;
import com.example.teame_hopreview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateReviewFragment extends Fragment {

    private MainActivity mainActivity;

    private List<String> courses = new ArrayList<String>();

    ArrayAdapter<String> adapter;

    private Spinner sItems;

    public CreateReviewFragment() {
        // Required empty public constructor
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
        //fillCourseDropdown(view);

        // Inflate the layout for this fragment
        return view;
    }

    private void fillCourseDropdown(View view) {
        courses =  new ArrayList<String>();
        courses.add("Calculus 1");
        courses.add("Calculus 2");

        adapter = new ArrayAdapter<String>(
                this.getContext(), android.R.layout.simple_spinner_item, courses);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //sItems = (Spinner) view.findViewById(R.id.create_review_course_dropdown);
        //sItems.setAdapter(adapter);
    }
}