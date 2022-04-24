package com.example.teame_hopreview.ui.review;

import com.example.teame_hopreview.database.Course;

//A wrapper object for CourseItem
public class CourseWrapper {
    private Course course;

    public CourseWrapper(Course course) {
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return course.getDisplayName();
    }
}