package com.example.teame_hopreview;

public class Profile {
    private int profileNum;
    private String profileName;

    public Profile() {
    }

    public Profile(int num, String name) {
        this.profileNum = num;
        this.profileName = name;
    }

    public int getProfileNum() {
        return this.profileNum;
    }

    public String getProfileName() {
        return this.profileName;
    }

    public void setProfileNum(int num) {
        this.profileNum = num;
    }

    public void setProfileName(String name) {
        this.profileName = name;
    }

}