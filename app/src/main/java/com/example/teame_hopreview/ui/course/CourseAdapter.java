package com.example.teame_hopreview.ui.course;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teame_hopreview.MainActivity;
import com.example.teame_hopreview.R;
import com.example.teame_hopreview.ReviewItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> implements Filterable {

    private List<CourseItem> courseData;
    private List<CourseItem> courseDataCopy;
    private LayoutInflater inflater;
    private AdapterView.OnItemClickListener clickListener;
    private MainActivity mainActivity;
    Context context;

    private final View.OnClickListener mOnClickListener = (view) -> {
        CourseItem course = (CourseItem) view.getTag();
        mainActivity.openCourseDetailFragment(course);
    };

    public CourseAdapter(MainActivity mainActivity, Context context, ArrayList<CourseItem> items, List<CourseItem> itemsCopy) {
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
        CourseItem currCourse = courseData.get(position);
        holder.getDesignation().setText(currCourse.getDesignation());
        System.out.println(currCourse.getName());
        holder.getName().setText(currCourse.getName());
        holder.getNum().setText(currCourse.getCourseNumber());

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

        for (int i = 0; i < 5; i++) {
            if (i < currCourse.getAverageRating()) {
                avgStars[i].setVisibility(View.VISIBLE);
                avgStars[i].setColorFilter(R.color.md_theme_light_primary);
            } else {
                avgStars[i].setVisibility(View.INVISIBLE);
            }
            if (i < currCourse.getFunRating()) {
                funStars[i].setVisibility(View.VISIBLE);
                funStars[i].setColorFilter(R.color.md_theme_light_primary);
            } else {
                funStars[i].setVisibility(View.INVISIBLE);
            }
            if (i < currCourse.getWorkloadRating()) {
                workStars[i].setVisibility(View.VISIBLE);
                workStars[i].setColorFilter(R.color.md_theme_light_primary);
            } else {
                workStars[i].setVisibility(View.INVISIBLE);
            }
        }

        ArrayList<ReviewItem> reviews = currCourse.getReviews();

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
            List<CourseItem> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(courseDataCopy);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (CourseItem item : courseDataCopy) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
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