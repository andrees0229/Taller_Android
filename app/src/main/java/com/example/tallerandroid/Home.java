package com.example.tallerandroid;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    String password, username;
    TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Bundle recibo = getIntent().getExtras();
        password = recibo.getString("Contraseña");
        username = recibo.getString("Usuario");
        tv1 = findViewById(R.id.tv1);
        tv1.setText("username: "+username + " password: " + password);
    }

}
