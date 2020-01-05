package com.lastproject.mycity.models.views;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.lastproject.mycity.models.Event;
import com.lastproject.mycity.repositories.CurrentEventDataRepository;
import com.lastproject.mycity.repositories.EventDataFireStoreRepository;
import com.lastproject.mycity.repositories.EventDataRoomRepository;
import com.lastproject.mycity.repositories.MayorDataFireStoreRepository;
import com.lastproject.mycity.repositories.UserDataAuthenticationRepository;
import com.lastproject.mycity.repositories.UserDataFireStoreRepository;

import java.util.concurrent.Executor;

public class DetailsEventViewModel extends ViewModel {

    // For debugging Mode
    private static final String TAG = DetailsEventViewModel.class.getSimpleName();

    // Repositories
    private final EventDataRoomRepository eventDataRoomSource;
    private final EventDataFireStoreRepository eventDataFireStoreSource;
    private final Executor executor;

    @NonNull
    private LiveData<Event> mCurrentEvent;

    public DetailsEventViewModel(EventDataRoomRepository eventDataRoomSource,
                                 EventDataFireStoreRepository eventDataFireStoreSource,
                                 Executor executor) {
        this.eventDataRoomSource = eventDataRoomSource;
        this.eventDataFireStoreSource = eventDataFireStoreSource;
        this.executor = executor;

        mCurrentEvent = Transformations.switchMap(
                CurrentEventDataRepository.getInstance().getCurrentEventID(),
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


