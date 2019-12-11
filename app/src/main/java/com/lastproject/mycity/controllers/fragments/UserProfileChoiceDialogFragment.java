package com.lastproject.mycity.controllers.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toolbar;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.lastproject.mycity.R;
import com.lastproject.mycity.controllers.activities.AuthenticationActivity;
import com.lastproject.mycity.firestore.helpers.UserHelper;
import com.lastproject.mycity.firestore.models.User;
import com.lastproject.mycity.utils.Toolbox;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnItemSelected;

/**
 * Created by Michaël SAGOT on 08/12/2019.
 */

public class UserProfileChoiceDialogFragment extends DialogFragment {

    // For debugging Mode
    private static final String TAG = UserProfileChoiceDialogFragment.class.getSimpleName();

    // For Design
    @BindView(R.id.fragment_user_profile_choice_tf_mayor_id) TextInputLayout mMayorID;
    @BindView(R.id.fragment_user_profile_choice_rb_mayor) RadioButton mRadioButtonMayor;

    public UserProfileChoiceDialogFragment() {
        // Required empty public constructor
    }

    // ---------------------------------------------------------------------------------------------
    //                                  FRAGMENT INSTANTIATION
    // ---------------------------------------------------------------------------------------------
    public static UserProfileChoiceDialogFragment newInstance() {
        Log.d(TAG, "UserProfileChoiceDialogFragment: ");

        // Create new fragment
        UserProfileChoiceDialogFragment fragment = new UserProfileChoiceDialogFragment();

        return fragment;
    }
    // ---------------------------------------------------------------------------------------------
    //                                    ENTRY POINT
    // ---------------------------------------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Initialize View
        this.init();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile_choice_dialog, container, false);

        // Get & serialise all views
        ButterKnife.bind(this, view);

        return view;
    }
    // --------------------------------------------------------------------------------------------
    //                                    INITIALIZATION
    // --------------------------------------------------------------------------------------------
    private void init(){
        Log.d(TAG, "init: ");

        getDialog().setTitle("Profile");
        updateUI();
    }

    // ---------------------------------------------------------------------------------------------
    //                                           ACTION
    // ---------------------------------------------------------------------------------------------
    @OnCheckedChanged(R.id.fragment_user_profile_choice_rb_mayor)
    public void onRadioButtonCheckChanged(CompoundButton button, boolean checked){
        if (checked) mMayorID.setVisibility(View.VISIBLE);
        else mMayorID.setVisibility(View.GONE);
    }

    @OnClick(R.id.fragment_user_profile_choice_bt_create)
    public void onCreateClick(View view) {
        Log.d(TAG, "onCreateClick: ");

        createUserInFireStore();

        Toolbox.showSnackBar(((AuthenticationActivity)getActivity()).getConstraintLayout(),
                    getString(R.string.connection_succeed));
    }

    private void createUserInFireStore() {
        Log.d(TAG, "createUserInFireStore: ");

        /*Activity act = ((AuthenticationActivity)getActivity());

        String urlPicture = (.getPhotoUrl() != null)
                ? this.getCurrentUser().getPhotoUrl().toString() : null;
        String userName = this.getCurrentUser().getDisplayName();
        String uid = this.getCurrentUser().getUid();
        String email = this.getCurrentUser().getEmail();
        String phoneNumber = this.getCurrentUser().getPhoneNumber();

        // Not Mayor by default but Citizen
        UserHelper.createUser(uid, userName, false, urlPicture, email, phoneNumber)
                .addOnFailureListener(this.onFailureListener());*/
    }
    // --------------------------------------------------------------------------------------------
    //                                        UI
    // --------------------------------------------------------------------------------------------
    public void updateUI() {
        Log.d(TAG, "updateUI: ");

    }
}
