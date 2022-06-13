package com.example.myapplication;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Is this finding my server through these codes?
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("OelobxWFbUl1tA3a42hPCE4GCFxjpHrh8yp6fvhj")
                .clientKey("TMlPOmqlwlqmtcBw1gLvJNaS1rRKpU0JKasiykyk")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}



