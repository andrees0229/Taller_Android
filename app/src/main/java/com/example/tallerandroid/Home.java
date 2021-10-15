package com.example.tallerandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Home extends AppCompatActivity implements View.OnClickListener{

    String password, username;
    TextView tv1;
    Button btnMantenimiento, btnPintar;
    //DataHttpClass HttpObj;
    Intent servicio;
    int contador, i;
    Pintar obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Bundle recibo = getIntent().getExtras();
        password = recibo.getString("Contraseña");
        username = recibo.getString("Usuario");

        //Enlazamiento
        tv1 = findViewById(R.id.tv1);
        tv1.setText("BIENVENIDO" + "\n" + "\n" + "Usuario: "+ username + "\n" + " Contraseña: " + password);
        btnMantenimiento = findViewById(R.id.btnMantenimiento);
        btnPintar = findViewById(R.id.btnPintar);

        //Agregamos eventos

        btnMantenimiento.setOnClickListener(this);

        servicio = new Intent(this, MyService.class);

        /*ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isWifiConn = false;
        boolean isMobileConn = false;
        NetworkInfo nInfo = cm.getNetworkInfo(cm.getActiveNetwork());
        if(nInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            HttpObj = new DataHttpClass();
            HttpObj.execute();
        } else if(nInfo.getType() == ConnectivityManager.TYPE_MOBILE){
            Toast.makeText(this, "Desea continuar con datos", Toast.LENGTH_LONG);
        }
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();*/
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btnMantenimiento: Toast.makeText(this, "Sesión en mantenimiento", Toast.LENGTH_LONG).show(); break;
        }
    }

    public void pintar(View g) {
        obj = new Pintar();
        obj.execute();

        if (contador > 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (i = 0; i < contador; i++) {
                        try {
                            Thread.sleep(1000);
                            btnPintar.setBackgroundColor(Color.rgb(aleatorio(), aleatorio(), aleatorio()));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                public int aleatorio() {
                    return (int) (Math.random() * 255) + 1;
                }
            }).start();
        } else { }
    }

    public int aleatorio() {
        int colorAleatorio = (int) (Math.random() * 256 + 1);
        Log.d("", "Color Aleatorio: " + colorAleatorio);
        return colorAleatorio;
    }

    public class Pintar extends AsyncTask<Void, Integer, Void> { // Parametro, progreso, resultado

        @Override
        protected Void doInBackground(Void... cualquiernombre) {
            for(int i = 0; i <= contador; i++) {
                if(isCancelled()) {
                    break;
                } else {
                    btnPintar.setBackgroundColor(new Color().rgb(aleatorio(), aleatorio(), aleatorio()));
                    publishProgress(i);      // Para acceder a la UI
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }
    }

    /*public class DataHttpClass extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            URL url = null;
            try {
                url = new URL("https://run.mocky.io/v3/d0dc703a-1a0c-49b1-9146-f7ba5b92088c");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                //InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                //Log.d("", "Respuesta httpUrlConnection: " + in);

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                InputStreamReader inputStreamReader = new InputStreamReader(in);
                Stream<String> streamOfString= new BufferedReader(inputStreamReader).lines();
                String streamToString = streamOfString.collect(Collectors.joining());

                Log.d("", "Respuesta httpUrlConnection: " + streamToString);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            return null;
        }
    }*/

    public void iniciarServicioFecha(View x) {
        startService(servicio);
    }

    public void detenerServicioFecha() {

    }
}
