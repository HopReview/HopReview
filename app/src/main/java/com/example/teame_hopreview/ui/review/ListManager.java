package com.example.teame_hopreview.ui.review;

import com.example.teame_hopreview.ui.course.CourseItem;
import com.example.teame_hopreview.ui.professors.Professor;

import java.util.ArrayList;

public class ListManager {

    private ArrayList<String> coursesList;

    private ArrayList<CourseItem> courses;

    private ArrayList<CourseWrapper> courseWrappers;

    private ArrayList<String> professorsList;

    private ArrayList<Professor> professors;

    private ArrayList<ProfessorWrapper> professorWrappers;

    public ListManager() {
        coursesList = new ArrayList<>();
        courses = new ArrayList<CourseItem>();
        courseWrappers = new ArrayList<CourseWrapper>();
        professorsList = new ArrayList<>();
        professors = new ArrayList<Professor>();
        professorWrappers = new ArrayList<>();
    }

    public void addCourseName(String course) {
        coursesList.add(course);
    }


    public void addCourse(CourseItem course) {
        courses.add(course);
        courseWrappers.add(new CourseWrapper(course));
    }

    public void addProfessorName(String prof) {
        professorsList.add(prof);
    }


    public void addProfessor(Professor professor) {
        professors.add(professor);
        professorWrappers.add(new ProfessorWrapper(professor));
    }

    public ArrayList<String> getCoursesList() { return coursesList; }

    public ArrayList<CourseItem> getCourses() {
        return courses;
    }

    public void setCoursesList(ArrayList<String> list) {
        this.coursesList.clear();
        for (String curr : list) {
            addCourseName(curr);
        }
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

    public ArrayList<String> getProfessorsList() { return professorsList; }


    public ArrayList<Professor> getProfessors() {
        return professors;
    }

    public void setProfessorsList(ArrayList<String> list) {
        this.professorsList.clear();

        for (String curr : list) {
            addProfessorName(curr);
        }
    }

    public void setProfessors(ArrayList<Professor> professors) {
        this.professors.clear();
        //this.professorWrappers.clear();
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
