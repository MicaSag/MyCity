package com.lastproject.mycity.repositories;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.lastproject.mycity.firebase.database.firestore.helpers.UserHelper;

public class UserDataFireStoreRepository {

    public UserDataFireStoreRepository() {}

    // --- GET ---

    public Task<DocumentSnapshot> getUser(String uid){
        return UserHelper.getUser(uid);
    }

    public Query getUserByName(String name){
        return UserHelper.getUserByName(name);
    }

    public Query getAllUser(){

        return UserHelper.getAllUser();
    }

    // --- CREATE ---

    public Task<Void> createUser(String uid, String userName, Boolean isMayor, String urlPicture, String email, String phoneNumber) {

        return UserHelper.createUser(uid, userName, isMayor, urlPicture, email, phoneNumber);
    }

    // --- DELETE ---

    public Task<Void> deleteUser(String uid) {
        return UserHelper.deleteUser(uid);
    }

    // --- UPDATE ---

    public Task<Void> updateUserName(String userName, String uid) {
        return UserHelper.updateUserName(userName, uid);
    }
}

