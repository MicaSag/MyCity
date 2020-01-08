package com.lastproject.mycity.controllers.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.lastproject.mycity.R;
import com.lastproject.mycity.controllers.activities.FullScreenImageActivity;
import com.lastproject.mycity.models.Event;
import com.lastproject.mycity.models.views.EventViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Michaël SAGOT on 07/01/2020.
 */
public class FullScreenImageFragment extends Fragment {

    // FOR Debug
    private static final String TAG = FullScreenImageFragment.class.getSimpleName();

    // Adding @BindView in order to indicate to ButterKnife to get & serialise it
    @BindView(R.id.fragment_full_screen_image) ImageView mImage;

    // Declaring a Glide object
    private RequestManager mGlide;

    public FullScreenImageFragment() {
        // Required empty public constructor
    }
    // ---------------------------------------------------------------------------------------------
    //                                  FRAGMENT INSTANTIATION
    // ---------------------------------------------------------------------------------------------
    public static FullScreenImageFragment newInstance() {
        Log.d(TAG, "newInstance: ");

        // Create new fragment
        return new FullScreenImageFragment();
    }
    // ---------------------------------------------------------------------------------------------
    //                                    ENTRY POINT
    // ---------------------------------------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_full_screen_image, container, false);

        // Initialize ButterKnife
        ButterKnife.bind(this, view);

        // Initialize glide
        mGlide = Glide.with(this);

        // Update UI with Photo
        this.updateUI();

        return view;
    }
    // ---------------------------------------------------------------------------------------------
    //                                             UI
    // ---------------------------------------------------------------------------------------------
    public void updateUI(){
        Log.d(TAG, "updateUI: ");

        // Display Event Photo
        String photo = ((FullScreenImageActivity) getActivity()).mPhoto;
        Log.d(TAG, "updateUI: photo = "+photo);

        mGlide.load(photo).into(mImage);
    }
}
