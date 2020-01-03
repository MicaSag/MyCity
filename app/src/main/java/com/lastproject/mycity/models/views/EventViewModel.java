package com.lastproject.mycity.models.views;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lastproject.mycity.models.Event;
import com.lastproject.mycity.repositories.EventDataRoomRepository;
import com.lastproject.mycity.repositories.MayorDataFireStoreRepository;
import com.lastproject.mycity.repositories.UserDataAuthenticationRepository;
import com.lastproject.mycity.repositories.UserDataFireStoreRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class EventViewModel extends ViewModel {

    // For Debug
    private static final String TAG = EventViewModel.class.getSimpleName();

    // Repositories
    private final UserDataAuthenticationRepository userDataAuthenticationSource;
    private final UserDataFireStoreRepository userDataFireStoreSource;
    private final MayorDataFireStoreRepository mayorDataFireStoreSource;
    private final EventDataRoomRepository eventDataRoomSource;
    private final Executor executor;

    // -- Current Events List
    private MutableLiveData<List<Event>> currentEvents = new MutableLiveData<>();

    public EventViewModel(UserDataAuthenticationRepository userDataAuthenticationSource,
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


    // --- ROOM  ---
    // =============
    // Get the whole list of events in Room
    public LiveData<List<Event>> getEvents(){
        return eventDataRoomSource.getEvents();
    }
}
