package com.example.jorge.conversordivisas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jorge.conversordivisas.services.ConversorService;
import com.example.jorge.conversordivisas.widget.divisa.AdapterDivisa;
import com.example.jorge.conversordivisas.widget.divisa.Divisa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DivisasActivity extends AppCompatActivity {

    private ConversorService conversorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        setContentView(R.layout.activity_divisas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        Intent i = getIntent();
        ArrayList<Divisa> listaDivisas = (ArrayList) i.getExtras().getParcelableArrayList("listaDivisas");

        // Cargamos el listView
        // ArrayList<Divisa> listaDivisas = new ArrayList<Divisa>();
        ListView lv = (ListView) findViewById(R.id.lvDivisas);
        AdapterDivisa adapter = new AdapterDivisa(this, listaDivisas);
        lv.setAdapter(adapter);
    }

    private void init() {
        this.conversorService = ConversorService.getInstance();
    }

}
