package com.example.myapplication;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //Register
        ParseObject.registerSubclass(Post.class);

        //Is this finding my server through these codes?
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("OelobxWFbUl1tA3a42hPCE4GCFxjpHrh8yp6fvhj")
                .clientKey("TMlPOmqlwlqmtcBw1gLvJNaS1rRKpU0JKasiykyk")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}



