package com.lastproject.mycity.room.dao;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.lastproject.mycity.room.models.Event;

import java.util.List;

@Dao
public interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createEvent(Event event);

    @Query("SELECT * FROM Event")
    LiveData<List<Event>> getEvents();

    @Query("SELECT * FROM Event where eventId = :eventId")
    LiveData<Event> getEvent(@NonNull String eventId);

    @Update
    int updateEvent(Event event);

    @Query("DELETE FROM Event WHERE eventId = :eventId")
    int deleteEvent(@NonNull String eventId);
}
