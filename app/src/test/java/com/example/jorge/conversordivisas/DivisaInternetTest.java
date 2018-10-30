package com.example.jorge.conversordivisas;

import android.util.Log;

import com.example.jorge.conversordivisas.services.DivisaInternet;

import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

public class DivisaInternetTest {


    @Test
    public void testConectar() {
        DivisaInternet divisa = DivisaInternet.getInstance();
        if (null != divisa) {
            try {
                divisa.cargar();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
        }
    }
}

