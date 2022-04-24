package com.example.teame_hopreview.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.teame_hopreview.ReviewItem;
import com.example.teame_hopreview.ui.course.CourseItem;
import com.example.teame_hopreview.ui.professors.Professor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DbManager {

    private DatabaseReference dbRef;

    public DbManager() {
        dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.addValueEventListener(new DatabaseChangeListener());
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



    class DatabaseChangeListener implements ValueEventListener {

        private static final String TAG = "dbref: ";

        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            Iterable<DataSnapshot> courses = snapshot.child("courses_data").getChildren();

            ArrayList<Course> courseList = new ArrayList<>();
            for (DataSnapshot crs : courses) {
                Course course = crs.getValue(Course.class);
                course.setKey(crs.getKey());

                for (Review review: course.getReviews()) {
                    review.setCourse(course);
                }
                courseList.add(course);
            }

            Iterable<DataSnapshot> professorsSnapshot = snapshot.child("professors_data").getChildren();

            for (DataSnapshot prof : professorsSnapshot) {
                Professor professor = prof.getValue(Pr.class);
                course.setKey(prof.getKey());

                for (Review review: course.getReviews()) {
                    review.setCourse(course);
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError error) {
            Log.w(TAG, "Failed to read value.", error.toException());
        }

        private String ensureStringLengthIsLessThanMax(String string) {
            int maxLength = 30;
            String result = new String(string);
            if (string.length() > maxLength) {
                result = string.substring(0, maxLength - 3 - 1);
                //Add three dots to end
                result = result + "...";
            }
            return result;
        }
    }




}
