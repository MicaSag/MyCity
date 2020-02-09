package com.lastproject.mycity.controllers.fragments;


import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lastproject.mycity.BuildConfig;
import com.lastproject.mycity.R;
import com.lastproject.mycity.adapter.photoslist.PhotosListAdapter;
import com.lastproject.mycity.controllers.activities.EventActivity;
import com.lastproject.mycity.controllers.activities.FullScreenImageActivity;
import com.lastproject.mycity.models.Event;
import com.lastproject.mycity.models.views.EventViewModel;
import com.lastproject.mycity.utils.Toolbox;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Michaël SAGOT on 26/12/2019.
 */
public class EventViewFragment extends Fragment implements  PhotosListAdapter.OnPhotoClick,
                                                            SwipeRefreshLayout.OnRefreshListener {

    // For debugging Mode
    private static final String TAG = EventViewFragment.class.getSimpleName();

    // For Design
    @BindView(R.id.fragment_event_details_photos_swipe_container) SwipeRefreshLayout mPhotosSFL;
    @BindView(R.id.fragment_event_details_photos_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.fragment_details_title) TextView mTitle;
    @BindView(R.id.fragment_details_description_text) TextView mDescription;
    @BindView(R.id.fragment_details_location_address_line_1) TextView mLocation_1;
    @BindView(R.id.fragment_details_location_address_line_2) TextView mLocation_2;
    @BindView(R.id.fragment_details_location_address_line_3) TextView mLocation_3;
    @BindView(R.id.fragment_details_location_address_line_4) TextView mLocation_4;
    @BindView(R.id.fragment_details_location_address_line_5) TextView mLocation_5;
    @BindView(R.id.fragment_event_details_start_date_value) TextView mStartDate;
    @BindView(R.id.fragment_event_details_end_date_value) TextView mEndDate;
    @BindView(R.id.fragment_details_static_map) ImageView mStaticMap;

    // Declare EventViewModel
    private EventViewModel mEventViewModel;

    // For Display list of photos
    PhotosListAdapter mPhotosListAdapter;

    public EventViewFragment() {
        // Required empty public constructor
    }
    // ---------------------------------------------------------------------------------------------
    //                                  FRAGMENT INSTANTIATION
    // ---------------------------------------------------------------------------------------------
    public static EventViewFragment newInstance() {
        Log.d(TAG, "newInstance: ");

        // Create new fragment
        EventViewFragment fragment = new EventViewFragment();

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
        View view = inflater.inflate(R.layout.fragment_event_view, container, false);
        ButterKnife.bind(this, view);

        // Configuration Photo RecyclerView
        this.configureRecyclerView();

        // Create Listener on SwipeRefreshLayout of photosList
        mPhotosSFL.setOnRefreshListener(this);

        // Configure EventEstateViewModel
        this.configureEventViewModel();

        return view;
    }
    // ---------------------------------------------------------------------------------------------
    //                                        VIEW MODEL
    // ---------------------------------------------------------------------------------------------
    // Configure ViewModel
    private void configureEventViewModel(){
        Log.d(TAG, "configureEventViewModel: ");

        mEventViewModel = ((EventActivity)getActivity()).getEventViewModel();

        // Observe a change of Current Event
        Log.d(TAG, "configureEventViewModel: mEventViewModel.getCurrentEvent() = "+mEventViewModel);
        mEventViewModel.getCurrentEvent().observe(getViewLifecycleOwner(), this::updateUI);
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
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
    }
    // ---------------------------------------------------------------------------------------------
    //                                           ACTION
    // ---------------------------------------------------------------------------------------------
    @Override
    public void onPhotoClick(int position, View viewClicked) {
        Log.d(TAG, "onPhotoClick() called with: photo = [" + viewClicked.toString() + "], " +
                "position = [" + position + "]");

        // Call Full Screen Image activity
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                    viewClicked, getString(R.string.animation_full_screen_image));
            // Create a intent for call Activity
            Intent intent = new Intent(getActivity(), FullScreenImageActivity.class);
            String photo = mPhotosListAdapter.getPhoto(position);
            Log.d(TAG, "onPhotoClick: photo = "+photo);
            intent.putExtra("PHOTO",photo);
            startActivity(intent, options.toBundle());
        } else {
            Toolbox.startActivity(getActivity(), FullScreenImageActivity.class);
        }
    }

    @Override
    public void onRefresh() {
        Log.d(TAG, "onRefresh: ");
        refreshPhotosList(mEventViewModel.getCurrentEvent().getValue());
    }
    // ---------------------------------------------------------------------------------------------
    //                                             UI
    // ---------------------------------------------------------------------------------------------
    public void updateUI(Event event){
        Log.d(TAG, "updateUI() called with: event = [" + event + "]");
        if (event != null) {

            // Display Title
            mTitle.setText(event.getTitle());

            // Display Description
            mDescription.setText(event.getDescription());

            // Display Event Start Date
            mStartDate.setText(event.getStartDate());

            // Display Event End Date
            mEndDate.setText(event.getEndDate());

            // Display Address
            if (event.getAddress() != null) {
                String address = "";
                if (event.getAddress().size() > 0) {
                    if (!event.getAddress().get(0).isEmpty()){
                        mLocation_1.setText(event.getAddress().get(0));
                        mLocation_1.setVisibility(View.VISIBLE);
                        address += event.getAddress().get(0) + "+";
                    } else mLocation_1.setVisibility(View.GONE);
                } else mLocation_1.setVisibility(View.GONE);
                if (event.getAddress().size() > 1) {
                    if (!event.getAddress().get(1).isEmpty()){
                        mLocation_2.setText(event.getAddress().get(1));
                        mLocation_2.setVisibility(View.VISIBLE);
                        address += event.getAddress().get(1) + "+";
                    } else mLocation_2.setVisibility(View.GONE);
                } else mLocation_2.setVisibility(View.GONE);
                if (event.getAddress().size() > 2) {
                    if (!event.getAddress().get(2).isEmpty()) {
                        mLocation_3.setText(event.getAddress().get(2));
                        mLocation_3.setVisibility(View.VISIBLE);
                        address += event.getAddress().get(2) + "+";
                    } else mLocation_3.setVisibility(View.GONE);
                } else mLocation_3.setVisibility(View.GONE);
                if (event.getAddress().size() > 3) {
                    if (!event.getAddress().get(3).isEmpty()) {
                        mLocation_4.setText(event.getAddress().get(3));
                        mLocation_4.setVisibility(View.VISIBLE);
                        address += event.getAddress().get(3) + "+";
                    } else mLocation_4.setVisibility(View.GONE);
                } else mLocation_4.setVisibility(View.GONE);
                if (event.getAddress().size() > 4) {
                    if (!event.getAddress().get(4).isEmpty()) {
                        mLocation_5.setText(event.getAddress().get(4));
                        mLocation_5.setVisibility(View.VISIBLE);
                        address += event.getAddress().get(4);
                    } else mLocation_5.setVisibility(View.GONE);
                } else mLocation_5.setVisibility(View.GONE);

                // Created Uri to recover the static Mapping
                Uri.Builder uriStaticMap
                        = Uri.parse("https://maps.googleapis.com/maps/api/staticmap").buildUpon();
                // Add options
                uriStaticMap
                        .appendQueryParameter("size", "300x300")
                        .appendQueryParameter("scale", "2")
                        .appendQueryParameter("zoom", "16")
                        .appendQueryParameter("key", BuildConfig.google_static_map_api);
                String markers = "size:mid|color:blue|label:E|" + address;
                uriStaticMap.appendQueryParameter("markers", markers);
                // Display static Map depending on the address of the property
                Glide.with(this)
                        .load(uriStaticMap.build())
                        .apply(RequestOptions.centerCropTransform())
                        .into(mStaticMap);
            }

            // Refresh Photos list with animation
            this.refreshPhotosList(event);
        }
    }

    // FOR Refresh PHOTOS with ANIMATION
    public void refreshPhotosList(Event event) {

        if (event.getPhotos() != null) {
            mPhotosListAdapter.setNewPhotos(event.getPhotos());

            mPhotosSFL.setRefreshing(false);

            LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getActivity(),
                    R.anim.layout_photos_event_animation);

            mRecyclerView.setLayoutAnimation(controller);
            mPhotosListAdapter.setNewPhotos(event.getPhotos());
            mRecyclerView.scheduleLayoutAnimation();
        }
    }
}
