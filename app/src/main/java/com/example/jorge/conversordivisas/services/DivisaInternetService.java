package com.example.jorge.conversordivisas.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class DivisaInternetService {

    private static final String URL_TIPOS_CAMBIO_DIVISA = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";

    private static DivisaInternetService divisaInternet;

    private HashMap<String, Float> tiposCambio;
    private URL url;
    private Document doc;

    private DivisaInternetService() {
        super();
        this.tiposCambio = new HashMap<String, Float>();
    }

    public static DivisaInternetService getInstance() {
        if (null == divisaInternet) {
            divisaInternet = new DivisaInternetService();
        }

        return divisaInternet;
    }

    private void init() throws IOException, ParserConfigurationException, SAXException {
        this.url = new URL(URL_TIPOS_CAMBIO_DIVISA);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        doc = db.parse(new InputSource(this.url.openStream()));
        doc.getDocumentElement().normalize();
    }

    public DivisaInternetService cargar() throws IOException, ParserConfigurationException, SAXException {

        init();
        NodeList nodeList = doc.getElementsByTagName("Cube");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            Element element = (Element) node;

            if (!"".equals(element.getAttribute("currency")) && !"".equals(element.getAttribute("rate"))) {
                String moneda = element.getAttribute("currency");
                Float tipoCambio = new Float(element.getAttribute("rate"));
                this.tiposCambio.put(moneda, tipoCambio);
                System.out.println(moneda + " - " + tipoCambio);
            }


        }

        return this;
    }

    public String getUltimaFechaActualizacion() throws ParserConfigurationException, SAXException, IOException, ParseException {
        init();
        NodeList nodeList = doc.getElementsByTagName("Cube");
        String fecha = null;
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            Element element = (Element) node;

            if (!"".equals(element.getAttribute("time"))) {
                String fechaActualizacion = element.getAttribute("time");
                fecha = fechaActualizacion;
                break;
            }
        }

        return fecha;
    }

    public HashMap<String, Float> getTiposCambio() {
        return this.tiposCambio;
    }

}
