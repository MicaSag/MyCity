package com.lastproject.mycity.injections;

import android.content.Context;

import com.lastproject.mycity.firebase.authentication.helpers.AuthenticationHelper;
import com.lastproject.mycity.repositories.EventDataRoomRepository;
import com.lastproject.mycity.repositories.MayorDataFireStoreRepository;
import com.lastproject.mycity.repositories.UserDataAuthenticationRepository;
import com.lastproject.mycity.repositories.UserDataFireStoreRepository;
import com.lastproject.mycity.room.database.MyCityDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    private static EventDataRoomRepository mEventDataRoomRepository;
    private static UserDataAuthenticationRepository mUserDataAuthenticationRepository;
    private static UserDataFireStoreRepository mUserDataFireStoreRepository;
    private static MayorDataFireStoreRepository mMayorDataFireStoreRepository;

    public static Executor provideExecutor(){ return Executors.newSingleThreadExecutor(); }

    public static EventDataRoomRepository provideEventDataRoomSource(Context context) {
        if (mEventDataRoomRepository == null) {
            MyCityDatabase database = MyCityDatabase.getInstance(context);
            mEventDataRoomRepository = new EventDataRoomRepository(database.eventDao());
        }
        return mEventDataRoomRepository;
    }

    public static UserDataAuthenticationRepository provideUserDataAuthenticationSource() {
        if (mUserDataAuthenticationRepository == null) {
            mUserDataAuthenticationRepository = new UserDataAuthenticationRepository(new AuthenticationHelper());
        }
        return mUserDataAuthenticationRepository;
    }

    public static UserDataFireStoreRepository provideUserDataFireStoreSource() {
        if (mUserDataFireStoreRepository == null) {
            mUserDataFireStoreRepository = new UserDataFireStoreRepository();
        }
        return mUserDataFireStoreRepository;
    }

    public static MayorDataFireStoreRepository provideMayorDataFireStoreSource() {
        if (mMayorDataFireStoreRepository == null) {
            mMayorDataFireStoreRepository = new MayorDataFireStoreRepository();
        }
        return mMayorDataFireStoreRepository;
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        EventDataRoomRepository dataRoomSourceEvent = provideEventDataRoomSource(context);
        UserDataAuthenticationRepository dataSourceAuthentication = provideUserDataAuthenticationSource();
        UserDataFireStoreRepository dataSourceFireStoreUser = provideUserDataFireStoreSource();
        MayorDataFireStoreRepository dataSourceFireStoreMayor = provideMayorDataFireStoreSource();
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataRoomSourceEvent,
                                    dataSourceAuthentication,
                                    dataSourceFireStoreUser,
                                    dataSourceFireStoreMayor,
                                    executor);
    }
}
