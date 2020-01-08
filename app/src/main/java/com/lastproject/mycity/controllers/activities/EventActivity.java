package com.lastproject.mycity.controllers.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.lastproject.mycity.R;
import com.lastproject.mycity.controllers.bases.BaseActivity;
import com.lastproject.mycity.controllers.fragments.EventCreateFragment;
import com.lastproject.mycity.controllers.fragments.EventEditFragment;
import com.lastproject.mycity.injections.Injection;
import com.lastproject.mycity.injections.ViewModelFactory;
import com.lastproject.mycity.models.views.EventViewModel;
import com.lastproject.mycity.utils.Toolbox;

import butterknife.BindView;

public class EventActivity extends BaseActivity {

    // For Debug
    private static final String TAG = EventActivity.class.getSimpleName();

    // Adding @BindView in order to indicate to ButterKnife to get & serialise it
    // - Get Coordinator Layout
    @BindView(R.id.activity_details_constraint_layout) ConstraintLayout mConstraintLayout;

    // Declare EventViewModel
    private EventViewModel mEventViewModel;

    // Fragments
    Fragment mEventFragment = null;
    EventEditFragment mEventEditFragment;
    EventCreateFragment mEventCreateFragment;
    //EventUpdateFragment mEventUpdateFragment;

    // ---------------------------------------------------------------------------------------------
    //                                DECLARATION BASE METHODS
    // ---------------------------------------------------------------------------------------------
    // BASE METHOD Implementation
    // Get the activity layout
    // CALLED BY BASE METHOD 'onCreate(...)'
    @Override
    protected int getActivityLayout() {
        return R.layout.activity_event;
    }

    // BASE METHOD Implementation
    // Get the constraint layout
    // CALLED BY BASE METHOD
    @Override
    protected View getConstraintLayout() {
        return mConstraintLayout;
    }

    // BASE METHOD Implementation
    // Get the menu toolbar LayoutIf the screen has a
    // CALLED BY BASE METHOD
    @Override
    protected int getToolbarMenu() {
        return R.menu.menu_activity_details_event;
    }
    // ---------------------------------------------------------------------------------------------
    //                                OVERLOAD BASE METHODS
    // ---------------------------------------------------------------------------------------------
    protected void configureToolbar(){
        super.configureToolbar();
        Log.d(TAG, "configureToolbar: ");
        // Enable the Up button
        super.mActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();

        // If the width of the device is => 1280 then if the activity is reset during a rotation,
        // then it is closed
        //Log.d(TAG, "onResume: is_w1280 = "+getResources().getBoolean(R.bool.is_w1280));
        //if (getResources().getBoolean(R.bool.is_w1280)) finish();
    }
    // ---------------------------------------------------------------------------------------------
    //                                        ENTRY POINT
    // ---------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configuring Toolbar
        this.configureToolbar();

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
        ViewModelFactory modelFactory = Injection.provideViewModelFactory(this);
        mEventViewModel = ViewModelProviders.of(this, modelFactory)
                .get(EventViewModel.class);

        mEventViewModel.getMode().observe(this, this::configureAndShowEventFragment);
    }
    // ---------------------------------------------------------------------------------------------
    //                                         CALLER
    // ---------------------------------------------------------------------------------------------
    // Retrieve caller data
    private void retrieveCallerData() {

        // Get Intent
        String mode = getIntent().getStringExtra(EventViewModel.MODE);

        // Save Mode in the Model
        mEventViewModel.setMode(EventViewModel.EventMode.valueOf(mode));
    }
    // ---------------------------------------------------------------------------------------------
    //                                        FRAGMENT
    // ---------------------------------------------------------------------------------------------
    private void configureAndShowEventFragment(EventViewModel.EventMode mode) {
        // Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        // Declare fragment
        mEventFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_event);

        switch (mode) {
            case EDIT:
                // Create new Event EDIT fragment
                mEventEditFragment = EventEditFragment.newInstance();
                mEventFragment = mEventEditFragment;
                break;
            case CREATE:
                // Create new Event CREATE fragment
                mEventCreateFragment = EventCreateFragment.newInstance();
                mEventFragment = mEventCreateFragment;
                break;
            case UPDATE:
                // Create new Event UPDATE fragment
                //mEventUpdateFragment = EventUpdateFragment.newInstance();
                //mEventFragment = mEventUpdateFragment;
                break;
            default:
                showSnackBar("Event Fragment Required");
                break;
        }
        // Add eventFragment to FrameLayout container
        getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_event, mEventFragment)
                    .commit();
    }
}
