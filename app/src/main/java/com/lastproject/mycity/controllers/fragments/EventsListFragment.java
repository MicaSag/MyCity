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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lastproject.mycity.R;
import com.lastproject.mycity.adapter.eventslist.EventsListAdapter;
import com.lastproject.mycity.controllers.activities.EventActivity;
import com.lastproject.mycity.injections.Injection;
import com.lastproject.mycity.injections.ViewModelFactory;
import com.lastproject.mycity.models.Event;
import com.lastproject.mycity.models.views.EventViewModel;
import com.lastproject.mycity.models.views.ListEventsViewModel;
import com.lastproject.mycity.repositories.CurrentEventIDDataRepository;
import com.lastproject.mycity.utils.Toolbox;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


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
    public @BindView(R.id.fragment_list_events_fab) FloatingActionButton mButtonCreateEvent;

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

        // Configure the display of the UI according to the user's profile
        configureProfileUI();

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
        mListEventsViewModel.getAllEventsRoomByInseeID(
                mListEventsViewModel.getCurrentTownHall().getValue().getInseeID(),
                mListEventsViewModel.getCurrentUser().getUserID())
                .observe(this, this::updateEventsList);

        // Observe a change of Current Event (for update selected event)
        CurrentEventIDDataRepository.getInstance().getCurrentEventID().observe(this, this::updateEventsList);
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
        int orientation;
        if (getResources().getBoolean(R.bool.is_w1280)) {orientation = RecyclerView.HORIZONTAL;}
        else{orientation = RecyclerView.VERTICAL;}
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                orientation,false));
    }
    // --------------------------------------------------------------------------------------------
    //                                        ACTIONS
    // --------------------------------------------------------------------------------------------
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mOnPropertyClick = (EventsListAdapter.OnEventClick)context;
    }

    @OnClick(R.id.fragment_list_events_fab)
    public void onClickFloatingActionButton(View v) {
        Log.d(TAG, "onClickFloatingActionButton: ");

        // Call EventActivity with CREATE MODE
        Toolbox.startActivity(  getActivity(),
                                EventActivity.class,
                                EventViewModel.MODE,
                                EventViewModel.EventMode.CREATE.name());
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
        Log.d(TAG, "updateEventsList: ");

        mEventsListAdapter.notifyDataSetChanged();
    }
    // configure the display of the UI according to the user's profile
    private void configureProfileUI(){
        Log.d(TAG, "configureProfileUI: ");

        // The event creation button should only be visible if the user is a mayor
        if (mListEventsViewModel.getCurrentUser().isMayor())
            mButtonCreateEvent.setVisibility(View.VISIBLE);
        else mButtonCreateEvent.setVisibility(View.GONE);
    }
}