package com.lastproject.mycity.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;

@Entity public class Event {

    @PrimaryKey @NonNull private String eventID;
    private String inseeID;
    private String title;
    private String description;
    private ArrayList<String> photos;
    private ArrayList<String> address;
    private GeoPoint location;
    private Long startDate;             // LocalDateTime in Long Format
    private Long endDate;               // LocalDateTime in Long Format

    // Blank constructor necessary for use with FireBase
    public Event() { }

    public Event(@NonNull String eventID, String inseeID, String title, String description,
                 ArrayList<String> photos, ArrayList<String> address, GeoPoint location,
                 Long startDate, Long endDate) {
        this.eventID = eventID;
        this.inseeID = inseeID;
        this.title = title;
        this.description = description;
        this.photos = photos;
        this.address = address;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventID='" + eventID + '\'' +
                ", inseeID='" + inseeID + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", photos=" + photos +
                ", address=" + address +
                ", location=" + location +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

// --- GETTERS ---

    public String getEventID() {
        return eventID;
    }

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

    // --- SETTERS ---

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

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
}
