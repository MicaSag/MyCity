package com.lastproject.mycity.room.dao;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.lastproject.mycity.models.Event;

import java.util.List;

@Dao
public interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createEvent(Event event);

    @Query("SELECT * FROM Event")
    LiveData<List<Event>> getAllEvents();

    @Query("SELECT * FROM Event where inseeID = :inseeID and userID = :userID")
    LiveData<List<Event>> getAllEventsByInseeID(@NonNull String inseeID, String userID);

    @Query("SELECT * FROM Event where eventID = :eventID and userID = :userID")
    LiveData<Event> getEvent(@NonNull String eventID, String userID);

    @Update
    int updateEvent(Event event);

    @Query("DELETE FROM Event WHERE eventID = :eventID")
    int deleteEvent(@NonNull String eventID);
}
