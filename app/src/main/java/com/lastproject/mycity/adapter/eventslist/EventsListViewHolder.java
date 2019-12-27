package com.lastproject.mycity.adapter.eventslist;

import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.google.android.material.card.MaterialCardView;
import com.lastproject.mycity.R;
import com.lastproject.mycity.repositories.CurrentEventDataRepository;
import com.lastproject.mycity.room.models.Event;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Michaël SAGOT on 26/12/2019.
 */
public class EventsListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String TAG = EventsListViewHolder.class.getSimpleName();

    @BindView(R.id.view_older_event_mcv) MaterialCardView mMCV;
    @BindView(R.id.view_older_event_title) TextView mTitle;
    @BindView(R.id.view_older_event_start_date)  TextView mStartDate;
    @BindView(R.id.view_older_event_end_date)  TextView mEndDate;
    @BindView(R.id.view_older_event_image) ImageView mImage;

    private EventsListAdapter.OnEventClick mOnEventClick;
    private Event mEvent;

    // Constructor
    public EventsListViewHolder(View eventView) {
        super(eventView);
        ButterKnife.bind(this, eventView);
        eventView.setOnClickListener(this);
    }

    // Method to update the current item
    public void updateWithProperty(Event event, RequestManager glide, EventsListAdapter.OnEventClick callback){
        mEvent = event;
        mOnEventClick = callback;

        // Display Event Title
        mTitle.setText(event.getTitle());

        // Display Event Photo
        if (event.getPhotos() !=null) if (event.getPhotos().get(0) != null)
            glide.load(event.getPhotos().get(0)).into(mImage);

        // Display Event Start Date
        mStartDate.setText(event.getStartDate().toString());

        // Display Event End Date
        mEndDate.setText(event.getEndDate().toString());

        // For indicate item Selected
        Resources res = itemView.getResources();
        Log.d(TAG, "updateWithProperty: event.getEventId() = "+event.getEventId());
        Log.d(TAG, "updateWithProperty: CurrentEventDataRepository.getInstance()\n" +
                "                .getCurrentEventId().getValue() = "+CurrentEventDataRepository.getInstance()
                .getCurrentEventId().getValue());
        if (event.getEventId().equals(CurrentEventDataRepository.getInstance()
                .getCurrentEventId().getValue())) {
            mMCV.setCardBackgroundColor(res.getColorStateList(R.color.colorAccent));
            mTitle.setTextColor(Color.WHITE);
        }
        else {
            mMCV.setCardBackgroundColor(Color.WHITE);
            mTitle.setTextColor(res.getColorStateList(R.color.colorAccent));
        }
    }

    @Override
    public void onClick(View view) {
        if (mOnEventClick != null) mOnEventClick.onEventClick(mEvent);
    }
}
