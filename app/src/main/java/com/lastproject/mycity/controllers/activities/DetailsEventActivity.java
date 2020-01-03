package com.lastproject.mycity.controllers.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.lastproject.mycity.R;
import com.lastproject.mycity.controllers.bases.BaseActivity;
import com.lastproject.mycity.controllers.fragments.EventDetailsFragment;

import butterknife.BindView;

public class DetailsEventActivity extends BaseActivity {

    private static final String TAG = DetailsEventActivity.class.getSimpleName();

    // Adding @BindView in order to indicate to ButterKnife to get & serialise it
    // - Get Coordinator Layout
    @BindView(R.id.activity_details_constraint_layout)
    ConstraintLayout mConstraintLayout;

    // ---------------------------------------------------------------------------------------------
    //                                DECLARATION BASE METHODS
    // ---------------------------------------------------------------------------------------------
    // BASE METHOD Implementation
    // Get the activity layout
    // CALLED BY BASE METHOD 'onCreate(...)'
    @Override
    protected int getActivityLayout() {
        return R.layout.activity_details_event;
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

        // Configure EstateDetailsFragment
        this.configureAndShowEstateDetailsFragment();
    }
    // ---------------------------------------------------------------------------------------------
    //                                        FRAGMENT
    // ---------------------------------------------------------------------------------------------
    private void configureAndShowEstateDetailsFragment() {
        // Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        // Declare fragment
        EventDetailsFragment mEventDetailsFragment = (EventDetailsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_event_details);

        // Create new Event Details fragment
        mEventDetailsFragment = EventDetailsFragment.newInstance();
        // Add it to FrameLayout container
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_event_details, mEventDetailsFragment)
                .commit();
    }
}
