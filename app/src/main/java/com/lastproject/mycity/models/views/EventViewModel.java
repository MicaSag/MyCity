package com.lastproject.mycity.models.views;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.lastproject.mycity.models.Event;
import com.lastproject.mycity.repositories.CurrentEventDataRepository;
import com.lastproject.mycity.repositories.EventDataFireStoreRepository;
import com.lastproject.mycity.repositories.EventDataRoomRepository;

import java.util.concurrent.Executor;

public class EventViewModel extends ViewModel {

    // For debugging Mode
    private static final String TAG = EventViewModel.class.getSimpleName();

    // Repositories
    private final EventDataRoomRepository eventDataRoomSource;
    private final EventDataFireStoreRepository eventDataFireStoreSource;
    private final Executor executor;

    // Activity processing MODE
    public static String MODE = "EVENT_MODE";
    public enum EventMode {
        EDIT,
        CREATE,
        UPDATE,
        FULL_SCREEN_IMAGE
    }

    // DATA
    MutableLiveData<EventMode> mode = new MutableLiveData<>();

    @NonNull
    private LiveData<Event> mCurrentEvent;

    public EventViewModel(  EventDataRoomRepository eventDataRoomSource,
                            EventDataFireStoreRepository eventDataFireStoreSource,
                            Executor executor) {
        this.eventDataRoomSource = eventDataRoomSource;
        this.eventDataFireStoreSource = eventDataFireStoreSource;
        this.executor = executor;

        mCurrentEvent = Transformations.switchMap(
                CurrentEventDataRepository.getInstance().getCurrentEventID(),
                this::getEvent);
    }

    // Get Activity MODE
    public LiveData<EventMode> getMode() {
        return mode;
    }
    // Set Activity MODE
    public void setMode(EventMode mode) {
        this.mode.setValue(mode);
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
