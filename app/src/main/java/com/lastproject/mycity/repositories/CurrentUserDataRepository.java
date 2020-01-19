package com.lastproject.mycity.repositories;

import com.lastproject.mycity.models.User;

public class CurrentUserDataRepository {

    private static CurrentUserDataRepository sInstance;

    // Current User
    private User mCurrentUser;

    public static CurrentUserDataRepository getInstance() {
        if (sInstance == null) {
            sInstance = new CurrentUserDataRepository();
        }
        return sInstance;
    }

    public CurrentUserDataRepository() {

        // The default current User
        mCurrentUser = null;
    }

    // --- GET ---
    // ===========

    // Return the Current User
    public User getCurrentUser(){
        return mCurrentUser;
    }

    // --- SET ---
    // ===========

    // Set the Current User
    public void setCurrentUser(User currentUser) {
        mCurrentUser = currentUser;
    }
}
