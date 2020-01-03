package com.lastproject.mycity.firebase.database.firestore.helpers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.lastproject.mycity.models.Event;

public class EventHelper {

    // For Debug
    private static final String TAG = EventHelper.class.getSimpleName();

    private static final String COLLECTION_NAME = "events";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getEventsCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<DocumentReference> createEvent(Event event) {
        return EventHelper.getEventsCollection()
                .add(event);
    }

    // --- GET ---

    // Get All Events
    public static Task<QuerySnapshot> getEvents(){
        return EventHelper.getEventsCollection().get();
    }

    // Get Event by eventID
    public static Task<DocumentSnapshot> getEventByEventID(String eventID){
        return EventHelper.getEventsCollection().document(eventID).get();
    }

    // Get all Events by InseeID (TownHall identification )
    public static Query getEventsByInseeID(String inseeID){
        return EventHelper.getEventsCollection().whereEqualTo("inseeID",inseeID);
    }

    // --- UPDATE ---

    // Update Event
    public static Task<Void> updateEvent(Event event) {
        return EventHelper.getEventsCollection().document(event.getEventID()).set(event);
    }

    // --- DELETE ---

    public static Task<Void> deleteEvent(String eventID) {
        return EventHelper.getEventsCollection().document(eventID).delete();
    }
}
