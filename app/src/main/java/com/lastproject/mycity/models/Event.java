package com.lastproject.mycity.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.lastproject.mycity.firebase.database.firestore.models.EventFireStore;

@Entity public class Event extends EventFireStore {

    @PrimaryKey @NonNull private String eventID;

    // Blank constructor necessary for use with FireBase
    public Event() { }

    public Event(@NonNull String eventID, EventFireStore eventFireStore) {
        super(eventFireStore.getInseeID(),
                eventFireStore.getTitle(),
                eventFireStore.getDescription(),
                eventFireStore.getPhotos(),
                eventFireStore.getAddress(),
                eventFireStore.getLocation(),
                eventFireStore.getStartDate(),
                eventFireStore.getEndDate(),
                eventFireStore.isCanceled());
        this.eventID = eventID;
    }

// --- GETTERS ---

    @NonNull
    public String getEventID() {
        return eventID;
    }


    public EventFireStore getEventFireStore() {
        return new EventFireStore(
                super.getInseeID(),
                super.getTitle(),
                super.getDescription(),
                super.getPhotos(),
                super.getAddress(),
                super.getLocation(),
                super.getStartDate(),
                super.getEndDate(),
                super.isCanceled());
    }

    // --- SETTERS ---

    public void setEventID(@NonNull String eventID) {
        this.eventID = eventID;
    }
}
