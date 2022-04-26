package com.example.teame_hopreview.ui.review;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class DbProfReview {
    private String reviewContent;

    private String date;

    private int knowledgeRating;

    private int gradingRating;

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

    public int getKnowledgeRating() {
        return knowledgeRating;
    }

    public void setKnowledgeRating(int knowledgeRating) {
        this.knowledgeRating = knowledgeRating;
    }

    public int getGradingRating() {
        return gradingRating;
    }

    public void setGradingRating(int gradingRating) {
        this.gradingRating = gradingRating;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public int getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(int avgRating) {
        this.avgRating = avgRating;
    }

    private int avgRating;


    public DbProfReview(String reviewContent, String date, int avgRating, int knowledgeRating, String reviewerName, int gradingRating) {
        this.reviewContent = reviewContent;
        this.date = date;
        this.knowledgeRating = knowledgeRating;
        this.gradingRating = gradingRating;
        this.reviewerName = reviewerName;
        this.avgRating = avgRating;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new
                HashMap<>();
        result.put("reviewContent", reviewContent);
        result.put("date", date);
        result.put("knowledgeRating", knowledgeRating);
        result.put("gradingRating", gradingRating);
        result.put("avgRating", avgRating);
        result.put("reviewerName", reviewerName);

        return result;
    }
}
