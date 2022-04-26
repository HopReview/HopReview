package com.example.teame_hopreview.ui.course;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teame_hopreview.MainActivity;
import com.example.teame_hopreview.R;
import com.example.teame_hopreview.ReviewItem;
import com.example.teame_hopreview.database.Course;
import com.example.teame_hopreview.database.Review;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> implements Filterable {

    private List<Course> courseData;
    private List<Course> courseDataCopy;
    private LayoutInflater inflater;
    private MainActivity mainActivity;
    Context context;

    /*private final View.OnClickListener bookmarkListener = (view) -> {
        CourseItem course = (CourseItem) view.getTag();

        // TODO: add to user's dataset

        Toast.makeText(mainActivity, "COURSE SAVED",
                Toast.LENGTH_LONG).show();
    };*/


    private final View.OnClickListener mOnClickListener = (view) -> {
        Course course = (Course) view.getTag();
        mainActivity.openCourseDetailFragment(course);
    };

    public CourseAdapter(MainActivity mainActivity, Context context, ArrayList<Course> items, List<Course> itemsCopy) {
        this.inflater = LayoutInflater.from(context);
        this.courseData = items;
        this.mainActivity = mainActivity;
        this.courseDataCopy = itemsCopy;
    }

    @NonNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.frag_course_item, parent, false);
        return new CourseAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, int position) {
        Course currCourse = courseData.get(position);
        holder.getDesignation().setText(currCourse.getCourseDesignation());
        System.out.println(currCourse.getCourseName());
        holder.getName().setText(currCourse.getCourseName());
        holder.getNum().setText(currCourse.getCourseNumber());

        ArrayList<String> professors = currCourse.getProfessor();

        StringBuilder profNamesStr = new StringBuilder();
        int counter = 1;
        int len = professors.size();
        for (String prof : professors) {
            System.out.println("Professors " + counter + " : " + prof);
            if (counter == len) {
                profNamesStr.append(prof);
            } else {
                profNamesStr.append(prof).append(" / ");
            }
            counter++;
        }



        holder.getProfessorNames().setText(profNamesStr.toString());



        // holder.getReviewNum().setText(currCourse.getReviews().size() + " reviews");

        ImageView[] avgStars = holder.getAvgImages();
        ImageView[] workStars = holder.getWorkImages();
        ImageView[] funStars = holder.getFunImages();

        context = mainActivity.getApplicationContext();

        for (int i = 0; i < 5; i++) {
            if (i < currCourse.getAverageRating()) {
                avgStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_filled));
                avgStars[i].setColorFilter(R.color.md_theme_light_primary);
            } else {
                avgStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_unfilled));
            }
            if (i < currCourse.getFunRating()) {
                funStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_filled));
                funStars[i].setColorFilter(R.color.md_theme_light_primary);
            } else {
                funStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_unfilled));
            }
            if (i < currCourse.getWorkloadRating()) {
                workStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_filled));
                workStars[i].setColorFilter(R.color.md_theme_light_primary);
            } else {
                workStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_unfilled));

            }
        }

        Map<String, Review> reviews = currCourse.getReviews();

        if (reviews == null) {
            holder.getReviewNum().setText("0 reviews");
        } else {
            holder.getReviewNum().setText(reviews.size() + " reviews");
        }

        holder.itemView.setTag(courseData.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return courseData.size();
    }

    @Override
    public Filter getFilter() {
        return courseFilter;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView designation;
        private final TextView name;
        private final TextView num;
        private final TextView professorNames;
        private final TextView reviewNum;
        private final ImageView avgStar1, avgStar2, avgStar3, avgStar4, avgStar5;
        private final ImageView workStar1, workStar2, workStar3, workStar4, workStar5;
        private final ImageView funStar1, funStar2, funStar3, funStar4, funStar5;


        public ViewHolder(@NonNull View courseView) {
            super(courseView);

            designation = (TextView) courseView.findViewById(R.id.designation);
            name = (TextView) courseView.findViewById(R.id.name);
            num = (TextView) courseView.findViewById(R.id.num);
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


        }

        public TextView getDesignation() {
            return designation;
        }

        public TextView getName() {
            return name;
        }

        public TextView getNum() { return num; }

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

    private Filter courseFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Course> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(courseDataCopy);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Course item : courseDataCopy) {
                    if (item.getCourseName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            courseData.clear();
            courseData.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}