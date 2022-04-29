package com.example.teame_hopreview.ui.professors;

import com.example.teame_hopreview.Profile;
import com.example.teame_hopreview.ReviewItem;
import com.example.teame_hopreview.ui.course.CourseItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Professor extends Profile {
    private String initials;
    private String professorName;
    private String department;
    private ArrayList<CourseItem> courses;
    private ArrayList<String> courseNames;
    private String course;
    private ArrayList<ReviewItem> reviews;
    private Map<CourseItem, Map<String, Integer>> mixedRatings;
    private int averageRating;
    private int gRating;
    private int kRating;
    private String id;

    /**
     * Constructor to create a Professor object
     */
    public Professor() { }

    /**
     * Constructor to create a Professor object
     * @param name : indicating the Professor's name
     * @param department : indicating the department of the Professor
     */
    public Professor(String name, String department, int grading, int knowledge, int avg) {
        this.professorName = name;
        this.department = department;
        String[] names = name.split(" ");
        this.initials = "";
        this.reviews = new ArrayList<>();
        this.courseNames = new ArrayList<>();

        for (String s : names) {
            initials += String.valueOf(s.charAt(0));
        }

        this.gRating = grading;
        this.kRating = knowledge;
        this.averageRating = avg;
    }

    public ArrayList<String> getCourseNames() { return this.courseNames; }


    public String getInitials() { return this.initials; }


    /**
     * Method for getting a Professor's name
     * @return String name of the Professor
     */
    public String getProfessorName() {
        return this.professorName;
    }

    /**
     * Method for getting a Professor's department
     * @return String department of the Professor
     */
    public String getDepartment() {
        return this.department;
    }

    /**
     * Method for getting the courses taught by the Professor
     * @return ArrayList representing courses taught by the Professor
     */
    public ArrayList<CourseItem> getCourses() { return this.courses; }

    public ArrayList<ReviewItem> getReviews() { return this.reviews; }


    public int getGradeRating() {
        return gRating;
    }

    public int getKnowledgeRating() {
        return kRating;
    }

    public void addReview(ReviewItem rev) {
        reviews.add(rev);
    }

    public void addCourseNames(String name) {
        courseNames.add(name);
    }

    /**
     * Method to add a specific course to a Professor's course list
     * Accordingly puts empty ratings for the course
     * @param crs the Course to add
     */
    public void addCourse(CourseItem crs) {
        courses.add(crs);
        Map<String, Integer> holderRatings = getEmptyMap();
        if (mixedRatings == null) mixedRatings = new HashMap<>();
        mixedRatings.put(crs, holderRatings);
    }

    /**
     * Method for adding ratings from a user for a specific course taught by the professor
     * Assumed that the currentRatings are passed on correctly by the caller
     * Assumed that the Course crs is a course taught by the professor, checked by the caller
     * @param ratings : Map of ratings that match specific categories with their average ratings
     * @param crs : Course for which ratings will be added
     */
    public void addRatingsForProfessor(Map<String, Integer> ratings, CourseItem crs) {
        Map<String, Integer> currentRatings = mixedRatings.get(crs);

        // get rate count in order to keep track of how many ratings there have been
        int rateCount = currentRatings.get("RateCount");

        // update ratings for the Workload param
        // check to see if it ever has been rated
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
        mixedRatings.put(crs, currentRatings);
        updateAverageRating();
    }

    /**
     * Method for getting the ratings for the Professor for a specific course
     * @param crs : the specific course for which ratings are inquired
     * @return Map that represents the ratings of the Professor for that course
     */
    public Map<String, Integer> getRatingsForCourse(CourseItem crs) {
        return mixedRatings.get(crs);
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
        for (CourseItem crs : courses) {
            currRating = mixedRatings.get(crs);
            ratingSum += (currRating.get("Grading") + currRating.get("Knowledge"));
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
        holderRatings.put("Grading", 0);
        holderRatings.put("Knowledge", 0);
        holderRatings.put("RateCount", 0);

        return holderRatings;
    }
}
