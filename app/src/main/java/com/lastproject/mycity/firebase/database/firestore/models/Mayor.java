package com.lastproject.mycity.firebase.database.firestore.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Mayor implements Parcelable {

    private String mayorID;
    private String userID;
    private String inseeID;
    @NonNull  private String codeID;

    // Blank constructor necessary for use with FireBase
    public Mayor() { }

    public Mayor(String mayorID, String userID, String inseeID, @NonNull String codeID) {
        this.mayorID = mayorID;
        this.userID = userID;
        this.inseeID = inseeID;
        this.codeID = codeID;
    }

    @Override
    public String toString() {
        return "Mayor{" +
                "mayorID='" + mayorID + '\'' +
                ", userID='" + userID + '\'' +
                ", inseeID='" + inseeID + '\'' +
                ", codeID='" + codeID + '\'' +
                '}';
    }

    // --- GETTERS ---

    public String getMayorID() {return mayorID;}

    public String getUserID() {
        return userID;
    }

    public String getInseeID() {
        return inseeID;
    }


// --- SETTERS ---

    public void setMayorID(String mayorID) {this.mayorID = mayorID;}

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setInseeID(String inseeID) {
        this.inseeID = inseeID;
    }

    public void setCodeID(String codeID) {
        this.codeID = codeID;
    }


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
        dest.writeString(this.mayorID);
        dest.writeString(this.userID);
        dest.writeString(this.inseeID);
        dest.writeString(this.codeID);
    }

    /* Factory Creator */
    public static final Creator<Mayor> CREATOR = new Creator<Mayor>() {
        @Override
        public Mayor createFromParcel(Parcel in) {
            return new Mayor(in);
        }

        @Override
        public Mayor[] newArray(int size) {
            return new Mayor[size];
        }
    };

    /* Constructor for build a Mayor from Parcel */
    protected Mayor(Parcel in) {
        this.mayorID = in.readString();
        this.userID = in.readString();
        this.inseeID = in.readString();
        this.codeID = in.readString();
    }
}
