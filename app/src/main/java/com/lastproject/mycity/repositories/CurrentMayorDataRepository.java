package com.lastproject.mycity.repositories;

import com.lastproject.mycity.firebase.database.firestore.models.Mayor;

public class CurrentMayorDataRepository {

    private static CurrentMayorDataRepository sInstance;

    // CurrentMayor
    private Mayor mCurrentMayor;

    public static CurrentMayorDataRepository getInstance() {
        if (sInstance == null) {
            sInstance = new CurrentMayorDataRepository();
        }
        return sInstance;
    }

    public CurrentMayorDataRepository() {

        // The default currentMayor
        mCurrentMayor = null;
    }

    // --- GET ---
    // ===========

    // Return the CurrentMayor
    public Mayor getCurrentMayor(){
        return mCurrentMayor;
    }

    // --- SET ---
    // ===========

    // Set the CurrentMayor
    public void setCurrentMayor(Mayor currentMayor) {
        mCurrentMayor = currentMayor;
    }
}
