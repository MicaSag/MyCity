package com.lastproject.mycity.models.views;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lastproject.mycity.firebase.database.firestore.models.TownHallFireStore;
import com.lastproject.mycity.models.Mayor;
import com.lastproject.mycity.models.User;
import com.lastproject.mycity.network.retrofit.models.Insee;
import com.lastproject.mycity.repositories.CurrentMayorDataRepository;
import com.lastproject.mycity.repositories.CurrentTownHallDataRepository;
import com.lastproject.mycity.repositories.CurrentUserDataRepository;
import com.lastproject.mycity.repositories.MayorDataFireStoreRepository;
import com.lastproject.mycity.repositories.TownHallDataFireStoreRepository;
import com.lastproject.mycity.repositories.UserDataAuthenticationRepository;
import com.lastproject.mycity.repositories.UserDataFireStoreRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TownHallSelectionViewModel extends ViewModel {

    // For Debug
    private static final String TAG = TownHallSelectionViewModel.class.getSimpleName();

    // Repositories
    private final UserDataAuthenticationRepository userDataAuthenticationSource;
    private final UserDataFireStoreRepository userDataFireStoreSource;
    private final MayorDataFireStoreRepository mayorDataFireStoreSource;
    private final TownHallDataFireStoreRepository townHallDataFireStoreSource;
    private final Executor executor;

    // DATA
    // - Insee List found in First Connexion Fragment
    private List<Insee> inseeList;
    private String inseeSelected;

    // Data
    // --- Manage Actions ---
    private MutableLiveData<ViewAction> mViewAction = new MutableLiveData<>();
    public enum ViewAction {
        TOWN_HALL_NOT_FOUND,
        CALL_TOWN_HALL_ACTIVITY,
        FIRE_STORE_ERROR,
        FINISH_ACTIVITY
    }
    public LiveData<ViewAction> getViewAction() {
        return mViewAction;
    }
    public void setViewAction(ViewAction viewAction) {
        mViewAction.setValue(viewAction);
    }

    public TownHallSelectionViewModel(UserDataAuthenticationRepository userDataAuthenticationSource,
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
    public String getInseeSelected() {return inseeSelected;}
    public void setInseeSelected(String inseeSelected) {this.inseeSelected = inseeSelected;}

    // --- CURRENT USER ---
    //
    public User getCurrentUser() {return CurrentUserDataRepository.getInstance().getCurrentUser();}
    public void setCurrentUser(User user) {CurrentUserDataRepository.getInstance().setCurrentUser(user);}

    // --- CURRENT MAYOR ---
    //
    public Mayor getCurrentMayor() {return CurrentMayorDataRepository.getInstance().getCurrentMayor();}
    public void setCurrentMayor(Mayor mayor) {CurrentMayorDataRepository.getInstance().setCurrentMayor(mayor);}

    // --- CURRENT TOWN HALL ---
    //
    public LiveData<TownHallFireStore> getCurrentTownHall() {return CurrentTownHallDataRepository.getInstance().getCurrentTownHall();}
    public void setCurrentTownHall(TownHallFireStore townHallFireStore) {CurrentTownHallDataRepository.getInstance().setCurrentTownHall(townHallFireStore);}

    // --- FIRE BASE : FIRE STORE ---
    //
    // USERS //
    //
    // Get a user in Fire Store
    public Task<DocumentSnapshot> getUser(String uid){
        return this.userDataFireStoreSource.getUser(uid);
    }
    // Update the InseeID a user in Fire Store
    public void setUserInseeID(String inseeID, String uid){
        this.userDataFireStoreSource.updateUserInseeID(inseeID, uid);
    }

    // MAYORS //
    //
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

    // Search And Show Town Hall
    public void searchAndShowTownHall() {

        // retrieves town hall data from the FireStore and stores it in the current Town Hall
        getTownHallByInseeID(getCurrentUser().getInseeID()).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.d(TAG, "onComplete: ");
                        if (task.isSuccessful()) {
                            if (task.getResult().size() != 0) {
                                Log.d(TAG, "task.getResult().size() != 0");
                                // Browse the result list
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, "TownHall = " + document.getId() + " => " + document.getData());

                                    // Get TownHall Object
                                    TownHallFireStore townHallFireStore = document.toObject(TownHallFireStore.class);

                                    // If the inseeID corresponds to the townHall document being read,
                                    // then we update inseeID of the user
                                    // As a precaution, only the first document found will allow the update,
                                    // the others will be ignored (ignore instruction)
                                    if (townHallFireStore.getInseeID().equals(getCurrentUser().getInseeID())) {

                                        // Set Current TownHall
                                        setCurrentTownHall(townHallFireStore);
                                        Log.d(TAG, "onComplete: townHallFireStore = " + getCurrentTownHall().getValue());

                                        // Show Town Hall Fragment
                                        mViewAction.setValue(ViewAction.CALL_TOWN_HALL_ACTIVITY);

                                        break;
                                    }
                                }
                            } else {
                                Log.d(TAG, "task.getResult().size() == 0");
                                mViewAction.setValue(ViewAction.TOWN_HALL_NOT_FOUND);
                            }
                        } else {
                            Log.d(TAG, "Error when querying the Fire Store database");
                            mViewAction.setValue(ViewAction.FIRE_STORE_ERROR);
                        }
                    }
                });
    }

    // TOWN HALL //
    //
    public Task<DocumentReference> createTownHall(TownHallFireStore townHallFireStore) {
        return townHallDataFireStoreSource.createTownHall(townHallFireStore);
    }
    // Get a Town Hall in Fire Store by TownHallID
    public Task<DocumentSnapshot> getTownHallByTownHallID(String townHallID) {
        return townHallDataFireStoreSource.getTownHallByTownHallID(townHallID);
    }
    // Get a Town Hall in Fire Store by InseeID
    public Task<QuerySnapshot> getTownHallByInseeID(String inseeID) {
        return townHallDataFireStoreSource.getTownHallByInseeID(inseeID).get();
    }
}
