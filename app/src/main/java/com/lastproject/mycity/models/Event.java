package com.lastproject.mycity.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.lastproject.mycity.firebase.database.firestore.models.EventFireStore;

@Entity public class Event extends EventFireStore {

    @PrimaryKey @NonNull private String eventID;
    private Boolean published;

    // Blank constructor necessary for use with FireBase
    public Event() { }

    public Event(@NonNull String eventID, Boolean published, EventFireStore eventFireStore) {
        super(  eventFireStore.getInseeID(),
                eventFireStore.getTitle(),
                eventFireStore.getDescription(),
                eventFireStore.getPhotos(),
                eventFireStore.getAddress(),
                eventFireStore.getLocation(),
                eventFireStore.getStartDate(),
                eventFireStore.getEndDate(),
                eventFireStore.isCanceled());
                this.eventID = eventID;
                this.published = published;
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

    // Display Class

    @Override
    public String toString() {
        return "Event{" +
                "eventID='" + eventID + '\'' +
                ", published=" + published +
                ", " + getEventFireStore() +
                '}';
    }


    // --- GETTERS ---

    @NonNull
    public String getEventID() {
        return eventID;
    }

    public Boolean isPublished() {
        return published;
    }

    // --- SETTERS ---

    public void setEventID(@NonNull String eventID) {
        this.eventID = eventID;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }
}
