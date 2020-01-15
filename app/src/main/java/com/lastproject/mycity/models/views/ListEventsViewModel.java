package com.lastproject.mycity.models.views;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lastproject.mycity.firebase.database.firestore.models.EventFireStore;
import com.lastproject.mycity.models.Mayor;
import com.lastproject.mycity.firebase.database.firestore.models.TownHall;
import com.lastproject.mycity.models.Event;
import com.lastproject.mycity.repositories.CurrentEventIDDataRepository;
import com.lastproject.mycity.repositories.CurrentMayorDataRepository;
import com.lastproject.mycity.repositories.CurrentTownHallDataRepository;
import com.lastproject.mycity.repositories.EventDataFireStoreRepository;
import com.lastproject.mycity.repositories.EventDataRoomRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class ListEventsViewModel extends ViewModel {

    // For debugging Mode
    private static final String TAG = ListEventsViewModel.class.getSimpleName();

    // Repositories
    private final EventDataRoomRepository eventDataRoomSource;
    private final EventDataFireStoreRepository eventDataFireStoreSource;
    private final Executor executor;

    public ListEventsViewModel(EventDataRoomRepository eventDataRoomSource,
                               EventDataFireStoreRepository eventDataFireStoreSource,
                               Executor executor) {
        this.eventDataRoomSource = eventDataRoomSource;
        this.eventDataFireStoreSource = eventDataFireStoreSource;
        this.executor = executor;

        // Get the whole list of Fire Store Events
        Log.d(TAG, "onChanged: getCurrentTownHall = " + getCurrentTownHall().getValue());
        Query fireStoreEvents = getEventsInFireStoreByInseeID(getCurrentTownHall().getValue().getInseeID());

        // get Events in FireStore By InseeID
        // And add Listener on them
        fireStoreEvents.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                @Nullable FirebaseFirestoreException e) {
                Log.d(TAG, "onEvent: ");
                if (e != null) {
                    Log.w(TAG, "listen:error", e);
                    return;
                }

                // Feature to add
                // ==> Purge of published events present in the local Room database

                // Loop on all events found
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {

                    EventFireStore eventFireStore = document.toObject(EventFireStore.class);
                    Log.d(TAG, "onEvent: event Fire Base = " + eventFireStore);

                    // Find in room dataBase if the event is already present
                    LiveData<Event> lEvent = getEventByEventIDRoom(document.getId());

                    lEvent.observeForever(new Observer<Event>() {
                        @Override
                        public void onChanged(Event event) {

                            // Unsubscribe the Observer
                            lEvent.removeObserver(this);

                            // Prepare the event to be integrated into the room
                            Event eventRoom = new Event(document.getId(), true, eventFireStore);

                            // If the event exists
                            if (event != null) {
                                Log.d(TAG, "onChanged: event != null");
                                // If the event has published status
                                if (event.isPublished()) {
                                    Log.d(TAG, "onChanged: event.isPublished()");
                                    Log.d(TAG, "onChanged: Create Event in Room");
                                    // Create Event in Room
                                    createEventRoom(eventRoom);
                                }
                            } else {
                                Log.d(TAG, "onChanged: event == null");
                                Log.d(TAG, "onChanged: Create Event in Room");
                                // Create Event in Room
                                createEventRoom(eventRoom);
                            }

                        }
                    });
                }
            }
        });
    }

    // --- CURRENT MAYOR ---
    //
    public Mayor getCurrentMayor() {
        return CurrentMayorDataRepository.getInstance().getCurrentMayor();
    }

    public void setCurrentMayor(Mayor mayor) {
        CurrentMayorDataRepository.getInstance().setCurrentMayor(mayor);
    }

    // --- CURRENT TOWN HALL ---
    //
    public LiveData<TownHall> getCurrentTownHall() {
        return CurrentTownHallDataRepository.getInstance().getCurrentTownHall();
    }

    public void setCurrentTownHall(TownHall townHall) {
        CurrentTownHallDataRepository.getInstance().setCurrentTownHall(townHall);
    }

    // --- CURRENT EVENT SELECTED ---
    //
    public LiveData<String> getCurrentEventID() {
        return CurrentEventIDDataRepository.getInstance().getCurrentEventID();
    }

    public void setCurrentEventID(String eventID) {
        CurrentEventIDDataRepository.getInstance().setCurrentEventID(eventID);
    }


    // --- FIRE STORE  ---
    // =============
    // Get the whole list of events in FireStore
    public Query getEventsInFireStoreByInseeID(String inseeID) {
        return eventDataFireStoreSource.getEventsInFireStoreByInseeID(inseeID);
    }

    // --- ROOM  ---
    // =============
    // Get the whole list of events in Room
    public LiveData<List<Event>> getAllEventsRoom() {
        return eventDataRoomSource.getAllEvents();
    }

    // Get the whole list of events in Room by InseeID
    public LiveData<List<Event>> getAllEventsRoomByInseeID(String inseeID) {
        return eventDataRoomSource.getAllEventsByInseeID(inseeID);
    }

    // Get event in Room by eventID
    public LiveData<Event> getEventByEventIDRoom(String eventID) {
        return eventDataRoomSource.getEvent(eventID);
    }

    // Create Event in Room
    public void createEventRoom(Event event) {
        executor.execute(() -> {
            eventDataRoomSource.createEvent(event);
        });
    }
}


