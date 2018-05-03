package com.example.nfame.realm_example;

import android.app.Application;
import android.util.Log;

import com.example.nfame.realm_example.Model.Migration;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class PersonasApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("personas.realm")
                .schemaVersion(1)
                .migration(new Migration())
                .build();

        Realm.setDefaultConfiguration(config);

        Log.d("start", "PersonasApp");
    }

}
