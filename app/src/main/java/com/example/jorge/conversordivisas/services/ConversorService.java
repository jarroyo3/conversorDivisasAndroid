package com.example.jorge.conversordivisas.services;

import com.example.jorge.conversordivisas.conversor.constants.ConversorConstants;
import com.example.jorge.conversordivisas.conversor.impl.Conversor;
import com.example.jorge.conversordivisas.widget.divisa.Divisa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConversorService {

    private static ConversorService conversorService;

    private Conversor conversor;
    private HashMap<String, Float> mapaDivisas;

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
        divisasFijas.put(ConversorConstants.FRANCO_SUIZO, ConversorConstants.TIPO_CAMBIO_FRANCO);
        divisasFijas.put(ConversorConstants.DOLAR_AUSTRALIANO, ConversorConstants.TIPO_CAMBIO_AUS_DOLAR);
        divisasFijas.put(ConversorConstants.DOLAR_CANADIENSE, ConversorConstants.TIPO_CAMBIO_CAN_DOLAR);
        divisasFijas.put(ConversorConstants.CORONA_SUECA, ConversorConstants.TIPO_CAMBIO_CORONA_SUECA);
        divisasFijas.put(ConversorConstants.PESO_ARGENTINO, ConversorConstants.TIPO_CAMBIO_ARG_PESO);
        divisasFijas.put(ConversorConstants.PESO_COLOMBIANO, ConversorConstants.TIPO_CAMBIO_COL_PESO);
        divisasFijas.put(ConversorConstants.PESO_CUBANO, ConversorConstants.TIPO_CAMBIO_CUB_PESO);
        divisasFijas.put(ConversorConstants.CORONA_DANESA, ConversorConstants.TIPO_CAMBIO_CORONA_DANESA);
        divisasFijas.put(ConversorConstants.RUPIA_INDIA, ConversorConstants.TIPO_CAMBIO_RUPIA_INDIA);
        divisasFijas.put(ConversorConstants.YUAN_CHINO, ConversorConstants.TIPO_CAMBIO_YUAN);
        divisasFijas.put(ConversorConstants.PESO_MEXICANO, ConversorConstants.TIPO_CAMBIO_MEX_DOLAR);

        return  divisasFijas;
    }

    public ArrayList<Divisa> loadDivisas() {
        ArrayList<Divisa> listaDivisas = new ArrayList<Divisa>();

        for (Map.Entry<String, Float> registroDivisa : getMapaDivisas().entrySet()) {
            String key = registroDivisa.getKey();
            Object value = registroDivisa.getValue();

            Divisa divisa = new Divisa();
            divisa.setNombreDivisa(key);
            divisa.setValorDivisa((Float)value);
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
}
