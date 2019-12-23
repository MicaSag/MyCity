package com.lastproject.mycity.repositories;

import com.google.firebase.auth.FirebaseUser;
import com.lastproject.mycity.firebase.authentication.helpers.AuthenticationHelper;


public class UserDataAuthenticationRepository {

    private final AuthenticationHelper authenticationHelper;

    public UserDataAuthenticationRepository(AuthenticationHelper authenticationHelper) { this.authenticationHelper = authenticationHelper; }

    // --- GET ---

    // Return Current User
    public FirebaseUser getCurrentUser(){
        return authenticationHelper.getCurrentUser();
    }

    // Check if current user is logged in
    public Boolean isCurrentUserLogged(){
        return (authenticationHelper.getCurrentUser() != null);
    }

}
