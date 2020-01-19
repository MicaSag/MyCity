package com.lastproject.mycity.firebase.database.firestore.models;

import androidx.annotation.Nullable;

public class UserFireStore {

    private String userName;
    private Boolean isMayor;
    @Nullable private String urlPicture;
    private String email;
    @Nullable private String phoneNumber;
    private String inseeID;

    // Blank constructor necessary for use with FireBase
    public UserFireStore() { }

    public UserFireStore(String userName, Boolean isMayor, @Nullable String urlPicture, String email, @Nullable String phoneNumber, String inseeID) {
        this.userName = userName;
        this.isMayor = isMayor;
        this.urlPicture = urlPicture;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.inseeID = inseeID;
    }

    @Override
    public String toString() {
        return "UserFireStore{" +
                "userName='" + userName + '\'' +
                ", isMayor=" + isMayor +
                ", urlPicture='" + urlPicture + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", inseeID='" + inseeID + '\'' +
                '}';
    }

// --- GETTERS ---

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

    public String getInseeID() {
        return inseeID;
    }

    // --- SETTERS ---

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

    public void setInseeID(String inseeID) {
        this.inseeID = inseeID;
    }
}
