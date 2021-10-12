package com.example.tallerandroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText etusuario,etcontraseña;
    String usuario = "Andres",contraseña = "123";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etusuario = findViewById(R.id.etusuario);
        etcontraseña = findViewById(R.id.etcontraseña);
    }

    public void next_home(View b){
        Intent ir = new Intent(this, Home.class);
        ir.addFlags(ir.FLAG_ACTIVITY_CLEAR_TOP | ir.FLAG_ACTIVITY_CLEAR_TASK);
        Bundle data = new Bundle();
        data.putString("Usuario",etusuario.getText().toString());
        data.putString("Contraseña",etcontraseña.getText().toString());
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
            if(etusuario.getText().toString().equals(usuario) && etcontraseña.getText().toString().equals(contraseña)){
                ir.putExtras(data);
                startActivity(ir);
            }else{
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setMessage("Usuario o contraseña no son validos");
                alert.setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alert.show();
            }



        }
    }
}