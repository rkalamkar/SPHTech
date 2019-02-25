package com.ngo.sphtech.Api;

import com.ngo.sphtech.Pojo.Records;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public interface LocalDataChangeListener {
    void onDataGenerated(TreeMap<String, Double> map,
                         HashMap<String, ArrayList<Records>> subMap, int lastItemCount);
}
