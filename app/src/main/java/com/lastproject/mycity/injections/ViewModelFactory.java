package com.lastproject.mycity.injections;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.lastproject.mycity.models.views.AuthenticationViewModel;
import com.lastproject.mycity.models.views.CitizenViewModel;
import com.lastproject.mycity.models.views.EventViewModel;
import com.lastproject.mycity.models.views.ListEventsViewModel;
import com.lastproject.mycity.models.views.MayorViewModel;
import com.lastproject.mycity.repositories.EventDataFireStoreRepository;
import com.lastproject.mycity.repositories.EventDataRoomRepository;
import com.lastproject.mycity.repositories.MayorDataFireStoreRepository;
import com.lastproject.mycity.repositories.TownHallDataFireStoreRepository;
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
    private final TownHallDataFireStoreRepository townHallDataFireStoreSource;
    private final EventDataFireStoreRepository eventDataFireStoreSource;
    private final Executor executor;

    public ViewModelFactory(EventDataRoomRepository eventDataRoomSource,
                            UserDataAuthenticationRepository userDataAuthenticationSource,
                            UserDataFireStoreRepository userDataFireStoreSource,
                            MayorDataFireStoreRepository mayorDataFireStoreSource,
                            TownHallDataFireStoreRepository townHallDataFireStoreSource,
                            EventDataFireStoreRepository eventDataFireStoreSource,
                            Executor executor) {
        this.eventDataRoomSource = eventDataRoomSource;
        this.userDataAuthenticationSource = userDataAuthenticationSource;
        this.userDataFireStoreSource = userDataFireStoreSource;
        this.mayorDataFireStoreSource = mayorDataFireStoreSource;
        this.townHallDataFireStoreSource = townHallDataFireStoreSource;
        this.eventDataFireStoreSource = eventDataFireStoreSource;
        this.executor = executor;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AuthenticationViewModel.class)) {
            return (T) new AuthenticationViewModel( this.userDataAuthenticationSource,
                                                    this.userDataFireStoreSource,
                                                    this.mayorDataFireStoreSource);
        }
        if (modelClass.isAssignableFrom(MayorViewModel.class)) {
            return (T) new MayorViewModel(  this.userDataAuthenticationSource,
                                            this.userDataFireStoreSource,
                                            this.mayorDataFireStoreSource,
                                            this.townHallDataFireStoreSource,
                                            this.executor);
        }
        if (modelClass.isAssignableFrom(CitizenViewModel.class)) {
            return (T) new CitizenViewModel(this.userDataAuthenticationSource,
                                            this.userDataFireStoreSource,
                                            this.mayorDataFireStoreSource,
                                            this.eventDataRoomSource,
                                            this.executor);
        }
        if (modelClass.isAssignableFrom(EventViewModel.class)) {
            return (T) new EventViewModel(  this.eventDataRoomSource,
                                            this.eventDataFireStoreSource,
                                            this.executor);
        }
        if (modelClass.isAssignableFrom(ListEventsViewModel.class)) {
                return (T) new ListEventsViewModel( this.eventDataRoomSource,
                                                    this.eventDataFireStoreSource,
                                                    this.executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
