package com.example.jorge.conversordivisas.conversor.impl;

import com.example.jorge.conversordivisas.conversor.ConversorModel;
import com.example.jorge.conversordivisas.widget.divisa.Divisa;

public class Conversor extends ConversorModel {

    protected Divisa divisa;
    protected Float euros;

    public Conversor(Float euros, Divisa divisa) {
        this.euros = euros;
        this.divisa = divisa;
    }

    @Override
    public Float convertirEuros() {

        return euros / divisa.getValorDivisa();
    }

    @Override
    public Float conviertirMonedaLocal() {
        return euros * divisa.getValorDivisa();
    }

}


