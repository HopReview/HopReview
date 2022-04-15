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
import com.example.teame_hopreview.ui.professors.Professor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProfessorAdapter extends RecyclerView.Adapter<ProfessorAdapter.ViewHolder> implements Filterable {

    private List<Professor> professorData;
    private List<Professor> professorDataCopy;
    private LayoutInflater inflater;
    private AdapterView.OnItemClickListener clickListener;
    private MainActivity mainActivity;
    Context context;

    private final View.OnClickListener mOnClickListener = (view) -> {
        Professor professor = (Professor) view.getTag();
        mainActivity.openProfessorDetailFragment(professor);
    };

    public ProfessorAdapter(MainActivity mainActivity, Context context, ArrayList<Professor> items, List<Professor> itemsCopy) {
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
        Professor currProfessor = professorData.get(position);
        holder.getInitials().setText(currProfessor.getInitials());
        System.out.println(currProfessor.getProfessorName());
        holder.getNameNum().setText(currProfessor.getProfessorName() + "\n" + currProfessor.getDepartment());

        String prof = currProfessor.getCourse();

        /*StringBuilder profNamesStr = new StringBuilder();
        for (Professor prof : professors) {
            profNamesStr.append(prof.getProfessorName()).append(" / ");
        }*/


        holder.getProfessorNames().setText(prof);


        // holder.getReviewNum().setText(currProfessor.getReviews().size() + " reviews");

        ImageView[] avgStars = holder.getAvgImages();
        ImageView[] workStars = holder.getWorkImages();
        ImageView[] funStars = holder.getFunImages();

        for (int i = 0; i < 5; i++) {
            if (i < currProfessor.getAverageRating()) {
                avgStars[i].setVisibility(View.VISIBLE);
                avgStars[i].setColorFilter(R.color.md_theme_light_primary);
            } else {
                avgStars[i].setVisibility(View.INVISIBLE);
            }
            if (i < currProfessor.getFunRating()) {
                funStars[i].setVisibility(View.VISIBLE);
                funStars[i].setColorFilter(R.color.md_theme_light_primary);
            } else {
                funStars[i].setVisibility(View.INVISIBLE);
            }
            if (i < currProfessor.getWorkloadRating()) {
                workStars[i].setVisibility(View.VISIBLE);
                workStars[i].setColorFilter(R.color.md_theme_light_primary);
            } else {
                workStars[i].setVisibility(View.INVISIBLE);
            }
        }

        ArrayList<ReviewItem> reviews = currProfessor.getReviews();

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
        private final TextView nameNum;
        private final TextView professorNames;
        private final TextView reviewNum;
        private final ImageView avgStar1, avgStar2, avgStar3, avgStar4, avgStar5;
        private final ImageView workStar1, workStar2, workStar3, workStar4, workStar5;
        private final ImageView funStar1, funStar2, funStar3, funStar4, funStar5;


        public ViewHolder(@NonNull View professorView) {
            super(professorView);

            initial = (TextView) professorView.findViewById(R.id.initials);
            nameNum = (TextView) professorView.findViewById(R.id.name_numProf);
            professorNames = (TextView) professorView.findViewById(R.id.course_names);
            reviewNum = (TextView) professorView.findViewById(R.id.review_countProf);

            avgStar1 = (ImageView) professorView.findViewById(R.id.avg_star1Prof);
            avgStar2 = (ImageView) professorView.findViewById(R.id.avg_star2Prof);
            avgStar3 = (ImageView) professorView.findViewById(R.id.avg_star3Prof);
            avgStar4 = (ImageView) professorView.findViewById(R.id.avg_star4Prof);
            avgStar5 = (ImageView) professorView.findViewById(R.id.avg_star5Prof);

            workStar1 = (ImageView) professorView.findViewById(R.id.work_star1Prof);
            workStar2 = (ImageView) professorView.findViewById(R.id.work_star2Prof);
            workStar3 = (ImageView) professorView.findViewById(R.id.work_star3Prof);
            workStar4 = (ImageView) professorView.findViewById(R.id.work_star4Prof);
            workStar5 = (ImageView) professorView.findViewById(R.id.work_star5Prof);

            funStar1 = (ImageView) professorView.findViewById(R.id.fun_star1Prof);
            funStar2 = (ImageView) professorView.findViewById(R.id.fun_star2Prof);
            funStar3 = (ImageView) professorView.findViewById(R.id.fun_star3Prof);
            funStar4 = (ImageView) professorView.findViewById(R.id.fun_star4Prof);
            funStar5 = (ImageView) professorView.findViewById(R.id.fun_star5Prof);


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

        public TextView getInitials() {
            return initial;
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

    private Filter professorFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Professor> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(professorDataCopy);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Professor item : professorDataCopy) {
                    if (item.getProfessorName().toLowerCase().contains(filterPattern)) {
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

