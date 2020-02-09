package com.lastproject.mycity.controllers.fragments;


import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.card.MaterialCardView;
import com.lastproject.mycity.BuildConfig;
import com.lastproject.mycity.R;
import com.lastproject.mycity.controllers.activities.TownHallActivity;
import com.lastproject.mycity.controllers.activities.WebViewActivity;
import com.lastproject.mycity.firebase.database.firestore.models.TownHallFireStore;
import com.lastproject.mycity.models.views.TownHallViewModel;
import com.lastproject.mycity.utils.Toolbox;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Michaël SAGOT on 28/12/2019.
 */
public class TownHallFragment extends Fragment {

    // For debugging Mode
    private static final String TAG = TownHallFragment.class.getSimpleName();

    // Adding @BindView in order to indicate to ButterKnife to get & serialise it
    // -- LAYOUT : fragment_town_hall
    public @BindView(R.id.fragment_town_hall_root) ConstraintLayout mRoot;
    public @BindView(R.id.fragment_town_hall_name) TextView mName;
    public @BindView(R.id.fragment_town_hall_mcv_hours) MaterialCardView mMCV_Hours;
    // -- LAYOUT : mcv_hours
    //public @BindView(R.id.hours_mcv) MaterialCardView mHoursMCV;
    public @BindView(R.id.hours_title) TextView mHoursTitle;
    public @BindView(R.id.hours_1) TextView mHour1;
    public @BindView(R.id.hours_2) TextView mHour2;
    public @BindView(R.id.hours_3) TextView mHour3;
    public @BindView(R.id.hours_4) TextView mHour4;
    public @BindView(R.id.hours_5) TextView mHour5;
    public @BindView(R.id.hours_6) TextView mHour6;
    public @BindView(R.id.hours_7) TextView mHour7;

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
    private TownHallViewModel mTownHallViewModel;

    // For use CALL_PHONE permission
    // 1 _ Permission name
    public static final String PERMISSION_CALL_PHONE = Manifest.permission.CALL_PHONE;
    // 2 _ Request Code
    public static final int RC_CALL_PHONE_PERMISSION = 1;

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

        mTownHallViewModel = ((TownHallActivity) getActivity()).getTownHallViewModel();

