package com.lastproject.mycity.controllers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.navigation.NavigationView;
import com.lastproject.mycity.R;
import com.lastproject.mycity.adapter.eventslist.EventsListAdapter;
import com.lastproject.mycity.controllers.bases.BaseActivity;
import com.lastproject.mycity.controllers.fragments.EventsListFragment;
import com.lastproject.mycity.controllers.fragments.TownHallFragment;
import com.lastproject.mycity.injections.Injection;
import com.lastproject.mycity.injections.ViewModelFactory;
import com.lastproject.mycity.models.Event;
import com.lastproject.mycity.models.views.EventViewModel;
import com.lastproject.mycity.models.views.TownHallViewModel;
import com.lastproject.mycity.repositories.CurrentEventIDDataRepository;
import com.lastproject.mycity.utils.Toolbox;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Michaël SAGOT on 28/12/2019.
 */

public class TownHallActivity extends BaseActivity implements  NavigationView.OnNavigationItemSelectedListener,
                                                            EventsListAdapter.OnEventClick {

    // For Debug
    private static final String TAG = TownHallActivity.class.getSimpleName();

    // Declare TownHallViewModel
    private TownHallViewModel mTownHallViewModel;

    // Fragments Declarations
    private TownHallFragment mTownHallFragment;
    private EventsListFragment mEventsListFragment;


    // Adding @BindView in order to indicate to ButterKnife to get & serialise it
    public @BindView(R.id.toolbar) Toolbar mToolBar;
    public @BindView(R.id.activity_town_hall_root) LinearLayout mRoot;
    public @BindView(R.id.activity_mayor_drawer_layout) DrawerLayout mDrawerLayout;
    public @BindView(R.id.activity_mayor_nav_view) NavigationView mNavigationView;
    public @BindView(R.id.activity_town_hall_fragment_events_list) FrameLayout mFragmentEventList;

    // ---------------------------------------------------------------------------------------------
    //                                DECLARATION BASE METHODS
    // ---------------------------------------------------------------------------------------------
    // BASE METHOD Implementation
    // Get the activity layout
    // CALLED BY BASE METHOD 'onCreate(...)'
    @Override
    protected int getActivityLayout() {
        return R.layout.activity_townhall;
    }

    // BASE METHOD Implementation
    // Get the coordinator layout
    // CALLED BY BASE METHOD
    @Override
    protected View getConstraintLayout() {
        return mRoot;
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

        // Search and Show Town Hall Fragment
        mTownHallViewModel.searchAndShowTownHall();

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
        mTownHallViewModel = new ViewModelProvider(this, modelFactory)
                .get(TownHallViewModel.class);

        mTownHallViewModel.getViewAction().
                observe(this,new Observer<TownHallViewModel.ViewAction>() {
                    @Override
                    public void onChanged (TownHallViewModel.ViewAction viewAction){
                        if (viewAction == null) {
                            return;
                        }

                        switch (viewAction) {

                            case SHOW_TOWN_HALL_FRAGMENT:
                                showSnackBar("SHOW_TOWN_HALL_FRAGMENT");
                                configureAndShowTownHallFragment();
                                break;

                            case FIRE_STORE_ERROR:
                                showSnackBar("error during the search of the town hall in the fireStore base");
                                break;

                            case FINISH_ACTIVITY:
                                break;
                        }
                    }
                });
    }

    public TownHallViewModel getTownHallViewModel() {return mTownHallViewModel;}

    // ---------------------------------------------------------------------------------------------
    //                                        FRAGMENTS
    // ---------------------------------------------------------------------------------------------
    public void configureAndShowTownHallFragment() {
        Log.d(TAG, "configureAndShowTownHallFragment: ");

        // Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.activity_town_hall_fragment_details);

        // if the fragment does not yet exist, we create it.
        // if we pass again in this part of code and that the fragment already exists,
        // then it is that there has been a rotation of the screen
        if (fragment == null) {
            // Create new fragment
            mTownHallFragment = mTownHallFragment.newInstance();
            // Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_town_hall_fragment_details, mTownHallFragment)
                    .commit();
        }

        // Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        Fragment fragmentEventsList = getSupportFragmentManager().findFragmentById(R.id.activity_town_hall_fragment_events_list);

        // if the fragment does not yet exist, we create it.
        // if we pass again in this part of code and that the fragment already exists,
        // then it is that there has been a rotation of the screen
        if (fragmentEventsList == null) {
            mEventsListFragment = mEventsListFragment.newInstance();
            // Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_town_hall_fragment_events_list, mEventsListFragment)
                    .commit();
        }
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
        Log.d(TAG, "configureNavigationMenuItem: ");
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
        CurrentEventIDDataRepository.getInstance().setCurrentEventID(event.getEventID());

        // Call EventActivity if event not canceled
        if (!event.isCanceled())
            Toolbox.startActivity(  this,
                                    EventActivity.class,
                                    EventViewModel.MODE,
                                    EventViewModel.EventMode.VIEW.name());
    }
}
