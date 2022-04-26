package com.example.teame_hopreview;

import static android.content.ContentValues.TAG;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.teame_hopreview.ui.course.CourseItem;
import com.example.teame_hopreview.ui.review.DbReview;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;

public class User {
    private String userName;
    private String email;
    private String userId;
    private ArrayList<String> bookmarkedCourses;
    private ArrayList<ReviewItem> userReviews;
    private String[] recentlyViewed;
    DatabaseReference dbref;
    Stack<String> rVHelper;
    ArrayList<String> toReturn;




    public User() { }
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
        dbref = FirebaseDatabase.getInstance().getReference();
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
                        ReviewItem newRev = new ReviewItem();
                        int avgRating = 0;
                        String date = "";
                        int firstRating = 0;
                        String revMessage = "";
                        String revUser = userName;
                        int secondRating = 0;

                        Iterable<DataSnapshot> reviews = snapshot.getChildren();
                        boolean isEmpty = true;
                        for (DataSnapshot rev : reviews) {
                            Iterable<DataSnapshot> revContent = rev.getChildren();
                            int counter = 1;
                            for (DataSnapshot content : revContent) {
                                isEmpty = false;
                                if (counter == 1) {
                                    avgRating = content.getValue(Integer.class);
                                } else if (counter == 2) {
                                    date = content.getValue(String.class);
                                } else if (counter == 3) {
                                    firstRating = content.getValue(Integer.class);
                                } else if (counter == 4) {
                                    revMessage = content.getValue(String.class);
                                } else if (counter == 6) {
                                    secondRating = content.getValue(Integer.class);
                                }
                                counter++;
                            }
                        }

                        if (!isEmpty) {
                            newRev = new ReviewItem(avgRating, date, firstRating, revMessage, revUser, secondRating);
                            addUserReview(newRev);
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
        if (bookmarkedCourses == null) {
            bookmarkedCourses = new ArrayList<>();
        }
        if (!bookmarkedCourses.contains(newCourse)) {
            bookmarkedCourses.add(newCourse);
        }
    }

    public void removeBookmarkedCourse (String removeCourse) {
        if (bookmarkedCourses != null) {
            bookmarkedCourses.remove(removeCourse);
        }
    }

    public ArrayList<String> getBookmarkedCourses() { return bookmarkedCourses; }

    public void addUserReview (ReviewItem newReview) {
        if (userReviews == null) {
            userReviews = new ArrayList<>();
        }
        userReviews.add(newReview);
    }

    public ArrayList<ReviewItem> getUserReviews() {
        return userReviews;
    }

    public void addRecentlyViewed(String recent) {
        if (recentlyViewed == null) {
            recentlyViewed = new String[3];
            rVHelper = new Stack<>();
        }
        if (rVHelper.contains(recent)) {
            rVHelper.remove(recent);
        } else {
            if (rVHelper.size() == 3) {
                rVHelper.remove(rVHelper.get(0));
            }
        }
        rVHelper.add(recent);
        for (int i = 0; i < 3; i++) {
            if (i < rVHelper.size()) {
                recentlyViewed[i] = rVHelper.get(i);
            }
        }
    }

    public ArrayList<String> getRecentlyViewedList() {
        this.retrieveUserData();
        ArrayList<String> toReturn = new ArrayList<>();
        if (recentlyViewed != null) {
            for (String str : recentlyViewed) {
                if (str == null) {
                    break;
                }
                toReturn.add(str);
            }
        }

        return toReturn;
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
        dbref.child("user_data").child(userId).child("recentlyViewed").setValue(getRecentlyViewedList());
    }

    public void updateUserReviewDatabase() {
        dbref.child("user_data").child(userId).child("userReviews").setValue(getUserReviews());
    }

    public void updateBookmarkedCoursesDatabase() {
        dbref.child("user_data").child(userId).child("bookmarkedCourses").setValue(getBookmarkedCourses());
    }

    public void retrieveUserData() {
        dbref.child("user_data").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Iterable<DataSnapshot> userData = snapshot.getChildren();
                int counter = 1;
                for (DataSnapshot data : userData) {
                    if (data.getKey().equals("bookmarkedCourses")) {
                        Iterable<DataSnapshot> bmCourses = data.getChildren();
                        for (DataSnapshot crs : bmCourses) {
                            addBookmarkedCourse(crs.getValue(String.class));
                        }
                    } else if (data.getKey().equals("email")) {
                        email = data.getValue(String.class);
                    } else if (data.getKey().equals("recentlyViewed")) {
                        Iterable<DataSnapshot> rvCourses = data.getChildren();
                        for (DataSnapshot crs : rvCourses) {
                            addRecentlyViewed(crs.getValue(String.class));
                        }
                    } else if (data.getKey().equals("userReviews")) {
                        Iterable<DataSnapshot> reviews = data.getChildren();
                        // for (DataSnapshot rev : reviews) {
                        //     for now omitted
                        // }
                    } else if (data.getKey().equals("username")) {
                        userName = data.getValue(String.class);
                    }
                }

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


    public ArrayList<String> retrieveUserDataRV() {
        dbref.child("user_data").child(userId).child("recentlyViewed").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    toReturn = new ArrayList<>();
                    Iterable<DataSnapshot> rvCourses = snapshot.getChildren();
                    for (DataSnapshot crs : rvCourses) {
                        addRecentlyViewed(crs.getValue(String.class));
                        toReturn = getRecentlyViewedList();
                    }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });


        return toReturn;
    }

    public String getUserId() {
        return this.userId;
    }

    private void setUserId(String email) {
        int splitIndex = email.indexOf('@');
        this.userId = email.substring(0, splitIndex);
    }
}
