package com.lastproject.mycity.firebase.database.firestore.helpers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.lastproject.mycity.firebase.database.firestore.models.UserFireStore;

public class UserHelper {

    private static final String COLLECTION_NAME = "users";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getUsersCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<Void> createUser(String userID, UserFireStore userFireStore) {
        return UserHelper.getUsersCollection().document(userID).set(userFireStore);
    }

    // --- GET ---

    public static Task<DocumentSnapshot> getUser(String uid){
        return UserHelper.getUsersCollection().document(uid).get();
    }

    public static Query getUserByName(String name){
        return UserHelper.getUsersCollection().whereEqualTo("userName",name);
    }

    public static Query getAllUser(){
        return UserHelper.getUsersCollection().orderBy("userName");
    }

    // --- UPDATE ---

    public static Task<Void> updateUserName(String userName, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("userName", userName);
    }

    public static Task<Void> updateUserInseeID(String inseeID, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("inseeID", inseeID);
    }

    // --- DELETE ---

    public static Task<Void> deleteUser(String uid) {
        return UserHelper.getUsersCollection().document(uid).delete();
    }
}
