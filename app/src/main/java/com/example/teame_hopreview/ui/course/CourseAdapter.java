package com.example.teame_hopreview.ui.course;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teame_hopreview.ui.professors.Professor;
import com.example.teame_hopreview.R;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private List<CourseItem> courseData;
    private LayoutInflater inflater;
    private AdapterView.OnItemClickListener clickListener;

    private final View.OnClickListener mOnClickListener = (view) -> {
        CourseItem course = (CourseItem) view.getTag();

        Context context = view.getContext();
        Intent intent = new Intent(context, CourseDetailFragment.class);
        intent.putExtra("course_name", course.getName());

        context.startActivity(intent);
    };

    public CourseAdapter(Context context, List<CourseItem> items) {
        this.inflater = LayoutInflater.from(context);
        this.courseData = items;
    }

    @NonNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.frag_course_item, parent, false);
        return new CourseAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, int position) {
        CourseItem currCourse = courseData.get(position);
        holder.getDesignation().setText(currCourse.getDesignation());
        holder.getNameNum().setText(currCourse.getName() + " " + currCourse.getCourseNumber());

        ArrayList<Professor> professors = currCourse.getProfessors();
        StringBuilder profNamesStr = new StringBuilder();
        for (Professor prof : professors) {
            profNamesStr.append(prof.getProfessorName()).append(" / ");
        }

        holder.getProfessorNames().setText(profNamesStr.toString());
        holder.getReviewNum().setText(currCourse.getReviewCount() + " reviews");

        holder.itemView.setTag(courseData.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return courseData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView designation;
        private final TextView nameNum;
        private final TextView professorNames;
        private final TextView reviewNum;

        public ViewHolder(@NonNull View courseView) {
            super(courseView);

            designation = (TextView) courseView.findViewById(R.id.designation);
            nameNum = (TextView) courseView.findViewById(R.id.name_num);
            professorNames = (TextView) courseView.findViewById(R.id.professor_names);
            reviewNum = (TextView) courseView.findViewById(R.id.review_count);
        }

        public TextView getDesignation() {
            return designation;
        }

        public TextView getNameNum() {
            return nameNum;
        }

        public TextView getProfessorNames() {
            return professorNames;
        }

        public TextView getReviewNum() {
            return reviewNum;
        }
    }
}

