package com.ngo.sphtech.Api;

import com.ngo.sphtech.Pojo.ApiResponse;

import java.util.ArrayList;

public interface DataChangeListener {
    void onDataReceived(ApiResponse apiResponse);

    void onDataFailure(String errorCode);
}
