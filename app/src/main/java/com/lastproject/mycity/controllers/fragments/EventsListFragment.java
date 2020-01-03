package com.lastproject.mycity.controllers.fragments;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lastproject.mycity.R;
import com.lastproject.mycity.adapter.eventslist.EventsListAdapter;
import com.lastproject.mycity.injections.Injection;
import com.lastproject.mycity.injections.ViewModelFactory;
import com.lastproject.mycity.models.Event;
import com.lastproject.mycity.models.views.ListEventsViewModel;
import com.lastproject.mycity.repositories.CurrentEventDataRepository;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Michaël SAGOT on 26/12/2019.
 */
public class EventsListFragment extends Fragment {

    // For debugging Mode
    private static final String TAG = EventsListFragment.class.getSimpleName();

    // Declare ViewModel
    private ListEventsViewModel mListEventsViewModel;

    //For Data
    // Declare list of property & Adapter
    private List<Event> mEvents;
    private EventsListAdapter mEventsListAdapter;

    // Declare OnPropertyClick Interface
    private EventsListAdapter.OnEventClick mOnPropertyClick;

    // For Design
    @BindView(R.id.fragment_list_events_recycler_view) RecyclerView mRecyclerView;

    public EventsListFragment() {
        // Required empty public constructor
    }
    // ---------------------------------------------------------------------------------------------
    //                                  FRAGMENT INSTANTIATION
    // ---------------------------------------------------------------------------------------------
    public static EventsListFragment newInstance() {
        Log.d(TAG, "newInstance: ");

        // Create new fragment
        return new EventsListFragment();
    }
    // ---------------------------------------------------------------------------------------------
    //                                    ENTRY POINT
    // ---------------------------------------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events_list, container, false);

        // Initialize ButterKnife
        ButterKnife.bind(this, view);

        // Configure ViewModel
        configureViewModel();

        // Call during UI creation
        this.configureRecyclerView();

        return view;
    }
    // ---------------------------------------------------------------------------------------------
    //                                        VIEW MODEL
    // ---------------------------------------------------------------------------------------------
    // Configure ViewModel
    private void configureViewModel() {
        Log.d(TAG, "configureViewModel: ");

        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getContext());
        mListEventsViewModel = ViewModelProviders.of(this, mViewModelFactory)
                .get(ListEventsViewModel.class);

        // Observe a change of Current Events List in Room
        mListEventsViewModel.getEventsRoom().observe(this, this::updateEventsList);

        // Observe a change of Current Event (for update selected event)
        CurrentEventDataRepository.getInstance().getCurrentEventID().observe(this, this::updateEventsList);
    }
    // --------------------------------------------------------------------------------------------
    //                                    CONFIGURATION
    // --------------------------------------------------------------------------------------------
    // Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView(){
        // Reset list
        mEvents = new ArrayList<>();
        // Create adapter passing the list of users
        mEventsListAdapter = new EventsListAdapter(mEvents, Glide.with(this), mOnPropertyClick);
        // Attach the adapter to the recyclerView to populate items
        mRecyclerView.setAdapter(mEventsListAdapter);
        // Set layout manager to position the items
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL,false));
    }
    // --------------------------------------------------------------------------------------------
    //                                        ACTIONS
    // --------------------------------------------------------------------------------------------
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mOnPropertyClick = (EventsListAdapter.OnEventClick)context;
    }
    // ---------------------------------------------------------------------------------------------
    //                                          UI
    // ---------------------------------------------------------------------------------------------
    // Update the list of Events
    private void updateEventsList(List<Event> events){
        Log.d(TAG, "updateEventsList() called with: events = [" + events + "]");

        if (events != null) mEventsListAdapter.updateData(events);
    }
    // Update the list of Events
    private void updateEventsList(String currentEventId){

        mEventsListAdapter.notifyDataSetChanged();
    }
}