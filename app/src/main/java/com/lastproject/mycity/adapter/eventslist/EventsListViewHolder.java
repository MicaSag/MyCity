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
import com.lastproject.mycity.models.Event;
import com.lastproject.mycity.repositories.CurrentEventDataRepository;
import com.lastproject.mycity.utils.Converters;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Michaël SAGOT on 26/12/2019.
 */
public class EventsListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String TAG = EventsListViewHolder.class.getSimpleName();

    @BindView(R.id.view_older_event_mcv) MaterialCardView mMCV;
    @BindView(R.id.view_older_event_title) TextView mTitle;
    @BindView(R.id.view_older_event_start_date_label)  TextView mStartDateLabel;
    @BindView(R.id.view_older_event_end_date_label)  TextView mEndDateLabel;
    @BindView(R.id.view_older_event_start_date)  TextView mStartDate;
    @BindView(R.id.view_older_event_end_date)  TextView mEndDate;
    @BindView(R.id.view_older_event_image) ImageView mImage;
    @BindView(R.id.view_older_event_canceled_image) ImageView mCanceledImage;

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
        if (event.getTitle() !=null) mTitle.setText(event.getTitle());

        // Display Event Start Date
        String date;
        LocalDateTime dateLDT;
        if (event.getStartDate() != null) {
            dateLDT = Converters.fromTimestampToLocalDateTime(event.getStartDate());
            date = dateLDT.format(DateTimeFormatter.ISO_DATE);
            mStartDate.setText(date);
        }

        // Display Event End Date
        if (event.getEndDate()!= null) {
            dateLDT = Converters.fromTimestampToLocalDateTime(event.getStartDate());
            date = dateLDT.format(DateTimeFormatter.ISO_DATE);
            mEndDate.setText(date);
        }

        // For indicate item Selected
        Resources res = itemView.getResources();
        Log.d(TAG, "updateWithProperty: event.getEventID() = "+event.getEventID());
        Log.d(TAG, "updateWithProperty: CurrentEventDataRepository.getInstance()\n" +
                "                .getCurrentEventId().getValue() = "+CurrentEventDataRepository.getInstance()
                .getCurrentEventID().getValue());
        if (event.getEventID().equals(CurrentEventDataRepository.getInstance()
                .getCurrentEventID().getValue())) {
            mMCV.setCardBackgroundColor(res.getColorStateList(R.color.colorPrimary));
            mTitle.setTextColor(Color.WHITE);
            mStartDate.setTextColor(Color.WHITE);
            mEndDate.setTextColor(Color.WHITE);
            mStartDateLabel.setTextColor(Color.WHITE);
            mEndDateLabel.setTextColor(Color.WHITE);
        }
        else {
            mMCV.setCardBackgroundColor(Color.WHITE);
            mTitle.setTextColor(res.getColorStateList(R.color.colorPrimary));
            mStartDate.setTextColor(res.getColorStateList(R.color.colorPrimary));
            mEndDate.setTextColor(res.getColorStateList(R.color.colorPrimary));
            mStartDateLabel.setTextColor(res.getColorStateList(R.color.colorPrimary));
            mEndDateLabel.setTextColor(res.getColorStateList(R.color.colorPrimary));
        }


        // Display Event Photo
        if (event.getPhotos() !=null) if (event.getPhotos().get(0) != null)
            glide.load(event.getPhotos().get(0)).into(mImage);

        // Display Canceled
        Log.d(TAG, "updateWithProperty: event.isCanceled() = "+event.isCanceled());
        if (event.isCanceled()) {
            mCanceledImage.setVisibility(View.VISIBLE);
        }
        else {
            mCanceledImage.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        if (mOnEventClick != null) mOnEventClick.onEventClick(mEvent);
    }
}
