package com.lastproject.mycity.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lastproject.mycity.firebase.database.firestore.models.TownHall;

public class CurrentTownHallDataRepository {

    private static CurrentTownHallDataRepository sInstance;

    // CurrentTownHall
    private MutableLiveData<TownHall> mCurrentTownHall;

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
    public LiveData<TownHall> getCurrentTownHall(){
        return mCurrentTownHall;
    }

    // --- SET ---
    // ===========

    // Set the CurrentTownHall
    public void setCurrentTownHall(TownHall currentTownHall) {
        mCurrentTownHall.setValue(currentTownHall);
    }
}
