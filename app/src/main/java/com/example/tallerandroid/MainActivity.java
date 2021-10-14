package com.example.tallerandroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText etusuario,etcontraseña;

    Button btnLogin,btnRegister;
    String nusuario,contraseña;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etusuario = findViewById(R.id.etusuario);
        etcontraseña = findViewById(R.id.etcontraseña);
        //Boton Login
        btnLogin = findViewById(R.id.bLogin);
        btnLogin.setOnClickListener(this::next_home);
        //Boton Register
        btnRegister = findViewById(R.id.bRegister);
        btnRegister.setOnClickListener(this::next_Formulario);


    }

    //Pasar al Inicio(HOME)
    public void next_home(View b){

        if(etusuario.getText().toString().isEmpty() || etcontraseña.getText().toString().isEmpty()){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Verifique Usuario y Contraseña");
            alert.setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alert.show();
        }else{

            nusuario = etusuario.getText().toString();
            contraseña = etcontraseña.getText().toString();
            Log.d("",nusuario + " " + contraseña);

                Intent ir = new Intent(this, Home.class);
                ir.addFlags(ir.FLAG_ACTIVITY_CLEAR_TOP | ir.FLAG_ACTIVITY_CLEAR_TASK);
                Bundle data = new Bundle();
                data.putString("Usuario",nusuario);
                data.putString("Contraseña",contraseña);
                Log.d("",nusuario + " " + contraseña );
                ir.putExtras(data);
                startActivity(ir);

            }


        }
    //Pasar al Formulario Registro
    public void next_Formulario(View b){
        Intent ir = new Intent(this, FormularioRegister.class);
        ir.addFlags(ir.FLAG_ACTIVITY_CLEAR_TOP | ir.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(ir);
    }



    }
