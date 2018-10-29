package com.example.jorge.conversordivisas.widget.divisa;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jorge.conversordivisas.R;

import java.util.ArrayList;

public class AdapterDivisa extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<Divisa> items;

    public AdapterDivisa(Activity activity, ArrayList<Divisa> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (null == convertView) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_divisa, null);
        }

        Divisa d =  this.items.get(position);
        TextView nombreDivisa = (TextView) v.findViewById(R.id.tvNombreDivisa);
        nombreDivisa.setText(d.getNombreDivisa());

        TextView valorDivisa = (TextView) v.findViewById(R.id.tvValorDivisa);
        valorDivisa.setText(d.getValorDivisa().toString());

        return v;
    }

    public void clear() {
        items.clear();
    }

    public void addAll(ArrayList<Divisa> divisa) {
        for (Divisa divisaI: divisa) {
            this.items.add(divisaI);
        }
    }
}

