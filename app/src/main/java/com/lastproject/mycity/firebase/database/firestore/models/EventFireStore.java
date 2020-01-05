package com.lastproject.mycity.firebase.database.firestore.models;

import androidx.room.Entity;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;

@Entity public class EventFireStore {

    private String inseeID;
    private String title;
    private String description;
    private ArrayList<String> photos;
    private ArrayList<String> address;
    private GeoPoint location;
    private Long startDate;             // LocalDateTime in Long Format
    private Long endDate;               // LocalDateTime in Long Format
    private boolean canceled;

    // Blank constructor necessary for use with FireBase
    public EventFireStore() { }

    public EventFireStore(String inseeID, String title, String description, ArrayList<String> photos,
                          ArrayList<String> address, GeoPoint location, Long startDate, Long endDate,
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

    public Long getStartDate() {
        return startDate;
    }

    public Long getEndDate() {
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

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
