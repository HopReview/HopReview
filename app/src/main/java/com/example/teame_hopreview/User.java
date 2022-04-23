package com.example.teame_hopreview;

import static android.content.ContentValues.TAG;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.teame_hopreview.ui.course.CourseItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class User {
    private String userName;
    private String email;
    private String userId;
    private ArrayList<String> bookmarkedCourses;
    private ArrayList<String> userReviews;
    private String[] recentlyViewed;
    DatabaseReference dbref;



    public User() {

    }
    // Constructor for creating new User
    public User(String userName, String email) {
        dbref = FirebaseDatabase.getInstance().getReference();
        this.userName = userName;
        this.email = email;
        bookmarkedCourses = new ArrayList<>();
        userReviews = new ArrayList<>();
        recentlyViewed = new String[3];
        setUserId(email);
    }

    // Constructor for retrieving existing user
    public User(String email) {
        setUserId(email);
    }



    public void updateFromDatabase() {
        dbref.child("user_data").child(userId).child("bookmarkedCourses").
                addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> bookmarks = snapshot.getChildren();
                for (DataSnapshot course : bookmarks) {
                    addBookmarkedCourse(course.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        dbref.child("user_data").child(userId).child("recentlyViewed").
                addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> recent = snapshot.getChildren();
                for (DataSnapshot rec : recent) {
                    addRecentlyViewed(rec.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        dbref.child("user_data").child(userId).child("userReviews")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Iterable<DataSnapshot> reviews = snapshot.getChildren();
                        for (DataSnapshot rev : reviews) {
                            addUserReview(rev.getValue(String.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());

                    }
                });
    }

    public String getUserName() {
        return this.userName;
    }

    public String getEmail() {
        return this.email;
    }

    public void addBookmarkedCourse (String newCourse) {
        bookmarkedCourses.add(newCourse);
    }

    public ArrayList<String> getBookmarkedCourses() {
        return bookmarkedCourses;
    }

    public void addUserReview (String newReview) {
        userReviews.add(newReview);
    }

    public ArrayList<String> getUserReviews() {
        return userReviews;
    }

    public void addRecentlyViewed(String recent) {
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

    public String[] getRecentlyViewed() {
        return recentlyViewed;
    }

    public void updateUsername() {
        dbref.child("user_data").child(userId).child("username").setValue(getUserName());
    }

    public void updateEmail() {
        dbref.child("user_data").child(userId).child("email").setValue(getEmail());
    }

    public void updateRecentlyViewedDatabase() {
        dbref.child("user_data").child(userId).child("recentlyViewed").setValue(getRecentlyViewed());
    }

    public void updateUserReviewDatabase() {
        dbref.child("user_data").child(userId).child("userReviews").setValue(getUserReviews());
    }

    public void updateBookmarkedCoursesDatabase() {
        dbref.child("user_data").child(userId).child("bookmarkedCourses").setValue(getBookmarkedCourses());
    }

    public void retrieveUserData() {
        dbref.child("user_data").child(userId).child("username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userName = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });

        dbref.child("user_data").child(userId).child("email").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                email = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });
        updateFromDatabase();
    }

    public String getUserId() {
        return this.userId;
    }

    private void setUserId(String email) {
        int splitIndex = email.indexOf('@');
        this.userId = email.substring(0, splitIndex);
    }



}
