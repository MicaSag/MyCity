package com.lastproject.mycity.repositories;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.lastproject.mycity.firebase.database.firestore.helpers.TownHallHelper;
import com.lastproject.mycity.firebase.database.firestore.models.TownHallFireStore;

public class TownHallDataFireStoreRepository {

    public TownHallDataFireStoreRepository() {}

    // --- CREATE ---

    public Task<DocumentReference> createTownHall(TownHallFireStore townHallFireStore) {
        return TownHallHelper.createTownHall(townHallFireStore);
    }

    // --- GET ---

    // Get TownH by townHallID
    public Task<DocumentSnapshot> getTownHallByTownHallID(String townHallID){
        return TownHallHelper.getTownHallByTownHallID(townHallID);
    }

    // Get TownH by inseeID
    public Query getTownHallByInseeID(String inseeID){
        return TownHallHelper.getTownHallByInseeID(inseeID);
    }

    // --- UPDATE ---

    // Update userID of Mayor
    public Task<Void> updateTownHallHours(String townHallID, String hours) {
        return TownHallHelper.updateTownHallHours(townHallID, hours);
    }

    // --- DELETE ---

    public Task<Void> deleteTownHall(String townHallID) {
        return TownHallHelper.deleteTownHall(townHallID);
    }
}

