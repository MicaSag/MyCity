package com.lastproject.mycity.repositories;

import androidx.lifecycle.LiveData;

import com.lastproject.mycity.models.Event;
import com.lastproject.mycity.room.dao.EventDao;

import java.util.List;

public class EventDataRoomRepository {

    private final EventDao eventDao;

    public EventDataRoomRepository(EventDao eventDao) { this.eventDao = eventDao; }

    // --- GET ---

    public LiveData<List<Event>> getAllEvents(){ return this.eventDao.getAllEvents(); }

    public LiveData<List<Event>> getAllEventsByInseeID(String inseeID, String userID){
        return this.eventDao.getAllEventsByInseeID(inseeID, userID);
    }

    public LiveData<Event> getEvent(String eventID, String userID){
        return this.eventDao.getEvent(eventID, userID);
    }

    // --- CREATE ---

    public void createEvent(Event event){ eventDao.createEvent(event); }

    // --- DELETE ---
    public void deleteEvent(String eventId){ eventDao.deleteEvent(eventId); }

    // --- UPDATE ---
    public void updateEvent(Event event){ eventDao.updateEvent(event); }
}
