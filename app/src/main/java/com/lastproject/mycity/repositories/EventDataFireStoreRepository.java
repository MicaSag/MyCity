package com.lastproject.mycity.repositories;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.lastproject.mycity.firebase.database.firestore.helpers.EventHelper;
import com.lastproject.mycity.models.Event;

public class EventDataFireStoreRepository {

    // --- COLLECTION REFERENCE ---

    public CollectionReference getEventsCollection(){
        return EventHelper.getEventsCollection();
    }

    public EventDataFireStoreRepository() {}

    // --- CREATE ---

    public static Task<DocumentReference> createEvent(Event event) {
        return EventHelper.createEvent(event);
    }

    // --- GET ---

    // Get All Events
    public Task<QuerySnapshot> getEvents() {
        return EventHelper.getEvents();
    }



    // Get Event by eventID
    public Task<DocumentSnapshot> getEventByEventID(String eventID){
        return EventHelper.getEventByEventID(eventID);
    }

    // Get All Events by townHallID
    public Query getEventsByInseeID(String inseeID){
        return EventHelper.getEventsByInseeID(inseeID);
    }

    // --- UPDATE ---

    // Update Event
    public Task<Void> updateEvent(Event event) {
        return EventHelper.updateEvent(event);
    }

    // --- DELETE ---

    public Task<Void> deleteEvent(String eventID) {
        return EventHelper.deleteEvent(eventID);
    }
}
