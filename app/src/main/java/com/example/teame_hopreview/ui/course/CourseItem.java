package com.example.teame_hopreview.ui.course;

import com.example.teame_hopreview.Profile;
import com.example.teame_hopreview.ReviewItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CourseItem extends Profile {
    private String courseName;
    private String courseNumber;
    private String courseDesignation;
    private ArrayList<String> professor;
    private ArrayList<ReviewItem> reviews;
    private int funRating;
    private int workloadRating;
    private int averageRating;
    private String id;

    /**
     * Constructor to create a Course object
     */
    public CourseItem() { }


    public CourseItem(int avgRate, String courseDesignation, String name,
                      String courseNum, int funRate, ArrayList<String> professor, int workRate) {
        this.courseName = name;
        this.courseNumber = courseNum;
        this.courseDesignation = courseDesignation;
        this.funRating = funRate;
        this.workloadRating = workRate;
        this.averageRating = avgRate;
        this.reviews = new ArrayList<ReviewItem>();
        this.professor = new ArrayList<>();
        this.professor.addAll(professor);
    }

    public void setCourseName(String name) {
        this.courseName = name;
    }

    public void setCourseNumber(String num) {
        this.courseNumber = num;
    }

    public void setCourseDesignation(String des) {
        this.courseDesignation = des;
    }

    public void setProfessor(ArrayList<String> profs) {
        this.professor.addAll(profs);
    }

    public void setFunRating(int fun) {
        this.funRating = fun;
    }

    public void setWorkloadRating(int work) {
        this.workloadRating = work;
    }

    public void setAverageRating(int avg) {
        this.averageRating = avg;
    }

    /**
     * Method for getting a Course's name
     * @return String name of the Course
     */
    public String getName() { return this.courseName; }


    /**
     * Method for getting a Course's number
     * @return String number of the Course
     */
    public String getCourseNumber() { return this.courseNumber; }

    /**
     * Method for getting a Course's area of designation
     * @return String course designation of a Course
     */
    public String getDesignation() { return this.courseDesignation; }


    /**
     * Method for getting reviews for the course
     * @return ArrayList of review items
     */
    public ArrayList<ReviewItem> getReviews() { return this.reviews; }


    /**
     * Method for adding a review for the course
     * @param newReview - review to be added
     */
    public void addReview(ReviewItem newReview) {
        reviews.add(newReview);
    }

    /**
     * Method for getting the professors teaching the Course
     * @return ArrayList representing the professors teaching the Course
     */
    public ArrayList<String> getProfessors() { return this.professor; }

    /**
     * Method for getting the average rating for the Professor across all courses
     * @return int averageRating representing the average rating of the Professor
     */
    public int getAverageRating() {
        return averageRating;
    }

    public int getFunRating() {
        return funRating;
    }

    public int getWorkloadRating() {
        return workloadRating;
    }

    private void updateAverageRating(int newAvg) {
        averageRating = newAvg;
    }

    /**
     * Helper method for creating an initialized map, for an 'empty' rating
     * @return 'empty' map
     */
    private Map<String, Integer> getEmptyMap() {
        Map<String, Integer> holderRatings = new HashMap<String, Integer>();
        holderRatings.put("Workload", 0);
        holderRatings.put("Fun", 0);
        holderRatings.put("RateCount", 0);

        return holderRatings;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CourseItem Copy() {
        CourseItem item = new CourseItem(averageRating, courseDesignation, courseName, courseNumber, funRating, professor, workloadRating);
        item.setId(getId());
        return item;
    }
}
