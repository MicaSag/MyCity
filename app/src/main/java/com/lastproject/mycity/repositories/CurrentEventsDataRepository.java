package com.lastproject.mycity.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lastproject.mycity.models.Event;
import com.lastproject.mycity.room.dao.EventDao;

import java.util.List;

public class CurrentEventsDataRepository {

    private static CurrentEventsDataRepository sInstance;

    // CurrentEvents
    private MutableLiveData<List<Event>> mCurrentEvents;

    public static CurrentEventsDataRepository getInstance() {
        if (sInstance == null) {
            sInstance = new CurrentEventsDataRepository();
        }
        return sInstance;
    }

    public CurrentEventsDataRepository() {

        // Init MutableLiveData
        mCurrentEvents = new MutableLiveData<>();
    }
    // --- GET ---
    // ===========

    // Return the CurrentEvents
    public LiveData<List<Event>> getCurrentEvents(){
        return mCurrentEvents;
    }

    // --- SET ---
    // ===========

    // Set the CurrentEvents
    public void setCurrentEvents(List<Event> currentEvents) {
        mCurrentEvents.setValue(currentEvents);
    }
}
