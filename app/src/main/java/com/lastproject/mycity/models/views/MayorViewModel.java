package com.lastproject.mycity.models.views;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lastproject.mycity.firebase.database.firestore.models.Mayor;
import com.lastproject.mycity.network.retrofit.models.townhall.TownHall;
import com.lastproject.mycity.repositories.EventDataRoomRepository;
import com.lastproject.mycity.repositories.MayorDataFireStoreRepository;
import com.lastproject.mycity.repositories.UserDataAuthenticationRepository;
import com.lastproject.mycity.repositories.UserDataFireStoreRepository;
import com.lastproject.mycity.room.models.Event;

import java.util.List;
import java.util.concurrent.Executor;

public class MayorViewModel extends ViewModel {

    // For Debug
    private static final String TAG = MayorViewModel.class.getSimpleName();

    // Repositories
    private final UserDataAuthenticationRepository userDataAuthenticationSource;
    private final UserDataFireStoreRepository userDataFireStoreSource;
    private final MayorDataFireStoreRepository mayorDataFireStoreSource;
    private final Executor executor;

    // DATA
    // - Current Mayor in the model
    private Mayor mayorInModel;

    public MayorViewModel(UserDataAuthenticationRepository userDataAuthenticationSource,
                          UserDataFireStoreRepository userDataFireStoreSource,
                          MayorDataFireStoreRepository mayorDataFireStoreSource,
                          Executor executor) {
        this.userDataAuthenticationSource = userDataAuthenticationSource;
        this.userDataFireStoreSource = userDataFireStoreSource;
        this.mayorDataFireStoreSource = mayorDataFireStoreSource;
        this.executor = executor;
    }

    // --- MAYOR IN MODEL ---
    //
    public Mayor getMayorInModel() {
        return mayorInModel;
    }
    public void setMayorInModel(Mayor mayorInModel) {this.mayorInModel = mayorInModel;}

    // --- FIRE BASE : AUTHENTICATION ---
    //
    // Get the Current User Connected
    public FirebaseUser getCurrentUser(){
        return this.userDataAuthenticationSource.getCurrentUser();
    }

    // Check if current user is logged in
    public Boolean isCurrentUserLogged(){
        return this.userDataAuthenticationSource.isCurrentUserLogged();
    }

    // --- FIRE BASE : FIRE STORE ---
    //
    // Get a user in Fire Store
    public Task<DocumentSnapshot> getUser(String uid){
        return this.userDataFireStoreSource.getUser(uid);
    }

    // Get a Mayor in Fire Store (by UserID)
    public Task<QuerySnapshot> getMayorByUserID(String userID){
        Log.d(TAG, "getMayorByUserID: ");

        // retrieve  query results
        return mayorDataFireStoreSource.getMayorByUserID(userID).get();
    }

    // Get Town Hall of the Mayor
    public void getMayorTownHall(String userID) {
        Log.d(TAG, "getMayorTownHall: ");

        this.getMayorByUserID(userID)
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.d(TAG, "onComplete getting documents: ");
                        if (task.isSuccessful()) {
                            if (task.getResult().size() != 0) {
                                Log.d(TAG, "task.getResult().size() != 0");

                                // browse the responses returned by the request
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                }
                            } else {
                                Log.d(TAG, "task.getResult().size() == 0");
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
