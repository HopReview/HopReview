package com.example.teame_hopreview;

import com.example.teame_hopreview.ui.course.CourseItem;

import java.util.ArrayList;

public class User {
    private String userName;
    private String email;
    private ArrayList<CourseItem> bookmarkedCourses;
    private ArrayList<ReviewItem> userReviews;
    private CourseItem[] recentlyViewed;


    public User() {

    }

    public User(String userName, String email) {
        this.userName = userName;
        this.email = email;
        bookmarkedCourses = new ArrayList<>();
        userReviews = new ArrayList<>();
        recentlyViewed = new CourseItem[3];
    }

    public String getUserName() {
        return this.userName;
    }

    public String getEmail() {
        return this.email;
    }

    public void addBookmarkedCourse (CourseItem newCourse) {
        bookmarkedCourses.add(newCourse);
    }

    public ArrayList<CourseItem> getBookmarkedCourses() {
        return bookmarkedCourses;
    }

    public void addUserReview (ReviewItem newReview) {
        userReviews.add(newReview);
    }

    public ArrayList<ReviewItem> getUserReviews() {
        return userReviews;
    }

    public void addRecentlyViewed(CourseItem recent) {
        if (recentlyViewed[0] == null) {
            recentlyViewed[0] = recent;
        } else if (recentlyViewed[1] == null) {
            recentlyViewed[1] = recentlyViewed[0];
            recentlyViewed[0] = recent;
        } else {
            recentlyViewed[2] = recentlyViewed[1];
            recentlyViewed[1] = recentlyViewed[0];
            recentlyViewed[0] = recent;
        }
    }

    public CourseItem[] getRecentlyViewed() {
        return recentlyViewed;
    }

}
