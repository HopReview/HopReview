package com.example.teame_hopreview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ReviewItem extends Profile {
    private String parentName;
    private String reviewerName;
    private String reviewContent;
    // TODO: add more fields to the review item

    /**
     * Constructor to create a ReviewItem object
     */
    public ReviewItem() { }

    /**
     * Constructor to create a ReviewItem object
     * @param parent : Course/Professor that review belongs to
     * @param reviewer : User leaving the review
     * @param review : content of the review
     */
    public ReviewItem(String parent, String reviewer, String review) {
        this.parentName = parent;
        this.reviewerName = reviewer;
        this.reviewContent = review;
    }


    /**
     * Method for getting parent Course/Professor name
     * @return String name of the parent
     */
    public String getParentName() { return this.parentName; }


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


    // TODO: methods for getting and setting other important review items

}
