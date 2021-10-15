package com.example.tallerandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    EditText etusuario,etcontraseña;

    Button btnLogin,btnRegister;
    String nusuario,contraseña;

    private FirebaseAuth mAuth;
    private GoogleApiClient googleApiClient;
    private GoogleSignInClient mGoogleCli;
    private final static int RC_SING_IN = 777;
    private SignInButton signInButton;
    private FirebaseAuth.AuthStateListener firebaseA;
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

        // inicio de seccion con google
        mAuth = FirebaseAuth.getInstance();

        firebaseA = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if(user !=null){
                    goMainScreen();
                }
            }
        } ;

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        signInButton = (SignInButton) findViewById(R.id.btnLoginGoogle);

        signInButton.setSize(SignInButton.SIZE_WIDE);


        ConnectivityManager connectivityManager =(ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo!= null && networkInfo.isConnectedOrConnecting()){
            Log.d("", "Conexion establecida");

            signInButton.setOnClickListener((v) -> {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,RC_SING_IN);

            });
        }else{
            Toast.makeText(MainActivity.this,"Error de Conexion", Toast.LENGTH_SHORT).show();
        }
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


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseA);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SING_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }


    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()){
            AutenticacionFirebase(result.getSignInAccount());
        }else{
            Toast.makeText(this,"No se puede iniciar seccion", Toast.LENGTH_SHORT).show();
        }
    }

    private void goMainScreen() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(firebaseA != null){
            mAuth.removeAuthStateListener(firebaseA);
        }
    }

    // metodo para autenticacion con firebase
    private void AutenticacionFirebase(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){

                            Toast.makeText(getApplicationContext(),"No se puedo autenticar",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
