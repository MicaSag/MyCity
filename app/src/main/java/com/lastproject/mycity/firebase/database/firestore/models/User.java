package com.lastproject.mycity.firebase.database.firestore.models;

import androidx.annotation.Nullable;

public class User {

    private String userID;
    private String userName;
    private Boolean isMayor;
    @Nullable private String urlPicture;
    private String email;
    @Nullable private String phoneNumber;

    // Blank constructor necessary for use with FireBase
    public User() { }

    public User(String userID, String userName, Boolean isMayor, @Nullable String urlPicture, String email, @Nullable String phoneNumber) {
        this.userID = userID;
        this.userName = userName;
        this.isMayor = isMayor;
        this.urlPicture = urlPicture;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                ", userName='" + userName + '\'' +
                ", isMayor=" + isMayor +
                ", urlPicture='" + urlPicture + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    // --- GETTERS ---


    public String getUserID() {return userID;}

    public String getUserName() {
        return this.userName;
    }

    public Boolean isMayor() {
        return isMayor;
    }

    @Nullable
    public String getUrlPicture() {
        return this.urlPicture;
    }

    public String getEmail() {
        return this.email;
    }

    @Nullable
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

// --- SETTERS ---

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setMayor(Boolean mayor) {
        isMayor = mayor;
    }

    public void setUrlPicture(@Nullable String urlPicture) {
        this.urlPicture = urlPicture;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(@Nullable String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
