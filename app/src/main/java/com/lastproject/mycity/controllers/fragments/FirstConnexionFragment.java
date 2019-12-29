package com.lastproject.mycity.controllers.fragments;


import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.lastproject.mycity.R;
import com.lastproject.mycity.controllers.activities.AuthenticationActivity;
import com.lastproject.mycity.controllers.activities.MayorActivity;
import com.lastproject.mycity.models.views.AuthenticationViewModel;
import com.lastproject.mycity.network.retrofit.models.Insee;
import com.lastproject.mycity.network.retrofit.models.townhall.TownHall;
import com.lastproject.mycity.network.retrofit.streams.InseeListStreams;
import com.lastproject.mycity.network.retrofit.streams.TownHallStreams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemSelected;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by Michaël SAGOT on 28/12/2019.
 */
public class FirstConnexionFragment extends Fragment {

    // For debugging Mode
    private static final String TAG = FirstConnexionFragment.class.getSimpleName();

    // Adding @BindView in order to indicate to ButterKnife to get & serialise it
    public @BindView(R.id.fragment_first_connexion_ed_city_value) EditText mCityValue;
    public @BindView(R.id.fragment_first_connexion_auto_city_list) AutoCompleteTextView mAutoCityList;

    //FOR DATA
    private Disposable disposable;

    public FirstConnexionFragment() {
        // Required empty public constructor
    }
    // ---------------------------------------------------------------------------------------------
    //                                  FRAGMENT INSTANTIATION
    // ---------------------------------------------------------------------------------------------
    public static FirstConnexionFragment newInstance() {
        Log.d(TAG, "newInstance: ");

        // Create new fragment
        FirstConnexionFragment fragment = new FirstConnexionFragment();

        return fragment;
    }

    // ---------------------------------------------------------------------------------------------
    //                                    ENTRY POINT
    // ---------------------------------------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_first_connexion, container, false);

        // Initialize ButterKnife
        ButterKnife.bind(this, view);

        // ButterKnife does not support an autocomplete view,
        // so you have to create a classic listener for this view
        createAutoCompleteCityListListener();

        return view;
    }
    // ---------------------------------------------------------------------------------------------
    //                                          ACTIONS
    // ---------------------------------------------------------------------------------------------
    public void createAutoCompleteCityListListener(){
        Log.d(TAG, "createAutoCompleteCityListListener: ");

        mAutoCityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected() called with: parent = [" + parent + "], view = ["
                        + view + "], position = [" + position + "], id = [" + id + "]");
            }
        });
    }

    // When you click on the Button Search
    @OnClick(R.id.fragment_first_connexion_bt_search)
    public void onSearchClick(View view) {
        Log.d(TAG, "onSearchClick: ");

        // If the desired character string is not empty
        // we start searching for Insee codes
        if (!mCityValue.getText().toString().equals("")) getInseeList();
    }

    // When you click on the Button Validate
    @OnClick(R.id.fragment_first_connexion_bt_validate)
    public void onValidateClick(View view) {
        Log.d(TAG, "onValidateClick: ");

        // If the desired character string is not empty
        // we start searching for Insee codes
        ((MayorActivity) getActivity()).configureAndShowTownHallFragment();
    }
    // ---------------------------------------------------------------------------------------------
    //                                       HTTP (RxJAVA)
    // ---------------------------------------------------------------------------------------------
    // Execute our Stream
    private void getInseeList() {
        Log.d(TAG, "getInseeList: ");

        // Retrieves cities based on an input character string
        String citySearch = mCityValue.getText().toString();
        // Execute the stream subscribing to Observable defined inside GithubStream
        this.disposable = InseeListStreams.streamFetchInseeList(citySearch).subscribeWith(new DisposableObserver<List<Insee>>() {
            @Override
            public void onNext(List<Insee> inseeList) {
                Log.e("TAG", "On Next");
                // Update UI with list of users
                displayInseeList(inseeList);
                //getTownHallInformation(inseeList.get(0).getCode());
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


    // Retrieves the information of a city according to its insee code
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

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                R.layout.dropdown_towns,
                ar);
        mAutoCityList.setAdapter(adapter);
    }
    // ---------------------------------------------------------------------------------------------
    //                            FOR DEBUGGING
    // ---------------------------------------------------------------------------------------------
    public void displayInseeList(List<Insee> inseeList) {
        Log.d(TAG, "displayInseeList: ");

        for (Insee insee : inseeList) {
            Log.d(TAG, "displayInseeList: ville = " + insee.getNom());
            Log.d(TAG, "displayInseeList: code  = " + insee.getCode());
            Log.d(TAG, "displayInseeList: code Postaux  = " + insee.getCodesPostaux().get(0));
        }
    }
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
