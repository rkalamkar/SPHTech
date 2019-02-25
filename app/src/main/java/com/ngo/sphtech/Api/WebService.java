package com.ngo.sphtech.Api;

import com.ngo.sphtech.Pojo.ApiResponse;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebService {
    public WebService(final DataChangeListener dataChangeListener, int limit) {

        URL url = null;
        try {
            url = new URL("https://data.gov.sg/api/action/datastore_search?resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f&limit=5");

            String baseUrl = url.getProtocol() + "://" + url.getHost();
            String apiName = url.getPath();
            String parameters = url.getQuery();

            HashMap<String, String> map = new HashMap<>();
            map.put("resource_id", "a807b7ab-6cad-4aa6-87d0-e283a7353a0f");
            map.put("limit", "" + limit);

            Api api = APIClient.getClient(baseUrl).create(Api.class);
            Call<ApiResponse> apiResponseCall = api.getData(apiName, map);

            apiResponseCall.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    dataChangeListener.onDataReceived(response.body());
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    dataChangeListener.onDataFailure("Failure");
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
            dataChangeListener.onDataFailure(e.getMessage());
        }
    }
}
