package com.example.teame_hopreview.database;

import com.example.teame_hopreview.ui.course.CourseItem;
import com.example.teame_hopreview.ui.professors.Professor;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Review {

    private Course course;

    private Professor professor;

    private int avgRating;

    private int firstRating;

    private int secondRating;

    private String reviewerContent;

    private String date;

    private String reviewerName;

    private String key;

    public CourseItem getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
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

    public int getSecondRating() {
        return secondRating;
    }

    public void setSecondRating(int secondRating) {
        this.secondRating = secondRating;
    }

    public String getReviewerContent() {
        return reviewerContent;
    }

    public void setReviewerContent(String reviewerContent) {
        this.reviewerContent = reviewerContent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Review() {

    }

    public Review(Course course, int avgRating, int firstRating, int secondRating, String reviewerContent, String date, String reviewerName) {
        this.course = course;
        this.avgRating = avgRating;
        this.firstRating = firstRating;
        this.secondRating = secondRating;
        this.reviewerContent = reviewerContent;
        this.date = date;
        this.reviewerName = reviewerName;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new
                HashMap<>();
        result.put("reviewerContent", reviewerContent);
        result.put("date", date);
        result.put("avgRating", avgRating);
        result.put("firstRating", firstRating);
        result.put("secondRating", secondRating);
        result.put("reviewerName", "john");

        return result;
    }
}
