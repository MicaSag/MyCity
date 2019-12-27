package com.lastproject.mycity.room.models;

import android.content.ContentValues;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;
import com.lastproject.mycity.utils.Converters;

import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;

/**
 * Created by Michaël SAGOT on 11/12/2019.
 */

@Entity public class Event {

    @PrimaryKey @NonNull private String eventId;
    private String title;
    private String description;
    private ArrayList<String> photos;
    private ArrayList<String> address;
    private LatLng location;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // --- CONSTRUCTORS ---

    public Event() {
    }

    public Event(String eventId, String title, String description, ArrayList<String> photos, ArrayList<String> address,
                 LatLng location, LocalDateTime startDate, LocalDateTime endDate) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.photos = photos;
        this.address = address;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // --- GETTER ---


    public String getEventId() {
        return eventId;
    }

    public String getTitle() { return title;
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

    public LatLng getLocation() {
        return location;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

        // --- SETTER ---


    public void setEventId(String eventId) {
        this.eventId = eventId;
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

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    // --- toSTRING ---

    @Override
    public String toString() {
        return "Event{" +
                "eventId='" + eventId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", photos=" + photos +
                ", address=" + address +
                ", location=" + location +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    // --- UTILS ---

    public static Event fromContentValues(ContentValues values) {

        final Event event = new Event();
        if (values.containsKey("eventId")) event.setEventId(values.getAsString("eventId"));
        if (values.containsKey("title")) event.setTitle(values.getAsString("title"));
        if (values.containsKey("description")) event.setDescription(values.getAsString("description"));
        if (values.containsKey("photos")) event.setPhotos(Converters.fromString(values.getAsString("photos")));
        if (values.containsKey("address")) event.setAddress(Converters.fromString(values.getAsString("address")));
        if (values.containsKey("location")) event.setLocation(Converters.fromStringToLatLng(values.getAsString("location")));
        if (values.containsKey("startDate")) event.setStartDate(Converters.fromTimestamp(values.getAsLong("startDate")));
        if (values.containsKey("endDate")) event.setEndDate(Converters.fromTimestamp(values.getAsLong("endDate")));

        return event;
    }
}
