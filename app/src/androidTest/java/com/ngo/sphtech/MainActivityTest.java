package com.ngo.sphtech;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    ConnectivityManager connectivityManager;
    MainActivity mainActivity = null;

    @Before
    public void setUp() throws Exception {
        mainActivity = mActivityTestRule.getActivity();
        connectivityManager =
                (ConnectivityManager)
                        mainActivity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @After
    public void tearDown() throws Exception {
        mainActivity = null;
    }

    @Test
    public void initView() {
        View mRecyclerView = mainActivity.findViewById(R.id.recyclerView);
        assertNotNull(mRecyclerView);
        View mProgressBar = mainActivity.findViewById(R.id.progressBar);
        assertNotNull(mProgressBar);
        View mErrorView = mainActivity.findViewById(R.id.errorView);
        assertNotNull(mErrorView);
        View txtError = mainActivity.findViewById(R.id.textViewErrorMessage);
        assertNotNull(txtError);
        View btnRetry = mainActivity.findViewById(R.id.buttonRetry);
        assertNotNull(btnRetry);
        View mOfflineView = mainActivity.findViewById(R.id.linearLayoutOffline);
        assertNotNull(mOfflineView);
        View btnGoOnline = mainActivity.findViewById(R.id.buttonGoOnline);
        assertNotNull(btnGoOnline);
        View txtInternetConnection = mainActivity.findViewById(R.id.textViewInternet);
        assertNotNull(txtInternetConnection);
    }

    @Test
    public void getData() {
        NetworkInfo wifiNetwork = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            assertNotNull(true);
        }

        NetworkInfo mobileNetwork = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            assertNotNull(true);
        }

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            assertNotNull(true);
        }
        assertNotNull(false);
    }

    @Test
    public void onDataReceived() {
    }

    @Test
    public void onDataGenerated() {
    }
}