package com.example.teame_hopreview.ui.review;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.teame_hopreview.R;

public class RatingsView extends LinearLayout {

    private ImageView star1;
    private ImageView star2;
    private ImageView star3;
    private ImageView star4;
    private ImageView star5;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
        updateStarIcons(rating);
    }

    private int rating = 0;

    private OnRatingChangedListener onRatingChangedListener;


    public RatingsView(Context context) {
        super(context);
        setupView();
    }

    public RatingsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setupView();
    }

    public RatingsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView();
    }

    public RatingsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setupView();
    }

    private void setupView() {
        inflate(getContext(), R.layout.create_review_ratings_view_layout,this);
        star1 = (ImageView) findViewById(R.id.ratings_view_star1);
        star1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStarIcons(1);
            }
        });

        star2 = (ImageView) findViewById(R.id.ratings_view_star2);
        star2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStarIcons(2);
            }
        });

        star3 = (ImageView) findViewById(R.id.ratings_view_star3);
        star3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStarIcons(3);
            }
        });

        star4 = (ImageView) findViewById(R.id.ratings_view_star4);
        star4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStarIcons(4);
            }
        });

        star5 = (ImageView) findViewById(R.id.ratings_view_star5);
        star5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStarIcons(5);
            }
        });
    }

    private void updateStarIcons(int newRating) {
        star1.setImageResource((newRating >= 1) ? R.drawable.star_filled: R.drawable.star_unfilled);
        star2.setImageResource((newRating >= 2) ? R.drawable.star_filled: R.drawable.star_unfilled);
        star3.setImageResource((newRating >= 3) ? R.drawable.star_filled: R.drawable.star_unfilled);
        star4.setImageResource((newRating >= 4) ? R.drawable.star_filled: R.drawable.star_unfilled);
        star5.setImageResource((newRating == 5) ? R.drawable.star_filled: R.drawable.star_unfilled);
        rating = newRating;
        if (onRatingChangedListener != null) onRatingChangedListener.onRatingChanged(newRating);
    }

    public OnRatingChangedListener getOnRatingChangedListener() {
        return onRatingChangedListener;
    }

    public void setOnRatingChangedListener(OnRatingChangedListener onRatingChangedListener) {
        this.onRatingChangedListener = onRatingChangedListener;
    }
}



