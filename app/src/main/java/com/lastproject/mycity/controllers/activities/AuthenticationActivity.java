package com.lastproject.mycity.controllers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lastproject.mycity.R;
import com.lastproject.mycity.controllers.fragments.UserProfileChoiceDialogFragment;
import com.lastproject.mycity.firebase.database.firestore.models.MayorFireStore;
import com.lastproject.mycity.firebase.database.firestore.models.UserFireStore;
import com.lastproject.mycity.injections.Injection;
import com.lastproject.mycity.injections.ViewModelFactory;
import com.lastproject.mycity.models.Mayor;
import com.lastproject.mycity.models.User;
import com.lastproject.mycity.models.views.AuthenticationViewModel;
import com.lastproject.mycity.repositories.CurrentMayorDataRepository;
import com.lastproject.mycity.utils.Toolbox;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Michaël SAGOT on 01/12/2019.
 */

public class AuthenticationActivity  extends AppCompatActivity
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
    //                                        METHODS
    // ---------------------------------------------------------------------------------------------
    public ConstraintLayout getConstraintLayout() {
        return mConstraintLayout;
    }
    public void startActivity(Class activityClass){Toolbox.startActivity(this,activityClass);}
    // ---------------------------------------------------------------------------------------------
    //                                        ENTRY POINT
    // ---------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_authentication);

        // Get & serialise all views
        ButterKnife.bind(this);

        // Configure ViewModel
        this.configureViewModel();
    }
    // ---------------------------------------------------------------------------------------------
    //                                        VIEW MODEL
    // ---------------------------------------------------------------------------------------------
    // Configure ViewModel
    private void configureViewModel(){
        ViewModelFactory modelFactory = Injection.provideViewModelFactory(this);
        mAuthenticationViewModel = new ViewModelProvider(this, modelFactory)
                .get(AuthenticationViewModel.class);

        mAuthenticationViewModel.getViewAction().
                observe(this,new Observer<AuthenticationViewModel.ViewAction>() {
                    @Override
                    public void onChanged (AuthenticationViewModel.ViewAction viewAction){
                        if (viewAction == null) {
                            return;
                        }

                        switch (viewAction) {

                            case START_TOWN_HALL_ACTIVITY:
                                showSnackBar("START_TOWN_HALL_ACTIVITY");
                                startActivity(TownHallActivity.class);

                                break;

                            case START_TOWN_HALL_SELECTION_ACTIVITY:
                                showSnackBar("START_TOWN_HALL_SELECTION_ACTIVITY");
                                startActivity(TownHallSelectionActivity.class);

                                break;

                            case FINISH_ACTIVITY:
                                // Close Activity and go back to previous activity
                                finish();
                                break;
                        }
                    }
                });
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
        if (Toolbox.isInternetAvailable(this)) this.startEmailSignInActivity();
        else Toolbox.showSnackBar(this.getConstraintLayout(), getString(R.string.error_no_internet));
    }

    @OnClick(R.id.activity_authentication_facebook_login_button)
    public void onClickFacebookLoginButton() {
        Log.d(TAG, "onClickFacebookLoginButton: ²");
        if (Toolbox.isInternetAvailable(this)) this.startFaceBookSignInActivity();
        else Toolbox.showSnackBar(this.getConstraintLayout(), getString(R.string.error_no_internet));
    }

    @OnClick(R.id.activity_authentication_google_login_button)
    public void onClickGoogleLoginButton() {
        Log.d(TAG, "onClickGoogleLoginButton: ");
        if (Toolbox.isInternetAvailable(this)) this.startGoogleSignInActivity();
        else Toolbox.showSnackBar(this.getConstraintLayout(), getString(R.string.error_no_internet));
    }

    @OnClick(R.id.activity_authentication_twitter_login_button)
    public void onClickTwitterLoginButton() {
        Log.d(TAG, "onClickTwitterLoginButton: ");
        if (Toolbox.isInternetAvailable(this)) this.startTwitterSignInActivity();
        else Toolbox.showSnackBar(this.getConstraintLayout(), getString(R.string.error_no_internet));
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
                mAuthenticationViewModel.getUserAuthentication().getUid()).addOnSuccessListener(documentSnapshot -> {
            UserFireStore userFireStore = documentSnapshot.toObject(UserFireStore.class);

            // Does the userFireStore already exist in the database Cloud FireStore ?
            if (userFireStore == null) {
                // UserFireStore not exist in Cloud FireStore (userFireStore is null)
                Log.d(TAG, "checkUserRegistrationInFireStore: UserFireStore not exist in Cloud FireStore (userFireStore is null)");

                // Ask the new userFireStore if he is a citizen or a mayor.
                this.openUserTypeChoiceDialog();

            } else {
                Log.d(TAG, "checkUserRegistrationInFireStore: UserFireStore exist in Cloud FireStore");

                // Save the UserFireStore in Current User
                User user = new User(mAuthenticationViewModel.getUserAuthentication().getUid(), userFireStore);
                mAuthenticationViewModel.setCurrentUser(user);

                Log.d(TAG, "checkUserRegistrationInFireStore: user = "+ user);

                if (user.isMayor()) {
                    Log.d(TAG, "checkUserRegistrationInFireStore: User is a Mayor");
                    mAuthenticationViewModel.getMayorByUserID(user.getUserID())
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    Log.d(TAG, "onComplete getting documents: ");
                                    if (task.isSuccessful()) {
                                        if (task.getResult().size() != 0) {
                                            Log.d(TAG, "task.getResult().size() != 0");
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Log.d(TAG, document.getId() + " => " + document.getData());

                                                MayorFireStore mayorFireStore = document.toObject(MayorFireStore.class);

                                                Log.d(TAG, "onComplete: mayorFireStore = "+mayorFireStore);
                                                // If the userID corresponds to the mayor document being read
                                                // As a precaution, only the first document found will be used
                                                // the others will be ignored (break instruction)
                                               if (mayorFireStore.getUserID().equals(user.getUserID())) {

                                                   // save Current Mayor
                                                   Mayor mayor = new Mayor(document.getId(), mayorFireStore);
                                                   CurrentMayorDataRepository.getInstance().setCurrentMayor(mayor);

                                                   // Start Town Hall Selection Activity
                                                  mAuthenticationViewModel
                                                          .setViewAction(AuthenticationViewModel.ViewAction
                                                                  .START_TOWN_HALL_ACTIVITY);
                                               }
                                            }
                                        } else {
                                            Log.d(TAG, "task.getResult().size() == 0");
                                            Toolbox.showSnackBar(getConstraintLayout(),"Mayor not Found");
                                        }
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                        Toolbox.showSnackBar(getConstraintLayout(),"FireBase : Mayor Error");
                                    }
                                }
                            });
                } else {
                    Log.d(TAG, "checkUserRegistrationInFireStore: UserFireStore is a Citizen");
                    // Start Town Hall Selection Activity
                    mAuthenticationViewModel.setViewAction(AuthenticationViewModel.ViewAction
                                    .START_TOWN_HALL_ACTIVITY);
                }
            }
        });
    }
    // ---------------------------------------------------------------------------------------------
    //                                          UI
    // ---------------------------------------------------------------------------------------------
    // Show Snack Bar with a message
    public void showSnackBar(String message){
        Log.d(TAG, "showSnackBar: ");

        Snackbar.make(mConstraintLayout, message, Snackbar.LENGTH_LONG).show();
    }

    // Call Type Choice Dialog
    private void openUserTypeChoiceDialog() {
        UserProfileChoiceDialogFragment userTypeChoiceDialog = UserProfileChoiceDialogFragment.newInstance();
        userTypeChoiceDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
        userTypeChoiceDialog.show(getSupportFragmentManager(), " userTypeChoiceDialog");
    }
}
