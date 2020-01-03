package com.lastproject.mycity.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lastproject.mycity.room.dao.EventDao;

public class CurrentEventDataRepository {

    public final EventDao mEventDao;

    private static CurrentEventDataRepository sInstance;

    // CurrentEventID
    private MutableLiveData<String> mCurrentEventID = new MutableLiveData<>();

    public static CurrentEventDataRepository getInstance() {
        if (sInstance == null) {
            sInstance = new CurrentEventDataRepository(null);
        }
        return sInstance;
    }

    public CurrentEventDataRepository(EventDao eventDao) {
        mEventDao = eventDao;

        // The default CurrentEventID is number 1
        mCurrentEventID.setValue("");
    }
    // --- GET ---
    // ===========

    // Return the CurrentEventID
    public LiveData<String> getCurrentEventID(){
        return mCurrentEventID;
    }

    // --- SET ---
    // ===========

    // Set the CurrentEventID
    public void setCurrentEventID(String currentEventID) {
        mCurrentEventID.setValue(currentEventID);
    }
}
