package com.lastproject.mycity.models;

import com.lastproject.mycity.firebase.database.firestore.models.UserFireStore;

public class User extends UserFireStore {

    private String userID;

    // Blank constructor necessary for use with FireBase
    public User() {}

    public User(String userID, UserFireStore userFireStore) {
        super(  userFireStore.getUserName(),
                userFireStore.isMayor(),
                userFireStore.getUrlPicture(),
                userFireStore.getEmail(),
                userFireStore.getPhoneNumber(),
                userFireStore.getInseeID()
        );
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                super.toString() +
                '}';
    }

    // --- GETTERS ---

    public String getUserID() {
        return userID;
    }

    // --- SETTERS ---

    public void setUserID(String userID) {
        this.userID = userID;
    }
}



