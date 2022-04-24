package com.example.teame_hopreview.database;

import com.example.teame_hopreview.ReviewItem;
import com.example.teame_hopreview.ui.course.CourseItem;

import java.util.ArrayList;

public class Course {
    private String courseName;
    private String courseNumber;
    private String courseDesignation;
    private ArrayList<String> professor;
    //private ArrayList<Review> reviews;
    private int funRating;
    private int workloadRating;
    private int averageRating;
    private String key;

    public Course() {

    }

    public Course(String courseName, String courseNumber, String courseDesignation, ArrayList<String> professor, ArrayList<Review> reviews, int funRating, int workloadRating, int averageRating, String key) {
        this.courseName = courseName;
        this.courseNumber = courseNumber;
        this.courseDesignation = courseDesignation;
        this.professor = professor;
        //this.reviews = reviews;
        this.funRating = funRating;
        this.workloadRating = workloadRating;
        this.averageRating = averageRating;
        this.key = key;
    }


    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getCourseDesignation() {
        return courseDesignation;
    }

    public void setCourseDesignation(String courseDesignation) {
        this.courseDesignation = courseDesignation;
    }

    public ArrayList<String> getProfessor() {
        return professor;
    }

    public void setProfessor(ArrayList<String> professor) {
        this.professor = professor;
    }

    /*public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }*/

    public int getFunRating() {
        return funRating;
    }

    public void setFunRating(int funRating) {
        this.funRating = funRating;
    }

    public int getWorkloadRating() {
        return workloadRating;
    }

    public void setWorkloadRating(int workloadRating) {
        this.workloadRating = workloadRating;
    }

    public int getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDisplayName() {
        int maxLength = 30;
        String result = new String(courseName);
        if (result.length() > maxLength) {
            result = result.substring(0, maxLength - 3 - 1);
            //Add three dots to end
            result = result + "...";
        }
        return result;
    }

    public Course copy() {
        return new Course(courseName, courseNumber, courseDesignation, professor, new ArrayList<>(), funRating, workloadRating, averageRating, key);
    }
}
