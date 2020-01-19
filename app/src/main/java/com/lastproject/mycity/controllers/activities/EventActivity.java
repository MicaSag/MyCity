package com.lastproject.mycity.controllers.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;
import com.lastproject.mycity.R;
import com.lastproject.mycity.controllers.fragments.EventCreateFragment;
import com.lastproject.mycity.controllers.fragments.EventViewFragment;
import com.lastproject.mycity.injections.Injection;
import com.lastproject.mycity.injections.ViewModelFactory;
import com.lastproject.mycity.models.Event;
import com.lastproject.mycity.models.views.EventViewModel;
import com.lastproject.mycity.repositories.CurrentEventIDDataRepository;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventActivity extends AppCompatActivity {

    // For Debug
    private static final String TAG = EventActivity.class.getSimpleName();

    // Adding @BindView in order to indicate to ButterKnife to get & serialise it
    public @BindView(R.id.activity_event_constraint_layout) ConstraintLayout mConstraintLayout;
    public @BindView(R.id.toolbar) Toolbar mToolBar;

    // For toolBar configuration
    protected ActionBar mActionBar;

    // Declare EventViewModel
    private EventViewModel mEventViewModel;

    // Fragments
    Fragment mEventFragment = null;
    EventViewFragment mEventViewFragment;
    EventCreateFragment mEventCreateFragment;

    // ---------------------------------------------------------------------------------------------
    //                                        ENTRY POINT
    // ---------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        // Get & serialise all views
        ButterKnife.bind(this);

        // Configure ViewModel
        this.configureViewModel();

        // Retrieve caller data
        this.retrieveCallerData();
    }

    // ---------------------------------------------------------------------------------------------
    //                                        VIEW MODEL
    // ---------------------------------------------------------------------------------------------
    // Configure ViewModel
    private void configureViewModel() {
        Log.d(TAG, "configureViewModel: ");

        ViewModelFactory modelFactory = Injection.provideViewModelFactory(this);
        mEventViewModel = ViewModelProviders.of(this, modelFactory)
                .get(EventViewModel.class);

        // When the call mode of the activity is modified
        // the fragment corresponding to the transmitted mode is displayed
        mEventViewModel.getMode().observe(this, new Observer<EventViewModel.EventMode>() {
            @Override
            public void onChanged(EventViewModel.EventMode eventMode) {
                if (eventMode == null)  {
                    return;
                }

                switch (eventMode) {
                    case VIEW:
                        Snackbar.make(mConstraintLayout,"View MODE",Snackbar.LENGTH_LONG).show();
                        // Configure Mode
                        configureMode_VIEW();
                        break;

                    case CREATE:
                    case UPDATE:
                        Snackbar.make(mConstraintLayout,"Create/Update MODE",Snackbar.LENGTH_LONG).show();
                        // Configure Mode
                        configureMode_CREATEorUPDATE();
                        break;

                    case DELETE:
                        Snackbar.make(mConstraintLayout,"Delete MODE",Snackbar.LENGTH_LONG).show();
                        finish();
                        break;

                    case FINISH:
                        Snackbar.make(mConstraintLayout,"Finish",Snackbar.LENGTH_LONG).show();
                        finish();
                        break;
                }
            }
        });
        // When a message is generated
        mEventViewModel.getMessage().observe(this, new Observer<EventViewModel.EventMessage>() {
            @Override
            public void onChanged(EventViewModel.EventMessage eventMessage) {
                if (eventMessage == null)  {
                    return;
                }

                switch (eventMessage) {
                    case INVALID_INPUT:
                        Snackbar.make(mConstraintLayout,"Invalidate input",Snackbar.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }
    private void configureMode_VIEW() {
        Log.d(TAG, "ConfigureMode_VIEW: ");

        // Get and Observe Event In Room (no Observation)
        Log.d(TAG, "ConfigureMode_VIEW: mEventViewModel.getCurrentEventID() = "
                +mEventViewModel.getCurrentEventID().getValue());
        LiveData<Event> ldEvent = mEventViewModel.getEventInRoom(mEventViewModel.getCurrentEventID().getValue(),
                mEventViewModel.getCurrentUser().getUserID());
        ldEvent.observe(this, new Observer<Event>() {
            @Override
            public void onChanged(Event event) {
                Log.d(TAG, "ConfigureMode_VIEW : onChanged: event = " + event);
                Log.d(TAG, "ConfigureMode_VIEW : mEventViewModel.getCurrentEventID() = "
                        +mEventViewModel.getCurrentEventID().getValue());

                // If the event is not yet published in FireStore
                // we do not observe its changes in Room
                //if (event == null) {ldEvent.removeObserver(this);}
                //if (event != null)
                //{
                    if (!event.isPublished()) {
                        Log.d(TAG, "ConfigureMode_VIEW : onChanged: !event.isPublished()");
                        ldEvent.removeObserver(this);
                    }
                //}

                // Set Current Event in Model
                mEventViewModel.setCurrentEvent(event);

                // Configuring Toolbar
                configureToolbar();

                // configure the Event Fragment
                configureAndShowEventFragment();
            }
        });
    }

    private void configureMode_CREATEorUPDATE(){
        Log.d(TAG, "configureMode_CREATEorUPDATE: ");

        // Configuring Toolbar
        configureToolbar();

        // configure the Event Fragment
        configureAndShowEventFragment();
    }

    // To use in child fragments
    public EventViewModel getEventViewModel() {
        return mEventViewModel;
    }
    // ---------------------------------------------------------------------------------------------
    //                                         CALLER
    // ---------------------------------------------------------------------------------------------
    // Retrieve caller data
    private void retrieveCallerData() {
        Log.d(TAG, "retrieveCallerData: ");

        // Get Intent
        String mode = getIntent().getStringExtra(EventViewModel.MODE);
        Log.d(TAG, "retrieveCallerData: mode = "+mode);

        // Save Mode in the Model
        mEventViewModel.setMode(EventViewModel.EventMode.valueOf(mode));
    }
    // ---------------------------------------------------------------------------------------------
    //                                  TOOLBAR & MENU ITEM
    // ---------------------------------------------------------------------------------------------
    private void configureToolbar(){
        Log.d(TAG, "configureToolbar: ");
        //Set the toolbar
        setSupportActionBar(mToolBar);
        // Get a support ActionBar corresponding to this toolbar
        mActionBar = getSupportActionBar();

        // Enable the Up button
        assert mActionBar != null;
        mActionBar.setDisplayHomeAsUpEnabled(true);
    }
    // Configure toolbar Menu
    // And controls its display according to the event mode
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: ");

        // inflate menu from xml
        getMenuInflater().inflate(R.menu.menu_activity_event, menu);

        Log.d(TAG, "onCreateOptionsMenu: mEventViewModel.getMode() = "
                +mEventViewModel.getMode().getValue());
        Log.d(TAG, "onCreateOptionsMenu: mEventViewModel.getCurrentEvent().getValue() = "
                +mEventViewModel.getCurrentEvent().getValue());

        // Only a user of Mayor profile can have access to the "Publish" and "Update" buttons
        if (mEventViewModel.getCurrentUser().isMayor()) {
            // In CREATE or UPDATE Mode
            if (mEventViewModel.getMode().getValue() == EventViewModel.EventMode.CREATE ||
                    mEventViewModel.getMode().getValue() == EventViewModel.EventMode.UPDATE
            ) {
                menu.findItem(R.id.menu_activity_event_CREATE).setVisible(true);
                return true;
            }

            // In VIEW Mode
            if (mEventViewModel.getMode().getValue() == EventViewModel.EventMode.VIEW) {
                // Active UPDATE Option
                menu.findItem(R.id.menu_activity_event_UPDATE).setVisible(true);
                // If Event Not published, Active PUBLISH Option
                if (!mEventViewModel.getCurrentEvent().getValue().isPublished())
                    menu.findItem(R.id.menu_activity_event_PUBLISH).setVisible(true);
                return true;
            }
        }
        return true;
    }
    // ---------------------------------------------------------------------------------------------
    //                                        FRAGMENT
    // ---------------------------------------------------------------------------------------------
    private void configureAndShowEventFragment() {
        Log.d(TAG, "configureAndShowEventFragment: ");
        // Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        // Declare fragment
        mEventFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_event);

        // Remove Fragment if it exists
        if (mEventFragment != null) {
            Log.d(TAG, "configureAndShowEventFragment: mEventFragment = "+mEventFragment);
            getSupportFragmentManager().beginTransaction()
                    .remove(mEventFragment).commit();
        }

        switch (mEventViewModel.getMode().getValue()) {
            case VIEW:
                // Create new Event EDIT fragment
                mEventViewFragment = EventViewFragment.newInstance();
                mEventFragment = mEventViewFragment;
                break;
            case CREATE:
            case UPDATE:
                // Create new Event CREATE/UPDATE fragment
                mEventCreateFragment = EventCreateFragment.newInstance();
                mEventFragment = mEventCreateFragment;
                break;
            default:
                Snackbar.make(mConstraintLayout,"Event Fragment Required",Snackbar.LENGTH_LONG).show();
                break;
        }
        // Add eventFragment to FrameLayout container
        getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_event, mEventFragment)
                    .commit();
    }
    // ---------------------------------------------------------------------------------------------
    //                                          ACTIONS
    // ---------------------------------------------------------------------------------------------
    // Manage Menu Item in ToolBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: ");

        //Handle actions on menu items
        switch (item.getItemId()) {

            case R.id.menu_activity_event_CREATE:
                Log.d(TAG, "onOptionsItemSelected: CREATE");

                // Variable to allow recovery of data from the creative fragment
                EventCreateFragment eventCreateFragment = (EventCreateFragment)mEventFragment;

                // If CREATE Mode, Create Event in Local Room dataBase
                if (mEventViewModel.getMode().getValue() == EventViewModel.EventMode.CREATE) {
                    Log.d(TAG, "onOptionsItemSelected: EventViewModel.EventMode.CREATE");
                    mEventViewModel.createEventInRoom(this, eventCreateFragment.getEventFireStore());
                }

                // If UPDATE Mode, Update Event in Local Room dataBase
                if (mEventViewModel.getMode().getValue() == EventViewModel.EventMode.UPDATE) {
                    Log.d(TAG, "onOptionsItemSelected: EventViewModel.EventMode.UPDATE");
                    mEventViewModel.updateEventInRoom(this, eventCreateFragment.getEventFireStore());
                }

                return true;

            case R.id.menu_activity_event_PUBLISH:
                Log.d(TAG, "onOptionsItemSelected: PUBLISH");

                // Create/Update Event in FireBase
                mEventViewModel.publishEvent(mEventViewModel.getCurrentEvent().getValue());

                return true;

            case R.id.menu_activity_event_UPDATE:
                Log.d(TAG, "onOptionsItemSelected: UPDATE");

                // Update Event in Local Room dataBase
                mEventViewModel.setMode(EventViewModel.EventMode.UPDATE);

                return true;

            case R.id.menu_activity_event_DELETE:
                Log.d(TAG, "onOptionsItemSelected: DELETE");

                // Delete Event in Local Room dataBase
                mEventViewModel.deleteEventInRoom(CurrentEventIDDataRepository
                        .getInstance().getCurrentEventID().getValue());

                mEventViewModel.setMode(EventViewModel.EventMode.FINISH);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    // ---------------------------------------------------------------------------------------------
    //                              MANGE INTENTS RETURN
    // ---------------------------------------------------------------------------------------------
    // For Manage Intents Return
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult() called with: requestCode         = [" + requestCode + "], " +
                "resultCode = [" + resultCode + "], data = [" + data + "]");
        Log.d(TAG, "onActivityResult: EventViewModel.REQUEST_TAKE_PHOTO = "+EventViewModel.REQUEST_TAKE_PHOTO);
        Log.d(TAG, "onActivityResult: EventViewModel.REQUEST_IMAGE_GET  = "+EventViewModel.REQUEST_IMAGE_GET);
        Log.d(TAG, "onActivityResult: RESULT_OK                         = "+RESULT_OK);

        // Manage Photo Take
        if (requestCode == EventViewModel.REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult: mCurrentPhotoPath = "+mEventViewModel.getCurrentPhotoPath());
            mEventViewModel.addPhoto(mEventViewModel.getCurrentPhotoPath());
        }
        // Manage Photo Pick
        if (requestCode == EventViewModel.REQUEST_IMAGE_GET && resultCode == RESULT_OK) {
            Uri fullPhotoUri = data.getData();
            Log.d(TAG, "onActivityResult: fullPhotoUri = "+fullPhotoUri.toString());
            mEventViewModel.addPhoto(fullPhotoUri.toString());
        }
    }
}
