package com.lastproject.mycity.models.views;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lastproject.mycity.models.Event;
import com.lastproject.mycity.repositories.EventDataRoomRepository;
import com.lastproject.mycity.repositories.MayorDataFireStoreRepository;
import com.lastproject.mycity.repositories.UserDataAuthenticationRepository;
import com.lastproject.mycity.repositories.UserDataFireStoreRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class CitizenViewModel extends ViewModel {

    // For Debug
    private static final String TAG = CitizenViewModel.class.getSimpleName();

    // Repositories
    private final UserDataAuthenticationRepository userDataAuthenticationSource;
    private final UserDataFireStoreRepository userDataFireStoreSource;
    private final MayorDataFireStoreRepository mayorDataFireStoreSource;
    private final EventDataRoomRepository eventDataRoomSource;
    private final Executor executor;

    // DATA
    // -- Registration Status
    private MutableLiveData<RegistrationStatus> registrationStatus = new MutableLiveData<>();
    public enum RegistrationStatus {
        REGISTRATION_OK,
        REGISTRATION_ERROR,
        REGISTRATION_MAYOR_UPDATE_USER_FAILED,
        REGISTRATION_MAYOR_WRONG_CODE_ID
    }
    // -- Current Events List
    private MediatorLiveData<List<Event>> currentEvents = new MediatorLiveData<>();

    public CitizenViewModel(UserDataAuthenticationRepository userDataAuthenticationSource,
                            UserDataFireStoreRepository userDataFireStoreSource,
                            MayorDataFireStoreRepository mayorDataFireStoreSource,
                            EventDataRoomRepository eventDataRoomSource,
                            Executor executor) {
        this.userDataAuthenticationSource = userDataAuthenticationSource;
        this.userDataFireStoreSource = userDataFireStoreSource;
        this.mayorDataFireStoreSource = mayorDataFireStoreSource;
        this.eventDataRoomSource = eventDataRoomSource;
        this.executor = executor;
    }

    // --- REGISTRATION STATUS ---
    // ===========================
    public LiveData<RegistrationStatus> getRegistrationStatus() {
        return registrationStatus;
    }

    // --- FIRE BASE : AUTHENTICATION ---
    // ==================================
    // Get the Current User Connected
    public FirebaseUser getCurrentUser(){
        return this.userDataAuthenticationSource.getCurrentUser();
    }

    // Check if current user is logged in
    public Boolean isCurrentUserLogged(){
        return this.userDataAuthenticationSource.isCurrentUserLogged();
    }

    // --- FIRE BASE : FIRE STORE ---
    // ==============================
    // Get a user in Fire Store
    public Task<DocumentSnapshot> getUser(String uid){
        return this.userDataFireStoreSource.getUser(uid);
    }

    // Get a Mayor in Fire Store (by CodeID)
    public Task<QuerySnapshot> getMayorByCodeID(String codeID){
        Log.d(TAG, "getMayorByCodeID: ");

        // retrieve  query results
        return mayorDataFireStoreSource.getMayorByCodeID(codeID).get();
    }

    // Update userID of a Mayor
    public Task<Void> updateMayorUserID(String mayorID, String userID){
        Log.d(TAG, "updateMayorUserID: ");

        return mayorDataFireStoreSource.updateMayorUserID(mayorID,userID);
    }

    // --- ROOM  ---
    // =============
    // Get the whole list of events in Room
    public LiveData<List<Event>> getEvents(){
        return eventDataRoomSource.getEvents();
    }
}
