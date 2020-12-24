package com.project.runexperience.viewmodel;


import android.app.ProgressDialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.runexperience.R;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText InputEmail, InputPassword;
    private Button LoginButton;
    private ProgressDialog loadingBar;
    private String parentDbName = "users";
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private Button RegisterButton;
    private Button anonymus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        LoginButton = (Button) findViewById(R.id.login_btn);
        RegisterButton = (Button) findViewById(R.id.register_btn);
        InputEmail = (EditText) findViewById(R.id.login_email_input);
        InputPassword = (EditText) findViewById(R.id.login_password_input);
        loadingBar = new ProgressDialog(this);

        anonymus = (Button) findViewById(R.id.anonymus);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,Registro.class));
            }
        });
        anonymus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginAnonymus();
            }
        });

    }

    private void LoginAnonymus() {
        loadingBar.setTitle("Iniciando");
        loadingBar.setMessage("Espere mientra se abre una cuenta");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        final long currentTimeCreation = System.currentTimeMillis();
        final String mail = "" + currentTimeCreation+"@gmail.com";

        //registrar usuario anonimo
        mAuth.createUserWithEmailAndPassword(mail, "currentTimeCreation").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Map<String, Object> map = new HashMap<>();
                    map.put("name", "Anonimo");
                    map.put("email", mail);
                    map.put("password", currentTimeCreation);


                    String id = mAuth.getCurrentUser().getUid();
                    mDatabase.child("users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {

                            if (task2.isSuccessful()) {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "No se logró Iniciar anonimo", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });

    }


    private void LoginUser() {
        String email = InputEmail.getText().toString();
        String password = InputPassword.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Por favor escriba su Email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Por favor escriba su contraseña", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Logueando   cuenta");
            loadingBar.setMessage("Espere mientra se abre su cuenta");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAcount(email, password);

        }
    }
    private void AllowAccessToAcount(String email, String password ){


        //logueamos al usuario
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();

                }
                else{
                    loadingBar.setTitle("No se pudo iniciar sesion");
                    loadingBar.setMessage("Compruebe los datos ingresados");
                    loadingBar.setCanceledOnTouchOutside(true);
                    loadingBar.show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //loguin automático
        if(mAuth.getCurrentUser() != null){
            Log.d("Main",mAuth.getCurrentUser().toString());
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }
    }
}