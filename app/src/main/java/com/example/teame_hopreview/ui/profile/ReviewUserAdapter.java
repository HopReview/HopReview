package com.example.teame_hopreview.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teame_hopreview.MainActivity;
import com.example.teame_hopreview.R;
import com.example.teame_hopreview.ReviewItem;
import com.example.teame_hopreview.ui.course.CourseItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReviewUserAdapter extends RecyclerView.Adapter<ReviewUserAdapter.ViewHolder> {

    private List<ReviewItem> reviewData;
    private LayoutInflater inflater;
    private AdapterView.OnItemClickListener clickListener;
    private MainActivity mainActivity;
    Context context;


    private final View.OnClickListener mOnClickListener = (view) -> {
        ReviewItem review = (ReviewItem) view.getTag();
        mainActivity.openReviewDetailFragment(review);
    };

    public ReviewUserAdapter(MainActivity mainActivity, Context context, ArrayList<ReviewItem> items) {
        this.inflater = LayoutInflater.from(context);
        this.reviewData = items;
        this.mainActivity = mainActivity;

    }


    @NonNull
    @Override
    public ReviewUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.frag_user_review, parent, false);
        Log.d("REVIEWER NAME", "HELLO");
        return new ReviewUserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewUserAdapter.ViewHolder holder, int position) {
        ReviewItem currReview = reviewData.get(position);
        Log.d("REVIEWER NAME", currReview.getReviewerName());
        Log.d("REVIEWER DATE", currReview.getDate());
        Log.d("REVIEWER CONTENT", currReview.getReviewContent());

        System.out.println("I am here!");

        holder.getUserName().setText(currReview.getReviewerName());
        holder.getDate().setText(currReview.getDate());
        holder.getRevContent().setText(currReview.getReviewContent());
        holder.getCourseName().setText(currReview.getCourseName());

        int avgRating = currReview.getAvgRating();
        System.out.println("AVGRATING FOR REVIEW: " + avgRating);

        context = mainActivity.getApplicationContext();
        ImageView[] stars = holder.getAvgImages();

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

        private final TextView name;
        private final TextView date;
        private final TextView courseName;
        private final TextView revContent;
        private final ImageView avgStar1, avgStar2, avgStar3, avgStar4, avgStar5;
        // private final ImageButton bookmark;

        public ViewHolder(@NonNull View userReviewView) {
            super(userReviewView);

            name = (TextView) userReviewView.findViewById(R.id.userNameUser);
            date = (TextView) userReviewView.findViewById(R.id.dateUser);
            courseName = (TextView) userReviewView.findViewById(R.id.nameUser);
            revContent = (TextView) userReviewView.findViewById(R.id.contentUser);
            avgStar1 = (ImageView) userReviewView.findViewById(R.id.avg_star1User);
            avgStar2 = (ImageView) userReviewView.findViewById(R.id.avg_star2User);
            avgStar3 = (ImageView) userReviewView.findViewById(R.id.avg_star3User);
            avgStar4 = (ImageView) userReviewView.findViewById(R.id.avg_star4User);
            avgStar5 = (ImageView) userReviewView.findViewById(R.id.avg_star5User);
        }

        public TextView getUserName() {
            return name;
        }
        public TextView getDate() { return date; }
        public TextView getCourseName() { return courseName; }
        public TextView getRevContent() { return revContent; }

        public ImageView[] getAvgImages() {
            ImageView[] avg = new ImageView[5];
            avg[0] = avgStar1;
            avg[1] = avgStar2;
            avg[2] = avgStar3;
            avg[3] = avgStar4;
            avg[4] = avgStar5;

            return avg;
        }
    }
}
