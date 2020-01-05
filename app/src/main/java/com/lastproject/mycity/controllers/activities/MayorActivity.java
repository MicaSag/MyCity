package com.lastproject.mycity.controllers.activities;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.lastproject.mycity.R;
import com.lastproject.mycity.adapter.eventslist.EventsListAdapter;
import com.lastproject.mycity.controllers.bases.BaseActivity;
import com.lastproject.mycity.controllers.fragments.EventsListFragment;
import com.lastproject.mycity.controllers.fragments.FirstConnexionFragment;
import com.lastproject.mycity.controllers.fragments.TownHallFragment;
import com.lastproject.mycity.firebase.database.firestore.models.TownHall;
import com.lastproject.mycity.injections.Injection;
import com.lastproject.mycity.injections.ViewModelFactory;
import com.lastproject.mycity.models.Event;
import com.lastproject.mycity.models.views.MayorViewModel;
import com.lastproject.mycity.repositories.CurrentEventDataRepository;
import com.lastproject.mycity.utils.Toolbox;

import butterknife.BindView;

/**
 * Created by Michaël SAGOT on 28/12/2019.
 */

public class MayorActivity extends BaseActivity implements  NavigationView.OnNavigationItemSelectedListener,
                                                            EventsListAdapter.OnEventClick {

    // For Debug
    private static final String TAG = MayorActivity.class.getSimpleName();

    // Declare MayorViewModel
    private MayorViewModel mMayorViewModel;

    // Fragments Declarations
    private FirstConnexionFragment mFirstConnexionFragment;
    private TownHallFragment mTownHallFragment;
    private EventsListFragment mEventsListFragment;


    // Adding @BindView in order to indicate to ButterKnife to get & serialise it
    public @BindView(R.id.toolbar) Toolbar mToolBar;
    public @BindView(R.id.activity_mayor_root) ConstraintLayout mConstraintLayout;
    public @BindView(R.id.activity_mayor_drawer_layout) DrawerLayout mDrawerLayout;
    public @BindView(R.id.activity_mayor_nav_view) NavigationView mNavigationView;
    public @BindView(R.id.activity_mayor_guideline_horizontal_50) Guideline mGuideLine50;
    public @BindView(R.id.activity_mayor_guideline_horizontal_70) Guideline mGuideLine70;
    public @BindView(R.id.activity_mayor_fragment_events_list) FrameLayout mFragmentEventList;

    // ---------------------------------------------------------------------------------------------
    //                                DECLARATION BASE METHODS
    // ---------------------------------------------------------------------------------------------
    // BASE METHOD Implementation
    // Get the activity layout
    // CALLED BY BASE METHOD 'onCreate(...)'
    @Override
    protected int getActivityLayout() {
        return R.layout.activity_mayor;
    }

    // BASE METHOD Implementation
    // Get the coordinator layout
    // CALLED BY BASE METHOD
    @Override
    protected View getConstraintLayout() {
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

        // Configure the Navigation Drawer
        this.configureDrawerLayout();
        this.configureNavigationView();
        this.configureNavigationMenuItem();

        // display switching according to the progress of the mayor's profile
        this.switchingDisplay();

        /*ConstraintLayout.LayoutParams params =
                (ConstraintLayout.LayoutParams)mFragmentEventList.getLayoutParams();
        params.topToBottom = mTownHallFragment.mHoursMCV.getId();
        mFragmentEventList.requestLayout();*/
    }
    // ---------------------------------------------------------------------------------------------
    //                                        VIEW MODEL
    // ---------------------------------------------------------------------------------------------
    // Configure ViewModel
    private void configureViewModel() {
        ViewModelFactory modelFactory = Injection.provideViewModelFactory(this);
        mMayorViewModel = ViewModelProviders.of(this, modelFactory)
                .get(MayorViewModel.class);
    }

    public MayorViewModel getMayorViewModel() {return mMayorViewModel;}

    // ---------------------------------------------------------------------------------------------
    //                                         DISPLAY
    // ---------------------------------------------------------------------------------------------
    public void switchingDisplay() {
        Log.d(TAG, "switchingDisplay: ");
        if (mMayorViewModel.getCurrentMayor().getMayorFireStore().getTownHallID().equals("")){
            Log.d(TAG, "switchingDisplay: First Connexion");
            // Show First Connexion Fragment
            configureAndShowFirstConnexionFragment();
        } else {
            Log.d(TAG, "switchingDisplay: Go To Town Hall _ townHallID = "
                    +mMayorViewModel.getCurrentMayor().getMayorFireStore().getTownHallID());
            // retrieves town hall data from the Fire Store and stores it in the view Model
            mMayorViewModel.getTownHallByTownHallID(mMayorViewModel
                                            .getCurrentMayor().getMayorFireStore().getTownHallID())
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            Log.d(TAG, "onComplete: ");
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                TownHall townHall = document.toObject(TownHall.class);

                                // Set Current TownHall
                                mMayorViewModel.setCurrentTownHall(townHall);
                                Log.d(TAG, "onComplete: townHall = "+mMayorViewModel.getCurrentTownHall().getValue());

                                // Show Town Hall Fragment
                                configureAndShowTownHallFragment();
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
    }

    // ---------------------------------------------------------------------------------------------
    //                                        FRAGMENTS
    // ---------------------------------------------------------------------------------------------
    public void configureAndShowFirstConnexionFragment() {
        Log.d(TAG, "configureAndShowFirstConnexionFragment: ");
        // Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        mFirstConnexionFragment = (FirstConnexionFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_mayor_fragment);

        if (mFirstConnexionFragment == null) {
            // Create new fragment
            mFirstConnexionFragment = FirstConnexionFragment.newInstance();
            // Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_mayor_fragment, mFirstConnexionFragment)
                    .commit();
        }
    }

    public void configureAndShowTownHallFragment() {
        Log.d(TAG, "configureAndShowTownHallFragment: ");
        // Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.activity_mayor_fragment);

        // if a fragment already exists, then we remove it
        if (fragment != null) getSupportFragmentManager().beginTransaction().remove(fragment).commit();

        // Create new fragment
        mTownHallFragment = mTownHallFragment.newInstance();
        // Add it to FrameLayout container
        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_mayor_fragment, mTownHallFragment)
                .commit();

        // Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        Fragment fragmentEventsList = getSupportFragmentManager().findFragmentById(R.id.activity_mayor_fragment_events_list);

        // if a fragment already exists, then we remove it
        if (fragmentEventsList != null) getSupportFragmentManager().beginTransaction().remove(fragmentEventsList).commit();

        mEventsListFragment = mEventsListFragment.newInstance();
        // Add it to FrameLayout container
        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_mayor_fragment_events_list, mEventsListFragment)
                .commit();
    }
    // ---------------------------------------------------------------------------------------------
    //                                     NAVIGATION DRAWER
    // ---------------------------------------------------------------------------------------------
    // >> CONFIGURATION <-------
    // Configure Drawer Layout and connects him the ToolBar and the NavigationView
    private void configureDrawerLayout() {
        Log.d(TAG, "configureDrawerLayout: ");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolBar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // Configure NavigationView
    private void configureNavigationView() {
        Log.d(TAG, "configureNavigationView: ");
        // Subscribes to listen the navigationView
        mNavigationView.setNavigationItemSelectedListener(this);
        // Mark as selected the menu item
        this.mNavigationView.getMenu().getItem(0).setChecked(true);
    }

    // Configure NavigationView
    private void configureNavigationMenuItem() {
        //Disable tint icons
        this.mNavigationView.setItemIconTintList(null);
    }
    // ---------------------------------------------------------------------------------------------
    //                                          ACTIONS
    // ---------------------------------------------------------------------------------------------
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "onNavigationItemSelected: ");

        // Handle Navigation Item Click
        int id = item.getItemId();
/*
        switch (id) {
            case R.id.activity_real_estate_manager_map:
                Utils.startActivity(this,MapActivity.class);
                break;
            case R.id.activity_real_estate_manager_search:
                // Create a intent for call Activity
                Intent searchIntent = new Intent(this, SearchEstateActivity.class);
                // Go to SearchEstateActivity
                startActivityForResult(searchIntent, SEARCH_ACTIVITY_REQUEST_CODE);
                break;
            default:
                break;
        }*/
        // Close menu drawer
        this.mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
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
    @Override
    public void onEventClick(Event event) {
        Log.d(TAG, "onEventClick: ");
        CurrentEventDataRepository.getInstance().setCurrentEventID(event.getEventID());

        // Call DetailsEventActivity if event not canceled
        if (!event.isCanceled()) Toolbox.startActivity(this, DetailsEventActivity.class);
    }
    // ---------------------------------------------------------------------------------------------
    //                                          UI
    // ---------------------------------------------------------------------------------------------
}
