package com.lastproject.mycity.models.views;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lastproject.mycity.firebase.database.firestore.models.Mayor;
import com.lastproject.mycity.firebase.database.firestore.models.TownHall;
import com.lastproject.mycity.models.Event;
import com.lastproject.mycity.repositories.CurrentEventDataRepository;
import com.lastproject.mycity.repositories.CurrentEventsDataRepository;
import com.lastproject.mycity.repositories.CurrentMayorDataRepository;
import com.lastproject.mycity.repositories.CurrentTownHallDataRepository;
import com.lastproject.mycity.repositories.EventDataFireStoreRepository;
import com.lastproject.mycity.repositories.EventDataRoomRepository;
import com.lastproject.mycity.room.database.MyCityDatabase;

import java.util.ArrayList;
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
        Query fireStoreEvents = getEventsFireStore(getCurrentTownHall().getValue().getInseeID());

        // get Events list in FireStore
        fireStoreEvents.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                @Nullable FirebaseFirestoreException e) {
                Log.d(TAG, "onEvent: ");
                if (e != null) {
                    Log.w(TAG, "listen:error", e);
                    return;
                }

                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    Event event = document.toObject(Event.class);
                    Log.d(TAG, "onEvent: event Fire Base = " + event);
                    // Create Event in Room
                    createEventRoom(event);
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

    // --- CURRENT EVENTS ---
    //
    public LiveData<String> getCurrentEventID() {
        return CurrentEventDataRepository.getInstance().getCurrentEventID();
    }

    public void setCurrentEventID(String eventID) {
        CurrentEventDataRepository.getInstance().setCurrentEventID(eventID);
    }


    // --- FIRE STORE  ---
    // =============
    // Get the whole list of events in FireStore
    public Query getEventsFireStore(String inseeID) {
        return eventDataFireStoreSource.getEventsByInseeID(inseeID);
    }

    // --- ROOM  ---
    // =============
    // Get the whole list of events in Room
    public LiveData<List<Event>> getEventsRoom() {
        return eventDataRoomSource.getEvents();
    }

    // Create Event in Room
    public void createEventRoom(Event event) {
        executor.execute(() -> {
            eventDataRoomSource.createEvent(event);
        });
    }
}


