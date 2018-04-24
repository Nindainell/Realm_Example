package com.example.nfame.realm_example.Model;

import android.util.Log;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmObject;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class Migration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();

        if(oldVersion == 0) {
            Log.d("Migration", "Actualizando a la versión 1");
            RealmObjectSchema personaSchema = schema.get("Persona");
            personaSchema.addIndex("nom");
            oldVersion++;
        }
    }
}
