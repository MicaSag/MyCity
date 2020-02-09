package com.lastproject.mycity.models.views;

import android.location.Location;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.GoogleMap;
import com.lastproject.mycity.models.Event;
import com.lastproject.mycity.repositories.CurrentTownHallDataRepository;
import com.lastproject.mycity.repositories.CurrentUserDataRepository;
import com.lastproject.mycity.repositories.EventDataRoomRepository;

import java.util.List;

public class MapViewModel extends ViewModel {

    // For debugging Mode
    private static final String TAG = MapViewModel.class.getSimpleName();

    // REPOSITORIES
    private final EventDataRoomRepository mEventDataRoomSource;

    // The geographical location where the device is currently located.
    // That is, the last-known location retrieved by the Fused Location Provider.
    // OR the default Location if permission not Granted
    private MutableLiveData<Location> mLastKnownLocation = new MutableLiveData<>();

    // ==> For use Api Google Play Service : map
    private GoogleMap mGoogleMap;

    public MapViewModel(EventDataRoomRepository eventDataRoomSource) {
        mEventDataRoomSource = eventDataRoomSource;
    }

    // --- ROOM  ---
    // =============
    // Get the whole list of events in Room by InseeID
    public LiveData<List<Event>> getEvents() {
        String inseeID = CurrentTownHallDataRepository.getInstance().getCurrentTownHall()
                .getValue().getInseeID();
        String userID = CurrentUserDataRepository.getInstance().getCurrentUser().getUserID();
        return mEventDataRoomSource.getAllEventsByInseeID(inseeID, userID);
    }

    // Get Last Know Location
    public LiveData<Location> getLastKnownLocation() {
        Log.d(TAG, "getLastKnownLocation: ");
        return mLastKnownLocation;
    }

    // Set Last Know Location
    public void setLastKnownLocation(Location lastKnownLocation) {
        Log.d(TAG, "setLastKnownLocation: ");
        this.mLastKnownLocation.setValue(lastKnownLocation);
    }

    // Get Current Location
    public LiveData<Location> getCurrentLocation() {
        Log.d(TAG, "getCurrentLocation: ");
        return mLastKnownLocation;
    }

    // Get Google Map
    public GoogleMap getGoogleMap() {
        return mGoogleMap;
    }

    // Set Google Map
    public void setGoogleMap(GoogleMap googleMap) {
        this.mGoogleMap = googleMap;
    }
}

