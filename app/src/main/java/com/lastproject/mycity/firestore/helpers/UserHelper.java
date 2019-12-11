package com.lastproject.mycity.firestore.helpers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.lastproject.mycity.firestore.models.User;

import java.util.Map;

public class UserHelper {

    private static final String COLLECTION_NAME = "users";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getUsersCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<Void> createUser(String uid, String userName, Boolean isMayor, String urlPicture, String email, String phoneNumber) {
        User userToCreate = new User(uid, userName,  isMayor, urlPicture, email, phoneNumber);
        return UserHelper.getUsersCollection().document(uid).set(userToCreate);
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

    public static Task<Void> updateRestaurantIdentifier(String uid, String restaurantIdentifier) {
        return UserHelper.getUsersCollection().document(uid).update("restaurantIdentifier", restaurantIdentifier);
    }

    public static Task<Void> updateRestaurantName(String uid, String restaurantName) {
        return UserHelper.getUsersCollection().document(uid).update("restaurantName", restaurantName);
    }

    public static Task<Void> updateListRestaurantLiked(String uid, Map<String, String> listRestaurantLiked) {
        return UserHelper.getUsersCollection().document(uid).update("listRestaurantLiked", listRestaurantLiked);
    }

    // --- DELETE ---

    public static Task<Void> deleteUser(String uid) {
        return UserHelper.getUsersCollection().document(uid).delete();
    }
}
