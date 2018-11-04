package com.example.jorge.conversordivisas.services;

import android.content.Context;

import com.example.jorge.conversordivisas.conversor.constants.ConversorConstants;
import com.example.jorge.conversordivisas.conversor.impl.Conversor;
import com.example.jorge.conversordivisas.dao.DivisaDAO;
import com.example.jorge.conversordivisas.widget.divisa.Divisa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConversorService {

    private static ConversorService conversorService;

    private Conversor conversor;
    private HashMap<String, Float> mapaDivisas;
    private Context context;

    private ConversorService() {
        super();
        this.mapaDivisas = new HashMap<String, Float>();
    }

    public static ConversorService getInstance() {
        if (null == conversorService) {
            conversorService = new ConversorService();
        }

        return conversorService;
    }

    public HashMap<String, Float> getDivisasFijas() {
        HashMap<String, Float> divisasFijas = new HashMap<String, Float>();

        divisasFijas.put(ConversorConstants.US_DOLAR, ConversorConstants.TIPO_CAMBIO_US_DOLAR);
        divisasFijas.put(ConversorConstants.YEN, ConversorConstants.TIPO_CAMBIO_YEN);
        divisasFijas.put(ConversorConstants.LIBRA, ConversorConstants.TIPO_CAMBIO_GB_LIBRA);

        return divisasFijas;
    }

    public ArrayList<Divisa> loadDivisas() {
        ArrayList<Divisa> listaDivisas = new ArrayList<Divisa>();

        for (Map.Entry<String, Float> registroDivisa : getMapaDivisas().entrySet()) {
            String key = registroDivisa.getKey();
            Float value = registroDivisa.getValue();
            String nuevoValorGuardado = DivisaDAO.getInstance().setContext(context).getTipoCambioPorMoneda(key).toString();
            if (!"".equals(nuevoValorGuardado)) {
                value = new Float(nuevoValorGuardado);
                System.out.println(nuevoValorGuardado);
            }
            Divisa divisa = new Divisa();
            divisa.setNombreDivisa(key);
            divisa.setValorDivisa((Float) value);
            listaDivisas.add(divisa);
        }


        return listaDivisas;
    }

    public void setMapaDivisas(HashMap<String, Float> mapaDivisas) {
        this.mapaDivisas = mapaDivisas;
    }

    public HashMap<String, Float> getMapaDivisas() {
        return this.mapaDivisas;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
