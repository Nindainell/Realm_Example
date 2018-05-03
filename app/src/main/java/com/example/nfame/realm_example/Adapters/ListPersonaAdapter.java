package com.example.nfame.realm_example.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.nfame.realm_example.Model.Persona;
import com.example.nfame.realm_example.R;
import com.example.nfame.realm_example.Views.Mod;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

public class ListPersonaAdapter extends RealmBaseAdapter<Persona> implements ListAdapter {

    private Context mContext;

    private static class ViewHolder {
        TextView txvNom;
        TextView txvCognom;
        TextView txvGenre;
        TextView txvEdat;
        Button eraseButton, modButton;
    }

    public ListPersonaAdapter(@Nullable OrderedRealmCollection<Persona> data, Context context) {
        super(data);
        this.mContext = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_persona, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.txvNom = convertView.findViewById(R.id.item_nom);
            viewHolder.txvCognom = convertView.findViewById(R.id.item_cognom);
            viewHolder.txvGenre = convertView.findViewById(R.id.item_genre);
            viewHolder.txvEdat = convertView.findViewById(R.id.item_edat);
            viewHolder.eraseButton = convertView.findViewById(R.id.item_bttn);
            viewHolder.modButton = convertView.findViewById(R.id.item_bttn2);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Persona item = adapterData.get(position);


        viewHolder.eraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                erasePerson(position);
            }
        });

        viewHolder.modButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,Mod.class);
                intent.putExtra("POSITION",position);
                mContext.startActivity(intent);
            }
        });

        if(item.getDataNaix()!= null){
            viewHolder.txvNom.setText(item.getNom());
            viewHolder.txvCognom.setText(item.getCognom());
            viewHolder.txvGenre.setText(item.getGenre());
            viewHolder.txvEdat.setText(String.valueOf(item.ageCalculator(item.getDataNaix())));
        }else {
            viewHolder.txvNom.setText(item.getNom());
            viewHolder.txvCognom.setText(item.getCognom());
            viewHolder.txvGenre.setText(item.getGenre());
            viewHolder.txvEdat.setText("Edad desconocida");
        }
        return convertView;
    }

    private void erasePerson(int pos) {
        Realm realm = Realm.getDefaultInstance();

        RealmResults<Persona> personas = realm.where(Persona.class).findAll();
        realm.beginTransaction();
        personas.get(pos).deleteFromRealm();
        realm.commitTransaction();
    }
}

