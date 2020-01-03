package com.lastproject.mycity.adapter.eventslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.lastproject.mycity.R;
import com.lastproject.mycity.models.Event;

import java.util.List;

/**
 * Created by Michaël SAGOT on 26/12/2019.
 */
public class EventsListAdapter extends RecyclerView.Adapter<EventsListViewHolder> {

    // For Debug
    private static final String TAG = EventsListAdapter.class.getSimpleName();

    // Declaring a Glide object
    private RequestManager mGlide;

    // For Data
    private List<Event> mListEvents;

    // CALLBACK
    private final OnEventClick mCallback;

    // CONSTRUCTOR
    public EventsListAdapter(List<Event> listEvents, RequestManager glide, OnEventClick callback) {
        mListEvents = listEvents;
        mGlide = glide;
        mCallback = callback;
    }

    @Override
    public EventsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_events_list_view_holder, parent, false);

        return new EventsListViewHolder(view);
    }

    // For update View Holder with Event
    @Override
    public void onBindViewHolder(EventsListViewHolder viewHolder, int position) {
        viewHolder.updateWithProperty(mListEvents.get(position), mGlide, mCallback);
    }

    // Return the size of the recycler view
    @Override
    public int getItemCount() {
        return mListEvents.size();
    }

    // Returns the Estate Identifier of the current position
    public Event getEvent(int position){
        return mListEvents.get(position);
    }

    // Update le recycler view data
    public void updateData(List<Event> events){
        this.mListEvents = events;
        this.notifyDataSetChanged();
    }

    public interface OnEventClick{
        void onEventClick(Event event);
    }
}
