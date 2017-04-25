package com.example.android.cloudy.data.model.remote;

import com.example.android.cloudy.pojos.ForecastListItem;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by ccu17 on 25/04/2017.
 */

public interface ForecastCallback {

//    void success(TreeMap<String, ArrayList<ForecastListItem>> map);

    void failure(String failed);

}
