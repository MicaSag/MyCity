package com.lastproject.mycity.firestore.models;

import androidx.annotation.Nullable;

public class User {

    private String mUid;
    private String mUserName;
    private Boolean isMayor;
    @Nullable private String mUrlPicture;
    private String mEmail;
    private String mPhoneNumber;

    // Blank constructor necessary for use with FireBase
    public User() { }

    public User(String mUid, String mUserName, Boolean isMayor, @Nullable String mUrlPicture, String mEmail, String mPhoneNumber) {
        this.mUid = mUid;
        this.mUserName = mUserName;
        this.isMayor = isMayor;
        this.mUrlPicture = mUrlPicture;
        this.mEmail = mEmail;
        this.mPhoneNumber = mPhoneNumber;
    }

// --- GETTERS ---

    public String getmUid() {
        return mUid;
    }

    public String getmUserName() {
        return mUserName;
    }

    public Boolean getMayor() {
        return isMayor;
    }

    @Nullable
    public String getmUrlPicture() {
        return mUrlPicture;
    }

    public String getmEmail() {
        return mEmail;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

// --- SETTERS ---

    public void setmUid(String mUid) {
        this.mUid = mUid;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public void setMayor(Boolean mayor) {
        isMayor = mayor;
    }

    public void setmUrlPicture(@Nullable String mUrlPicture) {
        this.mUrlPicture = mUrlPicture;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }
}
