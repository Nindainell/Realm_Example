package com.example.nfame.realm_example;

import android.content.Context;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.nfame.realm_example.Model.Persona;

import java.text.ParseException;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private Realm realm;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        Realm.init(context);

        realm = Realm.getDefaultInstance();
        deletePersona(0);
        //createItem(realm);
        searchPersona(realm);
    }

    public void createItem(Realm realm) throws ParseException {
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        createPersona(realm);
        realm.commitTransaction();
    }

    public void createPersona(Realm realm) throws ParseException {
        //Scanner sc = new Scanner(System.in);

        String nom = "Nik";
        String cognom = "Fernandez";
        String dni = "123456";

        Persona persona = realm.createObject(Persona.class, dni);
        persona.setNom(nom);
        persona.setCognom(cognom);



        System.out.println("                    PERSONA CREADA");
        System.out.println("_____________________________________________________________");
    }

    public void searchPersona(Realm realm) {
        realm = Realm.getDefaultInstance();
        RealmQuery<Persona> query = realm.where(Persona.class);
        RealmResults<Persona> personas = query.findAll();
        System.out.println(personas);
    }

    public void deletePersona(int pos) {
        System.out.println("Borrando Persona");
        Realm realm = Realm.getDefaultInstance();

        RealmResults<Persona> personas = realm.where(Persona.class).findAll();
        realm.beginTransaction();
        personas.get(pos).deleteFromRealm();
        realm.commitTransaction();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close(); // Remember to close Realm when done.
    }
}
