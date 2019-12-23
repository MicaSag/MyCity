package com.lastproject.mycity.firebase.database.firestore.models;

import androidx.annotation.NonNull;

public class Mayor {

    private String userID;
    private String inseeID;
    @NonNull  private String codeID;

    // Blank constructor necessary for use with FireBase
    public Mayor() { }

    public Mayor(String userID, String inseeID, String codeID) {
        this.userID = userID;
        this.inseeID = inseeID;
        this.codeID = codeID;
    }

    // --- GETTERS ---

    public String getUserID() {
        return userID;
    }

    public String getInseeID() {
        return inseeID;
    }

// --- SETTERS ---

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setInseeID(String inseeID) {
        this.inseeID = inseeID;
    }

    public void setCodeID(String codeID) {
        this.codeID = codeID;
    }
}
