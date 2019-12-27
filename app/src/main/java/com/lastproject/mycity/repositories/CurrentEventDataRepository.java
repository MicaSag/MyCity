package com.lastproject.mycity.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lastproject.mycity.room.dao.EventDao;

public class CurrentEventDataRepository {

    public final EventDao mEventDao;

    private static CurrentEventDataRepository sInstance;

    // CurrentEventId
    private MutableLiveData<String> mCurrentEventId = new MutableLiveData<>();

    public static CurrentEventDataRepository getInstance() {
        if (sInstance == null) {
            sInstance = new CurrentEventDataRepository(null);
        }
        return sInstance;
    }

    public CurrentEventDataRepository(EventDao eventDao) {
        mEventDao = eventDao;

        // The default CurrentEventId is number 1
        mCurrentEventId.setValue("new event 1");
    }
    // --- GET ---
    // ===========

    // Return the CurrentEventId
    public LiveData<String> getCurrentEventId(){
        return mCurrentEventId;
    }

    // --- SET ---
    // ===========

    // Set the CurrentEventId
    public void setCurrentEventId(String currentEventId) {
        mCurrentEventId.setValue(currentEventId);
    }
}
