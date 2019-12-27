package com.lastproject.mycity.room.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.lastproject.mycity.room.dao.EventDao;
import com.lastproject.mycity.room.models.Event;
import com.lastproject.mycity.utils.Converters;

import org.threeten.bp.LocalDateTime;

import java.util.Arrays;

@Database(entities = {Event.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class MyCityDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile MyCityDatabase INSTANCE;

    // --- DAO ---
    public abstract EventDao eventDao();

    // --- INSTANCE ---
    public static MyCityDatabase getInstance(Context context) {
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
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                /// ADD EVENTS //
                db.insert("Event", OnConflictStrategy.IGNORE, TestData.event1());
                db.insert("Event", OnConflictStrategy.IGNORE, TestData.event2());
                db.insert("Event", OnConflictStrategy.IGNORE, TestData.event3());
                db.insert("Event", OnConflictStrategy.IGNORE, TestData.event4());
                db.insert("Event", OnConflictStrategy.IGNORE, TestData.event5());
                db.insert("Event", OnConflictStrategy.IGNORE, TestData.event6());
                db.insert("Event", OnConflictStrategy.IGNORE, TestData.event7());
            }
        };
    }
}
