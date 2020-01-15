package com.lastproject.mycity.models.views;

import android.app.Activity;
import android.location.Address;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.GeoPoint;
import com.lastproject.mycity.firebase.database.firestore.models.EventFireStore;
import com.lastproject.mycity.firebase.database.firestore.models.TownHall;
import com.lastproject.mycity.models.Event;
import com.lastproject.mycity.repositories.CurrentEventIDDataRepository;
import com.lastproject.mycity.repositories.CurrentTownHallDataRepository;
import com.lastproject.mycity.repositories.EventDataFireStoreRepository;
import com.lastproject.mycity.repositories.EventDataRoomRepository;
import com.lastproject.mycity.utils.Toolbox;

import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class EventViewModel extends ViewModel {

    // For debugging Mode
    private static final String TAG = EventViewModel.class.getSimpleName();

    // Repositories
    private final EventDataRoomRepository eventDataRoomSource;
    private final EventDataFireStoreRepository eventDataFireStoreSource;
    private final Executor executor;

    // Activity processing MODES
    MutableLiveData<EventMode> mode = new MutableLiveData<>();
    public static String MODE = "EVENT_MODE";
    public enum EventMode {
        VIEW,
        CREATE,
        UPDATE,
        DELETE,
        FINISH
    }

    // Event MESSAGES
    MutableLiveData<EventMessage> message = new MutableLiveData<>();
    public static String MESSAGE = "EVENT_MESSAGE";
    public enum EventMessage {
        INVALID_INPUT
    }

    // DATA
    private MutableLiveData<Event> mCurrentEvent = new MutableLiveData<>();
    // For Manage Dates
    private MutableLiveData<String> mStartDate = new MutableLiveData<>();
    private MutableLiveData<String> mEndDate = new MutableLiveData<>();
    // For Manage Photos
    private MutableLiveData<ArrayList<String>> mPhotos = new MutableLiveData<>();
    private String mCurrentPhotoPath;
    // For use intents to retrieve photos
    public static final int REQUEST_TAKE_PHOTO = 1;
    public static final int REQUEST_IMAGE_GET = 10;

    public EventViewModel(  EventDataRoomRepository eventDataRoomSource,
                            EventDataFireStoreRepository eventDataFireStoreSource,
                            Executor executor) {
        this.eventDataRoomSource = eventDataRoomSource;
        this.eventDataFireStoreSource = eventDataFireStoreSource;
        this.executor = executor;
        this.mPhotos.setValue(new ArrayList<>());
    }

    // --- ACTIVITY MODE ---
    //
    public LiveData<EventMode> getMode() {
        return mode;
    }
    public void setMode(EventMode mode) {
        this.mode.setValue(mode);
    }

    // --- EVENT MESSAGES ---
    //
    public LiveData<EventMessage> getMessage() {
        return message;
    }
    public void setMessage(EventMessage message) {
        this.message.setValue(message);
    }

    // --- CURRENT TOWN HALL ---
    //
    public LiveData<TownHall> getCurrentTownHall() {return CurrentTownHallDataRepository.getInstance().getCurrentTownHall();}

    // Get Event of the Room DataBase
    public LiveData<Event> getEventInRoom(String eventId) {
        Log.d(TAG, "getEventInRoom: ");

        return eventDataRoomSource.getEvent(eventId);
    }
    // Get Current Event ID
    @NonNull
    public LiveData<String> getCurrentEventID() {
        return CurrentEventIDDataRepository.getInstance().getCurrentEventID();
    }

    // Set Current Event ID
    @NonNull
    public void setCurrentEventID(String eventID) {
        CurrentEventIDDataRepository.getInstance().setCurrentEventID(eventID);
    }

    // Get Current Event
    @NonNull
    public LiveData<Event> getCurrentEvent() {
        return mCurrentEvent;
    }

    // Set Current Event Live Data
    @NonNull
    public void setCurrentEvent(Event event) {
        mCurrentEvent.setValue(event);
    }
    /***
     *
     *  UPDATE an event in Room
     *
     * @param eventFireStore : event data
     * @return  void
     *
     */
    public void updateEventInRoom(Activity activity, EventFireStore eventFireStore) {
        Log.d(TAG, "updateEventInRoom() called with: event = [" + eventFireStore + "]");

        // validation of the data entered in the view
        eventFireStore = validateData(
                eventFireStore.getTitle(),
                eventFireStore.getDescription(),
                eventFireStore.getPhotos(),
                eventFireStore.getAddress(),
                eventFireStore.getStartDate(),
                eventFireStore.getEndDate());

        if (eventFireStore != null) {
            Log.d(TAG, "updateEventInRoom: eventFireStore != null");
            // If we are in update mode,
            // we enrich the event returned by the creation screen with the data of the original event
            if (getMode().getValue() == EventMode.UPDATE) {

                // Re Assign InseeID
                eventFireStore.setInseeID(getCurrentEvent().getValue().getInseeID());

                // Assign NEW GeoPoint
                try {
                    List<Address> geoCoderAddress = Toolbox.geocoderAddressFromPhysicalAddress(activity,
                            eventFireStore.getAddress());
                    GeoPoint gp = new GeoPoint( geoCoderAddress.get(0).getLongitude(),
                            geoCoderAddress.get(0).getLatitude());
                    eventFireStore.setLocation(gp);
                } catch (Exception e) {
                    Log.d(TAG, "updateEventInRoom: Exception e = "+e.getMessage());
                }
            }

            // Build NEW Room Event
            Event roomEvent = new Event(getCurrentEvent().getValue().getEventID(),
                    false,
                    eventFireStore);

            // Update Event iN Room DataBase
            executor.execute(() -> {
                // Update Event in Room DataBase
                eventDataRoomSource.updateEvent(roomEvent);

                // Activity goes into VIEW MODE
                mode.postValue(EventMode.VIEW);
            });
        } else {
            Log.d(TAG, "updateEventInRoom: EventMode.INVALID_INPUT");
            // Invalid Input is generate
            this.setMessage(EventMessage.INVALID_INPUT);
        }
    }
    /***
     *
     *  Create an event in room if the data entered in the view are valid
     *
     * @param eventFireStore : event data
     * @return  void
     *
     */
    public void createEventInRoom(Activity activity, EventFireStore eventFireStore) {
        Log.d(TAG, "createEventInRoom() called with: eventFireStore = [" + eventFireStore + "]");

        // validation of the data entered in the view
        eventFireStore = validateData(
                eventFireStore.getTitle(),
                eventFireStore.getDescription(),
                eventFireStore.getPhotos(),
                eventFireStore.getAddress(),
                eventFireStore.getStartDate(),
                eventFireStore.getEndDate());

        if (eventFireStore != null) {

            // Assign a unique event identifier
            String eventID = LocalDateTime.now().toString();

            // Assign Insee Code
            eventFireStore.setInseeID(getCurrentTownHall().getValue().getInseeID());
            Log.d(TAG, "createEventInRoom: eventFireStore.getInseeID = "+eventFireStore.getInseeID());

            // Assign GeoPoint
            try {
                List<Address> geoCoderAddress = Toolbox.geocoderAddressFromPhysicalAddress(activity,
                        eventFireStore.getAddress());
                GeoPoint gp = new GeoPoint( geoCoderAddress.get(0).getLongitude(),
                                            geoCoderAddress.get(0).getLatitude());
                eventFireStore.setLocation(gp);
            } catch (Exception e) {
                Log.d(TAG, "createEventInRoom: Exception e = "+e.getMessage());
            }

            // builds the event
            Event event = new Event( eventID, false, eventFireStore);

            // Change Event Current ID
            CurrentEventIDDataRepository.getInstance().setCurrentEventID(eventID);

            // Create Event in Room DataBase
            executor.execute(() -> {
                // Create event in Room DataBase
                eventDataRoomSource.createEvent(event);

                // Activity goes into VIEW MODE
                this.mode.postValue(EventMode.VIEW);
            });

        } else {
            Log.d(TAG, "createEventInRoom: EventMode.INVALID_INPUT");
            // Invalid Input is generate
            this.setMessage(EventMessage.INVALID_INPUT);
        }
    }

    /***
     *
     *  Check the event data
     *
     * @param title         : title of the event
     * @param description   : description of the event
     * @param photos        : event photos
     * @param address       : event address
     * @param startDate     : event start date
     * @param endDate       : event end date
     * @return  EventFireStore : formatted event data
     */
    private EventFireStore validateData(
            String title,
            String description,
            ArrayList<String> photos,
            ArrayList<String> address,
            String startDate,
            String endDate
    ) {
        // Create a new EventFireStore Object
        EventFireStore eventFireStore = new EventFireStore();

        // -------------------------
        // Title of the event Is required
        if(title.isEmpty()) {
            Log.d(TAG, "validateData: title.isEmpty()");
            return null;
        } else { eventFireStore.setTitle(title);}
        // -------------------------
        // Description of the event Not required
        eventFireStore.setDescription(description);
        // -------------------------
        // Address
        if (address != null ) {

            ArrayList<String> localAddress = new ArrayList<>();
            String street = address.get(0);
            String complement = address.get(1);
            String postalCode = address.get(2);
            String city = address.get(3);
            String state = address.get(4);

            // -- Street Is required
            if (street.isEmpty()) {
                Log.d(TAG, "validateData: street.isEmpty()");
                return null;
            }
            localAddress.add(street);
            // -- Complement Not required
            if (!complement.isEmpty()) {
                localAddress.add(complement);
            }
            // -- Postal code Is required
            if (postalCode.isEmpty()) {
                Log.d(TAG, "validateData: postalCode.isEmpty()");
                return null;
            }
            localAddress.add(postalCode);
            // -- City Is required
            if (city.isEmpty()) {
                Log.d(TAG, "validateData: city.isEmpty()");
                return null;
            }
            localAddress.add(city);
            // -- State Is required
            if (state.isEmpty()) {
                Log.d(TAG, "validateData: state.isEmpty()");
                return null;
            }
            localAddress.add(state);
            eventFireStore.setAddress(localAddress);
        } else {
            Log.d(TAG, "validateData: address == null");
            return null;
        }
        // -------------------------
        // Start Date Is required
        if(startDate.isEmpty()){
            Log.d(TAG, "validateData: startDate.isEmpty()");
            return null;
        } else { eventFireStore.setStartDate(startDate);}
        // -------------------------
        // Start Date Is required
        if(endDate.isEmpty()){
            Log.d(TAG, "validateData: endDate.isEmpty()");
            return null;
        } else { eventFireStore.setEndDate(endDate);}
        // -------------------------
        // Photo List Not required
        eventFireStore.setPhotos(photos);
        // -------------------------

        return eventFireStore;
    }

    /***
     *
     *  Publish an event in fireBase
     *
     * @param event : event data
     * @return  void
     *
     */
    public void publishEvent(Event event) {
        Log.d(TAG, "publishEvent() called with: event = [" + event + "]");
        Log.d(TAG, "publishEvent: getMode = "+getMode().getValue());

        // Create new event in FireStore Database
        // The addition of this event in FireStore triggers the update of the list of events in the Mayor activity
        createEventInFireStore(event).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {

                    DocumentReference document = task.getResult();

                    // Build NEW Room Event
                    Event roomEvent = new Event(document.getId(),true, event.getEventFireStore());

                    // Create new Event iN Room DataBase
                    executor.execute(() -> {
                        // Create NEW Event in Room DataBase
                        eventDataRoomSource.createEvent(roomEvent);

                        // Delete OLD Event in Room DataBase
                        eventDataRoomSource.deleteEvent(event.getEventID());

                        // Finish Activity
                        mode.postValue(EventMode.FINISH);
                    });

                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    public void deleteEventInRoom(String eventID){
        executor.execute(() -> {
            eventDataRoomSource.deleteEvent(eventID);
        });
    }

    public Task<DocumentReference> createEventInFireStore(Event event) {
        Log.d(TAG, "createEventInFireStore: ");
        return eventDataFireStoreSource.createEventInFireStore(event.getEventFireStore());
    }

    // For Manage Dates
    public MutableLiveData<String> getStartDate() {
        return mStartDate;
    }
    public void setStartDate(String startDate) {
        this.mStartDate.setValue(startDate);
    }
    public MutableLiveData<String> getEndDate() {
        return mEndDate;
    }
    public void setEndDate(String endDate) {
        this.mEndDate.setValue(endDate);
    }

    // For Manage Photos
    public LiveData<ArrayList<String>> getPhotos() {
        return mPhotos;
    }
    public void addPhoto(String photo) {
        Log.d(TAG, "addPhoto() called with: photo = [" + photo + "]");
        ArrayList<String> l = mPhotos.getValue();
        l.add(photo);
        this.mPhotos.setValue(l);
    }
    public String getCurrentPhotoPath() {
        return mCurrentPhotoPath;
    }
    public void setCurrentPhotoPath(String currentPhotoPath) {
        this.mCurrentPhotoPath = currentPhotoPath;
    }
}
