package com.lastproject.mycity.injections;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.lastproject.mycity.models.CreateEventViewModel;
import com.lastproject.mycity.repositories.EventDataRoomRepository;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final EventDataRoomRepository eventDataRoomSource;
    private final Executor executor;

    public ViewModelFactory(EventDataRoomRepository eventDataRoomSource, Executor executor) {
        this.eventDataRoomSource = eventDataRoomSource;
        this.executor = executor;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CreateEventViewModel.class)) {
            return (T) new CreateEventViewModel(eventDataRoomSource, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
