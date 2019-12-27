package com.lastproject.mycity.controllers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.lastproject.mycity.R;
import com.lastproject.mycity.controllers.bases.BaseActivity;
import com.lastproject.mycity.controllers.fragments.UserProfileChoiceDialogFragment;
import com.lastproject.mycity.firebase.database.firestore.models.User;
import com.lastproject.mycity.injections.Injection;
import com.lastproject.mycity.injections.ViewModelFactory;
import com.lastproject.mycity.models.views.AuthenticationViewModel;
import com.lastproject.mycity.network.retrofit.models.Insee;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Michaël SAGOT on 01/12/2019.
 */

public class AuthenticationActivity extends BaseActivity
{

    // For Debug
    private static final String TAG = AuthenticationActivity.class.getSimpleName();

    // Declare AuthenticationViewModel
    private AuthenticationViewModel mAuthenticationViewModel;

    // Adding @BindView in order to indicate to ButterKnife to get & serialise it
    @BindView(R.id.activity_authentication_constraint_layout) ConstraintLayout mConstraintLayout;

    // For Authentication
    // Identifier for Sign-In Activity
    private static final int RC_SIGN_IN = 100;

    // ---------------------------------------------------------------------------------------------
    //                                DECLARATION BASE METHODS
    // ---------------------------------------------------------------------------------------------
    // BASE METHOD Implementation
    // Get the activity layout
    // CALLED BY BASE METHOD 'onCreate(...)'
    @Override
    protected int getActivityLayout() {
        return R.layout.activity_authentication;
    }

    // BASE METHOD Implementation
    // Get the coordinator layout
    // CALLED BY BASE METHOD
    @Override
    public View getConstraintLayout() {
        return mConstraintLayout;
    }

