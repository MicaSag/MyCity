package com.lastproject.mycity.repositories;

import androidx.lifecycle.LiveData;

import com.lastproject.mycity.room.dao.EventDao;
import com.lastproject.mycity.room.models.Event;

import java.util.List;

public class EventDataRoomRepository {

    private final EventDao eventDao;

    public EventDataRoomRepository(EventDao eventDao) { this.eventDao = eventDao; }

    // --- GET ---

    public LiveData<List<Event>> getEvents(){ return this.eventDao.getEvents(); }

    public LiveData<Event> getEvent(long eventId){ return this.eventDao.getEvent(eventId); }

    // --- CREATE ---

    public void createEvent(Event event){ eventDao.createEvent(event); }

    // --- DELETE ---
    public void deleteEvent(long eventId){ eventDao.deleteEvent(eventId); }

    // --- UPDATE ---
    public void updateItem(Event event){ eventDao.updateEvent(event); }
}
