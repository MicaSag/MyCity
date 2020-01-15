package com.lastproject.mycity.controllers.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.lastproject.mycity.R;
import com.lastproject.mycity.controllers.bases.BaseActivity;
import com.lastproject.mycity.controllers.fragments.FullScreenImageFragment;

import butterknife.BindView;

public class FullScreenImageActivity extends BaseActivity {

    // FOR Debug
    private static final String TAG = FullScreenImageActivity.class.getSimpleName();

    // Adding @BindView in order to indicate to ButterKnife to get & serialise it
    // - Get Coordinator Layout
    public @BindView(R.id.activity_full_screen_image_constraint_layout) ConstraintLayout mConstraintLayout;

    // Photo
    public String mPhoto;

    // Fragment
   FullScreenImageFragment mFullScreenImageFragment;

    // ---------------------------------------------------------------------------------------------
    //                                DECLARATION BASE METHODS
    // ---------------------------------------------------------------------------------------------
    // BASE METHOD Implementation
    // Get the activity layout
    // CALLED BY BASE METHOD 'onCreate(...)'
    @Override
    protected int getActivityLayout() {
        return R.layout.activity_full_screen_image;
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
        return R.menu.menu_activity_event;
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
    // ---------------------------------------------------------------------------------------------
    //                                        ENTRY POINT
    // ---------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve caller data
        this.retrieveCallerData();

        // Configure and Show Full Screen Image Fragment
        this.configureAndShowFullScreenImageFragment();
    }
    // ---------------------------------------------------------------------------------------------
    //                                         CALLER
    // ---------------------------------------------------------------------------------------------
    // Retrieve caller data
    private void retrieveCallerData() {

        // Get Intent
        mPhoto = getIntent().getStringExtra("PHOTO");
    }
    // ---------------------------------------------------------------------------------------------
    //                                        FRAGMENT
    // ---------------------------------------------------------------------------------------------
    private void configureAndShowFullScreenImageFragment() {
        Log.d(TAG, "configureAndShowFullScreenImageFragment: ");

        // Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        // Declare fragment
        mFullScreenImageFragment = (FullScreenImageFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_full_screen_image);

        if (mFullScreenImageFragment == null) {
            // Create new Full Screen Image  fragment
            mFullScreenImageFragment = FullScreenImageFragment.newInstance();

            // Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_full_screen_image, mFullScreenImageFragment)
                    .commit();
        }
    }
}
