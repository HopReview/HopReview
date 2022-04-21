package com.example.teame_hopreview.ui.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.teame_hopreview.ui.course.CourseItem;

import java.util.ArrayList;
import java.util.List;

public class CourseBMAdapter extends RecyclerView.Adapter<CourseBMAdapter.ViewHolder> implements Filterable {

    private List<CourseItem> courseData;
    private List<CourseItem> courseDataCopy;
    private LayoutInflater inflater;
    private MainActivity mainActivity;
    Context context;

    private final View.OnClickListener bookmarkListener = (view) -> {
        CourseItem course = (CourseItem) view.getTag();


        Toast.makeText(mainActivity, "COURSE SAVED",
                Toast.LENGTH_LONG).show();
    };


    private final View.OnClickListener mOnClickListener = (view) -> {
        CourseItem course = (CourseItem) view.getTag();
        mainActivity.openCourseDetailFragment(course);
    };

    public CourseBMAdapter(MainActivity mainActivity, Context context, ArrayList<CourseItem> items, List<CourseItem> itemsCopy) {
        this.inflater = LayoutInflater.from(context);
        this.courseData = items;
        this.mainActivity = mainActivity;
        this.courseDataCopy = itemsCopy;
    }

    @NonNull
    @Override
    public CourseBMAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.frag_course_item, parent, false);
        return new CourseBMAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseBMAdapter.ViewHolder holder, int position) {
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

        context = mainActivity.getApplicationContext();

        for (int i = 0; i < 5; i++) {
            if (i < currCourse.getAverageRating()) {
                avgStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_filled));
                avgStars[i].setColorFilter(R.color.md_theme_light_primary);
            } else {
                avgStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_unfilled));
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
        holder.bookmark.setOnClickListener(bookmarkListener);
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
        private final ImageButton bookmark;


        public ViewHolder(@NonNull View courseView) {
            super(courseView);

            designation = (TextView) courseView.findViewById(R.id.designation);
            name = (TextView) courseView.findViewById(R.id.name);
            num = (TextView) courseView.findViewById(R.id.num);
            professorNames = (TextView) courseView.findViewById(R.id.professor_names);
            reviewNum = (TextView) courseView.findViewById(R.id.review_count);
            bookmark = (ImageButton) courseView.findViewById(R.id.bookmark);

            avgStar1 = (ImageView) courseView.findViewById(R.id.avg_star1);
            avgStar2 = (ImageView) courseView.findViewById(R.id.avg_star2);
            avgStar3 = (ImageView) courseView.findViewById(R.id.avg_star3);
            avgStar4 = (ImageView) courseView.findViewById(R.id.avg_star4);
            avgStar5 = (ImageView) courseView.findViewById(R.id.avg_star5);
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

        public ImageButton getBookmark() {
            return bookmark;
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