package com.example.teame_hopreview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CourseItem extends Profile {
    private String courseName;
    private String courseDesignation;
    private ArrayList<Professor> professors;
    private Map<Professor, Map<String, Integer>> mixedRatings;
    private int averageRating;

    /**
     * Constructor to create a Course object
     */
    public CourseItem() { }

    /**
     * Constructor to create a Course object
     * @param name : indicating the Course's name
     * @param courseDesignation : indicating the course designation of the Course
     * @param professors : indicating the professors teaching the Course
     */
    public CourseItem(String name, String courseDesignation,
                      ArrayList<Professor> professors) {
        this.courseName = name;
        this.courseDesignation = courseDesignation;
        this.professors = professors;

        Map<String, Integer> emptyRatings = getEmptyMap();
        for (Professor prof : Collections.unmodifiableList(professors)) {
            mixedRatings.put(prof, emptyRatings);
        }
    }


    /**
     * Method for getting a Course's name
     * @return String name of the Course
     */
    public String getName() { return this.courseName; }


    /**
     * Method for getting a Course's area of designation
     * @return String course designation of a Course
     */
    public String getDesignation() { return this.courseDesignation; }


    /**
     * Method for getting the professors teaching the Course
     * @return ArrayList representing the professors teaching the Course
     */
    public ArrayList<Professor> getProfessors() { return this.professors; }

    //    /**
    //     * Method to add a specific professor to a Course's professor list
    //     * Accordingly puts empty ratings for the professor
    //     * @param prof the Professor to add
    //     */
    //    public void addProfessor(Professor prof) {
    //        professors.add(prof);
    //        Map<String, Integer> holderRatings = getEmptyMap();
    //        mixedRatings.put(prof, holderRatings);
    //    }

    //    /**
    //     * Method to remove a specific professor from a Course's professor list
    //     * Accordingly removes ratings for the professor
    //     * @param prof the Professor to remove
    //     */
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
     */
    public Map<String, Integer> getRatingsForCourse(Professor prof) {
        return mixedRatings.get(prof);
    }


    /**
     * Method for getting the average rating for the Professor across all courses
     * @return int averageRating representing the average rating of the Professor
     */
    public int getAverageRating() {
        return averageRating;
    }


    /**
     * Method for calculating an average rating for the professor
     * based on all the ratings for the courses they teach
     *
     * the ratings are assumed to be existing, as this is a private method call
     * and previous methods necessarily check whether they exist or not
     */
    private void updateAverageRating() {
        Map<String, Integer> currRating;
        int ratingSum = 0;
        int totRates = 0;
        for (Professor prof : professors) {
            currRating = mixedRatings.get(prof);
            ratingSum += (currRating.get("Workload") + currRating.get("Fun")
                    + currRating.get("Grading") + currRating.get("Knowledge"));
            totRates += currRating.get("RateCount");
        }

        averageRating = ratingSum / totRates;
    }

    /**
     * Helper method for creating an initialized map, for an 'empty' rating
     * @return 'empty' map
     */
    private Map<String, Integer> getEmptyMap() {
        Map<String, Integer> holderRatings = new HashMap<String, Integer>();
        holderRatings.put("Workload", 0);
        holderRatings.put("Fun", 0);
        holderRatings.put("Grading", 0);
        holderRatings.put("Knowledge", 0);
        holderRatings.put("RateCount", 0);

        return holderRatings;
    }
}
