package com.example.jorge.conversordivisas.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class DivisaInternet {

    private static final String URL_TIPOS_CAMBIO_DIVISA = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";

    private static DivisaInternet divisaInternet;

    private HashMap<String, Float> tiposCambio;

    private DivisaInternet() {
        super();
        this.tiposCambio = new HashMap<String, Float>();
    }

    public static DivisaInternet getInstance() {
        if (null == divisaInternet) {
            divisaInternet = new DivisaInternet();
        }

        return divisaInternet;
    }

    public DivisaInternet cargar() throws IOException, ParserConfigurationException, SAXException {

        URL url = new URL(URL_TIPOS_CAMBIO_DIVISA);

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new InputSource(url.openStream()));
        doc.getDocumentElement().normalize();

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

    public HashMap<String, Float> getTiposCambio() {
        return this.tiposCambio;
    }

}
