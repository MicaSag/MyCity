package com.lastproject.mycity.controllers.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import com.lastproject.mycity.R;
import com.lastproject.mycity.controllers.bases.BaseActivity;
import com.lastproject.mycity.injections.Injection;
import com.lastproject.mycity.injections.ViewModelFactory;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Michaël SAGOT on 24/12/2019.
 */

public class ModelActivity extends BaseActivity {

    // For Debug
    private static final String TAG = ModelActivity.class.getSimpleName();

    // Declare MayorViewModel
    //private MayorViewModel mMayorViewModel;

    // Adding @BindView in order to indicate to ButterKnife to get & serialise it
    //@BindView(R.id.activity_mayor_constraint_layout)
    ConstraintLayout mConstraintLayout;

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
    public View getConstraintLayout() {
        return mConstraintLayout;
    }

    // ---------------------------------------------------------------------------------------------
    //                                        ENTRY POINT
    // ---------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Configure ViewModel
        this.configureViewModel();
    }
    // ---------------------------------------------------------------------------------------------
    //                                        VIEW MODEL
    // ---------------------------------------------------------------------------------------------
    // Configure ViewModel
    private void configureViewModel(){
        ViewModelFactory modelFactory = Injection.provideViewModelFactory(this);
        //mMayorViewModel = ViewModelProviders.of(this, modelFactory)
        //        .get(MayorViewModel.class);
    }

    /*public MayorViewModel getMayorViewModel() {
        return mMayorViewModel;
    }*/
    // ---------------------------------------------------------------------------------------------
    //                                          ACTIONS
    // ---------------------------------------------------------------------------------------------
    /*@OnClick(R.id.activity_mayor_)
    public void onClickEmailLoginButton() {
        Log.d(TAG, "onClickEmailLoginButton: ²");
    }*/

    // ---------------------------------------------------------------------------------------------
    //                                          UI
    // ---------------------------------------------------------------------------------------------
    public void updateUI() {
    }
}
