package com.example.teame_hopreview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

    }

    @Override
    public int getItemCount() {
        return reviewData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView reviewer;
        private final TextView date;
        private final TextView comment;

        public ViewHolder(@NonNull View courseView) {
            super(courseView);

            reviewer = (TextView) courseView.findViewById(R.id.reviewer);
            date = (TextView) courseView.findViewById(R.id.date);
            comment = (TextView) courseView.findViewById(R.id.comment);
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
    }
}
