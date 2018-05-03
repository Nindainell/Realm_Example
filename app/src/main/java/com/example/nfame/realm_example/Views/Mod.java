package com.example.nfame.realm_example.Views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nfame.realm_example.Model.Migration;
import com.example.nfame.realm_example.Model.Persona;
import com.example.nfame.realm_example.R;
import com.example.nfame.realm_example.Views.Lista;

import java.text.ParseException;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class Mod extends AppCompatActivity {

    private Realm realm;
    private Context context;
    EditText nom;
    EditText cognom;
    EditText data;
    EditText dni;
    EditText genre;
    EditText tel;
    EditText email;
    Button createBttn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod);
        context = getApplicationContext();


        Realm.init(context);
        realm = Realm.getDefaultInstance();

        nom = findViewById(R.id.nomTextM);
        cognom = findViewById(R.id.cognomTextM);
        dni = findViewById(R.id.dniTextM);
        data = findViewById(R.id.naixTextM);
        genre = findViewById(R.id.genreTextM);
        tel = findViewById(R.id.telTextM);
        email = findViewById(R.id.mailTextM);
        createBttn = findViewById(R.id.modButton);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("RealmExample");
        setSupportActionBar(myToolbar);

        createBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                try {
                    modPersona(realm);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                realm.commitTransaction();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbarmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this,Lista.class);
                startActivity(intent);
                return true;

            default:

                return super.onOptionsItemSelected(item);

        }
    }

    public void modPersona(Realm realm) throws ParseException {

        Persona persona = new Persona();
        persona.setNom(nom.getText().toString());
        persona.setCognom(cognom.getText().toString());
        persona.setDni(dni.getText().toString());
        persona.setDataNaix(data.getText().toString());
        persona.setGenre(genre.getText().toString());
        persona.setTel(tel.getText().toString());
        persona.setEmail(email.getText().toString());

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(persona);
        realm.commitTransaction();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
