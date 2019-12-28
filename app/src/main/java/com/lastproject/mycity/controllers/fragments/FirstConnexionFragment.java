package com.lastproject.mycity.controllers.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lastproject.mycity.R;

/**
 * Created by Michaël SAGOT on 28/12/2019.
 */
public class FirstConnexionFragment extends Fragment {

    // For debugging Mode
    private static final String TAG = FirstConnexionFragment.class.getSimpleName();

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
        return inflater.inflate(R.layout.fragment_first_connexion, container, false);
    }
}
