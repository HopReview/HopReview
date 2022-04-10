package com.example.teame_hopreview.ui.course;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teame_hopreview.R;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private List<CourseItem> courseData;
    private LayoutInflater inflater;
    private AdapterView.OnItemClickListener clickListener;
    Context context;

    private final View.OnClickListener mOnClickListener = (view) -> {
        CourseItem course = (CourseItem) view.getTag();

        context = view.getContext();
        Intent intent = new Intent(context, CourseDetailFragment.class);
        intent.putExtra("course_name", course.getName());


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
        System.out.println(currCourse.getName());
        holder.getNameNum().setText(currCourse.getName() + " " + currCourse.getCourseNumber());

        String prof = currCourse.getProfessors();

        /*StringBuilder profNamesStr = new StringBuilder();
        for (Professor prof : professors) {
            profNamesStr.append(prof.getProfessorName()).append(" / ");
        }*/


        holder.getProfessorNames().setText(prof);



        // holder.getReviewNum().setText(currCourse.getReviews().size() + " reviews");

        ImageView[] avgStars = holder.getAvgImages();
        ImageView[] workStars = holder.getWorkImages();
        ImageView[] funStars = holder.getFunImages();

        for (int i = 0; i < currCourse.getAverageRating(); i++) {
            avgStars[i].setVisibility(View.VISIBLE);
            avgStars[i].setColorFilter(R.color.md_theme_light_primary);
        }

        for (int i = 0; i < currCourse.getFunRating(); i++) {
            funStars[i].setVisibility(View.VISIBLE);
            avgStars[i].setColorFilter(R.color.md_theme_light_primary);
        }

        for (int i = 0; i < currCourse.getWorkloadRating(); i++) {
            workStars[i].setVisibility(View.VISIBLE);
            avgStars[i].setColorFilter(R.color.md_theme_light_primary);
        }


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
        private final ImageView avgStar1, avgStar2, avgStar3, avgStar4, avgStar5;
        private final ImageView workStar1, workStar2, workStar3, workStar4, workStar5;
        private final ImageView funStar1, funStar2, funStar3, funStar4, funStar5;


        public ViewHolder(@NonNull View courseView) {
            super(courseView);

            designation = (TextView) courseView.findViewById(R.id.designation);
            nameNum = (TextView) courseView.findViewById(R.id.name_num);
            professorNames = (TextView) courseView.findViewById(R.id.professor_names);
            reviewNum = (TextView) courseView.findViewById(R.id.review_count);

            avgStar1 = (ImageView) courseView.findViewById(R.id.avg_star1);
            avgStar2 = (ImageView) courseView.findViewById(R.id.avg_star2);
            avgStar3 = (ImageView) courseView.findViewById(R.id.avg_star3);
            avgStar4 = (ImageView) courseView.findViewById(R.id.avg_star4);
            avgStar5 = (ImageView) courseView.findViewById(R.id.avg_star5);

            workStar1 = (ImageView) courseView.findViewById(R.id.work_star1);
            workStar2 = (ImageView) courseView.findViewById(R.id.work_star2);
            workStar3 = (ImageView) courseView.findViewById(R.id.work_star3);
            workStar4 = (ImageView) courseView.findViewById(R.id.work_star4);
            workStar5 = (ImageView) courseView.findViewById(R.id.work_star5);

            funStar1 = (ImageView) courseView.findViewById(R.id.fun_star1);
            funStar2 = (ImageView) courseView.findViewById(R.id.fun_star2);
            funStar3 = (ImageView) courseView.findViewById(R.id.fun_star3);
            funStar4 = (ImageView) courseView.findViewById(R.id.fun_star4);
            funStar5 = (ImageView) courseView.findViewById(R.id.fun_star5);


            avgStar1.setVisibility(View.INVISIBLE);
            avgStar2.setVisibility(View.INVISIBLE);
            avgStar3.setVisibility(View.INVISIBLE);
            avgStar4.setVisibility(View.INVISIBLE);
            avgStar5.setVisibility(View.INVISIBLE);
            workStar1.setVisibility(View.INVISIBLE);
            workStar2.setVisibility(View.INVISIBLE);
            workStar3.setVisibility(View.INVISIBLE);
            workStar4.setVisibility(View.INVISIBLE);
            workStar5.setVisibility(View.INVISIBLE);
            funStar1.setVisibility(View.INVISIBLE);
            funStar2.setVisibility(View.INVISIBLE);
            funStar3.setVisibility(View.INVISIBLE);
            funStar4.setVisibility(View.INVISIBLE);
            funStar5.setVisibility(View.INVISIBLE);

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

        public ImageView[] getAvgImages() {
            ImageView[] avg = new ImageView[5];
            avg[0] = avgStar1;
            avg[1] = avgStar2;
            avg[2] = avgStar3;
            avg[3] = avgStar4;
            avg[4] = avgStar5;

            return avg;
        }

        public ImageView[] getWorkImages() {
            ImageView[] work = new ImageView[5];
            work[0] = workStar1;
            work[1] = workStar2;
            work[2] = workStar3;
            work[3] = workStar4;
            work[4] = workStar5;

            return work;
        }

        public ImageView[] getFunImages() {
            ImageView[] fun = new ImageView[5];
            fun[0] = funStar1;
            fun[1] = funStar2;
            fun[2] = funStar3;
            fun[3] = funStar4;
            fun[4] = funStar5;

            return fun;
        }
    }
}