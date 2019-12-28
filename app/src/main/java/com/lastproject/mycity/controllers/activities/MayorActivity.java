package com.lastproject.mycity.controllers.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.navigation.NavigationView;
import com.lastproject.mycity.R;
import com.lastproject.mycity.controllers.bases.BaseActivity;
import com.lastproject.mycity.controllers.fragments.FirstConnexionFragment;
import com.lastproject.mycity.controllers.fragments.TownHallFragment;
import com.lastproject.mycity.controllers.fragments.UserProfileChoiceDialogFragment;
import com.lastproject.mycity.firebase.database.firestore.models.Mayor;
import com.lastproject.mycity.injections.Injection;
import com.lastproject.mycity.injections.ViewModelFactory;
import com.lastproject.mycity.models.views.MayorViewModel;
import com.lastproject.mycity.network.retrofit.models.Insee;
import com.lastproject.mycity.network.retrofit.models.townhall.TownHall;
import com.lastproject.mycity.network.retrofit.streams.InseeListStreams;
import com.lastproject.mycity.network.retrofit.streams.TownHallStreams;
import com.lastproject.mycity.utils.Toolbox;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by Michaël SAGOT on 28/12/2019.
 */

public class MayorActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    // For Debug
    private static final String TAG = MayorActivity.class.getSimpleName();

    // Declare MayorViewModel
    private MayorViewModel mMayorViewModel;

    // Fragments Declarations
    private FirstConnexionFragment mFirstConnexionFragment;
    private TownHallFragment mTownHallFragment;


    // Adding @BindView in order to indicate to ButterKnife to get & serialise it
    public @BindView(R.id.toolbar) Toolbar mToolBar;
    public @BindView(R.id.activity_mayor_root) ConstraintLayout mConstraintLayout;
    public @BindView(R.id.activity_mayor_drawer_layout) DrawerLayout mDrawerLayout;
    public @BindView(R.id.activity_mayor_nav_view) NavigationView mNavigationView;

    //FOR DATA
    private Disposable disposable;

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

        // Get caller's intent
        this.getCallerIntent();

        // Configure the Navigation Drawer
        this.configureDrawerLayout();
        this.configureNavigationView();
        this.configureNavigationMenuItem();

        // display switching according to the progress of the mayor's profile
        this.switchingDisplay();

        //this.getInseeList();
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

    public MayorViewModel getMayorViewModel() {
        return mMayorViewModel;
    }

    // ---------------------------------------------------------------------------------------------
    //                                        CALLER INTENT
    // ---------------------------------------------------------------------------------------------
    // Get caller's intent
    private void getCallerIntent() {
        Log.d(TAG, "getCallerIntent: ");

        // Save Mayor in the Model
        Mayor mayor = getIntent().getExtras().getParcelable("mayor");
        Log.d(TAG, "getCallerIntent: mayor = "+mayor);
        mMayorViewModel.setMayorInModel(mayor);
    }

    // ---------------------------------------------------------------------------------------------
    //                                         DISPLAY
    // ---------------------------------------------------------------------------------------------
    public void switchingDisplay() {
        Log.d(TAG, "switchingDisplay: ");
        if (mMayorViewModel.getMayorInModel().getInseeID().equals(""))
            configureAndShowFirstConnexionFragment();
        else configureAndShowTownHallFragment();
    }

    // ---------------------------------------------------------------------------------------------
    //                                        FRAGMENTS
    // ---------------------------------------------------------------------------------------------
    private void configureAndShowFirstConnexionFragment() {
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

    private void configureAndShowTownHallFragment() {
        Log.d(TAG, "configureAndShowTownHallFragment: ");
        // Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        mTownHallFragment = (TownHallFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_events_list);

        if (mTownHallFragment == null) {
            // Create new fragment
            mTownHallFragment = mTownHallFragment.newInstance();
            // Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_mayor_fragment, mTownHallFragment)
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
    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    // Execute our Stream
    private void getInseeList() {

        // Execute the stream subscribing to Observable defined inside GithubStream
        this.disposable = InseeListStreams.streamFetchInseeList("BAILL").subscribeWith(new DisposableObserver<List<Insee>>() {
            @Override
            public void onNext(List<Insee> inseeList) {
                Log.e("TAG", "On Next");
                // Update UI with list of users
                displayInseeList(inseeList);
                getTownHallInformation(inseeList.get(0).getCode());
                configureAutoCompleteInseeList(inseeList);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "On Error" + Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.e("TAG", "On Complete !!");
            }
        });
    }


    // Execute our Stream
    private void getTownHallInformation(String insee) {

        // Execute the stream subscribing to Observable defined inside GithubStream
        this.disposable = TownHallStreams.streamFetchTownHall(insee).subscribeWith(new DisposableObserver<TownHall>() {
            @Override
            public void onNext(TownHall townHall) {
                Log.e("TAG", "On Next");
                // Update UI with list of users
                displayTownHall(townHall);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "On Error" + Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.e("TAG", "On Complete !!");
            }
        });
    }

    // ---------------------------------------------------------------------------------------------
    //                              AUTOCOMPLETE TYPE CONFIGURATION
    // ---------------------------------------------------------------------------------------------
    private void configureAutoCompleteInseeList(List<Insee> inseeList) {
        Log.d(TAG, "configureAutoCompleteType: ");


        ArrayList<String> ar = new ArrayList<>();

        for (Insee insee : inseeList) {
            ar.add(insee.getCodesPostaux().get(0) + " " + insee.getNom());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.dropdown_towns,
                ar);
        //mTowns.setAdapter(adapter);
    }

    // ---------------------------------------------------------------------------------------------
    //                                          UI
    // ---------------------------------------------------------------------------------------------
    public void displayTownHall(TownHall townHall) {

        Log.d(TAG, "displayTownHall: TownHall = " + townHall.getFeatures().get(0).getProperties().getId());
        Log.d(TAG, "displayTownHall: TownHall = " + townHall.getFeatures().get(0).getProperties().getPivotLocal());
        Log.d(TAG, "displayTownHall: TownHall = " + townHall.getFeatures().get(0).getProperties().getNom());
        Log.d(TAG, "displayTownHall: TownHall = " + townHall.getFeatures().get(0).getGeometry().getCoordinates().get(0));
        Log.d(TAG, "displayTownHall: TownHall = " + townHall.getFeatures().get(0).getGeometry().getCoordinates().get(1));
        Log.d(TAG, "displayTownHall: TownHall = " + townHall.getFeatures().get(0).getProperties()
                .getAdresses().get(0).getLignes().size());
        Log.d(TAG, "displayTownHall: TownHall = " + townHall.getFeatures().get(0).getProperties()
                .getAdresses().get(0).getLignes());
        Log.d(TAG, "displayTownHall: TownHall = " + townHall.getFeatures().get(0).getProperties()
                .getAdresses().get(0).getCodePostal());
        Log.d(TAG, "displayTownHall: TownHall = " + townHall.getFeatures().get(0).getProperties()
                .getAdresses().get(0).getCommune());
        Log.d(TAG, "displayTownHall: TownHall = " + townHall.getFeatures().get(0).getProperties()
                .getHoraires().size());
        Log.d(TAG, "displayTownHall: TownHall = " + townHall.getFeatures().get(0).getProperties()
                .getHoraires().get(0).getDu());
        Log.d(TAG, "displayTownHall: TownHall = " + townHall.getFeatures().get(0).getProperties()
                .getHoraires().get(0).getAu());
        Log.d(TAG, "displayTownHall: TownHall = " + townHall.getFeatures().get(0).getProperties()
                .getHoraires().get(0).getHeures().get(0).getDe());
        Log.d(TAG, "displayTownHall: TownHall = " + townHall.getFeatures().get(0).getProperties()
                .getHoraires().get(0).getHeures().get(0).getA());
        Log.d(TAG, "displayTownHall: TownHall = " + townHall.getFeatures().get(0).getProperties()
                .getEmail());
        Log.d(TAG, "displayTownHall: TownHall = " + townHall.getFeatures().get(0).getProperties()
                .getTelephone());
        Log.d(TAG, "displayTownHall: TownHall = " + townHall.getFeatures().get(0).getProperties()
                .getUrl());

    }

    public void displayInseeList(List<Insee> inseeList) {

        for (Insee insee : inseeList) {
            Log.d(TAG, "displayInseeList: ville = " + insee.getNom());
            Log.d(TAG, "displayInseeList: code  = " + insee.getCode());
            Log.d(TAG, "displayInseeList: code Postaux  = " + insee.getCodesPostaux().get(0));
        }
    }

    private void openUserTypeChoiceDialog() {
        UserProfileChoiceDialogFragment userTypeChoiceDialog = new UserProfileChoiceDialogFragment();
        userTypeChoiceDialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme);
        userTypeChoiceDialog.show(getSupportFragmentManager(), " userTypeChoiceDialog");
    }
    // ---------------------------------------------------------------------------------------------
    //                                      DESTROY ACTIVITY
    // ---------------------------------------------------------------------------------------------

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    private void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }
}
