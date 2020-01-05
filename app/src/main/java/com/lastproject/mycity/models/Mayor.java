package com.lastproject.mycity.models;

import com.lastproject.mycity.firebase.database.firestore.models.MayorFireStore;

public class Mayor {

    private String mayorID;
    private MayorFireStore mayorFireStore;

    // Blank constructor necessary for use with FireBase
    public Mayor() {
    }

    public Mayor(String mayorID, MayorFireStore mayorFireStore) {
        this.mayorID = mayorID;
        this.mayorFireStore = mayorFireStore;
    }

    @Override
    public String toString() {
        return "Mayor{" +
                "mayorID='" + mayorID + '\'' +
                ", mayorFireStore=" + mayorFireStore +
                '}';
    }

    // --- GETTERS ---

    public String getMayorID() {
        return mayorID;
    }

    public MayorFireStore getMayorFireStore() {
        return mayorFireStore;
    }

    // --- SETTERS ---


    public void setMayorID(String mayorID) {
        this.mayorID = mayorID;
    }

    public void setMayorFireStore(MayorFireStore mayorFireStore) {
        this.mayorFireStore = mayorFireStore;
    }
}



