package com.example.nfame.realm_example.Views;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.nfame.realm_example.Adapters.ListPersonaAdapter;
import com.example.nfame.realm_example.Model.Persona;
import com.example.nfame.realm_example.R;

import java.util.ArrayList;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;

public class Lista extends AppCompatActivity {

    private Button spn1a,spn2, spn3, spn1b;
    private ListView lsvLista;
    private ListPersonaAdapter listPersonaAdapter;
    private List<Persona> llista;
    private EditText edat;
    private EditText genre;
    private EditText btwn1;
    private EditText btwn2;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        edat = findViewById(R.id.edatEdit);
        genre = findViewById(R.id.genreEdit);
        btwn1 = findViewById(R.id.betweenEdit1);
        btwn2 = findViewById(R.id.betweenEdit2);
        spn1a = findViewById(R.id.spn1a);
        spn1b = findViewById(R.id.spn1b);
        spn2 = findViewById(R.id.spn2);
        spn3 = findViewById(R.id.spn3);

        lsvLista = findViewById(R.id.lsvLista);
        llista = new ArrayList<>();
        realm = Realm.getDefaultInstance();
        loadPersonas();

        spn1a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            searchGtAge();
            }
        });

        spn1b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchLsAge();
            }
        });

        spn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            searchBySex();
            }
        });

        spn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBetweenAges();
            }
        });
    }


    private void loadPersonas() {
        RealmResults<Persona> personas = realm.where(Persona.class)
                .findAll();
        llista.clear();
        listPersonaAdapter = new ListPersonaAdapter(personas,this);
        lsvLista.setAdapter(listPersonaAdapter);
        listPersonaAdapter.notifyDataSetChanged();
    }

    private void searchGtAge() {
        RealmResults<Persona> personas = realm.where(Persona.class)
                .greaterThan("edat",Integer.parseInt(edat.getText().toString()))
                .findAll();
        llista.clear();
        listPersonaAdapter = new ListPersonaAdapter(personas,this);
        lsvLista.setAdapter(listPersonaAdapter);
        listPersonaAdapter.notifyDataSetChanged();
    }

    private void searchLsAge() {
        RealmResults<Persona> personas = realm.where(Persona.class)
                .lessThan("edat",Integer.parseInt(edat.getText().toString()))
                .findAll();
        llista.clear();
        listPersonaAdapter = new ListPersonaAdapter(personas,this);
        lsvLista.setAdapter(listPersonaAdapter);
        listPersonaAdapter.notifyDataSetChanged();
    }

    private void searchBySex() {
        RealmResults<Persona> personas = realm.where(Persona.class)
                .equalTo("genre",genre.getText().toString(), Case.INSENSITIVE)
                .findAll();
        llista.clear();
        listPersonaAdapter = new ListPersonaAdapter(personas,this);
        lsvLista.setAdapter(listPersonaAdapter);
        listPersonaAdapter.notifyDataSetChanged();
    }

    private void searchBetweenAges() {
        RealmResults<Persona> personas = realm.where(Persona.class)
                .between("edat", Integer.parseInt(btwn1.getText().toString()), Integer.parseInt(btwn2.getText().toString()))
                .findAll();
        llista.clear();
        listPersonaAdapter = new ListPersonaAdapter(personas,this);
        lsvLista.setAdapter(listPersonaAdapter);
        listPersonaAdapter.notifyDataSetChanged();
    }
}

