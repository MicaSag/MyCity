package com.lastproject.mycity.firestore.models.views;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationViewModel extends ViewModel {

    // For debugging Mode
    private static final String TAG = AuthenticationViewModel.class.getSimpleName();

    public AuthenticationViewModel() {

    }

    // Return Current User
    public FirebaseUser getCurrentUser(){
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    // Check if current user is logged in
    public Boolean isCurrentUserLogged(){
        return (this.getCurrentUser() != null);
    }
}


