package com.lastproject.mycity.firebase.database.firestore.helpers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.lastproject.mycity.firebase.database.firestore.models.Mayor;
import com.lastproject.mycity.firebase.database.firestore.models.TownHall;

public class TownHallHelper {

    // For Debug
    private static final String TAG = TownHallHelper.class.getSimpleName();

    private static final String COLLECTION_NAME = "townHalls";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getTownHallCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<DocumentReference> createTownHall(TownHall townHall) {
        return TownHallHelper.getTownHallCollection()
                .add(townHall);
    }

    // --- GET ---

    // Get TownH by townHallID
    public static Task<DocumentSnapshot> getTownHallByTownHallID(String townHallID){
        return TownHallHelper.getTownHallCollection().document(townHallID).get();
    }

    // Get TownH by inseeID
    public static Query getTownHallByInseeID(String inseeID){
        return TownHallHelper.getTownHallCollection().whereEqualTo("inseeID",inseeID);
    }

    // --- UPDATE ---

    // Update userID of Mayor
    public static Task<Void> updateTownHallHours(String townHallID, String hours) {
        return TownHallHelper.getTownHallCollection().document(townHallID).update("hours", hours);
    }

    // --- DELETE ---

    public static Task<Void> deleteTownHall(String townHallID) {
        return TownHallHelper.getTownHallCollection().document(townHallID).delete();
    }
}
