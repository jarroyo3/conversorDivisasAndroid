package com.example.jorge.conversordivisas.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.jorge.conversordivisas.MainActivity;
import com.example.jorge.conversordivisas.conversor.constants.ConversorConstants;
import com.example.jorge.conversordivisas.dao.DivisaDAO;
import com.example.jorge.conversordivisas.services.DivisaInternetService;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

public class AsyncTaskUpdateDate extends AsyncTask<String, String, String> {

    private static final String TIEMPO_ESPERA = "5";

    private Context context;
    private String resp;
    ProgressDialog progressDialog;
    private String fechaDescargada;
    private Date fechaActualizacionXML;
    private Date dateLocalStorage;
    private MainActivity.AsyncTaskRunner runner;

    @Override
        protected String doInBackground(String... params) {

            publishProgress("Sleeping...");
            resp = "Iniciando. Por favor, espera";
            try {

                descargarFecha();

            } catch (ParseException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return resp;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
            /**
             * Comprobamos que la fecha de internet es mas reciente para descargar los nuevos tipos de cambio.
             */
            if (null == dateLocalStorage || fechaActualizacionXML.after(dateLocalStorage) ) {
                Log.i(this.getClass().getSimpleName(), "Compronado fecha actualizacion existente");
                Log.i(this.getClass().getSimpleName(), "Se guarda ultima actualizacion de fecha xml.");
                DivisaDAO.getInstance().setContext(context).setUltimaFechaActualizacion(fechaDescargada);
                this.runner.execute("5");

            } else {
                this.runner.inicializaComponentes();
            }
        }


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(context,
                    "Descargando",
                    "Se están descargando los nuevos tipos de cambio, por favor, espera ");
        }


        @Override
        protected void onProgressUpdate(String... text) {

        }

        public void setContext(Context context) {
            this.context = context;
        }

        protected void descargarFecha() throws ParseException, IOException, SAXException, ParserConfigurationException {
            /**
             * obtengo la ultima fecha de actualizacion guardada
             */
            String fechaActualizacionGuardada = DivisaDAO.getInstance().setContext(context).getUltimaActualizacion();

            /**
             * obtengo de Internet la fecha en la que se ha actualizado el xml
             */
            fechaDescargada = DivisaInternetService.getInstance().getUltimaFechaActualizacion();

            // fechaActualizacionXML = DivisaInternetService.getInstance().getUltimaFechaActualizacion();
            SimpleDateFormat formatDate = new SimpleDateFormat(ConversorConstants.FORMATO_FECHA);
            fechaActualizacionXML = formatDate.parse(fechaDescargada);
            dateLocalStorage = formatDate.parse(fechaActualizacionGuardada);
        }

    public void setDivisaTask(MainActivity.AsyncTaskRunner runner) {
        this.runner = runner;
    }
}
