package com.example.teame_hopreview;

import com.example.teame_hopreview.ui.professors.Professor;

public class ReviewItem extends Profile {
    private String reviewerName;
    private String reviewContent;
    private int avgRating;
    private int firstRating; // fun or grading
    private int secondRating; // workload or knowledge
    private String date;
    private String course = new String();
    private String professor = new String();


    /**
     * Constructor to create a ReviewItem object
     */
    public ReviewItem() { }

    /**
     * Constructor to create a ReviewItem object
     *
     * @param reviewer : User leaving the review
     * @param review : content of the review
     */
    public ReviewItem(int average, String dt, int firstRate, String review, String reviewer, int secondRate) {
        this.reviewerName = reviewer;
        this.reviewContent = review;
        this.avgRating = average;
        this.date = dt;

        // get the category ratings
        firstRating = firstRate;
        secondRating = secondRate;
    }


    public void setCourseName(String course) {
        this.course = course;
    }

    public String getCourseName() {
        return this.course;
    }

    public void setProfessorName(String prof) {
        this.professor = prof;
    }

    public String getProfessorName() {
        return this.professor;
    }



    /**
     * Method for getting parent Course/Professor name
     * @return String name of the parent
     */
    // public String getParentName() { return this.parentName; }


    /**
     * Method for getting a reviewers user name
     * @return String userName
     */
    public String getReviewerName() { return this.reviewerName; }


    /**
     * Method for getting the review content
     * @return String reviewContent
     */
    public String getReviewContent() { return this.reviewContent; }


    /**
     * Method for getting the average rating
     * @return int avgRating
     */
    public int getAvgRating() { return this.avgRating; }


    /**
     * Method for getting the date the review was left
     * @return String date
     */
    public String getDate() { return this.date;}


    // TODO: methods for getting and setting other important review items

}
