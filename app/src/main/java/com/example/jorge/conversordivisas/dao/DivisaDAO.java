package com.example.jorge.conversordivisas.dao;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.jorge.conversordivisas.conversor.constants.ConversorConstants;
import com.example.jorge.conversordivisas.widget.divisa.Divisa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DivisaDAO {

    private static final String PREFERENCIAS_DIVISAS = "divisasConf";

    private static DivisaDAO divisaDAO;

    private Context context;

    private DivisaDAO() {

    }

    public static DivisaDAO getInstance() {

        if (null == divisaDAO) {
            divisaDAO = new DivisaDAO();
        }

        return divisaDAO;
    }

    public DivisaDAO setContext(Context context) {
        this.context = context;
        return this;
    }

    public void guardar(String moneda, Float tipoCambio) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCIAS_DIVISAS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(moneda, tipoCambio);
        editor.commit();
    }

    public Float getTipoCambioPorMoneda(String moneda) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCIAS_DIVISAS, Context.MODE_PRIVATE);
        Float tipoCambio = preferences.getFloat(moneda, 0F);
        return tipoCambio;
    }

    public void guardarMasivo(HashMap<String, Float> divisas) {
        for (Map.Entry<String, Float> registroDivisa : divisas.entrySet()) {
            String key = registroDivisa.getKey();
            Float value = registroDivisa.getValue();
            this.guardar(key, value);
        }
    }

    public String getUltimaActualizacion() {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCIAS_DIVISAS, Context.MODE_PRIVATE);
        SimpleDateFormat formatDate = new SimpleDateFormat(ConversorConstants.FORMATO_FECHA);
        Date fechaActualPorDefecto = new Date();

        String fechaActualizacion = preferences.getString(
                ConversorConstants.FECHA_ACTUALIZACION_KEY,
                ""
        );

        return  fechaActualizacion;
    }

    public void setUltimaFechaActualizacion(String fecha) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCIAS_DIVISAS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ConversorConstants.FECHA_ACTUALIZACION_KEY, fecha);
        editor.commit();
    }

    public ArrayList<Divisa> getAllMonedas() {
        ArrayList<Divisa> monedas = new ArrayList<Divisa>();
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCIAS_DIVISAS, Context.MODE_PRIVATE);
        Map<String, ?> allEntries = preferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (!ConversorConstants.FECHA_ACTUALIZACION_KEY.equals(entry.getKey())) {
                String nombreMoneda = entry.getKey();
                Float tipoCambio = (Float)entry.getValue();
                Divisa divisa = new Divisa(nombreMoneda, tipoCambio);
                monedas.add(divisa);
            }
        }

        return monedas;
    }
}
