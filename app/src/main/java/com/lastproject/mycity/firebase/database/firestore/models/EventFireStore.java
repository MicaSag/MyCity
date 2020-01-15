package com.lastproject.mycity.firebase.database.firestore.models;

import androidx.room.Entity;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;

@Entity public class EventFireStore {

    private String inseeID;             // Insee the city where the event takes place
    private String title;               // Title of the event
    private String description;         // Description of the event
    private ArrayList<String> photos;   // Photos list of the event
    private ArrayList<String> address;  // Address where the event takes place
    private GeoPoint location;          // GeoPoint where the event takes place
    private String startDate;           // Date in format JJ/MM/SSAA
    private String endDate;             // Date in format JJ/MM/SSAA
    private boolean canceled;           // Is True  : event = canceled
                                        // Is false : event = good

    // Blank constructor necessary for use with FireBase
    public EventFireStore() { }

    public EventFireStore(String inseeID, String title, String description, ArrayList<String> photos,
                          ArrayList<String> address, GeoPoint location, String startDate, String endDate,
                          boolean canceled) {
        this.inseeID = inseeID;
        this.title = title;
        this.description = description;
        this.photos = photos;
        this.address = address;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.canceled = canceled;
    }

    @Override
    public String toString() {
        return "EventFireStore{" +
                "inseeID='" + inseeID + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", photos=" + photos +
                ", address=" + address +
                ", location=" + location +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", canceled=" + canceled +
                '}';
    }

    // --- GETTERS ---

    public String getInseeID() {
        return inseeID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getPhotos() {
        return photos;
    }

    public ArrayList<String> getAddress() {
        return address;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public boolean isCanceled() {
        return canceled;
    }

    // --- SETTERS ---

    public void setInseeID(String inseeID) {
        this.inseeID = inseeID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }

    public void setAddress(ArrayList<String> address) {
        this.address = address;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
