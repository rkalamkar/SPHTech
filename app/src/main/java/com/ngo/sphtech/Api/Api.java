package com.ngo.sphtech.Api;

import com.ngo.sphtech.Pojo.ApiResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface Api {
    @GET("{FETCH}")
    Call<ApiResponse> getData(@Path(value = "FETCH", encoded = true) String path, @QueryMap Map<String, String> headers);
}
