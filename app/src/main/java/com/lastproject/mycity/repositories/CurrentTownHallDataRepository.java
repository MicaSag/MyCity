package com.lastproject.mycity.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lastproject.mycity.firebase.database.firestore.models.TownHallFireStore;

public class CurrentTownHallDataRepository {

    private static CurrentTownHallDataRepository sInstance;

    // CurrentTownHall
    private MutableLiveData<TownHallFireStore> mCurrentTownHall;

    public static CurrentTownHallDataRepository getInstance() {
        if (sInstance == null) {
            sInstance = new CurrentTownHallDataRepository();
        }
        return sInstance;
    }

    public CurrentTownHallDataRepository() {

        // The default currentMayor
        mCurrentTownHall = new MutableLiveData<>();
    }

    // --- GET ---
    // ===========

    // Return the CurrentTownHall
    public LiveData<TownHallFireStore> getCurrentTownHall(){
        return mCurrentTownHall;
    }

    // --- SET ---
    // ===========

    // Set the CurrentTownHall
    public void setCurrentTownHall(TownHallFireStore currentTownHallFireStore) {
        mCurrentTownHall.setValue(currentTownHallFireStore);
    }
}
