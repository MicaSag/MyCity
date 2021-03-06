package com.lastproject.mycity.firebase.database.firestore.helpers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.lastproject.mycity.firebase.database.firestore.models.MayorFireStore;

public class MayorHelper {

    // For Debug
    private static final String TAG = MayorHelper.class.getSimpleName();

    private static final String COLLECTION_NAME = "mayors";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getMayorsCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<Void> createMayor(MayorFireStore mayorFireStore) {
        return MayorHelper.getMayorsCollection()
                .document()
                .set(mayorFireStore);
    }

    // --- GET ---

    // Get Mayor by mayorID
    public static Task<DocumentSnapshot> getMayorByMayorID(String mayorID){
        return MayorHelper.getMayorsCollection().document(mayorID).get();
    }

    // Get Mayor by userID
    public static Query getMayorByUserID(String userID){
        return MayorHelper.getMayorsCollection().whereEqualTo("userID",userID);
    }

    // Get Mayor by townHallID
    public static Query getMayorByTownHallID(String townHallID){
        return MayorHelper.getMayorsCollection().whereEqualTo("townHallID",townHallID);
    }

    // Get Mayor by codeID
    public static Query getMayorByCodeID(String codeID){
        return MayorHelper.getMayorsCollection().whereEqualTo("codeID",codeID);
    }

    // Get All Mayor
    public static Query getAllMayor(){
        return MayorHelper.getMayorsCollection().orderBy("mayorID");
    }

    // --- UPDATE ---

    // Update userID of Mayor
    public static Task<Void> updateMayorUserID(String mayorID, String userID) {
        return MayorHelper.getMayorsCollection().document(mayorID).update("userID", userID);
    }

    // Update townHallID of Mayor
    public static Task<Void> updateMayorTownHallID(String mayorID, String townHallID) {
        return MayorHelper.getMayorsCollection().document(mayorID).update("townHallID", townHallID);
    }

    // --- DELETE ---

    public static Task<Void> deleteUser(String uid) {
        return MayorHelper.getMayorsCollection().document(uid).delete();
    }
}
