package com.lastproject.mycity.room.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.lastproject.mycity.models.Event;
import com.lastproject.mycity.room.dao.EventDao;
import com.lastproject.mycity.utils.Converters;

@Database(entities = {Event.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class MyCityDatabase extends RoomDatabase {

    private static final String TAG = MyCityDatabase.class.getSimpleName();

    // --- SINGLETON ---
    private static volatile MyCityDatabase INSTANCE;

    // --- DAO ---
    public abstract EventDao eventDao();

    // --- INSTANCE ---
    public static MyCityDatabase getInstance(Context context) {
        Log.d(TAG, "getInstance: ");
        if (INSTANCE == null) {
            synchronized (MyCityDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyCityDatabase.class, "MyCityDatabase.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // ---

    private static Callback prepopulateDatabase() {
        Log.d(TAG, "prepopulateDatabase: ");
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                Log.d(TAG, "onCreate: Databae Open ? : "+db.isOpen());
                /// ADD EVENTS //
                Log.d(TAG, "onCreate: 1");
                db.insert("Event", OnConflictStrategy.IGNORE, TestData.event1());
                Log.d(TAG, "onCreate: 2");
                db.insert("Event", OnConflictStrategy.IGNORE, TestData.event2());
                Log.d(TAG, "onCreate: 3");
                db.insert("Event", OnConflictStrategy.IGNORE, TestData.event3());
                Log.d(TAG, "onCreate: 4");
                db.insert("Event", OnConflictStrategy.IGNORE, TestData.event4());
                Log.d(TAG, "onCreate: 5");
                db.insert("Event", OnConflictStrategy.IGNORE, TestData.event5());
                Log.d(TAG, "onCreate: 6");
                db.insert("Event", OnConflictStrategy.IGNORE, TestData.event6());
                Log.d(TAG, "onCreate: 7");
                db.insert("Event", OnConflictStrategy.IGNORE, TestData.event7());
            }
        };
    }
}
