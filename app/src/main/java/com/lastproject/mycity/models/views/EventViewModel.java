package com.lastproject.mycity.models.views;

import android.app.Activity;
import android.location.Address;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lastproject.mycity.firebase.database.firestore.models.EventFireStore;
import com.lastproject.mycity.firebase.database.firestore.models.TownHallFireStore;
import com.lastproject.mycity.models.Event;
import com.lastproject.mycity.models.User;
import com.lastproject.mycity.repositories.CurrentEventIDDataRepository;
import com.lastproject.mycity.repositories.CurrentTownHallDataRepository;
import com.lastproject.mycity.repositories.CurrentUserDataRepository;
import com.lastproject.mycity.repositories.EventDataFireStoreRepository;
import com.lastproject.mycity.repositories.EventDataRoomRepository;
import com.lastproject.mycity.utils.Toolbox;

import org.threeten.bp.LocalDateTime;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
        PUBLISH,
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

    // --- CURRENT USER ---
    //
    public User getCurrentUser() {return CurrentUserDataRepository.getInstance().getCurrentUser();}
    public void setCurrentUser(User user) {CurrentUserDataRepository.getInstance().setCurrentUser(user);}

    // --- CURRENT TOWN HALL ---
    //
    public LiveData<TownHallFireStore> getCurrentTownHall() {return CurrentTownHallDataRepository.getInstance().getCurrentTownHall();}

    // Get Event of the Room DataBase
    public LiveData<Event> getEventInRoom(String eventId, String userID) {
        Log.d(TAG, "getEventInRoom: ");

        return eventDataRoomSource.getEvent(eventId, userID);
    }
    // --- CURRENT EVENT ID ---
    //
    @NonNull
    public LiveData<String> getCurrentEventID() {
        return CurrentEventIDDataRepository.getInstance().getCurrentEventID();
    }
    @NonNull
    public void setCurrentEventID(String eventID) {
        CurrentEventIDDataRepository.getInstance().setCurrentEventID(eventID);
    }
    // --- CURRENT EVENT ---
    //
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
                    getCurrentUser().getUserID(),
                    eventFireStore);

            // Event not published
            roomEvent.setPublished(false);

            // Event at Update
            roomEvent.setAtUpdate(true);

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
            Event roomEvent = new Event( eventID, getCurrentUser().getUserID(), eventFireStore);

            // Event not published
            roomEvent.setPublished(false);

            // Event at Create
            roomEvent.setAtCreate(true);

            // Change Event Current ID
            CurrentEventIDDataRepository.getInstance().setCurrentEventID(eventID);

            // Create Event in Room DataBase
            executor.execute(() -> {
                // Create event in Room DataBase
                eventDataRoomSource.createEvent(roomEvent);

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
            localAddress.add(complement);
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

    public void publishPhotos() {
        Log.d(TAG, "publishPhotos: ");

        // get current event
        Event event = getCurrentEvent().getValue();

        if (event.getPhotos().size() !=0) {
            int photoCount = 0;

            // Creation photos of the event in FireBase
            Log.d(TAG, "publishPhotos: event.getPhotos().size() = " + event.getPhotos().size());

            for (int i = 0; i < event.getPhotos().size(); i++) {
                Log.d(TAG, "publishPhotos: photoPath : " + event.getPhotos().get(i));
                Log.d(TAG, "publishPhotos: event.getPhotos().get(i).indexOf(\"room_\") = "
                        + event.getPhotos().get(i).indexOf("room_"));

                // only save new photos
                if (event.getPhotos().get(i).indexOf("firebasestorage") == -1) {
                    Log.d(TAG, "publishPhotos: i = " + i);
                    uploadPhotoInFireBase(i);
                } else photoCount++;
            }
            // If no photo is to be published, we publish the event
            Log.d(TAG, "publishPhotos: photoCount = "+photoCount);
            if (photoCount == event.getPhotos().size()) mode.postValue(EventMode.PUBLISH);
        } else  //if there is no photo for the event
                // we publish the event
                mode.postValue(EventMode.PUBLISH);
    }


    // Upload a picture in FireBase
    private void uploadPhotoInFireBase(int i) {
        Log.d(TAG, "uploadPhotoInFireBase: ");

        Uri photoURI;

        if (getCurrentEvent().getValue().getPhotos().get(i).indexOf("content://") != -1){
            photoURI = Uri.parse(getCurrentEvent().getValue().getPhotos().get(i));
        } else{
            String photoPath = getCurrentEvent().getValue().getPhotos().get(i);
            File f = new File(photoPath);
            photoURI = Uri.fromFile(f);
        }
        Log.d(TAG, "uploadPhotoInFireBase: photoURI = "+photoURI);
        String uuid = UUID.randomUUID().toString(); // GENERATE UNIQUE STRING
        // Save photo in Fire Storage
        StorageReference mImageRef = FirebaseStorage.getInstance().getReference(uuid);
        mImageRef.putFile(photoURI)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Log.d(TAG, "onSuccess: taskSnapshot.getMetadata().getReference().getDownloadUrl().toString() = "+
                                taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());

                        Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                String photoStringLink = uri.toString();
                                Log.d(TAG, "onSuccess: uri = "+photoStringLink);

                                // Update photo Reference in Current Event
                                getCurrentEvent().getValue().getPhotos().set(i, photoStringLink);

                                // If this is the last updated photo
                                // then we can create/update the event in Room and FireStore
                                if (i == getCurrentEvent().getValue().getPhotos().size() -1) {

                                    // Update Event in Room
                                    executor.execute(() -> {
                                        eventDataRoomSource.updateEvent(getCurrentEvent().getValue());

                                        mode.postValue(EventMode.PUBLISH);

                                    });
                                }
                            }
                        });
                    }
                });
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


        EventFireStore eventFireStore = new EventFireStore(
                event.getInseeID(),
                event.getTitle(),
                event.getDescription(),
                event.getPhotos(),
                event.getAddress(),
                event.getLocation(),
                event.getStartDate(),
                event.getEndDate(),
                event.isCanceled()
        );

        if (event.isAtCreate()) {
            // Create new event in FireStore Database
            // The addition of this event in FireStore triggers the update of the list of events in the Mayor activity
            createEventInFireStore(eventFireStore).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {
                    if (task.isSuccessful()) {

                        DocumentReference document = task.getResult();

                        // Build NEW Room Event
                        Event roomEvent = new Event(document.getId(), getCurrentUser().getUserID(), event);

                        // Event published
                        roomEvent.setPublished(true);

                        // More event to create
                        roomEvent.setAtCreate(false);

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
        if (event.isAtUpdate()) {
            // Update event in FireStore Database
            // The update of this event in FireStore triggers the update of the list of events in the Event activity
            updateEventInFireStore(event.getEventID(),eventFireStore).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                        // Event published
                        event.setPublished(true);

                        // More event to update
                        event.setAtUpdate(false);

                        // Create new Event In Room DataBase
                        executor.execute(() -> {

                            // The key exists, so the creation will replace the already existing event
                            eventDataRoomSource.createEvent(event);

                            // Finish Activity
                            mode.postValue(EventMode.FINISH);
                        });

                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }
    }

    public void deleteEventInRoom(String eventID){
        executor.execute(() -> {
            eventDataRoomSource.deleteEvent(eventID);
        });
    }

    public void updateEventInRoom(Event event){
        Log.d(TAG, "updateEventInRoom: ");
        executor.execute(() -> {
            eventDataRoomSource.updateEvent(event);
        });
    }

    public Task<DocumentReference> createEventInFireStore(EventFireStore eventFireStore) {
        Log.d(TAG, "createEventInFireStore: ");
        return eventDataFireStoreSource.createEventInFireStore(eventFireStore);
    }

    public Task<Void> updateEventInFireStore(String eventID, EventFireStore eventFireStore) {
        Log.d(TAG, "updateEventInFireStore: ");
        return eventDataFireStoreSource.updateEventInFireStore(eventID, eventFireStore);
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
    public void setPhotos(ArrayList<String> photos) {
        Log.d(TAG, "setPhotos: ");
        this.mPhotos.setValue(photos);
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
