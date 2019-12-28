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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lastproject.mycity.BuildConfig;
import com.lastproject.mycity.R;
import com.lastproject.mycity.adapter.photoslist.PhotosListAdapter;
import com.lastproject.mycity.injections.Injection;
import com.lastproject.mycity.injections.ViewModelFactory;
import com.lastproject.mycity.models.views.DetailsEventViewModel;
import com.lastproject.mycity.room.models.Event;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Michaël SAGOT on 26/12/2019.
 */
public class EventDetailsFragment extends Fragment implements PhotosListAdapter.OnPhotoClick{

    // For debugging Mode
    private static final String TAG = EventDetailsFragment.class.getSimpleName();

    // For Design
    @BindView(R.id.fragment_details_photos_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.fragment_details_description_text) TextView mDescription;
    @BindView(R.id.fragment_details_location_address_line_1) TextView mLocation_1;
    @BindView(R.id.fragment_details_location_address_line_2) TextView mLocation_2;
    @BindView(R.id.fragment_details_location_address_line_3) TextView mLocation_3;
    @BindView(R.id.fragment_details_location_address_line_4) TextView mLocation_4;
    @BindView(R.id.fragment_details_location_address_line_5) TextView mLocation_5;
    @BindView(R.id.fragment_details_static_map) ImageView mStaticMap;

    // Declare DetailsEventViewModel
    private DetailsEventViewModel mDetailsEventViewModel;

    // For Display list of photos
    PhotosListAdapter mPhotosListAdapter;

    public EventDetailsFragment() {
        // Required empty public constructor
    }
    // ---------------------------------------------------------------------------------------------
    //                                  FRAGMENT INSTANTIATION
    // ---------------------------------------------------------------------------------------------
    public static EventDetailsFragment newInstance() {
        Log.d(TAG, "newInstance: ");

        // Create new fragment
        EventDetailsFragment fragment = new EventDetailsFragment();

        return fragment;
    }
    // ---------------------------------------------------------------------------------------------
    //                                    ENTRY POINT
    // ---------------------------------------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_details, container, false);
        ButterKnife.bind(this, view);

        // Configuration Photo RecyclerView
        this.configureRecyclerView();

        // Configure DetailsEstateViewModel
        this.configureEstateDetailsViewModel();

        return view;
    }
    // ---------------------------------------------------------------------------------------------
    //                                        VIEW MODEL
    // ---------------------------------------------------------------------------------------------
    // Configure ViewModel
    private void configureEstateDetailsViewModel(){

        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getContext());
        mDetailsEventViewModel = ViewModelProviders.of(this, mViewModelFactory)
                .get(DetailsEventViewModel.class);

        // Observe a change of Current Event
        mDetailsEventViewModel.getCurrentEvent().observe(this, this::updateUI);
    }
    // --------------------------------------------------------------------------------------------
    //                                    CONFIGURATION
    // --------------------------------------------------------------------------------------------
    // Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView(){
        // Create adapter
        mPhotosListAdapter = new PhotosListAdapter( this.getClass(),
                                                    Glide.with(this),
                                    this);
        // Attach the adapter to the recyclerView to populate items
        mRecyclerView.setAdapter(mPhotosListAdapter);
        // Set layout manager to position the items
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL,false));
    }
    // ---------------------------------------------------------------------------------------------
    //                                           ACTION
    // ---------------------------------------------------------------------------------------------
    @Override
    public void onPhotoClick(int position, View view) {
        Log.d(TAG, "onPhotoClick() called with: photo = [" + view.toString() + "], " +
                "position = [" + position + "]");
    }

    // ---------------------------------------------------------------------------------------------
    //                                             UI
    // ---------------------------------------------------------------------------------------------
    public void updateUI(Event event){
        if (event != null) {
            mDescription.setText(event.getDescription());

            // Display Location
            if (!event.getAddress().get(0).isEmpty()) {
                mLocation_1.setText(event.getAddress().get(0));
                mLocation_1.setVisibility(View.VISIBLE);
            } else  mLocation_1.setVisibility(View.GONE);
            if (!event.getAddress().get(1).isEmpty()) {
                mLocation_2.setText(event.getAddress().get(1));
                mLocation_2.setVisibility(View.VISIBLE);
            } else  mLocation_2.setVisibility(View.GONE);
            if (!event.getAddress().get(2).isEmpty()) {
                mLocation_3.setText(event.getAddress().get(2));
                mLocation_3.setVisibility(View.VISIBLE);
            } else  mLocation_3.setVisibility(View.GONE);
            if (!event.getAddress().get(3).isEmpty()) {
                mLocation_4.setText(event.getAddress().get(3));
                mLocation_4.setVisibility(View.VISIBLE);
            } else  mLocation_4.setVisibility(View.GONE);
            if (!event.getAddress().get(4).isEmpty()) {
                mLocation_5.setText(event.getAddress().get(4));
                mLocation_5.setVisibility(View.VISIBLE);
            } else  mLocation_5.setVisibility(View.GONE);

            mPhotosListAdapter.setNewPhotos(event.getPhotos());

            // Created Uri to recover the static map
            Uri.Builder uriStaticMap
                    = Uri.parse("https://maps.googleapis.com/maps/api/staticmap").buildUpon();
            // Add options
            uriStaticMap
                    .appendQueryParameter("size","300x300")
                    .appendQueryParameter("scale","2")
                    .appendQueryParameter("zoom","16")
                    .appendQueryParameter("key", BuildConfig.google_static_map_api);
            String markers = "size:mid|color:blue|label:E|"
                    + event.getAddress().get(0)+"+"
                    + event.getAddress().get(1)+"+"
                    + event.getAddress().get(2)+"+"
                    + event.getAddress().get(3)+"+"
                    + event.getAddress().get(4);
            uriStaticMap.appendQueryParameter("markers",markers);
            // Display static Map depending on the address of the property
            Glide.with(this)
                    .load(uriStaticMap.build())
                    .apply(RequestOptions.centerCropTransform())
                    .into(mStaticMap);
        }
    }
}
