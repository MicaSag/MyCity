package com.lastproject.mycity.controllers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.lastproject.mycity.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**************************************************************************************************
 *
 *  ACTIVITY that displays the web page of the town hall
 *  ------------------------------------------------------
 *  IN = Url of the TownHall site : String
 *
 **************************************************************************************************/

public class WebViewActivity extends AppCompatActivity {

    // For Debug
    private static final String TAG = WebViewActivity.class.getSimpleName();

    // Parameter for the construction of the fragment
    public static final String KEY_TOWN_HALL_WEB_SITE_URL = "KEY_TOWN_HALL_WEB_SITE_URL";

    // Adding @BindView in order to indicate to ButterKnife to get & serialise it
    @BindView(R.id.activity_web_view_layout) WebView mWebView;
    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        // Get & serialise all views
        ButterKnife.bind(this);

        // Get back the data intent in the activity
        Intent i = getIntent();
        // URL of the Restaurant WebSite
        String mTownHallWebSiteURL = i.getStringExtra(KEY_TOWN_HALL_WEB_SITE_URL);

        // Configuring Toolbar
        this.configureToolbar();

        // Set emulator View
        mWebView.setWebViewClient(new WebViewClient());

        // Show the page of the news
        mWebView.loadUrl(mTownHallWebSiteURL);
    }

    private void configureToolbar(){
        Log.d(TAG, "configureToolbar: ");
         //Set the toolbar
        setSupportActionBar(mToolBar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        // Respond to the action bar's Up/Home button
        case android.R.id.home:
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
