package com.example.teame_hopreview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends ArrayAdapter<CourseItem> {

    int resource;

    public CourseAdapter(Context ctx, int res, List<CourseItem> items) {
        super (ctx, res, items);
        resource = res;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ConstraintLayout courseView;
        CourseItem course = getItem(position);

        if (convertView == null) {
            courseView = new ConstraintLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater inf = (LayoutInflater) getContext().getSystemService(inflater);
            inf.inflate(resource, courseView, true);
        } else {
            courseView = (ConstraintLayout) convertView;
        }

        TextView designation = (TextView) courseView.findViewById(R.id.designation);
        TextView nameNum = (TextView) courseView.findViewById(R.id.name_num);
        TextView professorNames = (TextView) courseView.findViewById(R.id.professor_names);
        TextView reviewNum = (TextView) courseView.findViewById(R.id.review_count);

        designation.setText(course.getDesignation());
        nameNum.setText(course.getName() + " " + course.getCourseNumber());

        ArrayList<Professor> professors = course.getProfessors();
        StringBuilder profNamesStr = new StringBuilder();
        for (Professor prof : professors) {
            profNamesStr.append(prof.getProfessorName()).append(" / ");
        }

        professorNames.setText(profNamesStr.toString());
        reviewNum.setText(String.valueOf(course.getReviewCount()) + " reviews");

        return courseView;
    }
}
