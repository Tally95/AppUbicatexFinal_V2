package com.example.appubicatexfinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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

public class RegistrarseActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText textEmail;
    private EditText textPassword;
    private Button btnRegistrar;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();
        textEmail =(EditText) findViewById(R.id.email);
        textPassword = (EditText)findViewById(R.id.password);
        btnRegistrar = (Button) findViewById(R.id.btn_registrar);
        progressDialog = new ProgressDialog(this);
        btnRegistrar.setOnClickListener(this);

    }
    private  void registrarUsuario(){
        String email = textEmail.getText().toString().trim();
        String password = textPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Se debe ingresa un correo electrónico!",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Falta ingresar la contraseña!",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Realizando registro en linea...");
        progressDialog.show();


        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    Toast.makeText(RegistrarseActivity.this,"Se ha egistrado el email "+textEmail.getText()+" correctamete ",Toast.LENGTH_LONG).show();
                    Intent inten = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(inten);
                }else {

                    if (task.getException()instanceof FirebaseAuthUserCollisionException){//si existe un correo igual

                    Toast.makeText(RegistrarseActivity.this, "El usuario ya existe!", Toast.LENGTH_SHORT).show();
                }else {
                        Toast.makeText(RegistrarseActivity.this, "Error la contraseña debe de ser de mas de tres caracteres", Toast.LENGTH_LONG).show();
                    }
                }
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        registrarUsuario();

    }


}
