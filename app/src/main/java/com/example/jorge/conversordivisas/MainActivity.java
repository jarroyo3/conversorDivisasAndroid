package com.example.jorge.conversordivisas;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jorge.conversordivisas.conversor.constants.ConversorConstants;
import com.example.jorge.conversordivisas.conversor.impl.Conversor;
import com.example.jorge.conversordivisas.services.ConversorService;
import com.example.jorge.conversordivisas.services.DivisaInternet;
import com.example.jorge.conversordivisas.widget.divisa.Divisa;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {

    private static final String TIEMPO_ESPERA = "5";

    private Spinner spinnerCurrencyTo;
    private EditText etCurrency;
    private TextView tvResultado;
    private Conversor conversor;
    private Context context;
    private ConversorService conversorService;
    private DivisaInternet divisaInternet;
    private ArrayList<Divisa> listaDivisas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Cargamos los spinners
        spinnerCurrencyTo = (Spinner) findViewById(R.id.spinnerCurrencyTo);

        // Creamos una ArrayAdapter usando el xml de tipo_monedas
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipo_monedas, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerCurrencyTo.setAdapter(adapter);

        // Cargamos el editText de la cantidad de euros a convertir.
        etCurrency = (EditText) findViewById(R.id.etCurrency);

        // Cargamos el textView del resultado
        tvResultado = (TextView) findViewById(R.id.tvResultado);

        // cargamos el contexto
        this.context = this;

        // obtenemos instancia del servicio conversor
        this.conversorService = ConversorService.getInstance();

        // obtenemos instancia del servicio xml
        this.divisaInternet = DivisaInternet.getInstance();

        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute(TIEMPO_ESPERA);
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
        switch (spinnerCurrencyTo.getSelectedItem().toString()) {
            case ConversorConstants.US_DOLAR:
                conversor = new Conversor.ConversorUSDolar(euros, ConversorConstants.TIPO_CAMBIO_US_DOLAR);
                break;
            case ConversorConstants.YEN:
                conversor = new Conversor.ConversorYenes(euros, ConversorConstants.TIPO_CAMBIO_YEN);
                break;
            case ConversorConstants.LIBRA:
                conversor = new Conversor.ConversorLibra(euros, ConversorConstants.TIPO_CAMBIO_GB_LIBRA);
                break;
            case ConversorConstants.FRANCO_SUIZO:
                conversor = new Conversor.ConversorFrancoSuizo(euros, ConversorConstants.TIPO_CAMBIO_FRANCO);
                break;
            case ConversorConstants.DOLAR_AUSTRALIANO:
                conversor = new Conversor.ConversorDolarAustraliano(euros, ConversorConstants.TIPO_CAMBIO_AUS_DOLAR);
                break;
            case ConversorConstants.DOLAR_CANADIENSE:
                conversor = new Conversor.ConversorDolarCanada(euros, ConversorConstants.TIPO_CAMBIO_CAN_DOLAR);
                break;
            case ConversorConstants.CORONA_SUECA:
                conversor = new Conversor.ConversorCoronaSueca(euros, ConversorConstants.TIPO_CAMBIO_CORONA_SUECA);
                break;
            case ConversorConstants.PESO_ARGENTINO:
                conversor = new Conversor.ConversorPesoArg(euros, ConversorConstants.TIPO_CAMBIO_ARG_PESO);
                break;
            case ConversorConstants.PESO_CUBANO:
                conversor = new Conversor.ConversorPesoCub(euros, ConversorConstants.TIPO_CAMBIO_CUB_PESO);
                break;
            case ConversorConstants.CORONA_DANESA:
                conversor = new Conversor.ConversorCoronaDanesa(euros, ConversorConstants.TIPO_CAMBIO_CORONA_DANESA);
                break;
            case ConversorConstants.RUPIA_INDIA:
                conversor = new Conversor.ConversorRupiaIndia(euros, ConversorConstants.TIPO_CAMBIO_RUPIA_INDIA);
                break;
            case ConversorConstants.YUAN_CHINO:
                conversor = new Conversor.ConversorYuan(euros, ConversorConstants.TIPO_CAMBIO_YUAN);
                break;
            case ConversorConstants.PESO_MEXICANO:
                conversor = new Conversor.ConversorPesoMex(euros, ConversorConstants.TIPO_CAMBIO_MEX_DOLAR);
                break;

        }
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
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
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
        intent.putExtra("listaDivisas", this.listaDivisas);

        startActivity(intent);
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

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
             * DivisaInternet y en la url cambiarle la extensión o desactivar Internet en el movil,
             * por lo que la descarga fallará.
            */
            publishProgress("Sleeping...");
            resp = "La sincronización se ha realizado con éxito.";
            try {

                int time = Integer.parseInt(params[0])*1000;
                Thread.sleep(time);
                divisaInternet.cargar();
                HashMap<String, Float> tiposCambio = divisaInternet.getTiposCambio();
                conversorService.setMapaDivisas(tiposCambio);
                listaDivisas = conversorService.loadDivisas();

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
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
        }


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MainActivity.this,
                    "Descargando",
                    "Se están descargando los nuevos tipos de cambio, por favor, espera ");
        }


        @Override
        protected void onProgressUpdate(String... text) {

        }

    }
}
