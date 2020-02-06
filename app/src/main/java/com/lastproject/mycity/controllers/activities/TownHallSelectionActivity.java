package com.lastproject.mycity.controllers.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.lastproject.mycity.R;
import com.lastproject.mycity.controllers.fragments.FirstConnexionFragment;
import com.lastproject.mycity.injections.Injection;
import com.lastproject.mycity.injections.ViewModelFactory;
import com.lastproject.mycity.models.views.TownHallSelectionViewModel;
import com.lastproject.mycity.utils.Toolbox;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TownHallSelectionActivity extends AppCompatActivity {

    // For Debug
    private static final String TAG = TownHallSelectionActivity.class.getSimpleName();

    // Declare TownHallSelectionViewModel
    private TownHallSelectionViewModel mTownHallSelectionViewModel;

    // Adding @BindView in order to indicate to ButterKnife to get & serialise it
    @BindView(R.id.activity_town_hall_selection_cl) ConstraintLayout mConstraintLayout;

    // ---------------------------------------------------------------------------------------------
    //                                        METHODS
    // ---------------------------------------------------------------------------------------------
    public void startActivity(Class activityClass){Toolbox.startActivity(this,activityClass);}
    // ---------------------------------------------------------------------------------------------
    //                                        ENTRY POINT
    // ---------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_town_hall_selection);

        // Get & serialise all views
        ButterKnife.bind(this);

        // Configure ViewModel
        configureViewModel();

        // Configure And Show First Connexion fragment
        this.configureAndShowFirstConnexionFragment();
    }
    // ---------------------------------------------------------------------------------------------
    //                                        VIEW MODEL
    // ---------------------------------------------------------------------------------------------
    // Configure ViewModel
    private void configureViewModel() {
        ViewModelFactory modelFactory = Injection.provideViewModelFactory(this);
        mTownHallSelectionViewModel = ViewModelProviders.of(this, modelFactory)
                .get(TownHallSelectionViewModel.class);

        mTownHallSelectionViewModel.getViewAction().
                observe(this,new Observer<TownHallSelectionViewModel.ViewAction>() {
                    @Override
                    public void onChanged (TownHallSelectionViewModel.ViewAction viewAction){
                        if (viewAction == null) {
                            return;
                        }

                        switch (viewAction) {

                            case TOWN_HALL_NOT_FOUND:
                                Toolbox.showSnackBar(mConstraintLayout,"No Town Hall found in FireStore Database");
                                break;

                            case CALL_TOWN_HALL_ACTIVITY:
                                Toolbox.showSnackBar(mConstraintLayout,"CALL_TOWN_HALL_ACTIVITY");
                                startActivity(TownHallActivity.class);
                                break;

                            case FIRE_STORE_ERROR:
                                Toolbox.showSnackBar(mConstraintLayout,"error during the search of the town hall in the fireStore base");
                                break;

                            case FINISH_ACTIVITY:
                                break;
                        }
                    }
                });
    }
    public TownHallSelectionViewModel getTownHallSelectionViewModel() {
        return mTownHallSelectionViewModel;
    }
    // ---------------------------------------------------------------------------------------------
    //                                        FRAGMENTS
    // ---------------------------------------------------------------------------------------------
    public void configureAndShowFirstConnexionFragment() {
        Log.d(TAG, "configureAndShowFirstConnexionFragment: ");

        // Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        FirstConnexionFragment firstConnexionFragment = (FirstConnexionFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_town_hall_selection_fragment_first_connexion);

        if (firstConnexionFragment == null) {
            // Create new fragment
            firstConnexionFragment = FirstConnexionFragment.newInstance();
            // Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_town_hall_selection_fragment_first_connexion, firstConnexionFragment)
                    .commit();
        }
    }
}