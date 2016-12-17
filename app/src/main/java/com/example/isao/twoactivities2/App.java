package com.example.isao.twoactivities2;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Isao on 14.12.2016.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration configuration =
                new RealmConfiguration.Builder()
                        .name("realm.realm")
                        .schemaVersion(2)
                        .build();
        Realm.setDefaultConfiguration(configuration);
    }
}
