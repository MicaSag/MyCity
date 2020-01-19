package com.lastproject.mycity.models;

import com.lastproject.mycity.firebase.database.firestore.models.MayorFireStore;

public class Mayor extends MayorFireStore {

    private String mayorID;

    // Blank constructor necessary for use with FireBase
    public Mayor() {}

    public Mayor(String mayorID, MayorFireStore mayorFireStore) {
        super(  mayorFireStore.getUserID(),
                mayorFireStore.getTownHallID(),
                mayorFireStore.getCodeID()
        );
        this.mayorID = mayorID;
    }

    @Override
    public String toString() {
        return "Mayor{" +
                "mayorID='" + mayorID + '\'' +
                super.toString() +
                '}';
    }

    // --- GETTERS ---

    public String getMayorID() {return mayorID;}

    // --- SETTERS ---

    public void setMayorID(String mayorID) {this.mayorID = mayorID;}
}



