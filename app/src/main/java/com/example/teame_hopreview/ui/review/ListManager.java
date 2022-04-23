package com.example.teame_hopreview.ui.review;

import com.example.teame_hopreview.ui.course.CourseItem;
import com.example.teame_hopreview.ui.professors.Professor;

import java.util.ArrayList;

public class ListManager {


    private ArrayList<CourseItem> courses;

    private ArrayList<CourseWrapper> courseWrappers;


    private ArrayList<Professor> professors;

    private ArrayList<ProfessorWrapper> professorWrappers;

    public ListManager() {
        courses = new ArrayList<CourseItem>();
        courseWrappers = new ArrayList<CourseWrapper>();
        professors = new ArrayList<Professor>();
        professorWrappers = new ArrayList<>();
    }



    public void addCourse(CourseItem course) {
        courses.add(course);
        courseWrappers.add(new CourseWrapper(course));
    }



    public void addProfessor(Professor professor) {
        professors.add(professor);
        professorWrappers.add(new ProfessorWrapper(professor));
    }


    public ArrayList<CourseItem> getCourses() {
        return courses;
    }


    public void setCourses(ArrayList<CourseItem> courses) {
        this.courses.clear();
        this.courseWrappers.clear();
        for (CourseItem course: courses) {
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


    public void setProfessors(ArrayList<Professor> professors) {
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
