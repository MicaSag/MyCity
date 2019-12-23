package com.lastproject.mycity.injections;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.lastproject.mycity.models.views.AuthenticationViewModel;
import com.lastproject.mycity.models.views.CreateEventViewModel;
import com.lastproject.mycity.repositories.EventDataRoomRepository;
import com.lastproject.mycity.repositories.MayorDataFireStoreRepository;
import com.lastproject.mycity.repositories.UserDataAuthenticationRepository;
import com.lastproject.mycity.repositories.UserDataFireStoreRepository;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    // For debugging Mode
    private static final String TAG = ViewModelFactory.class.getSimpleName();

    private final EventDataRoomRepository eventDataRoomSource;
    private final UserDataAuthenticationRepository userDataAuthenticationSource;
    private final UserDataFireStoreRepository userDataFireStoreSource;
    private final MayorDataFireStoreRepository mayorDataFireStoreSource;
    private final Executor executor;

    public ViewModelFactory(EventDataRoomRepository eventDataRoomSource,
                            UserDataAuthenticationRepository userDataAuthenticationSource,
                            UserDataFireStoreRepository userDataFireStoreSource,
                            MayorDataFireStoreRepository mayorDataFireStoreSource,
                            Executor executor) {
        this.eventDataRoomSource = eventDataRoomSource;
        this.userDataAuthenticationSource = userDataAuthenticationSource;
        this.userDataFireStoreSource = userDataFireStoreSource;
        this.mayorDataFireStoreSource = mayorDataFireStoreSource;
        this.executor = executor;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AuthenticationViewModel.class)) {
            return (T) new AuthenticationViewModel( this.userDataAuthenticationSource,
                                                    this.userDataFireStoreSource,
                                                    this.mayorDataFireStoreSource,
                                                    this.executor);
        }
        if (modelClass.isAssignableFrom(CreateEventViewModel.class)) {
            return (T) new CreateEventViewModel(eventDataRoomSource, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
