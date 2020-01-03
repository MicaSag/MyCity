package com.lastproject.mycity.controllers.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lastproject.mycity.BuildConfig;
import com.lastproject.mycity.R;
import com.lastproject.mycity.controllers.activities.MayorActivity;
import com.lastproject.mycity.firebase.database.firestore.models.TownHall;
import com.lastproject.mycity.models.views.MayorViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

/**
 * Created by Michaël SAGOT on 28/12/2019.
 */
public class TownHallFragment extends Fragment {

    // For debugging Mode
    private static final String TAG = TownHallFragment.class.getSimpleName();

    // Adding @BindView in order to indicate to ButterKnife to get & serialise it
    public @BindView(R.id.fragment_town_hall_name) TextView mName;
    public @BindView(R.id.fragment_town_hall_hours_title) TextView mHoursTitle;
    public @BindView(R.id.fragment_town_hall_hours_1) TextView mHour1;
    public @BindView(R.id.fragment_town_hall_hours_2) TextView mHour2;
    public @BindView(R.id.fragment_town_hall_hours_3) TextView mHour3;
    public @BindView(R.id.fragment_town_hall_hours_4) TextView mHour4;
    public @BindView(R.id.fragment_town_hall_hours_5) TextView mHour5;
    public @BindView(R.id.fragment_town_hall_hours_6) TextView mHour6;
    public @BindView(R.id.fragment_town_hall_hours_7) TextView mHour7;
    public @BindView(R.id.fragment_town_hall_address_title) TextView mAddressTitle;
    public @BindView(R.id.fragment_town_hall_address_line_1) TextView mAddressLine1;
    public @BindView(R.id.fragment_town_hall_address_line_2) TextView mAddressLine2;
    public @BindView(R.id.fragment_town_hall_address_line_3) TextView mAddressLine3;
    public @BindView(R.id.fragment_town_hall_address_line_4) TextView mAddressLine4;
    public @BindView(R.id.fragment_town_hall_email) TextView mEmail;
    public @BindView(R.id.fragment_town_hall_phone) TextView mPhone;
    public @BindView(R.id.fragment_town_hall_url) TextView mUrl;
    public @BindView(R.id.fragment_town_hall_static_map) ImageView mStaticMap;

    // Declare ViewModel
    private MayorViewModel mMayorViewModel;

    //FOR DATA
    private Disposable disposable;

    public TownHallFragment() {
        // Required empty public constructor
    }
    // ---------------------------------------------------------------------------------------------
    //                                  FRAGMENT INSTANTIATION
    // ---------------------------------------------------------------------------------------------
    public static TownHallFragment newInstance() {
        Log.d(TAG, "newInstance: ");

        // Create new fragment
        TownHallFragment fragment = new TownHallFragment();

        return fragment;
    }

    // ---------------------------------------------------------------------------------------------
    //                                    ENTRY POINT
    // ---------------------------------------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_town_hall, container, false);

        // Initialize ButterKnife
        ButterKnife.bind(this, view);

        // Configure ViewModel
        configureViewModel();

