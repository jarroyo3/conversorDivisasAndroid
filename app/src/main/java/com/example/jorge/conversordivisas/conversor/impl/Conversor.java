package com.example.jorge.conversordivisas.conversor.impl;

import com.example.jorge.conversordivisas.conversor.ConversorModel;

public class Conversor extends ConversorModel {

    protected Float tipoCambio;
    protected Float euros;

    public Conversor(Float euros, Float tipoCambio) {
        this.euros = euros;
        this.tipoCambio = tipoCambio;
    }

    @Override
    public Float convertirEuros() {
        return euros / tipoCambio;
    }

    @Override
    public Float conviertirMonedaLocal() {
        return euros * tipoCambio;
    }

    public static class ConversorUSDolar extends Conversor {

        public ConversorUSDolar(Float euros, Float tipoCambio) {
            super(euros, tipoCambio);
        }

    }

    public static class ConversorLibra extends Conversor {
        public ConversorLibra(Float euros, Float tipoCambio) {
            super(euros, tipoCambio);
        }
    }

    public static class ConversorYenes extends  Conversor {
        public ConversorYenes (Float euros, Float tipoCambio) {
            super(euros, tipoCambio);
        }
    }

    public static class ConversorFrancoSuizo extends Conversor {
        public ConversorFrancoSuizo (Float euros, Float tipoCambio) {
            super(euros, tipoCambio);
        }
    }

    public static class ConversorDolarAustraliano extends Conversor {
        public  ConversorDolarAustraliano (Float euros, Float tipoCambio) {
            super(euros, tipoCambio);
        }
    }

    public static class ConversorDolarCanada extends Conversor {
        public ConversorDolarCanada (Float euros, Float tipoCambio) {
            super(euros, tipoCambio);
        }
    }

    public static class ConversorCoronaSueca extends Conversor {
        public ConversorCoronaSueca (Float euros, Float tipoCambio) {
            super(euros, tipoCambio);
        }
    }

    public static class ConversorPesoArg extends Conversor {
        public ConversorPesoArg (Float euros, Float tipoCambio) {
            super(euros, tipoCambio);
        }
    }

    public static class ConversorPesoCol extends Conversor {
        public ConversorPesoCol (Float euros, Float tipoCambio) {
            super(euros, tipoCambio);
        }
    }

    public static class ConversorPesoCub extends Conversor {
        public ConversorPesoCub (Float euros, Float tipoCambio) {
            super(euros, tipoCambio);
        }
    }

    public static class ConversorCoronaDanesa extends Conversor {
        public ConversorCoronaDanesa (Float euros, Float tipoCambio) {
            super(euros, tipoCambio);
        }
    }

    public static class ConversorRupiaIndia extends Conversor {
        public ConversorRupiaIndia (Float euros, Float tipoCambio) {
            super(euros, tipoCambio);
        }
    }

    public static class ConversorYuan extends Conversor {
        public ConversorYuan (Float euros, Float tipoCambio) {
            super(euros, tipoCambio);
        }
    }

    public static class ConversorPesoMex extends Conversor {
        public ConversorPesoMex (Float euros, Float tipoCambio) {
            super(euros, tipoCambio);
        }
    }
}


