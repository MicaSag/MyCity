package com.lastproject.mycity.room.models;

import android.content.ContentValues;

import com.google.type.LatLng;
import com.lastproject.mycity.utils.Converters;

import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;

/**
 * Created by Michaël SAGOT on 11/12/2019.
 */

public class Event {

    private long id;
    private String title;
    private String description;
    private ArrayList<String> photos;
    private LatLng location;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // --- CONSTRUCTORS ---

    public Event() {
    }

    public Event(long id, String title, String description, ArrayList<String> photos, LatLng location, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.photos = photos;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // --- GETTER ---

    public long getId() {
        return id;
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

    public void setId(long id) {
        this.id = id;
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
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", photos=" + photos +
                ", location=" + location +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    // --- UTILS ---

    public static Event fromContentValues(ContentValues values) {

        final Event event = new Event();
        if (values.containsKey("id")) event.setId(values.getAsLong("id"));
        if (values.containsKey("title")) event.setTitle(values.getAsString("title"));
        if (values.containsKey("description")) event.setDescription(values.getAsString("description"));
        if (values.containsKey("photos")) event.setPhotos(Converters.fromString(values.getAsString("photos")));
        if (values.containsKey("location")) event.setLocation(Converters.fromStringToLatLng(values.getAsString("location")));
        if (values.containsKey("startDate")) event.setStartDate(Converters.fromTimestamp(values.getAsLong("startDate")));
        if (values.containsKey("endDate")) event.setStartDate(Converters.fromTimestamp(values.getAsLong("endDate")));

        return event;
    }
}
