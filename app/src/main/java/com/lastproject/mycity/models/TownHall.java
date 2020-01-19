package com.lastproject.mycity.models;

import com.lastproject.mycity.firebase.database.firestore.models.TownHallFireStore;

public class TownHall extends TownHallFireStore {

    private String townHallID;

    // Blank constructor necessary for use with FireBase
    public TownHall() {}

    public TownHall(String townHallID, TownHallFireStore townHallFireStore) {
        super(  townHallFireStore.getInseeID(),
                townHallFireStore.getName(),
                townHallFireStore.getAddress(),
                townHallFireStore.getEmail(),
                townHallFireStore.getPhoneNumber(),
                townHallFireStore.getUrl(),
                townHallFireStore.getLocation(),
                townHallFireStore.getHours(),
                townHallFireStore.getPhoto()
        );
        this.townHallID = townHallID;
    }

    @Override
    public String toString() {
        return "TownHall{" +
                "townHallID='" + townHallID + '\'' +
                super.toString() +
                '}';
    }

    // --- GETTERS ---

    public String getTownHallID() {
        return townHallID;
    }

    // --- SETTERS ---

    public void setTownHallID(String townHallID) {
        this.townHallID = townHallID;
    }
}



