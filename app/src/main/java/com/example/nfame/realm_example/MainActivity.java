package com.example.nfame.realm_example;

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
import com.example.nfame.realm_example.Views.Lista;

import java.text.ParseException;


import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        Realm.init(context);

        realm = Realm.getDefaultInstance();

        nom = findViewById(R.id.nomText);
        cognom = findViewById(R.id.cognomText);
        dni = findViewById(R.id.dniText);
        data = findViewById(R.id.naixText);
        genre = findViewById(R.id.genreText);
        tel = findViewById(R.id.telText);
        email = findViewById(R.id.mailText);
        createBttn = findViewById(R.id.createButton);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("RealmExample");
        setSupportActionBar(myToolbar);

        createBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                try {
                    createPersona(realm);
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

    public void createPersona(Realm realm) throws ParseException {
        RealmResults<Persona> personas = realm.where(Persona.class).equalTo("dni",dni.getText().toString()).findAll();
        int repited = 0;
        for(Persona p: personas){
            if(p.getDni().equals(dni.getText().toString())){
                repited++;
            }
        }
        System.out.println(repited);

        if(repited>0){
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Error de clave primaria, persona ya existente");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            System.out.println("Ya existe");
        }else{
            Persona persona = realm.createObject(Persona.class, dni.getText().toString());
            persona.setNom(nom.getText().toString());
            persona.setCognom(cognom.getText().toString());
            persona.setDataNaix(data.getText().toString());
            persona.setGenre(genre.getText().toString());
            persona.setTel(tel.getText().toString());
            persona.setEmail(email.getText().toString());
            persona.setEdat(persona.getEdat());
            persona.setAnyNaix(persona.getAnyNaix());
        }
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
