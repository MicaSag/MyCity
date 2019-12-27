package com.lastproject.mycity.models.views;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.lastproject.mycity.repositories.CurrentEventDataRepository;
import com.lastproject.mycity.repositories.EventDataRoomRepository;
import com.lastproject.mycity.repositories.MayorDataFireStoreRepository;
import com.lastproject.mycity.repositories.UserDataAuthenticationRepository;
import com.lastproject.mycity.repositories.UserDataFireStoreRepository;
import com.lastproject.mycity.room.models.Event;

import java.util.concurrent.Executor;

public class DetailsEventViewModel extends ViewModel {

    // For debugging Mode
    private static final String TAG = DetailsEventViewModel.class.getSimpleName();

    // Repositories
    private final UserDataAuthenticationRepository userDataAuthenticationSource;
    private final UserDataFireStoreRepository userDataFireStoreSource;
    private final MayorDataFireStoreRepository mayorDataFireStoreSource;
    private final EventDataRoomRepository eventDataRoomSource;
    private final Executor executor;

    @NonNull
    private LiveData<Event> mCurrentEvent;

    public DetailsEventViewModel(UserDataAuthenticationRepository userDataAuthenticationSource,
                                 UserDataFireStoreRepository userDataFireStoreSource,
                                 MayorDataFireStoreRepository mayorDataFireStoreSource,
                                 EventDataRoomRepository eventDataRoomSource,
                                 Executor executor) {
        this.userDataAuthenticationSource = userDataAuthenticationSource;
        this.userDataFireStoreSource = userDataFireStoreSource;
        this.mayorDataFireStoreSource = mayorDataFireStoreSource;
        this.eventDataRoomSource = eventDataRoomSource;
        this.executor = executor;

        mCurrentEvent = Transformations.switchMap(
                CurrentEventDataRepository.getInstance().getCurrentEventId(),
                this::getEvent);
    }

    // Get Event of the Room DataBase
    public LiveData<Event> getEvent(String eventId) {
        Log.d(TAG, "getEvent: ");

        return eventDataRoomSource.getEvent(eventId);
    }

    // Get Current Event
    @NonNull
    public LiveData<Event> getCurrentEvent() {
        return mCurrentEvent;
    }
}


