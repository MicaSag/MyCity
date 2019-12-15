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

    private static Callback prepopulateDatabase(){
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues contentValues = new ContentValues();
                contentValues.put("title", "Broquante");
                contentValues.put("description", "Jolie petite merde devenements pour se faire emmerder par des cons");
                contentValues.put("photos", "[" +
                        "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house6/house_6_outside.jpg\"," +
                        "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house6/house_6_facade.jpg\"," +
                        "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house6/house_6_living_room.jpg\"," +
                        "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house6/house_6_dining_room.jpg\"," +
                        "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house6/house_6_kitchen.jpg\"," +
                        "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house6/house_6_bedroom.jpg\"," +
                        "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house6/house_6_bathroom.jpg\"," +
                        "\"/sdcard/Android/data/com.openclassrooms.realestatemanager/files/Pictures/house6/house_6_swimming_pool.jpg\"]");
                contentValues.put("startDate", Converters.dateToTimestamp(LocalDateTime.now().withDayOfMonth(2).withYear(2019).withMonth(4)));
                contentValues.put("endDate", Converters.dateToTimestamp(LocalDateTime.now().withDayOfMonth(17).withYear(2019).withMonth(4)));

                db.insert("Event", OnConflictStrategy.IGNORE, contentValues);
            }
        };
    }
}
