package com.example.jorge.conversordivisas.widget.divisa;

import java.io.Serializable;

public class Divisa implements Serializable {
    private String nombreDivisa;
    private Float valorDivisa;
    private String divisaId;

    public Divisa() {
        super();
    }

    public Divisa(String nombreDivisa, Float valorDivisa) {
        super();
        this.nombreDivisa = nombreDivisa;
        this.valorDivisa = valorDivisa;
    }

    public Divisa(String divisaId, String nombreDivisa, Float valorDivisa) {
        super();
        this.nombreDivisa = nombreDivisa;
        this.divisaId = divisaId;
        this.valorDivisa = valorDivisa;

    }

    public String getNombreDivisa() {
        return nombreDivisa;
    }

    public void setNombreDivisa(String nombreDivisa) {
        this.nombreDivisa = nombreDivisa;
    }

    public Float getValorDivisa() {
        return valorDivisa;
    }

    public void setValorDivisa(Float valorDivisa) {
        this.valorDivisa = valorDivisa;
    }

    public String getDivisaId() {
        return divisaId;
    }

    public void setDivisaId(String divisaId) {
        this.divisaId = divisaId;
    }
}
