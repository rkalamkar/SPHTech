package com.ngo.sphtech;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ngo.sphtech.Api.DataChangeListener;
import com.ngo.sphtech.Api.LocalDataChangeListener;
import com.ngo.sphtech.Api.WebService;
import com.ngo.sphtech.Pojo.ApiResponse;
import com.ngo.sphtech.Pojo.Data.DataConsumption;
import com.ngo.sphtech.Pojo.Data.Query;
import com.ngo.sphtech.Pojo.Records;
import com.ngo.sphtech.View.DataAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity implements DataChangeListener, LocalDataChangeListener {

    RecyclerView mRecyclerView;
    ProgressBar mProgressBar;
    int limit = 10;
    DataAdapter dataAdapter;
    LinearLayout mErrorView, mOfflineView;
    Button btnRetry, btnGoOnline;
    TextView txtError, txtInternetConnection;
    public static AlertDialog alert = null;
    public static MainActivity mainActivity;
    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;
        initView();
        query = new Query();
        getData();
    }

    // Initialize all view
    public void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mErrorView = (LinearLayout) findViewById(R.id.errorView);
        txtError = (TextView) findViewById(R.id.textViewErrorMessage);
        btnRetry = (Button) findViewById(R.id.buttonRetry);
        mOfflineView = (LinearLayout) findViewById(R.id.linearLayoutOffline);
        btnGoOnline = (Button) findViewById(R.id.buttonGoOnline);
        txtInternetConnection = (TextView) findViewById(R.id.textViewInternet);
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });

        btnGoOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }

    // Api call for data
    public void getData() {
        //hide all error view while fetching data
        hideView(false);
        isLoading = true;
        mProgressBar.setVisibility(View.VISIBLE);
        // Check internet connection before api call
        if (new Utils().hasConnection(getApplicationContext())) {
            new WebService(this, limit);
        } else {
            // go for offline data
            getLocalData();
            // showError("No internet connection");
        }
    }
    // Get offline data
    public void getLocalData() {
        List<DataConsumption> dataConsumptions = query.getDataList(getApplicationContext());
        if (dataConsumptions != null && dataConsumptions.size() > 0) {
            new Utils().processData(this, dataConsumptions);
        } else {
            // no data cache show internet connection error
            showError("No internet connection");
        }
    }

    // error message to display
    public void showError(String string) {
        txtError.setText(string);
        hideView(true);
    }

    public void hideView(boolean isToHide) {
        if (isToHide) {
            mErrorView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.GONE);
        } else {
            mErrorView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
        }
    }

    LinearLayoutManager linearLayoutManager;
    boolean isLastPage = false;
    boolean isLoading = false;
    int lastItemCount = 0;

    TreeMap<String, Double> map;
    HashMap<String, ArrayList<Records>> subMap;

    // Api call back received
    @Override
    public void onDataReceived(ApiResponse apiResponse) {
        if (apiResponse == null) {
            showError("Empty data");
            return;
        } else if (!apiResponse.isSuccess()) {
            showError("Failed to get data");
            return;
        } else {
            isLoading = false;
            if (limit >= apiResponse.getResult().getTotal())
                isLastPage = true;

            query.addData(getApplicationContext(), apiResponse);
            new Utils().processData(this, apiResponse);
        }
    }

    // Setting data to Recycler view
    public void setData() {
        dataAdapter = new DataAdapter(getApplicationContext(), map, subMap);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(dataAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && lastItemCount >= limit) {
                        limit = limit + 10;
                        getData();
                    }
                }
            }
        });
        hideView(false);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDataFailure(String errorCode) {
        Log.e("Data", "Failed");
        showError(errorCode);
    }

    // Dialog box on click of image view
    public static void showDialogView(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);

        builder.setTitle(title);
        builder.setMessage(message);


        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog diag = builder.create();
        diag.show();
    }

    // Data processed ready to attach
    @Override
    public void onDataGenerated(TreeMap<String, Double> map, HashMap<String, ArrayList<Records>> subMap, int lastItemCount) {
        this.map = map;
        this.subMap = subMap;
        this.lastItemCount = lastItemCount;
        showInternetError();
        if (map.size() != 0) {
            setData();
        } else {
            limit = limit + 10;
            getData();
        }
    }

    // Red banner on top for internet connection
    public void showInternetError() {
        if (!new Utils().hasConnection(getApplicationContext())) {
            mOfflineView.setVisibility(View.VISIBLE);
        } else {
            mOfflineView.setVisibility(View.GONE);
        }
    }
}
