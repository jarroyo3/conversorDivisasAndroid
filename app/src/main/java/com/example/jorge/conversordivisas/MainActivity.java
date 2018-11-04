package com.example.jorge.conversordivisas;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jorge.conversordivisas.conversor.impl.Conversor;
import com.example.jorge.conversordivisas.dao.DivisaDAO;
import com.example.jorge.conversordivisas.services.ConversorService;
import com.example.jorge.conversordivisas.services.DivisaInternetService;
import com.example.jorge.conversordivisas.task.AsyncTaskUpdateDate;
import com.example.jorge.conversordivisas.widget.divisa.Divisa;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {


    private  static final String LOG_TAG = "MAIN_ACTIVITY";
    private static final String TIEMPO_ESPERA_TASK = "3";

    private Spinner spinnerCurrencyTo;
    private EditText etCurrency;
    private TextView tvResultado;
    private Conversor conversor;
    private  Context context;
    private ConversorService conversorService;
    private DivisaInternetService divisaInternetService;
    private ArrayList<Divisa> listaDivisas;
    private DivisaDAO divisaDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initTask();
    }

    private void initTask() {
        AsyncTaskUpdateDate task = new AsyncTaskUpdateDate();
        AsyncTaskRunner runner = new AsyncTaskRunner();
        task.setContext(context);
        task.setDivisaTask(runner);
        task.execute(TIEMPO_ESPERA_TASK);
    }

    private void initSpinner() {
        // Cargamos los spinners
        spinnerCurrencyTo = (Spinner) findViewById(R.id.spinnerCurrencyTo);

        // cargar spinner dinamicamente
        ArrayList<String> spinnerMonedas = new ArrayList<String>();
        for (Divisa divisa: listaDivisas) {
            spinnerMonedas.add(divisa.getNombreDivisa());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerMonedas);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerCurrencyTo.setAdapter(adapter);
    }

    private void init() {
        setContentView(R.layout.activity_main);
        // Cargamos el editText de la cantidad de euros a convertir.
        etCurrency = (EditText) findViewById(R.id.etCurrency);

        // Cargamos el textView del resultado
        tvResultado = (TextView) findViewById(R.id.tvResultado);

        // cargamos el contexto
        this.context = this;

        // obtenemos instancia del servicio conversor
        this.conversorService = ConversorService.getInstance();

        // obtenemos instancia del servicio xml
        this.divisaInternetService = DivisaInternetService.getInstance();

        // obtenemos el repositorio de divisas
        this.divisaDAO = DivisaDAO.getInstance().setContext(this);

    }

    public void onBtnConvertirClick(View view) {
        if (!"".equals(etCurrency.getText().toString())) {
            ocultaTeclado();
            convertirDivisa(view);
        } else {
            muestraError();
        }
    }

    public void onBtnConvierteEurosClick(View view) {
        if (!"".equals(etCurrency.getText().toString())) {
            ocultaTeclado();
            convertirEuros(view);
        } else {
            muestraError();
        }
    }

    private void ocultaTeclado() {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void convertirDivisa(View view) {
        inicializaMonedaSeleccionada();
        Float convertido = conversor.conviertirMonedaLocal();
        tvResultado.setText(String.format("%.2f", convertido) + " " + spinnerCurrencyTo.getSelectedItem().toString());
    }

    private void convertirEuros(View view) {
        inicializaMonedaSeleccionada();
        Float convertido = conversor.convertirEuros();
        tvResultado.setText(String.format("%.2f", convertido) + " EUR.");
    }

    private void inicializaMonedaSeleccionada() {

        Float euros = new Float(etCurrency.getText().toString());
        String monedaSeleccionada = spinnerCurrencyTo.getSelectedItem().toString();
        Float tipoCambio = this.divisaDAO.getTipoCambioPorMoneda(monedaSeleccionada);
        conversor = new Conversor(euros, new Divisa(monedaSeleccionada, tipoCambio));

    }

    private void muestraError() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // titulo del popup
        alertDialogBuilder.setTitle("Tienes que introducir una cantidad.");

        // cuerpo del mensaje del popup
        alertDialogBuilder
                .setMessage("Debes introducir un valor para realizar la conversión a otra divisa.")
                .setCancelable(false)
                .setNegativeButton("Entendido", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        tvResultado.setText("0");
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void goToDivisasActivity(View view) {
        Intent intent = new Intent(context, DivisasActivity.class);
        intent.putExtra("listaDivisas", divisaDAO.getAllMonedas());

        startActivity(intent);
    }

    public void cargaMoendas() {

        if (!"".equals(divisaDAO.getUltimaActualizacion())) {
            listaDivisas = divisaDAO.getAllMonedas();
        } else {
            listaDivisas = conversorService.loadDivisas();
        }
    }

    public class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            /**
             * Tratamos de cargar la lista de divisas en segundo plano.
             * Si diese un pete, cargaremos la lista de divisa fija, en lugar
             * de mostrar la descargada desde internet.
             *
             * Para probar una sincronización erronea, bastaría, por ejemplo, ir al servicio
             * DivisaInternetService y en la url cambiarle la extensión o desactivar Internet en el movil,
             * por lo que la descarga fallará.
            */
            publishProgress("Sleeping...");
            resp = "La sincronización se ha realizado con éxito.";
            try {

                int time = Integer.parseInt(params[0])*1000;
                Thread.sleep(time);
                HashMap<String, Float> tiposCambio  = divisaInternetService.cargar().getTiposCambio();
                divisaDAO.guardarMasivo(tiposCambio);
                conversorService.setMapaDivisas(tiposCambio);

            } catch (InterruptedException|SAXException|IOException|ParserConfigurationException e) {
                e.printStackTrace();
                resp = "La sincronización ha fallado. Se mantendrán los tipos de cambio previos.";
                conversorService.setMapaDivisas(conversorService.getDivisasFijas());
                listaDivisas = conversorService.loadDivisas();
            }

            return resp;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
            inicializaComponentes();
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

        public void inicializaComponentes() {
            cargaMoendas();
            initSpinner();
        }
    }
}
