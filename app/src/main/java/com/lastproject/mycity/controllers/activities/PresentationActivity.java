package com.lastproject.mycity.controllers.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import com.lastproject.mycity.R;
import com.lastproject.mycity.controllers.bases.BaseActivity;
import com.lastproject.mycity.controllers.fragments.UserProfileChoiceDialogFragment;
import com.lastproject.mycity.network.retrofit.models.Insee;
import com.lastproject.mycity.network.retrofit.models.townhall.TownHall;
import com.lastproject.mycity.network.retrofit.streams.InseeListStreams;
import com.lastproject.mycity.network.retrofit.streams.TownHallStreams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by Michaël SAGOT on 08/12/2019.
 */

public class PresentationActivity extends BaseActivity {

    // For Debug
    private static final String TAG = PresentationActivity.class.getSimpleName();

    // Adding @BindView in order to indicate to ButterKnife to get & serialise it
    @BindView(R.id.activity_presentation_constraint_layout) ConstraintLayout mConstraintLayout;
    @BindView(R.id.activity_presentation_auto_towns) AutoCompleteTextView mTowns;

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
        return R.layout.activity_presentation;
    }

    // BASE METHOD Implementation
    // Get the coordinator layout
    // CALLED BY BASE METHOD
    @Override
    protected View getConstraintLayout() {
        return mConstraintLayout;
    }

    // ---------------------------------------------------------------------------------------------
    //                                        ENTRY POINT
    // ---------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getInseeList();

    }
    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    // Execute our Stream
    private void getInseeList(){

        // Execute the stream subscribing to Observable defined inside GithubStream
        this.disposable = InseeListStreams.streamFetchInseeList("BAILL").subscribeWith(new DisposableObserver<List<Insee>>() {
            @Override
            public void onNext(List<Insee> inseeList) {
                Log.e("TAG","On Next");
                // Update UI with list of users
                displayInseeList(inseeList);
                getTownHallInformation(inseeList.get(0).getCode());
                configureAutoCompleteInseeList(inseeList);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG","On Error"+Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.e("TAG","On Complete !!");
            }
        });
    }


    // Execute our Stream
    private void getTownHallInformation(String insee){

        // Execute the stream subscribing to Observable defined inside GithubStream
        this.disposable = TownHallStreams.streamFetchTownHall(insee).subscribeWith(new DisposableObserver<TownHall>() {
            @Override
            public void onNext(TownHall townHall) {
                Log.e("TAG","On Next");
                // Update UI with list of users
                displayTownHall(townHall);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG","On Error"+Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.e("TAG","On Complete !!");
            }
        });
    }
    // ---------------------------------------------------------------------------------------------
    //                              AUTOCOMPLETE TYPE CONFIGURATION
    // ---------------------------------------------------------------------------------------------
    private void configureAutoCompleteInseeList(List<Insee> inseeList) {
        Log.d(TAG, "configureAutoCompleteType: ");


        ArrayList<String> ar = new ArrayList<>();

        for(Insee insee : inseeList) {
            ar.add(insee.getCodesPostaux().get(0) + " "+insee.getNom());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.dropdown_towns,
                ar);
        mTowns.setAdapter(adapter);
    }
    // ---------------------------------------------------------------------------------------------
    //                                          UI
    // ---------------------------------------------------------------------------------------------
    public void displayTownHall(TownHall townHall) {

        Log.d(TAG, "displayTownHall: TownHall = "+townHall.getFeatures().get(0).getProperties().getId());
        Log.d(TAG, "displayTownHall: TownHall = "+townHall.getFeatures().get(0).getProperties().getPivotLocal());
        Log.d(TAG, "displayTownHall: TownHall = "+townHall.getFeatures().get(0).getProperties().getNom());
        Log.d(TAG, "displayTownHall: TownHall = "+townHall.getFeatures().get(0).getGeometry().getCoordinates().get(0));
        Log.d(TAG, "displayTownHall: TownHall = "+townHall.getFeatures().get(0).getGeometry().getCoordinates().get(1));
        Log.d(TAG, "displayTownHall: TownHall = "+townHall.getFeatures().get(0).getProperties()
                .getAdresses().get(0).getLignes().size());
        Log.d(TAG, "displayTownHall: TownHall = "+townHall.getFeatures().get(0).getProperties()
                .getAdresses().get(0).getLignes());
        Log.d(TAG, "displayTownHall: TownHall = "+townHall.getFeatures().get(0).getProperties()
                .getAdresses().get(0).getCodePostal());
        Log.d(TAG, "displayTownHall: TownHall = "+townHall.getFeatures().get(0).getProperties()
                .getAdresses().get(0).getCommune());
        Log.d(TAG, "displayTownHall: TownHall = "+townHall.getFeatures().get(0).getProperties()
                .getHoraires().size());
        Log.d(TAG, "displayTownHall: TownHall = "+townHall.getFeatures().get(0).getProperties()
                .getHoraires().get(0).getDu());
        Log.d(TAG, "displayTownHall: TownHall = "+townHall.getFeatures().get(0).getProperties()
                .getHoraires().get(0).getAu());
        Log.d(TAG, "displayTownHall: TownHall = "+townHall.getFeatures().get(0).getProperties()
                .getHoraires().get(0).getHeures().get(0).getDe());
        Log.d(TAG, "displayTownHall: TownHall = "+townHall.getFeatures().get(0).getProperties()
                .getHoraires().get(0).getHeures().get(0).getA());
        Log.d(TAG, "displayTownHall: TownHall = "+townHall.getFeatures().get(0).getProperties()
                .getEmail());
        Log.d(TAG, "displayTownHall: TownHall = "+townHall.getFeatures().get(0).getProperties()
                .getTelephone());
        Log.d(TAG, "displayTownHall: TownHall = "+townHall.getFeatures().get(0).getProperties()
                .getUrl());

    }

    public void displayInseeList(List<Insee> inseeList) {

        for(Insee insee : inseeList){
            Log.d(TAG, "displayInseeList: ville = "+insee.getNom());
            Log.d(TAG, "displayInseeList: code  = "+insee.getCode());
            Log.d(TAG, "displayInseeList: code Postaux  = "+insee.getCodesPostaux().get(0));
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

    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }
}
