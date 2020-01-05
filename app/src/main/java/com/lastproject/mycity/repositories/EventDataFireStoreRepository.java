package com.lastproject.mycity.repositories;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.lastproject.mycity.firebase.database.firestore.helpers.EventHelper;
import com.lastproject.mycity.firebase.database.firestore.models.EventFireStore;
import com.lastproject.mycity.models.Event;

public class EventDataFireStoreRepository {

    // --- COLLECTION REFERENCE ---

    public CollectionReference getEventsCollection(){
        return EventHelper.getEventsCollection();
    }

    public EventDataFireStoreRepository() {}

    // --- CREATE ---

    public Task<DocumentReference> createEventInFireStore(EventFireStore eventFireStore) {
        return EventHelper.createEvent(eventFireStore);
    }

    // --- GET ---

    // Get All Events
    public Task<QuerySnapshot> getEventsInFireStore() {
        return EventHelper.getEvents();
    }



    // Get Event by eventID
    public Task<DocumentSnapshot> getEventInFireStoreByEventID(String eventID){
        return EventHelper.getEventByEventID(eventID);
    }

    // Get All Events by townHallID
    public Query getEventsInFireStoreByInseeID(String inseeID){
        return EventHelper.getEventsByInseeID(inseeID);
    }

    // --- UPDATE ---

    // Update Event
    public Task<Void> updateEventInFireStore(Event event) {
        return EventHelper.updateEvent(event);
    }

    // --- DELETE ---

    public Task<Void> deleteEventInFireStore(String eventID) {
        return EventHelper.deleteEvent(eventID);
    }
}
