package com.example.teame_hopreview.ui.review;

import com.example.teame_hopreview.ui.course.CourseItem;

//A wrapper object for CourseItem
public class CourseWrapper {
    private CourseItem course;

    public CourseWrapper(CourseItem course) {
        this.course = course;
    }

    public CourseItem getCourse() {
        return course;
    }

    public void setCourse(CourseItem course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return course.getName();
    }
}