        return view;
    }
    // ---------------------------------------------------------------------------------------------
    //                                        VIEW MODEL
    // ---------------------------------------------------------------------------------------------
    // Configure ViewModel
    private void configureViewModel() {
        Log.d(TAG, "configureViewModel: ");

        mMayorViewModel = ((MayorActivity) getActivity()).getMayorViewModel();

        // Observe a change of Current TownHall
        mMayorViewModel.getCurrentTownHall().observe(this, this::updateUI);
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
    //                                             UI
    // ---------------------------------------------------------------------------------------------
    public void updateUI(TownHall townHall){
        Log.d(TAG, "updateUI: ");

        if (townHall != null) {
            mName.setText(townHall.getName());

            // Display Hours
            String hourAtDisplay = "";
            if (townHall.getHours() != null) {
                // Display Hour Line 1
                if (townHall.getHours().size() > 0) {
                    hourAtDisplay = "Du " + townHall.getHours().get(0).getDu() +
                                    " au " + townHall.getHours().get(0).getAu() +
                                    " de " + townHall.getHours().get(0).getHeures().get(0).getDe() +
                                    " a  " + townHall.getHours().get(0).getHeures().get(0).getA();
                    mHour1.setText(hourAtDisplay);
                    mHour1.setVisibility(View.VISIBLE);
                } else mHour1.setVisibility(View.GONE);
                // Display Hour Line 2
                if (townHall.getHours().size() > 1) {
                    hourAtDisplay = "Du " + townHall.getHours().get(1).getDu() +
                                    " au " + townHall.getHours().get(1).getAu() +
                                    " de " + townHall.getHours().get(1).getHeures().get(0).getDe() +
                                    " a  " + townHall.getHours().get(1).getHeures().get(0).getA();
                    mHour2.setText(hourAtDisplay);
                    mHour2.setVisibility(View.VISIBLE);
                } else mHour2.setVisibility(View.GONE);
                // Display Hour Line 3
                if (townHall.getHours().size() > 2) {
                    hourAtDisplay = "Du " + townHall.getHours().get(2).getDu() +
                                    " au " + townHall.getHours().get(2).getAu() +
                                    " de " + townHall.getHours().get(2).getHeures().get(0).getDe() +
                                    " a  " + townHall.getHours().get(2).getHeures().get(0).getA();
                    mHour3.setText(hourAtDisplay);
                    mHour3.setVisibility(View.VISIBLE);
                } else mHour3.setVisibility(View.GONE);
                // Display Hour Line 4
                if (townHall.getHours().size() > 3) {
                    hourAtDisplay = "Du " + townHall.getHours().get(3).getDu() +
                                    " au " + townHall.getHours().get(3).getAu() +
                                    " de " + townHall.getHours().get(3).getHeures().get(0).getDe() +
                                    " a  " + townHall.getHours().get(3).getHeures().get(0).getA();
                    mHour4.setText(hourAtDisplay);
                    mHour4.setVisibility(View.VISIBLE);
                } else mHour4.setVisibility(View.GONE);
                // Display Hour Line 5
                if (townHall.getHours().size() > 4) {
                    hourAtDisplay = "Du " + townHall.getHours().get(4).getDu() +
                                    " au " + townHall.getHours().get(4).getAu() +
                                    " de " + townHall.getHours().get(4).getHeures().get(0).getDe() +
                                    " a  " + townHall.getHours().get(4).getHeures().get(0).getA();
                    mHour5.setText(hourAtDisplay);
                    mHour5.setVisibility(View.VISIBLE);
                } else mHour5.setVisibility(View.GONE);
                // Display Hour Line 6
                if (townHall.getHours().size() > 5) {
                    hourAtDisplay = "Du " + townHall.getHours().get(5).getDu() +
                                    " au " + townHall.getHours().get(5).getAu() +
                                    " de " + townHall.getHours().get(5).getHeures().get(0).getDe() +
                                    " a  " + townHall.getHours().get(5).getHeures().get(0).getA();
                    mHour6.setText(hourAtDisplay);
                    mHour6.setVisibility(View.VISIBLE);
                } else mHour6.setVisibility(View.GONE);
                // Display Hour Line 7
                if (townHall.getHours().size() > 6) {
                    hourAtDisplay = "Du " + townHall.getHours().get(6).getDu() +
                                    " au " + townHall.getHours().get(6).getAu() +
                                    " de " + townHall.getHours().get(6).getHeures().get(0).getDe() +
                                    " a  " + townHall.getHours().get(6).getHeures().get(0).getA();
                    mHour7.setText(hourAtDisplay);
                    mHour7.setVisibility(View.VISIBLE);
                } else mHour7.setVisibility(View.GONE);
            }



            // Display Address
            String address = "";
            if (townHall.getAddress() != null) {
                // Display address Line 1
                if (townHall.getAddress().size() > 0) {
                    mAddressLine1.setText(townHall.getAddress().get(0));
                    mAddressLine1.setVisibility(View.VISIBLE);
                    address+= townHall.getAddress().get(0)+"+";
                } else mAddressLine1.setVisibility(View.GONE);
                // Display address Line 2
                if (townHall.getAddress().size() > 1) {
                    mAddressLine2.setText(townHall.getAddress().get(1));
                    mAddressLine2.setVisibility(View.VISIBLE);
                    address+= townHall.getAddress().get(1)+"+";
                } else mAddressLine2.setVisibility(View.GONE);
                // Display address Line 3
                if (townHall.getAddress().size() > 2) {
                    mAddressLine3.setText(townHall.getAddress().get(2));
                    mAddressLine3.setVisibility(View.VISIBLE);
                    address+= townHall.getAddress().get(2)+"+";
                } else mAddressLine3.setVisibility(View.GONE);
                // Display address Line 4
                if (townHall.getAddress().size() > 3) {
                    mAddressLine4.setText(townHall.getAddress().get(3));
                    mAddressLine4.setVisibility(View.VISIBLE);
                    address+= townHall.getAddress().get(3);
                } else mAddressLine4.setVisibility(View.GONE);
            }

            // Display Email
            if (townHall.getEmail() != null) {
                mEmail.setText(townHall.getEmail());
                mEmail.setVisibility(View.VISIBLE);
            } else  mEmail.setVisibility(View.GONE);

            // Display PhoneNumber
            if (townHall.getPhoneNumber() != null) {
                mPhone.setText(townHall.getPhoneNumber());
                mPhone.setVisibility(View.VISIBLE);
            } else  mPhone.setVisibility(View.GONE);

            // Display Url
            if (townHall.getUrl() != null) {
                mUrl.setText(townHall.getUrl());
                mUrl.setVisibility(View.VISIBLE);
            } else  mUrl.setVisibility(View.GONE);

            // Display STATIC MAP
            // Created Uri to recover the static Mapping
            Uri.Builder uriStaticMap
                    = Uri.parse("https://maps.googleapis.com/maps/api/staticmap").buildUpon();
            // Add options
            uriStaticMap
                    .appendQueryParameter("size","300x300")
                    .appendQueryParameter("scale","2")
                    .appendQueryParameter("zoom","16")
                    .appendQueryParameter("key", BuildConfig.google_static_map_api);
            String markers = "size:mid|color:blue|label:E|" + address;
            uriStaticMap.appendQueryParameter("markers",markers);
            // Display static Map depending on the address of the property
            Glide.with(this)
                    .load(uriStaticMap.build())
                    .apply(RequestOptions.centerCropTransform())
                    .into(mStaticMap);
        }
    }
}
