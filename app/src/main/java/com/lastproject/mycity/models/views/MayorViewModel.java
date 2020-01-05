package com.lastproject.mycity.models.views;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lastproject.mycity.models.Mayor;
import com.lastproject.mycity.firebase.database.firestore.models.TownHall;
import com.lastproject.mycity.network.retrofit.models.Insee;
import com.lastproject.mycity.repositories.CurrentMayorDataRepository;
import com.lastproject.mycity.repositories.CurrentTownHallDataRepository;
import com.lastproject.mycity.repositories.MayorDataFireStoreRepository;
import com.lastproject.mycity.repositories.TownHallDataFireStoreRepository;
import com.lastproject.mycity.repositories.UserDataAuthenticationRepository;
import com.lastproject.mycity.repositories.UserDataFireStoreRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class MayorViewModel extends ViewModel {

    // For Debug
    private static final String TAG = MayorViewModel.class.getSimpleName();

    // Repositories
    private final UserDataAuthenticationRepository userDataAuthenticationSource;
    private final UserDataFireStoreRepository userDataFireStoreSource;
    private final MayorDataFireStoreRepository mayorDataFireStoreSource;
    private final TownHallDataFireStoreRepository townHallDataFireStoreSource;
    private final Executor executor;

    // DATA
    // - Insee List found in First Connexion Fragment
    private List<Insee> inseeList;

    public MayorViewModel(UserDataAuthenticationRepository userDataAuthenticationSource,
                          UserDataFireStoreRepository userDataFireStoreSource,
                          MayorDataFireStoreRepository mayorDataFireStoreSource,
                          TownHallDataFireStoreRepository townHallDataFireStoreSource,
                          Executor executor) {
        this.userDataAuthenticationSource = userDataAuthenticationSource;
        this.userDataFireStoreSource = userDataFireStoreSource;
        this.mayorDataFireStoreSource = mayorDataFireStoreSource;
        this.townHallDataFireStoreSource = townHallDataFireStoreSource;
        this.executor = executor;
    }

    // --- DATA Methods ---
    //
    public List<Insee> getInseeList() {return inseeList;}
    public void setInseeList(List<Insee> inseeList) {this.inseeList = inseeList;}


    // --- CURRENT MAYOR ---
    //
    public Mayor getCurrentMayor() {return CurrentMayorDataRepository.getInstance().getCurrentMayor();}
    public void setCurrentMayor(Mayor mayor) {CurrentMayorDataRepository.getInstance().setCurrentMayor(mayor);}


    // --- CURRENT TOWN HALL ---
    //
    public LiveData<TownHall> getCurrentTownHall() {return CurrentTownHallDataRepository.getInstance().getCurrentTownHall();}
    public void setCurrentTownHall(TownHall townHall) {CurrentTownHallDataRepository.getInstance().setCurrentTownHall(townHall);}


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
    // // USERS //
    // Get a user in Fire Store
    public Task<DocumentSnapshot> getUser(String uid){
        return this.userDataFireStoreSource.getUser(uid);
    }

    // // MAYORS //
    // Get a Mayor in Fire Store (by UserID)
    public Task<QuerySnapshot> getMayorByUserID(String userID){
        Log.d(TAG, "getMayorByUserID: ");

        // retrieve  query results
        return mayorDataFireStoreSource.getMayorByUserID(userID).get();
    }

    // Update townHallID Mayor in Fire Store
    public Task<Void> updateMayorTownHallID(String mayorID, String townHallID){
        return mayorDataFireStoreSource.updateMayorTownHallID(mayorID,townHallID);
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
    // // TOWN HALL //
    public Task<DocumentReference> createTownHall(TownHall townHall) {
        return townHallDataFireStoreSource.createTownHall(townHall);
    }
    // Get a Town Hall in Fire Store
    public Task<DocumentSnapshot> getTownHallByTownHallID(String townHallID) {
        return townHallDataFireStoreSource.getTownHallByTownHallID(townHallID);
    }
}
