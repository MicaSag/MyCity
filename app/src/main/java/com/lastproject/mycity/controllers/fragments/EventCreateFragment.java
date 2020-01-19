package com.lastproject.mycity.controllers.fragments;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.lastproject.mycity.R;
import com.lastproject.mycity.adapter.photoslist.PhotosListAdapter;
import com.lastproject.mycity.controllers.activities.EventActivity;
import com.lastproject.mycity.firebase.database.firestore.models.EventFireStore;
import com.lastproject.mycity.models.Event;
import com.lastproject.mycity.models.views.EventViewModel;
import com.lastproject.mycity.utils.Toolbox;
import com.lastproject.mycity.views.CityEditText;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Michaël SAGOT on 06/01/2020.
 */
public class EventCreateFragment extends Fragment implements    PhotosListAdapter.OnPhotoClick {

    // For debugging Mode
    private static final String TAG = EventCreateFragment.class.getSimpleName();

    // For Design
    @BindView(R.id.fragment_event_create_photos_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.fragment_event_create_title) CityEditText mTitle;
    @BindView(R.id.fragment_event_create_description_text) CityEditText mDescription;
    @BindView(R.id.fragment_event_create_location_address_line_1) CityEditText mLocation_1;
    @BindView(R.id.fragment_event_create_location_address_line_2) CityEditText mLocation_2;
    @BindView(R.id.fragment_event_create_location_address_line_3) CityEditText mLocation_3;
    @BindView(R.id.fragment_event_create_location_address_line_4) CityEditText mLocation_4;
    @BindView(R.id.fragment_event_create_location_address_line_5) CityEditText mLocation_5;
    @BindView(R.id.fragment_event_create_start_date_value) TextInputEditText mStartDate;
    @BindView(R.id.fragment_event_create_end_date_value) TextInputEditText mEndDate;
    @BindView(R.id.fragment_event_create_static_map) ImageView mStaticMap;

    // Declare EventViewModel
    private EventViewModel mEventViewModel;

    // Declarations for management of the date fields with a DatePickerDialog
    private DatePickerDialog mStartDatePickerDialog;
    private DatePickerDialog mEndDatePickerDialog;
    private Calendar newCalendar;

    // For Display list of photos
    private PhotosListAdapter mPhotoListAdapter;

