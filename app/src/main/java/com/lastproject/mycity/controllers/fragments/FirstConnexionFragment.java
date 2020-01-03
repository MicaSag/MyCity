package com.lastproject.mycity.controllers.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.lastproject.mycity.R;
import com.lastproject.mycity.controllers.activities.MayorActivity;
import com.lastproject.mycity.firebase.database.firestore.models.TownHall;
import com.lastproject.mycity.models.views.MayorViewModel;
import com.lastproject.mycity.network.retrofit.models.Insee;
import com.lastproject.mycity.network.retrofit.models.townhall.TownH;
import com.lastproject.mycity.network.retrofit.streams.InseeListStreams;
import com.lastproject.mycity.network.retrofit.streams.TownHallStreams;
import com.lastproject.mycity.repositories.CurrentMayorDataRepository;
import com.lastproject.mycity.repositories.CurrentTownHallDataRepository;
import com.lastproject.mycity.utils.Mapping;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    // Declare ViewModel
    private MayorViewModel mMayorViewModel;

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

        // Configure ViewModel
        configureViewModel();

        // ButterKnife does not support an autocomplete view,
        // so you have to create a classic listener for this view
        createAutoCompleteCityListListener();

        return view;
    }
    // ---------------------------------------------------------------------------------------------
    //                                        VIEW MODEL
    // ---------------------------------------------------------------------------------------------
    // Configure ViewModel
    private void configureViewModel() {
        Log.d(TAG, "configureViewModel: ");

        mMayorViewModel = ((MayorActivity) getActivity()).getMayorViewModel();
    }
    // ---------------------------------------------------------------------------------------------
    //                                          ACTIONS
    // ---------------------------------------------------------------------------------------------
    public void createAutoCompleteCityListListener(){
        Log.d(TAG, "createAutoCompleteCityListListener: ");

        mAutoCityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected() :");

                String item = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "onItemClick: item = "+item);

                // Update Current Mayor with townHallID (Insee code)
                mMayorViewModel.getCurrentMayor()
                        .setTownHallID(mMayorViewModel.getInseeList().get(position).getCode());
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

        // Get TownH Data
        // save its in viewModel and FireStore
        // And Show Town Hall Fragment
        getTownHallInformations(mMayorViewModel.getCurrentMayor().getTownHallID());

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
        this.disposable = InseeListStreams.streamFetchInseeList(citySearch)
                .subscribeWith(new DisposableObserver<List<Insee>>() {
            @Override
            public void onNext(List<Insee> inseeList) {
                Log.d(TAG, "onNext: ");
                // Update UI with list of users
                displayInseeList(inseeList);
                configureAutoCompleteInseeList(inseeList);
            }
            @Override
            public void onError(Throwable e) {Log.d(TAG, "onError: ");}
            @Override
            public void onComplete() {Log.d(TAG, "onComplete: ");}
        });
    }
    // Retrieves the information of a city according to its insee code
    private void getTownHallInformations(String insee) {
        Log.d(TAG, "getTownHallInformations: ");

        // Execute the stream subscribing to Observable defined inside GithubStream
        this.disposable = TownHallStreams.streamFetchTownHall(insee).subscribeWith(new DisposableObserver<TownH>() {
            @Override
            public void onNext(TownH townH) {
                Log.d(TAG, "onNext: ");
                // Update UI with list of users
                displayTownHall(townH);

                // Map TownH To TownHall
                TownHall townHall = Mapping.mapTownHToTownHall(townH);

                // Create TownHall in Fire Store
                mMayorViewModel
                        .createTownHall(townHall)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "onSuccess: ");

                        // Save TownHall in model
                        mMayorViewModel.setCurrentTownHall(townHall);

                        // Update townHallID Mayor in Fire Store
                        mMayorViewModel.updateMayorTownHallID(mMayorViewModel
                                .getCurrentMayor().getMayorID(), documentReference.getId() )
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        // Show Town Hall Fragment
                                        ((MayorActivity) getActivity()).configureAndShowTownHallFragment();
                                    }
                                });
                    }
                });
            }
            @Override
            public void onError(Throwable e) {Log.d(TAG, "onError: ");}
            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });
    }

    // ---------------------------------------------------------------------------------------------
    //                      AUTOCOMPLETE INSEE CONFIGURATION
    // ---------------------------------------------------------------------------------------------
    private void configureAutoCompleteInseeList(List<Insee> inseeList) {
        Log.d(TAG, "configureAutoCompleteType: ");

        // Save insee List in view Model
        mMayorViewModel.setInseeList(inseeList);

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
    public void displayTownHall(TownH townH) {

        Log.d(TAG, "displayTownHall: TownH = " + townH.getFeatures().get(0).getProperties().getId());
        Log.d(TAG, "displayTownHall: TownH = " + townH.getFeatures().get(0).getProperties().getPivotLocal());
        Log.d(TAG, "displayTownHall: TownH = " + townH.getFeatures().get(0).getProperties().getNom());
        Log.d(TAG, "displayTownHall: TownH = " + townH.getFeatures().get(0).getGeometry().getCoordinates().get(0));
        Log.d(TAG, "displayTownHall: TownH = " + townH.getFeatures().get(0).getGeometry().getCoordinates().get(1));
        Log.d(TAG, "displayTownHall: TownH = " + townH.getFeatures().get(0).getProperties()
                .getAdresses().get(0).getLignes().size());
        Log.d(TAG, "displayTownHall: TownH = " + townH.getFeatures().get(0).getProperties()
                .getAdresses().get(0).getLignes());
        Log.d(TAG, "displayTownHall: TownH = " + townH.getFeatures().get(0).getProperties()
                .getAdresses().get(0).getCodePostal());
        Log.d(TAG, "displayTownHall: TownH = " + townH.getFeatures().get(0).getProperties()
                .getAdresses().get(0).getCommune());
        Log.d(TAG, "displayTownHall: TownH = " + townH.getFeatures().get(0).getProperties()
                .getHoraires().size());
        Log.d(TAG, "displayTownHall: TownH = " + townH.getFeatures().get(0).getProperties()
                .getHoraires().get(0).getDu());
        Log.d(TAG, "displayTownHall: TownH = " + townH.getFeatures().get(0).getProperties()
                .getHoraires().get(0).getAu());
        Log.d(TAG, "displayTownHall: TownH = " + townH.getFeatures().get(0).getProperties()
                .getHoraires().get(0).getHeures().get(0).getDe());
        Log.d(TAG, "displayTownHall: TownH = " + townH.getFeatures().get(0).getProperties()
                .getHoraires().get(0).getHeures().get(0).getA());
        Log.d(TAG, "displayTownHall: TownH = " + townH.getFeatures().get(0).getProperties()
                .getEmail());
        Log.d(TAG, "displayTownHall: TownH = " + townH.getFeatures().get(0).getProperties()
                .getTelephone());
        Log.d(TAG, "displayTownHall: TownH = " + townH.getFeatures().get(0).getProperties()
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
