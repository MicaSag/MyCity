package com.lastproject.mycity.repositories;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.lastproject.mycity.firebase.database.firestore.helpers.MayorHelper;
import com.lastproject.mycity.firebase.database.firestore.helpers.UserHelper;
import com.lastproject.mycity.firebase.database.firestore.models.Mayor;

public class MayorDataFireStoreRepository {

    public MayorDataFireStoreRepository() {}

    // --- CREATE ---

    public Task<Void> createMayor(String userID, String inseeID, String codeID) {
        return MayorHelper.createMayor(userID, inseeID, codeID);
    }

    // --- GET ---

    // Get Mayor by mayorID
    public Task<DocumentSnapshot> getMayorByMayorID(String mayorID){
        return MayorHelper.getMayorByMayorID(mayorID);
    }

    // Get Mayor by userID
    public Query getMayorByUserID(String userID){
        return MayorHelper.getMayorByUserID(userID);
    }

    // Get Mayor by townHallID
    public Query getMayorByTownHallID(String townHallID){
        return MayorHelper.getMayorByTownHallID(townHallID);
    }

    // Get Mayor by codeID
    public Query getMayorByCodeID(String codeID){
        return MayorHelper.getMayorByCodeID(codeID);
    }

    // Get All Mayor
    public Query getAllMayor(){
        return MayorHelper.getAllMayor();
    }

    // --- DELETE ---



    // --- UPDATE ---

    // Update userID of Mayor
    public Task<Void> updateMayorUserID(String mayorID, String userID) {
        return MayorHelper.updateMayorUserID(mayorID, userID);
    }

    // Update townHallID of Mayor
    public Task<Void> updateMayorTownHallID(String mayorID, String townHallID) {
        return MayorHelper.updateMayorTownHallID(mayorID, townHallID);
    }
}

