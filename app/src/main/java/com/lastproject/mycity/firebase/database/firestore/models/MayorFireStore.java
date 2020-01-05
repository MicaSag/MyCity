package com.lastproject.mycity.firebase.database.firestore.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class MayorFireStore implements Parcelable {

    private String userID;
    private String townHallID;
    private String codeID;

    // Blank constructor necessary for use with FireBase
    public MayorFireStore() { }

    public MayorFireStore(String userID, String townHallID, String codeID) {
        this.userID = userID;
        this.townHallID = townHallID;
        this.codeID = codeID;
    }

    @Override
    public String toString() {
        return "MayorFireStore{" +
                "userID='" + userID + '\'' +
                ", townHallID='" + townHallID + '\'' +
                ", codeID='" + codeID + '\'' +
                '}';
    }

// --- GETTERS ---

    public String getUserID() {
        return userID;
    }

    public String getTownHallID() {
        return townHallID;
    }

    @NonNull public String getCodeID() {return codeID;}

    // --- SETTERS ---

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setTownHallID(String townHallID) {
        this.townHallID = townHallID;
    }

    public void setCodeID(@NonNull String codeID) {this.codeID = codeID;}

    // ---------------------------------------------------------------------------------------------
    //                                         PARCELABLE SECTION
    // ---------------------------------------------------------------------------------------------
    /* Return the number of complex fields */
    @Override
    public int describeContents() {
        /* Generally not used */
        return 0;
    }

    /* Write Mayor in parcel */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userID);
        dest.writeString(this.townHallID);
        dest.writeString(this.codeID);
    }

    /* Factory Creator */
    public static final Creator<MayorFireStore> CREATOR = new Creator<MayorFireStore>() {
        @Override
        public MayorFireStore createFromParcel(Parcel in) {
            return new MayorFireStore(in);
        }

        @Override
        public MayorFireStore[] newArray(int size) {
            return new MayorFireStore[size];
        }
    };

    /* Constructor for build a Mayor from Parcel */
    protected MayorFireStore(Parcel in) {
        this.userID = in.readString();
        this.townHallID = in.readString();
        this.codeID = in.readString();
    }
}
