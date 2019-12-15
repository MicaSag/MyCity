package com.lastproject.mycity.models;

import androidx.lifecycle.ViewModel;

import com.lastproject.mycity.repositories.EventDataRoomRepository;

import java.util.concurrent.Executor;

public class CreateEventViewModel extends ViewModel {

    // Repositories
    private final EventDataRoomRepository eventDataRoomSource;
    private final Executor executor;


    public CreateEventViewModel(EventDataRoomRepository eventDataRoomSource, Executor executor) {
        this.eventDataRoomSource = eventDataRoomSource;
        this.executor = executor;
    }
}
