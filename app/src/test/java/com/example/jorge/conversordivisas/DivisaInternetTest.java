package com.example.jorge.conversordivisas;


import com.example.jorge.conversordivisas.dao.DivisaDAO;
import com.example.jorge.conversordivisas.services.DivisaInternetService;

import static org.junit.Assert.*;

import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

public class DivisaInternetTest {


    @Test
    public void testConectar() {
        DivisaInternetService divisa = DivisaInternetService.getInstance();
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

    @Test
    public void testObtenFecha() {
        DivisaInternetService divisa = DivisaInternetService.getInstance();
        if (null != divisa) {
            try {
                System.out.println("Fecha actualizacion fichero: " + divisa.getUltimaFechaActualizacion().toString());
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testFechaXMLMenosRecienteQueOtra() {
        DivisaInternetService divisa = DivisaInternetService.getInstance();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        if (null != divisa) {
            try {
                Date date = formatDate.parse("2020-01-31");
                Date fechaXML =  divisa.getUltimaFechaActualizacion();
                assertTrue("Fecha XML mas antigua que la actual.", fechaXML.before(date));
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testFechaXMLMasReciente() {
        DivisaInternetService divisa = DivisaInternetService.getInstance();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        if (null != divisa) {
            try {
                Date date = formatDate.parse("2018-11-01");
                Date fechaXML =  divisa.getUltimaFechaActualizacion();
                assertTrue("Fecha XML es más reciente que otra dada.", fechaXML.after(date));
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void recuperaFechaActualizacion() {
        DivisaInternetService divisa = DivisaInternetService.getInstance();
        if (null != divisa) {
            try {
                System.out.print("Fecha actualizacion guardada: " + divisa.getUltimaFechaActualizacion());
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}

