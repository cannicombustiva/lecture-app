package com.salva.lecture.App;

import android.app.Application;
import android.util.Log;

import com.salva.lecture.api.RestClient;
import com.salva.lecture.helpers.SharedData;

public class App extends Application
{
    private static RestClient restClient;
    private static SharedData sharedData;

    @Override
    public void onCreate()
    {
        super.onCreate();

        restClient = new RestClient();
        sharedData = new SharedData(getApplicationContext());
    }

    public static RestClient getRestClient()
    {

        return restClient;
    }
    public static SharedData getSharedData() {
        return sharedData;
    }
}
