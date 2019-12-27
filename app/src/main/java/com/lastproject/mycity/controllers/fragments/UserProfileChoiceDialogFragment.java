package com.lastproject.mycity.controllers.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.lastproject.mycity.R;
import com.lastproject.mycity.controllers.activities.AuthenticationActivity;
import com.lastproject.mycity.models.views.AuthenticationViewModel;
import com.lastproject.mycity.utils.Toolbox;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by Michaël SAGOT on 08/12/2019.
 */

public class UserProfileChoiceDialogFragment extends DialogFragment {

    // For debugging Mode
    private static final String TAG = UserProfileChoiceDialogFragment.class.getSimpleName();

    // For Design
    @BindView(R.id.fragment_user_profile_choice_tf_mayor_id) TextInputLayout mMayorID;
    @BindView(R.id.fragment_user_profile_choice_rb_mayor) RadioButton mRadioButtonMayor;
    @BindView(R.id.fragment_user_profile_choice_et_mayor_id) TextInputEditText mMayorIDValue;


    // Declare ViewModel
    private AuthenticationViewModel mAuthenticationViewModel;

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

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile_choice_dialog, container, false);

        // Get & serialise all views
        ButterKnife.bind(this, view);

        // Configure ViewModel
        configureViewModel();

        // Initialize View
        this.init();

        return view;
    }
    // ---------------------------------------------------------------------------------------------
    //                                        VIEW MODEL
    // ---------------------------------------------------------------------------------------------
    // Configure ViewModel
    private void configureViewModel() {

        mAuthenticationViewModel = ((AuthenticationActivity) getActivity()).getAuthenticationViewModel();

        mAuthenticationViewModel.getRegistrationStatus().observe(this, registrationStatus -> {
            if (registrationStatus == null)  {
                return;
            }

            switch (registrationStatus) {
                case REGISTRATION_OK:
                    Log.d(TAG, "onChanged: REGISTRATION_OK");
                    Toolbox.showSnackBar(((AuthenticationActivity)getActivity()).getConstraintLayout(),
                            getString(R.string.registration_succeed));

                    // // Start Mayor Activity or Citizen Activity
                    ((AuthenticationActivity) getActivity()).startInterface();
                    break;

                case REGISTRATION_MAYOR_UPDATE_USER_FAILED:
                    Log.d(TAG, "onChanged: REGISTRATION_MAYOR_UPDATE_USER_FAILED");
                    Toolbox.showSnackBar(((AuthenticationActivity)getActivity()).getConstraintLayout(),
                            getString(R.string.registration_mayor_update_user_failed));
                    break;

                case REGISTRATION_MAYOR_WRONG_CODE_ID:
                    Log.d(TAG, "onChanged: REGISTRATION_MAYOR_WRONG_CODE_ID");
                    Toolbox.showSnackBar(((AuthenticationActivity)getActivity()).getConstraintLayout(),
                            getString(R.string.registration_mayor_wrong_code_id));
                    break;

                case REGISTRATION_ERROR:
                    Log.d(TAG, "onChanged: REGISTRATION_ERROR");
                    Toolbox.showSnackBar(((AuthenticationActivity)getActivity()).getConstraintLayout(),
                            getString(R.string.registration_error));
                    break;
            }
        });
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
    // When the Radio Button Mayor check changed
    @OnCheckedChanged(R.id.fragment_user_profile_choice_rb_mayor)
    public void onRadioButtonCheckChanged(CompoundButton button, boolean checked){
        Log.d(TAG, "onRadioButtonCheckChanged: ");

        if (checked) mMayorID.setVisibility(View.VISIBLE);
        else mMayorID.setVisibility(View.GONE);
    }

    // When you click on the Button Create
    @OnClick(R.id.fragment_user_profile_choice_bt_create)
    public void onCreateClick(View view) {
        Log.d(TAG, "onCreateClick: ");

        // Create Mayor in FireStore
        if (mRadioButtonMayor.isChecked()){
            // Assigns the role of mayor to the user
            mAuthenticationViewModel.updateMayorUserByCodeID(mMayorIDValue.getText().toString(),
                    mAuthenticationViewModel.getCurrentUser().getUid());
        } else {
            // Create Citizen in FireStore
            mAuthenticationViewModel.createUser(false);
        }
    }
    // --------------------------------------------------------------------------------------------
    //                                        UI
    // --------------------------------------------------------------------------------------------
    public void updateUI() {
        Log.d(TAG, "updateUI: ");

    }
}
