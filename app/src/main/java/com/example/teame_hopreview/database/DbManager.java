package com.example.teame_hopreview.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbManager {

    private DatabaseReference dbRef;

    private List<Course> courses;

    private List<Professor> professors;

    private ArrayList<CoursesOnChangeListener> coursesOnChangeListeners;

    private ArrayList<ProfessorsOnChangeListener> professorsOnChangeListeners;

    private static DbManager dbManager;

    public static DbManager getDbManager() {
        if (dbManager == null) {
            dbManager = new DbManager();
        }
        return dbManager;
    }

    public void addCoursesOnChangeListener(CoursesOnChangeListener coursesOnChangeListener) {
        coursesOnChangeListeners.add(coursesOnChangeListener);
    }

    public void addProfessorsOnChangeListener(ProfessorsOnChangeListener professorsOnChangeListener) {
        professorsOnChangeListeners.add(professorsOnChangeListener);
    }

    private DbManager() {
        courses = new ArrayList<>();
        professors = new ArrayList<>();
        dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.addValueEventListener(new DatabaseChangeListener());
        coursesOnChangeListeners = new ArrayList<>();
        professorsOnChangeListeners = new ArrayList<>();

    }

    //Creates a review for a course and professor
    public void createReview(Review review) {
        String courseReviewKey = dbRef.child("courses_data").child(review.getCourse().getKey()).child("reviews").push().getKey();
        Map<String, Object> courseReviewValues = review.toMap();
        //Add professor key to course review values
        //courseReviewValues.put("professorKey", review.getProfessor().getKey());

        //String professorReviewKey = dbRef.child("professors_data").child(review.getProfessor().getKey()).child("reviews").push().getKey();
        //Map<String, Object> professorReviewValues = review.toMap();
        //Add professor key to course review values
        //professorReviewValues.put("courseKey", review.getCourse().getKey());

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/courses_data/" + review.getCourse().getKey() + "/reviews/" + courseReviewKey, courseReviewValues);
        //childUpdates.put("/professors_data/" + review.getProfessor().getKey() + "/reviews/" + professorReviewKey, professorReviewValues);

        dbRef.updateChildren(childUpdates);
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Professor> getProfessors() {
        return professors;
    }

    class DatabaseChangeListener implements ValueEventListener {

        private static final String TAG = "dbref: ";

        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            Iterable<DataSnapshot> coursesSnapshot = snapshot.child("courses_data").getChildren();

            ArrayList<Course> courseList = new ArrayList<>();
            for (DataSnapshot crs : coursesSnapshot) {
                Course course = crs.getValue(Course.class);
                course.setKey(crs.getKey());


                /*for (Review review: course.getReviews()) {
                    if (review != null) review.setCourse(course);
                }*/

                while (course.getProfessor().remove(null));
                courseList.add(course);
            }

            Iterable<DataSnapshot> professorsSnapshot = snapshot.child("professors_data").getChildren();

            ArrayList<Professor> professorList = new ArrayList<>();
            for (DataSnapshot prof : professorsSnapshot) {
                Professor professor = prof.getValue(Professor.class);
                professor.setKey(prof.getKey());
                professor.setName(professor.getKey());

                for (Review review: professor.getReviews()) {
                    if (review != null) review.setProfessor(professor);
                }
                professorList.add(professor);
            }

            courses = courseList;
            professors = professorList;

            for (CoursesOnChangeListener listener: coursesOnChangeListeners) {
                listener.onChange(courses);
            }

            for (ProfessorsOnChangeListener listener: professorsOnChangeListeners) {
                listener.onChange(professors);
            }
        }

        @Override
        public void onCancelled(DatabaseError error) {
            Log.w(TAG, "Failed to read value.", error.toException());
        }
    }




}
