package com.lastproject.mycity.firestore.models;

import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;

public class Event {

    private long id;
    private String name;
    private String description;
    private ArrayList<String> photos;
    private ArrayList<String> address;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private long cityId;

    // Blank constructor necessary for use with FireBase
    public Event() { }

    public Event(String name, String description, ArrayList<String> photos, ArrayList<String> address, LocalDateTime startDate, LocalDateTime endDate) {
        this.name = name;
        this.description = description;
        this.photos = photos;
        this.address = address;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // --- GETTERS ---

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public long getCityId() {
        return cityId;
    }

    // --- SETTERS ---

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }
}
