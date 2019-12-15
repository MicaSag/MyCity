package com.lastproject.mycity.injections;

import android.content.Context;

import com.lastproject.mycity.repositories.EventDataRoomRepository;
import com.lastproject.mycity.room.database.MyCityDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static EventDataRoomRepository provideEventDataRoomSource(Context context) {
        MyCityDatabase database = MyCityDatabase.getInstance(context);
        return new EventDataRoomRepository(database.eventDao());
    }

    public static Executor provideExecutor(){ return Executors.newSingleThreadExecutor(); }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        EventDataRoomRepository dataRoomSourceEvent = provideEventDataRoomSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataRoomSourceEvent, executor);
    }
}
