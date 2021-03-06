package com.lastproject.mycity.adapter.photoslist;

import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.lastproject.mycity.R;
import com.lastproject.mycity.controllers.fragments.EventCreateFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotosListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private static final String TAG = PhotosListViewHolder.class.getSimpleName();

    @BindView(R.id.photos_list_image) ImageView mImage;
    @BindView(R.id.photos_list_bt_delete) Button mDeleteButton;

    private PhotosListAdapter.OnPhotoClick mOnPhotoClick;

    // Constructor
    public PhotosListViewHolder(View photoView) {
        super(photoView);
        ButterKnife.bind(this, photoView);
        mImage.setOnClickListener(this);
        mDeleteButton.setOnClickListener(this);
    }

    // Method to update the current item
    public void updateWithPhoto(Class caller, String photo, RequestManager glide,
                                PhotosListAdapter.OnPhotoClick callback_OnPhotoClick){
        Log.d(TAG, "updateWithPhoto: ");
        mOnPhotoClick = callback_OnPhotoClick;

        // Get Resources
        Resources res = itemView.getResources();

        // Display Photo
        if (photo !=null) glide.load(photo).into(mImage);

        // Button Visibility
        if (caller == EventCreateFragment.class) {
            mDeleteButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: getAdapterPosition = "+getAdapterPosition());
        mOnPhotoClick.onPhotoClick(getAdapterPosition(),view);
        if (view == mImage) Log.d(TAG, "onClick: image");
        if (view == mDeleteButton) Log.d(TAG, "onClick: button delete");
    }
}
