package com.lastproject.mycity.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lastproject.mycity.room.dao.EventDao;

public class CurrentEventIDDataRepository {

    public final EventDao mEventDao;

    private static CurrentEventIDDataRepository sInstance;

    // CurrentEventID
    private MutableLiveData<String> mCurrentEventID = new MutableLiveData<>();

    public static CurrentEventIDDataRepository getInstance() {
        if (sInstance == null) {
            sInstance = new CurrentEventIDDataRepository(null);
        }
        return sInstance;
    }

    public CurrentEventIDDataRepository(EventDao eventDao) {
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
