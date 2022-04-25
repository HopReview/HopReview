package com.example.teame_hopreview.ui.review;

import com.example.teame_hopreview.database.Course;
import com.example.teame_hopreview.database.Professor;

import java.util.ArrayList;
import java.util.List;

public class ListManager {

    private ArrayList<Course> courses;

    private ArrayList<CourseWrapper> courseWrappers;

    private ArrayList<Professor> professors;

    private ArrayList<ProfessorWrapper> professorWrappers;

    public ListManager() {
        courses = new ArrayList<Course>();
        courseWrappers = new ArrayList<CourseWrapper>();
        professors = new ArrayList<Professor>();
        professorWrappers = new ArrayList<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
        courseWrappers.add(new CourseWrapper(course));
    }

    public void addProfessor(Professor professor) {
        professors.add(professor);
        professorWrappers.add(new ProfessorWrapper(professor));
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses.clear();
        this.courseWrappers.clear();
        for (Course course: courses) {
            addCourse(course);
        }
    }

    public ArrayList<CourseWrapper> getCourseWrappers() {
        return courseWrappers;
    }

    public void setCourseWrappers(ArrayList<CourseWrapper> courseWrappers) {
        this.courseWrappers = courseWrappers;
    }

    public ArrayList<Professor> getProfessors() {
        return professors;
    }

    public void setProfessors(List<Professor> professors) {
        this.professors.clear();
        this.professorWrappers.clear();
        for (Professor prof: professors) {
            addProfessor(prof);
        }
    }

    public ArrayList<ProfessorWrapper> getProfessorWrappers() {
        return professorWrappers;
    }

    public void setProfessorWrappers(ArrayList<ProfessorWrapper> professorWrappers) {
        this.professorWrappers = professorWrappers;
    }

}
