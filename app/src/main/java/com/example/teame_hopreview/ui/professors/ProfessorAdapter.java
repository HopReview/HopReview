package com.example.teame_hopreview.ui.professors;

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
import com.example.teame_hopreview.database.Review;
import com.example.teame_hopreview.ui.professors.Professor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ProfessorAdapter extends RecyclerView.Adapter<ProfessorAdapter.ViewHolder> implements Filterable {

    private List<com.example.teame_hopreview.database.Professor> professorData;
    private List<com.example.teame_hopreview.database.Professor> professorDataCopy;
    private LayoutInflater inflater;
    private AdapterView.OnItemClickListener clickListener;
    private MainActivity mainActivity;
    Context context;

    private final View.OnClickListener mOnClickListener = (view) -> {
        Professor professor = (Professor) view.getTag();
        mainActivity.openProfessorDetailFragment(professor);
    };

    public ProfessorAdapter(MainActivity mainActivity, Context context, ArrayList<com.example.teame_hopreview.database.Professor> items, List<com.example.teame_hopreview.database.Professor> itemsCopy) {
        this.inflater = LayoutInflater.from(context);
        this.professorData = items;
        this.mainActivity = mainActivity;
        this.professorDataCopy = itemsCopy;
    }

    @NonNull
    @Override
    public ProfessorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.frag_professor_item, parent, false);
        return new ProfessorAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfessorAdapter.ViewHolder holder, int position) {
        com.example.teame_hopreview.database.Professor currProfessor = professorData.get(position);
        holder.getInitials().setText(currProfessor.getInitials());
        holder.getName().setText(currProfessor.getName());
        holder.getDepartment().setText(currProfessor.getDepartment());
        ArrayList<String> courseNames = currProfessor.getCourses();

        StringBuilder courseNamesStr = new StringBuilder();
        int counter = 0;
        for (String course : courseNames) {
            if (counter != 0) {
                courseNamesStr.append(" / ");
            }
            courseNamesStr.append(course);
            counter++;
        }


        holder.getCourseNames().setText(courseNamesStr);


        ImageView[] avgStars = holder.getAvgImages();
        ImageView[] gradeStars = holder.getGradeImages();
        ImageView[] knowStars = holder.getKnowImages();

        context = mainActivity.getApplicationContext();

        for (int i = 0; i < 5; i++) {
            if (i < currProfessor.getAvg_rating()) {
                avgStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_filled));
                avgStars[i].setColorFilter(R.color.md_theme_light_primary);
            } else {
                avgStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_unfilled));
            }
            if (i < currProfessor.getGrading_rating()) {
                gradeStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_filled));
                gradeStars[i].setColorFilter(R.color.md_theme_light_primary);
            } else {
                gradeStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_unfilled));
            }
            if (i < currProfessor.getKnowledge_rating()) {
                knowStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_filled));
                knowStars[i].setColorFilter(R.color.md_theme_light_primary);
            } else {
                knowStars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_unfilled));
            }
        }

        Map<String, Review> reviews = currProfessor.getReviews();

        if (reviews == null) {
            holder.getReviewNum().setText("0 reviews");
        } else {
            holder.getReviewNum().setText(reviews.size() + " reviews");
        }

        holder.itemView.setTag(professorData.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return professorData.size();
    }

    @Override
    public Filter getFilter() {
        return professorFilter;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView initial;
        private final TextView department;
        private final TextView name;
        private final TextView courseNames;
        private final TextView reviewNum;
        private final ImageView avgStar1, avgStar2, avgStar3, avgStar4, avgStar5;
        private final ImageView gradeStar1, gradeStar2, gradeStar3, gradeStar4, gradeStar5;
        private final ImageView knowStar1, knowStar2, knowStar3, knowStar4, knowStar5;


        public ViewHolder(@NonNull View professorView) {
            super(professorView);

            initial = (TextView) professorView.findViewById(R.id.initialsProf);
            name = (TextView) professorView.findViewById(R.id.nameProf);
            department = (TextView) professorView.findViewById(R.id.department);
            courseNames = (TextView) professorView.findViewById(R.id.course_names);
            reviewNum = (TextView) professorView.findViewById(R.id.review_countProf);

            avgStar1 = (ImageView) professorView.findViewById(R.id.avg_star1ProfItem);
            avgStar2 = (ImageView) professorView.findViewById(R.id.avg_star2ProfItem);
            avgStar3 = (ImageView) professorView.findViewById(R.id.avg_star3ProfItem);
            avgStar4 = (ImageView) professorView.findViewById(R.id.avg_star4ProfItem);
            avgStar5 = (ImageView) professorView.findViewById(R.id.avg_star5ProfItem);

            gradeStar1 = (ImageView) professorView.findViewById(R.id.grade_star1ProfItem);
            gradeStar2 = (ImageView) professorView.findViewById(R.id.grade_star2ProfItem);
            gradeStar3 = (ImageView) professorView.findViewById(R.id.grade_star3ProfItem);
            gradeStar4 = (ImageView) professorView.findViewById(R.id.grade_star4ProfItem);
            gradeStar5 = (ImageView) professorView.findViewById(R.id.grade_star5ProfItem);

            knowStar1 = (ImageView) professorView.findViewById(R.id.know_star1ProfItem);
            knowStar2 = (ImageView) professorView.findViewById(R.id.know_star2ProfItem);
            knowStar3 = (ImageView) professorView.findViewById(R.id.know_star3ProfItem);
            knowStar4 = (ImageView) professorView.findViewById(R.id.know_star4ProfItem);
            knowStar5 = (ImageView) professorView.findViewById(R.id.know_star5ProfItem);


        }

        public TextView getInitials() {
            return initial;
        }

        public TextView getName() {
            return name;
        }

        public TextView getDepartment() { return department; }

        public TextView getCourseNames() {
            return courseNames;
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

        public ImageView[] getGradeImages() {
            ImageView[] grade = new ImageView[5];
            grade[0] = gradeStar1;
            grade[1] = gradeStar2;
            grade[2] = gradeStar3;
            grade[3] = gradeStar4;
            grade[4] = gradeStar5;

            return grade;
        }

        public ImageView[] getKnowImages() {
            ImageView[] know = new ImageView[5];
            know[0] = knowStar1;
            know[1] = knowStar2;
            know[2] = knowStar3;
            know[3] = knowStar4;
            know[4] = knowStar5;

            return know;
        }
    }

    private Filter professorFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<com.example.teame_hopreview.database.Professor> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(professorDataCopy);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (com.example.teame_hopreview.database.Professor item : professorDataCopy) {
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
            professorData.clear();
            professorData.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

}