    // ---------------------------------------------------------------------------------------------
    //                                        ENTRY POINT
    // ---------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Configure ViewModel
        this.configureViewModel();
    }
    // ---------------------------------------------------------------------------------------------
    //                                        VIEW MODEL
    // ---------------------------------------------------------------------------------------------
    // Configure ViewModel
    private void configureViewModel(){
        ViewModelFactory modelFactory = Injection.provideViewModelFactory(this);
        mAuthenticationViewModel = ViewModelProviders.of(this, modelFactory)
                .get(AuthenticationViewModel.class);
    }

    public AuthenticationViewModel getAuthenticationViewModel() {
        return mAuthenticationViewModel;
    }
    // ---------------------------------------------------------------------------------------------
    //                                          ACTIONS
    // ---------------------------------------------------------------------------------------------

    @OnClick(R.id.activity_authentication_email_login_button)
    public void onClickEmailLoginButton() {
        Log.d(TAG, "onClickEmailLoginButton: ²");
        this.startEmailSignInActivity();
    }

    @OnClick(R.id.activity_authentication_facebook_login_button)
    public void onClickFacebookLoginButton() {
        Log.d(TAG, "onClickFacebookLoginButton: ²");
        this.startFaceBookSignInActivity();
    }

    @OnClick(R.id.activity_authentication_google_login_button)
    public void onClickGoogleLoginButton() {
        Log.d(TAG, "onClickGoogleLoginButton: ");
        this.startGoogleSignInActivity();
    }

    @OnClick(R.id.activity_authentication_twitter_login_button)
    public void onClickTwitterLoginButton() {
        Log.d(TAG, "onClickTwitterLoginButton: ");
        this.startTwitterSignInActivity();
    }

    // ---------------------------------------------------------------------------------------------
    //                                 FIRE BASE AUTHENTICATION
    // ---------------------------------------------------------------------------------------------
    // ----------------------------
    // AUTHENTICATION WITH EMAIL
    // ----------------------------
    // Launch Email Sign-In
    private void startEmailSignInActivity(){
        Log.d(TAG, "startEmailSignInActivity: ");

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setAvailableProviders(
                                Arrays.asList(  new AuthUI.IdpConfig.EmailBuilder().build()))
                        .setIsSmartLockEnabled(false, true) // Email list possible
                        .setLogo(R.drawable.screen_town_hall)
                        .build(),
                RC_SIGN_IN);
    }

    // ----------------------------
    // AUTHENTICATION WITH GOOGLE
    // ----------------------------
    // Launch Google Sign-In
    private void startGoogleSignInActivity(){
        Log.d(TAG, "startGoogleSignInActivity: ");

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setAvailableProviders(
                                Arrays.asList(  new AuthUI.IdpConfig.GoogleBuilder().build()))
                        .setIsSmartLockEnabled(false, true) // Email list possible
                        .setLogo(R.drawable.screen_town_hall)
                        .build(),
                RC_SIGN_IN);
    }

    // -----------------------------
    // AUTHENTICATION WITH FACEBOOK
    // -----------------------------
    // Launch Facebook Sign-In
    private void startFaceBookSignInActivity(){
        Log.d(TAG, "startSignInActivity: ");

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setAvailableProviders(
                                Arrays.asList( new AuthUI.IdpConfig.FacebookBuilder().build())) // FACEBOOK
                        .setIsSmartLockEnabled(false, true) // Email list possible
                        .setLogo(R.drawable.screen_town_hall)
                        .build(),
                RC_SIGN_IN);
    }
    // -----------------------------
    // AUTHENTICATION WITH TWITTER
    // -----------------------------
    // Launch Twitter Sign-In
    private void startTwitterSignInActivity(){
        Log.d(TAG, "startSignInActivity: ");

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setAvailableProviders(
                                Arrays.asList( new AuthUI.IdpConfig.TwitterBuilder().build())) // TWITTER
                        .setIsSmartLockEnabled(false, true) // Email list possible
                        .setLogo(R.drawable.screen_town_hall)
                        .build(),
                RC_SIGN_IN);
    }

    // -----------------------------
    //    AUTHENTICATION RETURN
    // -----------------------------
    // Method that retrieves the result of the authentication
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: ");

        super.onActivityResult(requestCode, resultCode, data);

        // Handle SignIn Activity response on activity result
        this.handleResponseAfterSignIn(requestCode, resultCode, data);
    }

    // Method that handles response after SignIn Activity close
    private void handleResponseAfterSignIn(int requestCode, int resultCode, Intent data){
    Log.d(TAG, "handleResponseAfterSignIn: ");

    IdpResponse response = IdpResponse.fromResultIntent(data);

    Log.d(TAG, "handleResponseAfterSignIn: requestCode = "+requestCode);
    Log.d(TAG, "handleResponseAfterSignIn: resultCode  = "+resultCode);
    if (requestCode == RC_SIGN_IN) {   // = 100
        if (resultCode == RESULT_OK) { // SUCCESS = -1

            // Check user registration in cloud FireStore
            this.checkUserRegistrationInFireStore();

        } else { // ERRORS
            if (response == null) {
                showSnackBar(getString(R.string.error_authentication_canceled));
            } else if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {     // = 1
                showSnackBar(getString(R.string.error_no_internet));
            } else if (response.getError().getErrorCode() == ErrorCodes.PROVIDER_ERROR) { // = 4
                showSnackBar(getString(R.string.error_provider));
            } else if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {   // = 0
                showSnackBar(getString(R.string.error_unknown_error));
            } else if (response.getError().getErrorCode() == ErrorCodes.DEVELOPER_ERROR) {   // = 3
                showSnackBar(getString(R.string.error_developer_error));
            }
        }
    }
}
    // ---------------------------------------------------------------------------------------------
    //                                 CLOUD FIRE STORE
    // ---------------------------------------------------------------------------------------------
    // ----------------------------------------------------
    //  Check if the user is registered in Cloud FireStore
    // ----------------------------------------------------
    // Method that check user registration in cloud FireStore
    private void checkUserRegistrationInFireStore() {
        Log.d(TAG, "checkUserRegistrationInFireStore: ");

        // Retrieves the information of the user according to his ID in FireStore Database
        mAuthenticationViewModel.getUserInFireStore(
                mAuthenticationViewModel.getCurrentUser().getUid()).addOnSuccessListener(documentSnapshot -> {
            User user = documentSnapshot.toObject(User.class);

            // Does the user already exist in the database Cloud FireStore ?
            if (user == null) {
                // User not exist in Cloud FireStore (user is null)
                Log.d(TAG, "checkUserRegistrationInFireStore: User not exist in Cloud FireStore (user is null)");

                // Ask the new user if he is a citizen or a mayor.
                this.openUserTypeChoiceDialog();
            }
            else{
                Log.d(TAG, "checkUserRegistrationInFireStore: User exist in Cloud FireStore");

                // Save the User in Model
                mAuthenticationViewModel.setUserInModel(user);

                // Start Mayor Activity or Citizen Activity
                startInterface();
            }
        });
    }
    // ---------------------------------------------------------------------------------------------
    //                                      CALL ACTIVITY
    // ---------------------------------------------------------------------------------------------
    public void startInterface(){
        Log.d(TAG, "startInterface: ");

        Intent intent;
        if (mAuthenticationViewModel.getUserInModel().getMayor()) {
            // Call Mayor Activity
            intent = new Intent(this, MayorActivity.class);
        }else{
            // Call Citizen Activity
            intent = new Intent(this, PresentationActivity.class);
        }
        startActivity(intent);
    }

    // ---------------------------------------------------------------------------------------------
    //                                          UI
    // ---------------------------------------------------------------------------------------------
    public void displayInseeList(List<Insee> inseeList) {

        for(Insee insee : inseeList){
            Log.d(TAG, "displayInseeList: ville = "+insee.getNom());
            Log.d(TAG, "displayInseeList: code  = "+insee.getCode());
            Log.d(TAG, "displayInseeList: code Postaux  = "+insee.getCodesPostaux().get(0));
        }
    }

    private void openUserTypeChoiceDialog() {
        UserProfileChoiceDialogFragment userTypeChoiceDialog = UserProfileChoiceDialogFragment.newInstance();
        userTypeChoiceDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
        userTypeChoiceDialog.show(getSupportFragmentManager(), " userTypeChoiceDialog");
    }
}
