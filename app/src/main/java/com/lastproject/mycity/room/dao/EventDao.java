package com.lastproject.mycity.room.dao;

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

    @Query("SELECT * FROM Event where id = :id")
    LiveData<Event> getEvent(long id);

    @Update
    int updateEvent(Event event);

    @Query("DELETE FROM Event WHERE id = :id")
    int deleteEvent(long id);
}
