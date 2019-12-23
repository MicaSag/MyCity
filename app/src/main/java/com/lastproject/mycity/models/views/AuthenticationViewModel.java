package com.lastproject.mycity.models.views;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
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
import com.lastproject.mycity.repositories.MayorDataFireStoreRepository;
import com.lastproject.mycity.repositories.UserDataAuthenticationRepository;
import com.lastproject.mycity.repositories.UserDataFireStoreRepository;

import java.util.concurrent.Executor;

public class AuthenticationViewModel extends ViewModel {

    // For Debug
    private static final String TAG = AuthenticationViewModel.class.getSimpleName();

    // Repositories
    private final UserDataAuthenticationRepository userDataAuthenticationSource;
    private final UserDataFireStoreRepository userDataFireStoreSource;
    private final MayorDataFireStoreRepository mayorDataFireStoreSource;
    private final Executor executor;

    // Data
    private MutableLiveData<RegistrationStatus> registrationStatus = new MutableLiveData<>();

    public enum RegistrationStatus {
        REGISTRATION_OK,
        REGISTRATION_ERROR
    }

    public AuthenticationViewModel(UserDataAuthenticationRepository userDataAuthenticationSource,
                                   UserDataFireStoreRepository userDataFireStoreSource,
                                   MayorDataFireStoreRepository mayorDataFireStoreSource,
                                   Executor executor) {
        this.userDataAuthenticationSource = userDataAuthenticationSource;
        this.userDataFireStoreSource = userDataFireStoreSource;
        this.mayorDataFireStoreSource = mayorDataFireStoreSource;
        this.executor = executor;
    }

    // --- REGISTRATION STATUS ---
    //
    public LiveData<RegistrationStatus> getRegistrationStatus() {
        return registrationStatus;
    }

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

    // Create user in Fire Store
    public void createUser(boolean isMayor) {
        Log.d(TAG, "createUser: ");

        String urlPicture = (this.getCurrentUser().getPhotoUrl() != null)
            ?this.getCurrentUser().getPhotoUrl().toString() :null;
        String userName = this.getCurrentUser().getDisplayName();
        String userID = this.getCurrentUser().getUid();
        String email = this.getCurrentUser().getEmail();
        String phoneNumber = this.getCurrentUser().getPhoneNumber();

        this.userDataFireStoreSource.createUser(userID,
                                                userName,
                                                isMayor,
                                                urlPicture,
                                                email,
                                                phoneNumber);

        registrationStatus.setValue(RegistrationStatus.REGISTRATION_OK);
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

    public void updateMayorUserByCodeID(String codeID, String userID){
        Log.d(TAG, "updateMayorUserByCodeID: ");

        this.getMayorByCodeID(codeID)
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.d(TAG, "onComplete getting documents: ");
                        if (task.isSuccessful()) {
                            if (task.getResult().size() != 0) {
                                Log.d(TAG, "task.getResult().size() != 0");
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());

                                    // UPDATE Mayor
                                    updateMayorUserID(document.getId(),userID)
                                            .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "Error getting documents: " + e.getMessage());
                                            registrationStatus.setValue(RegistrationStatus.REGISTRATION_ERROR);
                                        }
                                    })      .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "onSuccess getting documents: ");

                                            // create the user in fireStore as mayor
                                            createUser(true);
                                        }
                                    });
                                }
                            }
                            else {
                                Log.d(TAG, "task.getResult().size() == 0");
                                registrationStatus.setValue(RegistrationStatus.REGISTRATION_ERROR);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            registrationStatus.setValue(RegistrationStatus.REGISTRATION_ERROR);
                        }
                    }
                });
    }
}