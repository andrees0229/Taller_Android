package com.example.tallerandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class FormularioRegister extends AppCompatActivity {
    ImageButton btnsalir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formularioregistro);
        //Boton Salir
        btnsalir = findViewById(R.id.btnexit);
        btnsalir.setOnClickListener(this::back);
    }

    public void back(View h){
        Intent ir = new Intent(this, MainActivity.class);
        ir.addFlags(ir.FLAG_ACTIVITY_CLEAR_TOP | ir.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(ir);
    }
}
