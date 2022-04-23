package com.example.teame_hopreview;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teame_hopreview.ui.course.CourseItem;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private List<ReviewItem> reviewData;
    private LayoutInflater inflater;
    private AdapterView.OnItemClickListener clickListener;
    private MainActivity mainActivity;
    Context context;


    private final View.OnClickListener mOnClickListener = (view) -> {
        ReviewItem review = (ReviewItem) view.getTag();
        mainActivity.openReviewDetailFragment(review);
    };

    public ReviewAdapter(MainActivity mainActivity, Context context, List<ReviewItem> items) {
        this.inflater = LayoutInflater.from(context);
        this.reviewData = items;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.frag_review_item, parent, false);
        Log.d("REVIEWER NAME", "HELLO");
        return new ReviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ViewHolder holder, int position) {
        ReviewItem currReview = reviewData.get(position);
        Log.d("REVIEWER NAME", currReview.getReviewerName());
        Log.d("REVIEWER DATE", currReview.getDate());
        Log.d("REVIEWER CONTENT", currReview.getReviewContent());

        holder.getReviewer().setText(currReview.getReviewerName());
        holder.getDate().setText(currReview.getDate());
        holder.getComment().setText(currReview.getReviewContent());

        int avgRating = currReview.getAvgRating();
        System.out.println("AVGRATING FOR REVIEW: " + avgRating);

        context = mainActivity.getApplicationContext();
        ImageView[] stars = holder.getStars();

        for (int i = 0; i < 5; i++) {
            if (i < avgRating) {
                stars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_filled));
                stars[i].setColorFilter(R.color.md_theme_light_primary);
            } else {
                stars[i].setImageDrawable(context.getResources().getDrawable(R.drawable.star_unfilled));
            }
        }


        holder.itemView.setTag(reviewData.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);

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

        public ImageView[] getStars() {
            ImageView stars[] = new ImageView[5];
            stars[0] = star1;
            stars[1] = star2;
            stars[2] = star3;
            stars[3] = star4;
            stars[4] = star5;

            return stars;
        }
    }
}