        // Observe a change of Current TownHallFireStore
        mTownHallViewModel.getCurrentTownHall().observe(getActivity(), this::updateUI);
    }
    // ---------------------------------------------------------------------------------------------
    //                                   REQUEST PERMISSION
    // ---------------------------------------------------------------------------------------------
    /**
     * Method to ask the user for permission to make calls
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    // ---------------------------------------------------------------------------------------------
    //                                          ACTIONS
    // ---------------------------------------------------------------------------------------------
    @OnClick(R.id.fragment_town_hall_url)
    public void onUrlClick(View v) {
        Log.d(TAG, "onUrlClick: ");
        // Launch WebViewActivity
        // Param : Url to display
        Intent myIntent = new Intent(getActivity(), WebViewActivity.class);
        Log.d(TAG, "onUrlClick: mUrlAddress.getText() = "+mUrl.getText().toString());
        myIntent.putExtra(WebViewActivity.KEY_TOWN_HALL_WEB_SITE_URL, mUrl.getText().toString());
        this.startActivity(myIntent);
    }

    @OnClick(R.id.fragment_town_hall_phone)
    // Ask permission when accessing to this listener
    @AfterPermissionGranted(RC_CALL_PHONE_PERMISSION)
    public void onPhoneClick() {
        Log.d(TAG, "onPhoneClick: ");
        // Action only possible if an internet connection is available
        if (Toolbox.isInternetAvailable(getActivity())) {
            if (!EasyPermissions.hasPermissions(getActivity(), PERMISSION_CALL_PHONE)) {
                EasyPermissions.requestPermissions(this, "Permission CALL_PHONE", RC_CALL_PHONE_PERMISSION, PERMISSION_CALL_PHONE);
                return;
            }
            try {
                Log.d(TAG, "submitCallButton: Permission GRANTED :-)");
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                String phoneNumber = "tel:" + mTownHallViewModel.getCurrentTownHall().getValue()
                        .getPhoneNumber().replaceAll(" ", "");
                Log.d(TAG, "submitCallButton: phoneNumber = " + phoneNumber);
                callIntent.setData(Uri.parse(phoneNumber));
                startActivity(callIntent);
            } catch (SecurityException e) {
                Log.d(TAG, "submitCallButton: EXCEPTION ALERT ALERT ALERT");
            }
        }else{Toolbox.showSnackBar(mRoot,getString(R.string.common_not_network));}
    }

    @OnClick(R.id.fragment_town_hall_email)
    public void onEmailClick(View v) {
        Log.d(TAG, "onEmailClick: ");
        Log.d(TAG, "onEmailClick: email = "+mTownHallViewModel.getCurrentTownHall().getValue().getEmail());

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"+mTownHallViewModel.getCurrentTownHall().getValue().getEmail())); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, mTownHallViewModel.getCurrentTownHall().getValue().getEmail());
        intent.putExtra(Intent.EXTRA_SUBJECT, "Information request");
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    // ---------------------------------------------------------------------------------------------
    //                                             UI
    // ---------------------------------------------------------------------------------------------
    public void updateUI(TownHallFireStore townHallFireStore){
        Log.d(TAG, "updateUI: ");

        if (townHallFireStore != null) {
            mName.setText(townHallFireStore.getName());

            // Display Hours
            String hourAtDisplay = "";
            if (townHallFireStore.getHours() != null) {
                // Display Hour Line 1
                if (townHallFireStore.getHours().size() > 0) {
                    hourAtDisplay = "Du " + townHallFireStore.getHours().get(0).getDu() +
                                    " au " + townHallFireStore.getHours().get(0).getAu() +
                                    " de " + townHallFireStore.getHours().get(0).getHeures().get(0).getDe() +
                                    " a  " + townHallFireStore.getHours().get(0).getHeures().get(0).getA();
                    mHour1.setText(hourAtDisplay);
                    mHour1.setVisibility(View.VISIBLE);
                } else mHour1.setVisibility(View.GONE);
                // Display Hour Line 2
                if (townHallFireStore.getHours().size() > 1) {
                    hourAtDisplay = "Du " + townHallFireStore.getHours().get(1).getDu() +
                                    " au " + townHallFireStore.getHours().get(1).getAu() +
                                    " de " + townHallFireStore.getHours().get(1).getHeures().get(0).getDe() +
                                    " a  " + townHallFireStore.getHours().get(1).getHeures().get(0).getA();
                    mHour2.setText(hourAtDisplay);
                    mHour2.setVisibility(View.VISIBLE);
                } else mHour2.setVisibility(View.GONE);
                // Display Hour Line 3
                if (townHallFireStore.getHours().size() > 2) {
                    hourAtDisplay = "Du " + townHallFireStore.getHours().get(2).getDu() +
                                    " au " + townHallFireStore.getHours().get(2).getAu() +
                                    " de " + townHallFireStore.getHours().get(2).getHeures().get(0).getDe() +
                                    " a  " + townHallFireStore.getHours().get(2).getHeures().get(0).getA();
                    mHour3.setText(hourAtDisplay);
                    mHour3.setVisibility(View.VISIBLE);
                } else mHour3.setVisibility(View.GONE);
                // Display Hour Line 4
                if (townHallFireStore.getHours().size() > 3) {
                    hourAtDisplay = "Du " + townHallFireStore.getHours().get(3).getDu() +
                                    " au " + townHallFireStore.getHours().get(3).getAu() +
                                    " de " + townHallFireStore.getHours().get(3).getHeures().get(0).getDe() +
                                    " a  " + townHallFireStore.getHours().get(3).getHeures().get(0).getA();
                    mHour4.setText(hourAtDisplay);
                    mHour4.setVisibility(View.VISIBLE);
                } else mHour4.setVisibility(View.GONE);
                // Display Hour Line 5
                if (townHallFireStore.getHours().size() > 4) {
                    hourAtDisplay = "Du " + townHallFireStore.getHours().get(4).getDu() +
                                    " au " + townHallFireStore.getHours().get(4).getAu() +
                                    " de " + townHallFireStore.getHours().get(4).getHeures().get(0).getDe() +
                                    " a  " + townHallFireStore.getHours().get(4).getHeures().get(0).getA();
                    mHour5.setText(hourAtDisplay);
                    mHour5.setVisibility(View.VISIBLE);
                } else mHour5.setVisibility(View.GONE);
                // Display Hour Line 6
                if (townHallFireStore.getHours().size() > 5) {
                    hourAtDisplay = "Du " + townHallFireStore.getHours().get(5).getDu() +
                                    " au " + townHallFireStore.getHours().get(5).getAu() +
                                    " de " + townHallFireStore.getHours().get(5).getHeures().get(0).getDe() +
                                    " a  " + townHallFireStore.getHours().get(5).getHeures().get(0).getA();
                    mHour6.setText(hourAtDisplay);
                    mHour6.setVisibility(View.VISIBLE);
                } else mHour6.setVisibility(View.GONE);
                // Display Hour Line 7
                if (townHallFireStore.getHours().size() > 6) {
                    hourAtDisplay = "Du " + townHallFireStore.getHours().get(6).getDu() +
                                    " au " + townHallFireStore.getHours().get(6).getAu() +
                                    " de " + townHallFireStore.getHours().get(6).getHeures().get(0).getDe() +
                                    " a  " + townHallFireStore.getHours().get(6).getHeures().get(0).getA();
                    mHour7.setText(hourAtDisplay);
                    mHour7.setVisibility(View.VISIBLE);
                } else mHour7.setVisibility(View.GONE);
            }



            // Display Address
            String address = "";
            if (townHallFireStore.getAddress() != null) {
                // Display address Line 1
                if (townHallFireStore.getAddress().size() > 0) {
                    mAddressLine1.setText(townHallFireStore.getAddress().get(0));
                    mAddressLine1.setVisibility(View.VISIBLE);
                    address+= townHallFireStore.getAddress().get(0)+"+";
                } else mAddressLine1.setVisibility(View.GONE);
                // Display address Line 2
                if (townHallFireStore.getAddress().size() > 1) {
                    mAddressLine2.setText(townHallFireStore.getAddress().get(1));
                    mAddressLine2.setVisibility(View.VISIBLE);
                    address+= townHallFireStore.getAddress().get(1)+"+";
                } else mAddressLine2.setVisibility(View.GONE);
                // Display address Line 3
                if (townHallFireStore.getAddress().size() > 2) {
                    mAddressLine3.setText(townHallFireStore.getAddress().get(2));
                    mAddressLine3.setVisibility(View.VISIBLE);
                    address+= townHallFireStore.getAddress().get(2)+"+";
                } else mAddressLine3.setVisibility(View.GONE);
                // Display address Line 4
                if (townHallFireStore.getAddress().size() > 3) {
                    mAddressLine4.setText(townHallFireStore.getAddress().get(3));
                    mAddressLine4.setVisibility(View.VISIBLE);
                    address+= townHallFireStore.getAddress().get(3);
                } else mAddressLine4.setVisibility(View.GONE);
            }

            // Display Email
            if (townHallFireStore.getEmail() != null) {
                mEmail.setText(townHallFireStore.getEmail());
                mEmail.setVisibility(View.VISIBLE);
            } else  mEmail.setVisibility(View.GONE);

            // Display PhoneNumber
            if (townHallFireStore.getPhoneNumber() != null) {
                mPhone.setText(townHallFireStore.getPhoneNumber());
                mPhone.setVisibility(View.VISIBLE);
            } else  mPhone.setVisibility(View.GONE);

            // Display Url
            if (townHallFireStore.getUrl() != null) {
                mUrl.setText(townHallFireStore.getUrl());
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
