package com.lastproject.mycity;

import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;
import com.lastproject.mycity.firebase.database.firestore.models.EventFireStore;
import com.lastproject.mycity.models.Event;
import com.lastproject.mycity.room.database.MyCityDatabase;
import com.lastproject.mycity.utils.Converters;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class EventDaoTest {

    // For Debug
    private static final String TAG = EventDaoTest.class.getSimpleName();

    // FOR DATA
    private MyCityDatabase database;

    // DATA SET FOR TEST
    // --> New Event

    private static EventFireStore efs = new EventFireStore(
            "99999",
            "Noël des enfants",
            "Des cadeaux, des cadeaux, des cadeaux",
            new ArrayList<>(Arrays.asList("https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg",
                    "https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg",
                    "https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg")),
            new ArrayList<>(Arrays.asList("3 rue de la chesnaie",
                    "hameau de cagneux",
                    "60140","BAILLEVAL")),
            new GeoPoint(3.0d,0.5d),
            Converters.fromLocalDateTimeToTimestamp(LocalDateTime.now().withDayOfMonth(10).withYear(2019).withMonth(8)),
            Converters.fromLocalDateTimeToTimestamp(LocalDateTime.now()),
            false );
    private static String eventID_1 = LocalDateTime.now().toString();
    private static Event newEvent_1 = new Event(eventID_1, efs );
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static EventFireStore efs2 = new EventFireStore(
            "33333",
            "Brocante des trois fontaines",
            "Encore un vide grenier à vider.",
            new ArrayList<>(Arrays.asList("https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg",
                    "https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg")),
            new ArrayList<>(Arrays.asList("73 rue de la chesnaie",
                    "hameau de bétancourt",
                    "60170","BAILLEVAL")),
            new GeoPoint(10.0d,20.0d),
            Converters.fromLocalDateTimeToTimestamp(LocalDateTime.now().withDayOfMonth(10).withYear(2019).withMonth(8)),
            Converters.fromLocalDateTimeToTimestamp(LocalDateTime.now()),
            true);
    private static String eventID_2 = LocalDateTime.now().toString();
    private static Event newEvent_2 = new Event(eventID_2, efs2 );


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        Log.d(TAG, "initDb: ");
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getTargetContext(),
                MyCityDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        Log.d(TAG, "closeDb: ");
        database.close();
    }

    @Test
    public void createEvent() throws InterruptedException {
        Log.d(TAG, "createEvent: ");
        // BEFORE : Adding new Events
        this.database.eventDao().createEvent(newEvent_1);
        // TEST
        List<Event> events =
                LiveDataTestUtil.getValue(this.database.eventDao().getEvents());

        assertTrue(events.get(0).getTitle().equals("Noël des enfants"));

        Log.d(TAG, "createEvent : AFTER");
        this.displayEvents(events);
    }

    @Test
    public void getAndUpdateEvent() throws InterruptedException {
        Log.d(TAG, "getAndUpdateEvent: ");
        // BEFORE : Update events
        this.database.eventDao().createEvent(newEvent_1);
        this.database.eventDao().createEvent(newEvent_2);

        Log.d(TAG, "getAndUpdateEvent : BEFORE");
        List<Event> events =
                LiveDataTestUtil.getValue(this.database.eventDao().getEvents());
        this.displayEvents(events);

        Event eventUpdate = LiveDataTestUtil.getValue(this.database.eventDao().getEvent(eventID_1));
        eventUpdate.setTitle("Sortie picine");
        this.database.eventDao().updateEvent(eventUpdate);

        //TEST
        Event event = LiveDataTestUtil.getValue(this.database.eventDao().getEvent(eventID_1));

        assertTrue(event.getTitle().equals("Sortie picine"));

        Log.d(TAG, "getAndUpdateEvent : AFTER");
        events =
                LiveDataTestUtil.getValue(this.database.eventDao().getEvents());
        this.displayEvents(events);
    }

    @Test
    public void deleteEvent() throws InterruptedException {
        Log.d(TAG, "deleteEvent: ");

        this.database.eventDao().createEvent(newEvent_1);
        this.database.eventDao().createEvent(newEvent_2);

        Log.d(TAG, "deleteEvent : BEFORE");
        List<Event> events =
                LiveDataTestUtil.getValue(this.database.eventDao().getEvents());
        this.displayEvents(events);

        // BEFORE : Adding demo user & demo item. Next, get the item added & delete it.
        List<Event> eventsDelete = LiveDataTestUtil.getValue(this.database.eventDao().getEvents());
        int i = 0;
        for(Event event : eventsDelete){
            Log.d(TAG, "deleteEvent: Delete Event ("+i+")");
            this.database.eventDao().deleteEvent(event.getEventID());
            i++;
        }
        //TEST
        events = LiveDataTestUtil.getValue(this.database.eventDao().getEvents());
        assertTrue(events.isEmpty());

        Log.d(TAG, "deleteEvent: AFTER");
        this.displayEvents(events);
    }

    public void displayEvents(List<Event> events){
        Log.d(TAG, "displayEvents: ");

        for(Event event : events){
            Log.d(TAG, "displayEvents: event ID = "+event.getEventID());
            Log.d(TAG, "displayEvents: event Title       = "+event.getTitle());
            Log.d(TAG, "displayEvents: event Description = "+event.getDescription());
            int i = 0;
            for(String photo : event.getPhotos()){
                Log.d(TAG, "displayEvents: event Photo("+i+") = "+photo);
                i++;
            }
            Log.d(TAG, "displayEvents: event Location    = "+event.getLocation());
            Log.d(TAG, "displayEvents: event StartDate   = "+event.getStartDate());
            Log.d(TAG, "displayEvents: event EndDate     = "+event.getEndDate());
        }
    }
}
