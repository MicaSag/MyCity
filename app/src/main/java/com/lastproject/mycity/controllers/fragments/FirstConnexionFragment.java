package com.lastproject.mycity.controllers.fragments;


import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.lastproject.mycity.R;
import com.lastproject.mycity.controllers.activities.TownHallSelectionActivity;
import com.lastproject.mycity.firebase.database.firestore.models.TownHallFireStore;
import com.lastproject.mycity.models.views.TownHallSelectionViewModel;
import com.lastproject.mycity.network.retrofit.models.Insee;
import com.lastproject.mycity.network.retrofit.models.townhall.TownH;
import com.lastproject.mycity.network.retrofit.streams.InseeListStreams;
import com.lastproject.mycity.network.retrofit.streams.TownHallStreams;
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
    public @BindView(R.id.fragment_first_connexion_message_1) TextView mMessage1;
    public @BindView(R.id.fragment_first_connexion_message_2) TextView mMessage2;
    public @BindView(R.id.fragment_first_connexion_bt_validate) Button mValidateBt;

    // Declare ViewModel
    private TownHallSelectionViewModel mTownHallSelectionViewModel;

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

        // Personalization of the display according to the role of the user
        configureProfileUI();

        // ButterKnife does not support an autocomplete view,
        // so you have to create a classic listener for this view
        createAutoCompleteCityListListener();

        mAutoCityList.setEnabled(false);
        mValidateBt.setEnabled(false);

        return view;
    }
    // ---------------------------------------------------------------------------------------------
    //                                        VIEW MODEL
    // ---------------------------------------------------------------------------------------------
    // Configure ViewModel
    private void configureViewModel() {
        Log.d(TAG, "configureViewModel: ");

        mTownHallSelectionViewModel = ((TownHallSelectionActivity) getActivity()).getTownHallSelectionViewModel();
    }
    // ---------------------------------------------------------------------------------------------
    //                                          ACTIONS
    // ---------------------------------------------------------------------------------------------
    public void createAutoCompleteCityListListener(){
        Log.d(TAG, "createAutoCompleteCityListListener: ");

        mAutoCityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: ");

                String item = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "createAutoCompleteCityListListener onItemClick: item = "+item);

                String inseeID = mTownHallSelectionViewModel.getInseeList().get(position).getCode();
                Log.d(TAG, "createAutoCompleteCityListListener onItemClick: inseeID = "+inseeID);
                mTownHallSelectionViewModel.setInseeSelected(inseeID);

                // we update the user's document with the INSEE identifier
                mTownHallSelectionViewModel.getCurrentUser().setInseeID(inseeID);
                Log.d(TAG, "createAutoCompleteCityListListener onItemClick: getCurrentUser() = "+
                        mTownHallSelectionViewModel.getCurrentUser());

                // Activation of the validate button
                mValidateBt.setEnabled(true);
            }
        });
    }

    // When you click on the Button Search
    @OnClick(R.id.fragment_first_connexion_bt_search)
    public void onSearchClick(View view) {
        Log.d(TAG, "onSearchClick: ");

        // If the desired character string is not empty
        // we start searching for Insee codes
        if (!mCityValue.getText().toString().equals("")){
            mAutoCityList.setText("");
            getInseeList();
        } else{
            mValidateBt.setEnabled(false);
            mAutoCityList.setText(getString(R.string.bad_research));
        }
    }

    // When you click on the Button Validate
    @OnClick(R.id.fragment_first_connexion_bt_validate)
    public void onValidateClick(View view) {
        Log.d(TAG, "onValidateClick: ");

        // Get Town Hall Data
        // save its in viewModel and FireStore
        // And Show Town Hall Fragment
        getTownHallInformations(mTownHallSelectionViewModel.getCurrentUser().getInseeID());
    }
    // ---------------------------------------------------------------------------------------------
    //                                       HTTP (RxJAVA)
    // ---------------------------------------------------------------------------------------------
    // Execute our Stream
    private void getInseeList() {
        Log.d(TAG, "getInseeList: ");

        // Retrieves cities based on an input character string
        String citySearch = mCityValue.getText().toString();
        Log.d(TAG, "getInseeList: citySearch = "+citySearch);
        // Execute the stream subscribing to Observable defined inside GithubStream
        this.disposable = InseeListStreams.streamFetchInseeList(citySearch)
                .subscribeWith(new DisposableObserver<List<Insee>>() {
            @Override
            public void onNext(List<Insee> inseeList) {
                Log.d(TAG, "getInseeList: onNext: inseeList = "+inseeList);

                // Update UI with list of Insee code
                displayInseeList(inseeList);
                configureAutoCompleteInseeList(inseeList);
            }
            @Override
            public void onError(Throwable e) {Log.d(TAG, "getInseeList: onError: ");}
            @Override
            public void onComplete() {Log.d(TAG, "getInseeList: onComplete: ");}
        });
    }
    // Retrieves the information of a city according to its insee code
    private void getTownHallInformations(String insee) {
        Log.d(TAG, "getTownHallInformations() called with: insee = [" + insee + "]");

        // Execute the stream subscribing to Observable defined inside GithubStream
        this.disposable = TownHallStreams.streamFetchTownHall(insee).subscribeWith(new DisposableObserver<TownH>() {
            @Override
            public void onNext(TownH townH) {
                Log.d(TAG, "getTownHallInformations: onNext: ");
                // Update UI with list of users
                displayTownHall(townH);

                // Map TownH To TownHallFireStore
                TownHallFireStore townHallFireStore = Mapping.mapTownHToTownHall(townH);

                // Create TownHall in FireStore
                mTownHallSelectionViewModel
                        .createTownHall(townHallFireStore)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "getTownHallInformations: onSuccess: ");

                        // Save TownHallFireStore in model
                        mTownHallSelectionViewModel.setCurrentTownHall(townHallFireStore);

                        // Update User in FireStore with InseeID
                        mTownHallSelectionViewModel.setUserInseeID(mTownHallSelectionViewModel.getInseeSelected(),
                                mTownHallSelectionViewModel.getCurrentUser().getUserID());

                        // If the user is a mayor, we update Mayor Document with TownHallID
                        if (mTownHallSelectionViewModel.getCurrentUser().isMayor()) {

                            // Update townHallID Mayor in Fire Store
                            Log.d(TAG, "onSuccess: mTownHallViewModel.getCurrentMayor().getMayorID() = "
                                    + mTownHallSelectionViewModel.getCurrentMayor().getMayorID());
                            Log.d(TAG, "onSuccess: documentReference.getId() = "
                                    + documentReference.getId());

                            mTownHallSelectionViewModel.updateMayorTownHallID(mTownHallSelectionViewModel
                                    .getCurrentMayor().getMayorID(), documentReference.getId())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "onSuccess() : Update townHallID Mayor in Fire Store");

                                            // Show Town Hall Fragment
                                            mTownHallSelectionViewModel.setViewAction(TownHallSelectionViewModel.ViewAction.
                                                    CALL_TOWN_HALL_ACTIVITY);
                                        }
                                    });
                        } else{
                            // Show Town Hall Fragment
                            mTownHallSelectionViewModel.setViewAction(TownHallSelectionViewModel.ViewAction.
                                    CALL_TOWN_HALL_ACTIVITY);
                        }
                    }
                });
            }
            @Override
            public void onError(Throwable e) {Log.d(TAG, "getTownHallInformations: onError: ");}
            @Override
            public void onComplete() {
                Log.d(TAG, "getTownHallInformations: onComplete: ");
            }
        });
    }
    // ---------------------------------------------------------------------------------------------
    //                      AUTOCOMPLETE INSEE CONFIGURATION
    // ---------------------------------------------------------------------------------------------
    private void configureAutoCompleteInseeList(List<Insee> inseeList) {
        Log.d(TAG, "configureAutoCompleteType: ");

        // Save insee List in view Model
        mTownHallSelectionViewModel.setInseeList(inseeList);

        ArrayList<String> ar = new ArrayList<>();

        String postalCode;
        String nom;
        for (Insee insee : inseeList) {
            if (insee != null) {
                if (insee.getCodesPostaux().size() >0 )
                    postalCode = insee.getCodesPostaux().get(0);
                else
                    postalCode = "unknown";

                if (insee.getNom() != null) nom = insee.getNom();
                else
                    nom = "unknown";
                ar.add(postalCode + " " + nom);
            }
        }

        // Displays in the comboBox the number of cities found
        if (inseeList.size() > 0) {
            mAutoCityList.setEnabled(true);
            mAutoCityList.setText(inseeList.size() + getString(R.string.city_found));
        }else{
            mAutoCityList.setText(getString(R.string.no_city_found));
            mValidateBt.setEnabled(false);
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
            if (insee.getNom() != null)
                Log.d(TAG, "displayInseeList: ville = " + insee.getNom());
            if (insee.getCode() != null)
                Log.d(TAG, "displayInseeList: code  = " + insee.getCode());
            if (insee.getCodesPostaux().size() > 0)
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
    // ---------------------------------------------------------------------------------------------
    //                                          UI
    // ---------------------------------------------------------------------------------------------
    // configure the display of the UI according to the user's profile
    private void configureProfileUI(){
        Log.d(TAG, "configureProfileUI: ");

        Resources res = getResources();
        String message1;
        String message2;
        // Message personalization
        if (mTownHallSelectionViewModel.getCurrentUser().isMayor()) {
            message1 = res.getString(R.string.city_choice_mayor_message_1);
            message2 = res.getString(R.string.city_choice_mayor_message_2);
        }
        else {
            message1 = res.getString(R.string.city_choice_citizen_message_1);
            message2 = res.getString(R.string.city_choice_citizen_message_2);
        }
        mMessage1.setText(message1);
        mMessage2.setText(message2);
    }
}
