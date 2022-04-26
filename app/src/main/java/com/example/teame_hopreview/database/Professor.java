package com.example.teame_hopreview.database;

import java.util.ArrayList;
import java.util.Map;

public class Professor {

    private String name;

    private String key;
    private int avg_rating;
    private String department;
    private int grading_rating;
    private int knowledge_rating;

    private ArrayList<String> courses;
    private Map<String, Review> reviews;

    public Professor() {

    }

    public Professor(String name, String key, int avg_rating, String department, int grading_rating, int knowledge_rating, ArrayList<String> courses, Map<String, Review> reviews) {
        this.name = name;
        this.key = key;
        this.avg_rating = avg_rating;
        this.department = department;
        this.grading_rating = grading_rating;
        this.knowledge_rating = knowledge_rating;
        this.courses = courses;
        this.reviews = reviews;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvg_rating() {
        return avg_rating;
    }

    public void setAvg_rating(int avg_rating) {
        this.avg_rating = avg_rating;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getGrading_rating() {
        return grading_rating;
    }

    public void setGrading_rating(int grading_rating) {
        this.grading_rating = grading_rating;
    }

    public int getKnowledge_rating() {
        return knowledge_rating;
    }

    public void setKnowledge_rating(int knowledge_rating) {
        this.knowledge_rating = knowledge_rating;
    }

    public ArrayList<String> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<String> courses) {
        this.courses = courses;
    }

    public Map<String, Review> getReviews() {
        return reviews;
    }

    public void setReviews(Map<String, Review> reviews) {
        this.reviews = reviews;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getInitials() {
        String[] names = name.split(" ");
        String initials = "";
        for (String s : names) {
            initials += String.valueOf(s.charAt(0));
        }
        return initials;
    }
}
