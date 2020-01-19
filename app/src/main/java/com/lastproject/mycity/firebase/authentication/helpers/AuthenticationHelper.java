package com.lastproject.mycity.firebase.authentication.helpers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationHelper {

    // --- GET ---

    // Return Current UserFireStore
    public FirebaseUser getCurrentUser(){
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    // Check if current user is logged in
    public Boolean isCurrentUserLogged(){
        return (this.getCurrentUser() != null);
    }
}
