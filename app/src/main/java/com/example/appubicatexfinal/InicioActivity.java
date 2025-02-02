package com.example.appubicatexfinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class InicioActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText textEmail;
    private EditText textPassword;
    private Button btnIngresar;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private Button btnRegistrarse;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incio);
       // Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();
        textEmail = findViewById(R.id.email);
        textPassword = findViewById(R.id.password);
        btnIngresar =  findViewById(R.id.btn_iniciarsesion);
        btnRegistrarse = findViewById(R.id.btn_registrarseINICIO);
        progressDialog = new ProgressDialog(this);
        btnIngresar.setOnClickListener(this);
        btnRegistrarse.setOnClickListener(this);
    }



    //loggear un usuario
    private void iniciarSesion(){
        final  String email = textEmail.getText().toString().trim();
        String password = textPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Se debe ingresa un correo electrónico!",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Falta ingresar la contraseña!",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Comprobando usuario...");
        progressDialog.show();


        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    Toast.makeText(InicioActivity.this,"Bienvenido " + textEmail.getText(),Toast.LENGTH_LONG).show();

                    Intent inten = new Intent(InicioActivity.this, MainActivity.class);
                    inten.putExtra("usuario", email);
                    startActivity(inten);
                }else {
                    if (task.getException()instanceof FirebaseAuthUserCollisionException){//si existe un correo igual

                        Toast.makeText(InicioActivity.this, "El usuario ya existe!", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(InicioActivity.this, "Error en el correo o contraseña", Toast.LENGTH_LONG).show();
                    }
                }
                progressDialog.dismiss();
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_iniciarsesion:
                iniciarSesion();
                break;

            case R.id.btn_registrarseINICIO:
               Intent inten = new Intent(InicioActivity.this,RegistrarseActivity.class);
               startActivity(inten);
                break;

        }
     //   iniciarSesion();
    }
}
