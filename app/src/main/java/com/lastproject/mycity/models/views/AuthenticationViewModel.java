package com.lastproject.mycity.models.views;

import android.util.Log;

import androidx.annotation.NonNull;
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
import com.lastproject.mycity.firebase.database.firestore.models.MayorFireStore;
import com.lastproject.mycity.firebase.database.firestore.models.User;
import com.lastproject.mycity.models.Mayor;
import com.lastproject.mycity.repositories.CurrentMayorDataRepository;
import com.lastproject.mycity.repositories.MayorDataFireStoreRepository;
import com.lastproject.mycity.repositories.UserDataAuthenticationRepository;
import com.lastproject.mycity.repositories.UserDataFireStoreRepository;

public class AuthenticationViewModel extends ViewModel {

    // For Debug
    private static final String TAG = AuthenticationViewModel.class.getSimpleName();

    // Repositories
    private final UserDataAuthenticationRepository userDataAuthenticationSource;
    private final UserDataFireStoreRepository userDataFireStoreSource;
    private final MayorDataFireStoreRepository mayorDataFireStoreSource;

    // Data
    // - Registration Status
    private MutableLiveData<RegistrationStatus> registrationStatus = new MutableLiveData<>();
    public enum RegistrationStatus {
        REGISTRATION_OK,
        REGISTRATION_ERROR,
        REGISTRATION_MAYOR_UPDATE_USER_FAILED,
        REGISTRATION_MAYOR_WRONG_CODE_ID
    }
    // - Current User in the model
    private User userInModel;

    public AuthenticationViewModel(UserDataAuthenticationRepository userDataAuthenticationSource,
                                   UserDataFireStoreRepository userDataFireStoreSource,
                                   MayorDataFireStoreRepository mayorDataFireStoreSource) {
        this.userDataAuthenticationSource = userDataAuthenticationSource;
        this.userDataFireStoreSource = userDataFireStoreSource;
        this.mayorDataFireStoreSource = mayorDataFireStoreSource;
    }

    // --- USER IN MODEL ---
    //
    public User getUserInModel() {
        return userInModel;
    }

    public void setUserInModel(User userInModel) {
        this.userInModel = userInModel;
    }

    // --- REGISTRATION STATUS ---
    //
    public MutableLiveData<RegistrationStatus> getRegistrationStatus() {
        return registrationStatus;
    }

    // --- CURRENT MAYOR ---
    //
    public void setCurrentMayor(Mayor mayor) {CurrentMayorDataRepository.getInstance().setCurrentMayor(mayor);}

    // --- FIRE BASE : AUTHENTICATION ---
    //
    // Get the Current User Connected
    public FirebaseUser getCurrentUser(){
        return this.userDataAuthenticationSource.getCurrentUser();
    }

    // --- FIRE BASE : FIRE STORE ---
    //
    // Get a user in Fire Store
    public Task<DocumentSnapshot> getUserInFireStore(String uid){
        return this.userDataFireStoreSource.getUser(uid);
    }

    // Create user in Fire Store
    public void createUser(boolean isMayor) {
        Log.d(TAG, "createUser: ");

        // Create new User
        String uri;
        if (this.getCurrentUser().getPhotoUrl() != null)
            uri =  this.getCurrentUser().getPhotoUrl().toString();
        else uri = "";
        User user = new User(   this.getCurrentUser().getUid(),
                                this.getCurrentUser().getDisplayName(),
                                isMayor,
                                uri,
                                this.getCurrentUser().getEmail(),
                                this.getCurrentUser().getPhoneNumber());

        // -- Save It In Fire Store
        this.userDataFireStoreSource.createUser(user);
        // -- Save It In Model
        this.setUserInModel(user);
    }

    // Get a Mayor in Fire Store (by CodeID)
    public Task<QuerySnapshot> getMayorByCodeID(String codeID){
        Log.d(TAG, "getMayorByCodeID: ");

        // retrieve  query results
        return mayorDataFireStoreSource.getMayorByCodeID(codeID).get();
    }

    // Get a Mayor in Fire Store (by UserID)
    public Task<QuerySnapshot> getMayorByUserID(String userID){
        Log.d(TAG, "getMayorByUserID: ");

        // retrieve  query results
        return mayorDataFireStoreSource.getMayorByUserID(userID).get();
    }

    // Update userID of a Mayor
    public Task<Void> updateMayorUserID(String mayorID, String userID){
        Log.d(TAG, "updateMayorUserID: ");

        return mayorDataFireStoreSource.updateMayorUserID(mayorID,userID);
    }

    // Update Mayor User by CodeID
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

                                    // Get MayorFireStore Object
                                    MayorFireStore mayorFireStore = document.toObject(MayorFireStore.class);

                                    // If the code corresponds to the mayor document being read,
                                    // then we update userID of the Mayor document
                                    // As a precaution, only the first document found will be updated, 
                                    // the others will be ignored (break instruction)
                                    Log.d(TAG, "onComplete: document.get("+codeID+") = "+mayorFireStore.getCodeID());
                                    if (mayorFireStore.getCodeID().equals(codeID)) {
                                        Log.d(TAG, "onComplete: UPDATE MAYOR with UserID");
                                        // UPDATE Mayor
                                        updateMayorUserID(document.getId(), userID)
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.d(TAG, "Error getting documents: " + e.getMessage());
                                                        registrationStatus.setValue(RegistrationStatus.REGISTRATION_MAYOR_UPDATE_USER_FAILED);
                                                    }
                                                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "onSuccess getting documents: ");

                                                // save Current Mayor
                                                Mayor mayor = new Mayor(document.getId(),mayorFireStore);
                                                setCurrentMayor(mayor);

                                                // create the user in fireStore as mayor
                                                createUser(true);

                                                registrationStatus.setValue(RegistrationStatus.REGISTRATION_OK);
                                            }
                                        });
                                        break;
                                    }
                                }
                            }
                            else {
                                Log.d(TAG, "task.getResult().size() == 0");
                                registrationStatus.setValue(RegistrationStatus.REGISTRATION_MAYOR_WRONG_CODE_ID);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            registrationStatus.setValue(RegistrationStatus.REGISTRATION_ERROR);
                        }
                    }
                });
    }
}
