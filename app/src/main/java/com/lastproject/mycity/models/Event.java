package com.lastproject.mycity.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.lastproject.mycity.firebase.database.firestore.models.EventFireStore;

@Entity public class Event extends EventFireStore {

    @PrimaryKey @NonNull private String eventID;
    private String userID;
    private Boolean published;
    private Boolean atCreate;
    private Boolean atUpdate;

    // Blank constructor necessary for use with FireBase
    public Event() {}

    public Event(@NonNull String eventID, String userID, EventFireStore eventFireStore) {
        super(  eventFireStore.getInseeID(),
                eventFireStore.getTitle(),
                eventFireStore.getDescription(),
                eventFireStore.getPhotos(),
                eventFireStore.getAddress(),
                eventFireStore.getLocation(),
                eventFireStore.getStartDate(),
                eventFireStore.getEndDate(),
                eventFireStore.isCanceled()
        );
        this.eventID = eventID;
        this.userID = userID;
        this.published = true;
        this.atCreate = false;
        this.atUpdate = false;
    }

    // Display Class

    @Override
    public String toString() {
        return "Event{" +
                "eventID='" + eventID + '\'' +
                ", userID='" + userID + '\'' +
                ", published=" + published +
                ", create=" + atCreate +
                ", update=" + atUpdate +
                ", update=" + super.getPhotos() +
                '}';
    }

    // --- GETTERS ---

    @NonNull
    public String getEventID() {
        return eventID;
    }

    public String getUserID() {
        return userID;
    }

    public Boolean isPublished() {
        return published;
    }

    public Boolean isAtCreate() {
        return atCreate;
    }

    public Boolean isAtUpdate() {
        return atUpdate;
    }

    // --- SETTERS ---

    public void setEventID(@NonNull String eventID) {
        this.eventID = eventID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public void setAtCreate(Boolean atCreate) {
        this.atCreate = atCreate;
    }

    public void setAtUpdate(Boolean atUpdate) {
        this.atUpdate = atUpdate;
    }
}