    public EventCreateFragment() {
        // Required empty public constructor
    }
    // ---------------------------------------------------------------------------------------------
    //                                  FRAGMENT INSTANTIATION
    // ---------------------------------------------------------------------------------------------
    public static EventCreateFragment newInstance() {
        Log.d(TAG, "newInstance: ");

        // Create new fragment
        EventCreateFragment fragment = new EventCreateFragment();

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
        View view = inflater.inflate(R.layout.fragment_event_create, container, false);
        ButterKnife.bind(this, view);

        // Configuration Photo RecyclerView
        this.configureRecyclerView();

        // Configure eventViewModel
        this.configureEventViewModel();

        // Management of Date Fields
        this.manageDateFields();

        // Configure Mode CREATE/UPDATE
        if (mEventViewModel.getMode().getValue() == EventViewModel.EventMode.UPDATE) {
            // Restore Estate on the View
            mEventViewModel.getCurrentEvent().observe(this, this::restoreData);
        }
        return view;
    }
    // ---------------------------------------------------------------------------------------------
    //                                        VIEW MODEL
    // ---------------------------------------------------------------------------------------------
    // Configure ViewModel
    private void configureEventViewModel(){

        mEventViewModel = ((EventActivity)getActivity()).getEventViewModel();

        // Observe a change of Start Date
        mEventViewModel.getStartDate().observe(this,this::refreshStartDate);
        // Observe a change of End Date
        mEventViewModel.getEndDate().observe(this,this::refreshEndDate);
        // Observe a change of the photo list
        mEventViewModel.getPhotos().observe(this,this::refreshPhotos);
    }
    // --------------------------------------------------------------------------------------------
    //                                    CONFIGURATION
    // --------------------------------------------------------------------------------------------
    // Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView(){
        // Create adapter
        mPhotoListAdapter = new PhotosListAdapter( this.getClass(),
                                                    Glide.with(this),
                                    this);
        // Attach the adapter to the recyclerView to populate items
        mRecyclerView.setAdapter(mPhotoListAdapter);
        // Set layout manager to position the items
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
    }
    // ---------------------------------------------------------------------------------------------
    //                                           ACTION
    // ---------------------------------------------------------------------------------------------
    // Click on StartDate Field
    @OnClick(R.id.fragment_event_create_start_date_value)
    public void onClickStartDate(View view) {
        mStartDatePickerDialog.show();
    }
    // Click on EndDate Field
    @OnClick(R.id.fragment_event_create_end_date_value)
    public void onClickEndDate(View view) {
        mEndDatePickerDialog.show();
    }
    // PHOTOS ACTIONS
    // ---------------
    // Click on Take Photo
    @OnClick(R.id.estate_iv_take_photo)
    public void onTakePhotoClick(View view) {
        this.dispatchTakePictureIntent();
    }
    // Click on Select Photo
    @OnClick(R.id.estate_iv_select_photo)
    public void onSelectPhotoClick(View view) {
        this.selectImage();
    }
    // Click photo of the recycler view
    @Override
    public void onPhotoClick(int position,View view) {
        Log.d(TAG, "onPhotoClick: ");
        if (view.getId() == R.id.photos_list_image) Log.d(TAG, "onClick: image");
        if (view.getId() == R.id.photos_list_bt_delete) {
            mEventViewModel.getPhotos().getValue().remove(position);
            this.refreshPhotos(mEventViewModel.getPhotos().getValue());
        }
    }
    // ---------------------------------------------------------------------------------------------
    //                                     MANAGE DATE FIELDS
    // ---------------------------------------------------------------------------------------------
    private void manageDateFields() {
        newCalendar = Calendar.getInstance();
        setStartDateField();
        setEndDateField();
    }
    // Manage Start Date Field
    private void setStartDateField() {
        // Create a DatePickerDialog and manage it
        mStartDatePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                String startDate = Toolbox.dateReformatJJMMSSAA(year, monthOfYear+1, dayOfMonth);

                // Update startDate in ViewModel
                mEventViewModel.setStartDate(startDate);
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));
    }
    // Manage End Date Field
    private void setEndDateField() {
        // Create a DatePickerDialog and manage it
        mEndDatePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                String endDate = Toolbox.dateReformatJJMMSSAA(year, monthOfYear+1, dayOfMonth);

                // Update endDate in ViewModel
                mEventViewModel.setEndDate(endDate);
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));
    }
    // ---------------------------------------------------------------------------------------------
    //                                     MANAGE PHOTO LIST
    // ---------------------------------------------------------------------------------------------
    private void dispatchTakePictureIntent() {
        Log.d(TAG, "dispatchTakePictureIntent: ");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.lastproject.mycity.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                Log.d(TAG, "dispatchTakePictureIntent: photoURI = "+photoURI);

                // Save a file: path for use with ACTION_VIEW intents
                mEventViewModel.setCurrentPhotoPath(photoFile.getAbsolutePath());
                Log.d(TAG, "dispatchTakePictureIntent: mCurrentPhotoPath = "+mEventViewModel);

                getActivity().startActivityForResult(takePictureIntent, EventViewModel.REQUEST_TAKE_PHOTO);
            }
        }
    }
    // Create à image File name
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "event_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",    /* suffix */
                storageDir      /* directory */
        );
        return image;
    }
    // Selects a photo in a device location
    public void selectImage() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            getActivity().startActivityForResult(intent, EventViewModel.REQUEST_IMAGE_GET);
        }
    }
    // ---------------------------------------------------------------------------------------------
    //                                         RESTORE DATA
    // ---------------------------------------------------------------------------------------------
    protected void restoreData(Event event) {

        // Restore Start Date
        mEventViewModel.getStartDate().setValue(event.getStartDate());

        // Restore End Date
        mEventViewModel.getEndDate().setValue(event.getEndDate());

        // Restore Photo List
        mEventViewModel.setPhotos(event.getPhotos());

        // Display Data
        updateUI(event);
    }
    // ---------------------------------------------------------------------------------------------
    //                                             UI
    // ---------------------------------------------------------------------------------------------
    // Refresh the Start date Field
    private void refreshStartDate(String startDate){
        mStartDate.setText(startDate);
    }
    // Refresh the End date Field
    private void refreshEndDate(String endDate) {
        mEndDate.setText(endDate);
    }
    // refresh the Photo List
    private void refreshPhotos(List<String> photos){
        Log.d(TAG, "refreshPhotos() called with: photos = [" + photos + "]");
        mPhotoListAdapter.setNewPhotos(photos);
    }

    // Return Data Event UI
    public EventFireStore getEventFireStore() {
        Log.d(TAG, "getEventFireStore: ");

        ArrayList<String> address = new ArrayList<>();
        address.add(mLocation_1.getText().toString());
        address.add(mLocation_2.getText().toString());
        address.add(mLocation_3.getText().toString());
        address.add(mLocation_4.getText().toString());
        address.add(mLocation_5.getText().toString());
        EventFireStore efs = new EventFireStore(null,
                                                mTitle.getText().toString(),
                                                mDescription.getText().toString(),
                                                mEventViewModel.getPhotos().getValue(),
                                                address,
                                                null,
                                                mStartDate.getText().toString(),
                                                mEndDate.getText().toString(),
                                                false);
        Log.d(TAG, "getEventFireStore: EFS = "+efs);
        return efs;
    }
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
                if (event.getAddress().size() > 0) mLocation_1.setText(event.getAddress().get(0));
                if (event.getAddress().size() > 1) mLocation_2.setText(event.getAddress().get(1));
                if (event.getAddress().size() > 2) mLocation_3.setText(event.getAddress().get(2));
                if (event.getAddress().size() > 3) mLocation_4.setText(event.getAddress().get(3));
                if (event.getAddress().size() > 4) mLocation_5.setText(event.getAddress().get(4));
            }
        }
    }
}
