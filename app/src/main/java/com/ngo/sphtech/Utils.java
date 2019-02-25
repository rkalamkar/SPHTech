package com.ngo.sphtech;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ngo.sphtech.Api.LocalDataChangeListener;
import com.ngo.sphtech.Pojo.ApiResponse;
import com.ngo.sphtech.Pojo.Data.DataConsumption;
import com.ngo.sphtech.Pojo.Records;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class Utils {
    public Utils() {
    }

    // Processing online data
    public void processData(LocalDataChangeListener localDataChangeListener, ApiResponse apiResponse) {

        TreeMap<String, Double> map;
        HashMap<String, ArrayList<Records>> subMap;
        int lastItemCount = 0;
        map = getHashMap(apiResponse.getResult().getRecords());
        subMap = getChildHashMap(apiResponse.getResult().getRecords());
        lastItemCount = apiResponse.getResult().getRecords().size();

        localDataChangeListener.onDataGenerated(map, subMap, lastItemCount);
    }

    // Processing offline data
    public void processData(LocalDataChangeListener localDataChangeListener, List<DataConsumption> list) {

        TreeMap<String, Double> map;
        HashMap<String, ArrayList<Records>> subMap;
        int lastItemCount = 0;
        map = getHashMapLocal(list);
        subMap = getChildHashMapLocal(list);
        lastItemCount = list.size();

        localDataChangeListener.onDataGenerated(map, subMap, lastItemCount);
    }

    // Create hash Map for year
    public TreeMap<String, Double> getHashMap(ArrayList<Records> list) {
        HashMap<String, Double> yearList = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i).getQuarter().substring(0, 4);
            int yearValue = Integer.parseInt(s);
            if (yearValue > 2007)
                if (!yearList.containsKey(s)) {
                    yearList.put(s, list.get(i).getVolume_of_mobile_data());
                } else {
                    yearList.put(s, (yearList.get(s) + list.get(i).getVolume_of_mobile_data()));
                }
        }
        return new TreeMap<>(yearList);
    }

    // Create hash Map for year quarter
    public HashMap<String, ArrayList<Records>> getChildHashMap(ArrayList<Records> list) {
        HashMap<String, ArrayList<Records>> quarterList = new HashMap<>();
        HashMap<String, Double> yearList = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i).getQuarter().substring(0, 4);
            if (!yearList.containsKey(s)) {
                yearList.put(s, list.get(i).getVolume_of_mobile_data());
                ArrayList<Records> temp = new ArrayList<>();
                temp.add(list.get(i));
                quarterList.put(s, temp);
            } else {
                ArrayList<Records> temp = new ArrayList<>();
                temp.addAll(quarterList.get(s));
                temp.add(list.get(i));
                quarterList.put(s, temp);
            }
        }
        return quarterList;
    }

    public TreeMap<String, Double> getHashMapLocal(List<DataConsumption> list) {
        HashMap<String, Double> yearList = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i).getQuarter().substring(0, 4);
            int yearValue = Integer.parseInt(s);
            if (yearValue > 2007)
                if (!yearList.containsKey(s)) {
                    yearList.put(s, list.get(i).getVolume());
                } else {
                    yearList.put(s, (yearList.get(s) + list.get(i).getVolume()));
                }
        }
        return new TreeMap<>(yearList);
    }

    public HashMap<String, ArrayList<Records>> getChildHashMapLocal(List<DataConsumption> list) {
        HashMap<String, ArrayList<Records>> quarterList = new HashMap<>();
        HashMap<String, Double> yearList = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i).getQuarter().substring(0, 4);
            if (!yearList.containsKey(s)) {
                yearList.put(s, list.get(i).getVolume());
                ArrayList<Records> temp = new ArrayList<>();
                temp.add(new Records(list.get(i).getVolume(), list.get(i).getQuarter(), list.get(i).getId()));
                quarterList.put(s, temp);
            } else {
                ArrayList<Records> temp = new ArrayList<>();
                temp.addAll(quarterList.get(s));
                temp.add(new Records(list.get(i).getVolume(), list.get(i).getQuarter(), list.get(i).getId()));
                quarterList.put(s, temp);
            }
        }
        return quarterList;
    }

    public boolean hasConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }

        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        }

        return false;
    }
}
