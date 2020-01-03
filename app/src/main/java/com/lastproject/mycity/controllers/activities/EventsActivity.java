package com.lastproject.mycity.controllers.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import com.lastproject.mycity.R;
import com.lastproject.mycity.adapter.eventslist.EventsListAdapter;
import com.lastproject.mycity.controllers.bases.BaseActivity;
import com.lastproject.mycity.controllers.fragments.EventDetailsFragment;
import com.lastproject.mycity.controllers.fragments.EventsListFragment;
import com.lastproject.mycity.injections.Injection;
import com.lastproject.mycity.injections.ViewModelFactory;
import com.lastproject.mycity.models.Event;
import com.lastproject.mycity.models.views.EventViewModel;
import com.lastproject.mycity.repositories.CurrentEventDataRepository;
import com.lastproject.mycity.utils.Toolbox;

import butterknife.BindView;


/**
 * Created by Michaël SAGOT on 24/12/2019.
 */

public class EventsActivity extends BaseActivity
                            implements  EventsListAdapter.OnEventClick {
    // For Debug
    private static final String TAG = EventsActivity.class.getSimpleName();

    // Declare EventViewModel
    private EventViewModel mEventViewModel;

    // Fragments Declarations
    private EventDetailsFragment mEventDetailsFragment;
    private EventsListFragment mEventsListFragment;

    // Adding @BindView in order to indicate to ButterKnife to get & serialise it
    public @BindView(R.id.toolbar) Toolbar mToolBar;
    public @BindView(R.id.activity_mayor_root) ConstraintLayout mConstraintLayout;

    // ---------------------------------------------------------------------------------------------
    //                                DECLARATION BASE METHODS
    // ---------------------------------------------------------------------------------------------
    // BASE METHOD Implementation
    // Get the activity layout
    // CALLED BY BASE METHOD 'onCreate(...)'
    @Override
    protected int getActivityLayout() {
        return R.layout.activity_events;
    }

    // BASE METHOD Implementation
    // Get the coordinator layout
    // CALLED BY BASE METHOD
    @Override
    public View getConstraintLayout() {
        return mConstraintLayout;
    }

    // BASE METHOD Implementation
    // Get the menu toolbar Layout
    // CALLED BY BASE METHOD
    @Override
    protected int getToolbarMenu() {
        return R.menu.menu_activity_mayor;
    }

    // ---------------------------------------------------------------------------------------------
    //                                        ENTRY POINT
    // ---------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Configure ViewModel
        this.configureViewModel();

        // Configuring Events List Fragment (left position on Tablet)
        this.configureAndShowEventsListFragment();

        // Configuring Event Details Fragment (right position on Tablet)
        this.configureAndShowEventDetailsFragment();
    }
    // ---------------------------------------------------------------------------------------------
    //                                        VIEW MODEL
    // ---------------------------------------------------------------------------------------------
    // Configure ViewModel
    private void configureViewModel(){
        ViewModelFactory modelFactory = Injection.provideViewModelFactory(this);
        mEventViewModel = ViewModelProviders.of(this, modelFactory)
                .get(EventViewModel.class);
    }

    public EventViewModel getMayorViewModel() {return mEventViewModel;}

    // ---------------------------------------------------------------------------------------------
    //                                        FRAGMENTS
    // ---------------------------------------------------------------------------------------------
    private void configureAndShowEventsListFragment() {
        // Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        mEventsListFragment = (EventsListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_events_list);

        if (mEventsListFragment == null) {
            // Create new events list fragment
            mEventsListFragment = EventsListFragment.newInstance();
            // Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_events_list, mEventsListFragment)
                    .commit();
        }
    }
    private void configureAndShowEventDetailsFragment() {
        // Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        mEventDetailsFragment = (EventDetailsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_event_details);

        // We only add DetailsFragment for wight 1280 (If found frame_layout_detail)
        if (mEventDetailsFragment == null && getResources().getBoolean(R.bool.is_w1280)) {
            // Create new main fragment
            mEventDetailsFragment = EventDetailsFragment.newInstance();
            // Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_event_details, mEventDetailsFragment)
                    .commit();
        }
    }

    // ---------------------------------------------------------------------------------------------
    //                                          ACTIONS
    // ---------------------------------------------------------------------------------------------
    @Override
    public void onEventClick(Event event) {
        Log.d(TAG, "onEventClick: ");
        CurrentEventDataRepository.getInstance().setCurrentEventID(event.getEventID());

        // If wight < 1280 then call DetailsEventActivity
        Log.d(TAG, "onEventClick: is_w1280 = "+getResources().getBoolean(R.bool.is_w1280));
        if (!getResources().getBoolean(R.bool.is_w1280))
            Toolbox.startActivity(this, DetailsEventActivity.class);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle actions on menu items
        /*switch (item.getItemId()) {
            case R.id.menu_activity_real_estate_manager_search:
                // Create a intent for call Activity
                Intent searchIntent = new Intent(this, SearchEstateActivity.class);

                // Go to SearchEstateActivity
                startActivityForResult(searchIntent, SEARCH_ACTIVITY_REQUEST_CODE);
                return true;
            case R.id.menu_activity_real_estate_manager_edit:
                // Create a intent for call Activity
                Intent updateIntent = new Intent(this, UpdateEstateActivity.class);

                // Go to CreateEstateActivity
                startActivityForResult(updateIntent, UPDATE_ACTIVITY_REQUEST_CODE);
                return true;
            case R.id.menu_activity_real_estate_manager_add:
                // Create a intent for call Activity
                Intent createIntent = new Intent(this, CreateEstateActivity.class);

                // Go to CreateEstateActivity
                startActivityForResult(createIntent, CREATE_ACTIVITY_REQUEST_CODE);
            default:
                return super.onOptionsItemSelected(item);
        }*/
        return true;
    }
    // ---------------------------------------------------------------------------------------------
    //                                          UI
    // ---------------------------------------------------------------------------------------------
}
