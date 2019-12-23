package com.lastproject.mycity.firebase.database.firestore.helpers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lastproject.mycity.firebase.database.firestore.models.Mayor;

public class MayorHelper {

    // For Debug
    private static final String TAG = MayorHelper.class.getSimpleName();

    private static final String COLLECTION_NAME = "mayors";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getMayorsCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<Void> createMayor(String userID, String inseeID, String codeID) {
        Mayor mayorToCreate = new Mayor(userID, inseeID, codeID);
        return MayorHelper.getMayorsCollection()
                .document()
                .set(mayorToCreate);
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

    // Get Mayor by inseeID
    public static Query getMayorByInseeID(String inseeID){
        return MayorHelper.getMayorsCollection().whereEqualTo("inseeID",inseeID);
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

    // Update inseeID of Mayor
    public static Task<Void> updateMayorInseeID(String mayorID, String inseeID) {
        return MayorHelper.getMayorsCollection().document(mayorID).update("inseeID", inseeID);
    }

    // --- DELETE ---

    public static Task<Void> deleteUser(String uid) {
        return MayorHelper.getMayorsCollection().document(uid).delete();
    }
}
