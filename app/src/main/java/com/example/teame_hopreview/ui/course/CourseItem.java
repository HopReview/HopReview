package com.example.teame_hopreview.ui.course;

import com.example.teame_hopreview.ui.professors.Professor;
import com.example.teame_hopreview.Profile;
import com.example.teame_hopreview.ReviewItem;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CourseItem extends Profile {
    private String courseName;
    private String courseNumber;
    private String courseDesignation;
    private String professor;
    private ArrayList<ReviewItem> reviews;
    private int funRating;
    private int workloadRating;
    private int averageRating;

    /**
     * Constructor to create a Course object
     */
    public CourseItem() { }


    public CourseItem(int avgRate, String courseDesignation, String name,
                      String courseNum, int funRate, String professor, int workRate) {
        this.courseName = name;
        this.courseNumber = courseNum;
        this.courseDesignation = courseDesignation;
        this.professor = professor;
        this.funRating = funRate;
        this.workloadRating = workRate;
        this.averageRating = avgRate;

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

    public void setProfessor(String prof) {
        this.professor = prof;
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
    public String getProfessors() { return this.professor; }

    /*
    //    /**
    //     * Method to add a specific professor to a Course's professor list
    //     * Accordingly puts empty ratings for the professor
    //     * @param prof the Professor to add
    //
    //    public void addProfessor(Professor prof) {
    //        professors.add(prof);
    //        Map<String, Integer> holderRatings = getEmptyMap();
    //        mixedRatings.put(prof, holderRatings);
    //    }

    //    /**
    //     * Method to remove a specific professor from a Course's professor list
    //     * Accordingly removes ratings for the professor
    //     * @param prof the Professor to remove
    //
    //    public void removeProfessor(Professor prof) {
    //        professors.remove(prof);
    //        mixedRatings.remove(prof);
    //    }


    /**
     * Method for adding ratings from a user for a specific professor teaching the course
     * Assumed that the currentRatings are passed on correctly by the caller
     * Assumed that the Professor prof is a professor teaching the course, checked by the caller
     * @param ratings
     * @param prof
     */
    /*
    public void addRatingsForProfessor(Map<String, Integer> ratings, Professor prof) {
        Map<String, Integer> currentRatings = mixedRatings.get(prof);

        // get rate count in order to keep track of how many ratings there have been
        int rateCount = currentRatings.get("RateCount");

        if (rateCount == 0) {
            currentRatings.put("Workload", ratings.get("Workload"));
        } else {
            // rating rounded, not a floating value
            int newRating = (ratings.get("Workload") + (rateCount * currentRatings.get("Workload"))) / (rateCount + 1);
            currentRatings.put("Workload", newRating);
        }

        // update ratings for the Fun param
        // check to see if it ever has been rated
        if (rateCount == 0) {
            currentRatings.put("Fun", ratings.get("Fun"));
        } else {
            // rating rounded, not a floating value
            int newRating = (ratings.get("Fun") + (rateCount * currentRatings.get("Fun"))) / (rateCount + 1);
            currentRatings.put("Fun", newRating);
        }

        // update ratings for the Grading param
        // check to see if it ever has been rated
        if (rateCount == 0) {
            currentRatings.put("Grading", ratings.get("Grading"));
        } else {
            // rating rounded, not a floating value
            int newRating = (ratings.get("Grading") + (rateCount * currentRatings.get("Grading"))) / (rateCount + 1);
            currentRatings.put("Grading", newRating);
        }

        // update ratings for the Knowledge param
        // check to see if it ever has been rated
        if (rateCount == 0) {
            currentRatings.put("Knowledge", ratings.get("Knowledge"));
        } else {
            // rating rounded, not a floating value
            int newRating = (ratings.get("Knowledge") + (rateCount * currentRatings.get("Knowledge"))) / (rateCount + 1);
            currentRatings.put("Knowledge", newRating);
        }

        // update the rate count for the specific course
        currentRatings.put("RateCount", rateCount + 1);

        // update the course ratings in mixedRatings
        mixedRatings.put(prof, currentRatings);
        updateAverageRating();
    }


    /**
     * Method for getting the ratings for the Course for a specific professor
     * @param prof : the specific professor for which ratings are inquired
     * @return Map that represents the ratings of the Professor for that course
     *//*
    public Map<String, Integer> getRatingsForCourse(Professor prof) {
        return mixedRatings.get(prof);
    }*/


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

    /*
    /**
     * Method for calculating an average rating for the professor
     * based on all the ratings for the courses they teach
     *
     * the ratings are assumed to be existing, as this is a private method call
     * and previous methods necessarily check whether they exist or not
     *//*
    private void updateAverageRating() {
        Map<String, Integer> currRating;
        int ratingSum = 0;
        int totRates = 0;
        for (Professor prof : professors) {
            currRating = mixedRatings.get(prof);
            ratingSum += (currRating.get("Workload") + currRating.get("Fun"));
            totRates += currRating.get("RateCount");
        }

        averageRating = ratingSum / totRates;
    }*/

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
        //        holderRatings.put("Grading", 0);
        //        holderRatings.put("Knowledge", 0);
        holderRatings.put("RateCount", 0);

        return holderRatings;
    }
}
