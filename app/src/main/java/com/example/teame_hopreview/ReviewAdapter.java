package com.example.teame_hopreview;

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

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private List<ReviewItem> reviewData;
    private LayoutInflater inflater;
    private AdapterView.OnItemClickListener clickListener;


    public ReviewAdapter(Context context, List<ReviewItem> items) {
        this.inflater = LayoutInflater.from(context);
        this.reviewData = items;
    }

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.frag_course_item, parent, false);
        return new ReviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ViewHolder holder, int position) {
        ReviewItem currReview = reviewData.get(position);
        holder.getReviewer().setText(currReview.getReviewerName());
        holder.getDate().setText(currReview.getDate());
        holder.getComment().setText(currReview.getReviewContent());

        int avgRating = currReview.getAvgRating();

        if (avgRating < 2) {
            holder.getStar3().setVisibility(View.VISIBLE);
        } else if (avgRating < 3) {
            holder.getStar3().setVisibility(View.VISIBLE);
            holder.getStar4().setVisibility(View.VISIBLE);
        } else if (avgRating < 4) {
            holder.getStar3().setVisibility(View.VISIBLE);
            holder.getStar4().setVisibility(View.VISIBLE);
            holder.getStar2().setVisibility(View.VISIBLE);
        } else if (avgRating < 5) {
            holder.getStar3().setVisibility(View.VISIBLE);
            holder.getStar4().setVisibility(View.VISIBLE);
            holder.getStar2().setVisibility(View.VISIBLE);
            holder.getStar5().setVisibility(View.VISIBLE);
        } else {
            holder.getStar3().setVisibility(View.VISIBLE);
            holder.getStar4().setVisibility(View.VISIBLE);
            holder.getStar2().setVisibility(View.VISIBLE);
            holder.getStar5().setVisibility(View.VISIBLE);
            holder.getStar1().setVisibility(View.VISIBLE);
        }

        holder.itemView.setTag(reviewData.get(position));
        // holder.itemView.setOnClickListener(mOnClickListener);

    }

    @Override
    public int getItemCount() {
        return reviewData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView reviewer;
        private final TextView date;
        private final TextView comment;
        private final ImageView star1, star2, star3, star4, star5;

        public ViewHolder(@NonNull View reviewView) {
            super(reviewView);

            reviewer = (TextView) reviewView.findViewById(R.id.reviewer);
            date = (TextView) reviewView.findViewById(R.id.date);
            comment = (TextView) reviewView.findViewById(R.id.comment);

            star1 = (ImageView) reviewView.findViewById(R.id.review_star1);
            star2 = (ImageView) reviewView.findViewById(R.id.review_star2);
            star3 = (ImageView) reviewView.findViewById(R.id.review_star3);
            star4 = (ImageView) reviewView.findViewById(R.id.review_star4);
            star5 = (ImageView) reviewView.findViewById(R.id.review_star5);

            star1.setVisibility(View.INVISIBLE);
            star2.setVisibility(View.INVISIBLE);
            star3.setVisibility(View.INVISIBLE);
            star4.setVisibility(View.INVISIBLE);
            star5.setVisibility(View.INVISIBLE);

        }

        public TextView getReviewer() {
            return reviewer;
        }

        public TextView getDate() {
            return date;
        }

        public TextView getComment() {
            return comment;
        }

        public ImageView getStar1() { return star1; }

        public ImageView getStar2() { return star2; }

        public ImageView getStar3() { return star3; }

        public ImageView getStar4() { return star4; }

        public ImageView getStar5() { return star5; }
    }
}
