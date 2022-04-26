package com.example.teame_hopreview.ui.review;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class DbReview {

    private String reviewContent;

    private String date;

    private int avgRating;

    private int firstRating;

    private String reviewerName;

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(int avgRating) {
        this.avgRating = avgRating;
    }

    public int getFirstRating() {
        return firstRating;
    }

    public void setFirstRating(int firstRating) {
        this.firstRating = firstRating;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public int getSecondRating() {
        return secondRating;
    }

    public void setSecondRating(int secondRating) {
        this.secondRating = secondRating;
    }

    private int secondRating;


    public DbReview(String reviewContent, String date, int avgRating, int firstRating, String reviewerName, int secondRating) {
        this.reviewContent = reviewContent;
        this.date = date;
        this.avgRating = avgRating;
        this.firstRating = firstRating;
        this.reviewerName = reviewerName;
        this.secondRating = secondRating;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new
                HashMap<>();
        result.put("reviewContent", reviewContent);
        result.put("date", date);
        result.put("avgRating", avgRating);
        result.put("firstRating", firstRating);
        result.put("secondRating", secondRating);
        result.put("reviewerName", reviewerName);

        return result;
    }
}
