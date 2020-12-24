package com.project.runexperience.viewmodel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class Registro extends AppCompatActivity {
    private EditText mName, mEmail, mPassword;
    private Button mButtonRegistro;

    // Variables a registrar
    private String name, email, password;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mName = (EditText) findViewById(R.id.name);
        mEmail = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);
        mButtonRegistro = (Button) findViewById(R.id.register);

        mButtonRegistro.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 name = mName.getText().toString();
                 email = mEmail.getText().toString();
                 password = mPassword.getText().toString();

                 if(!(name.isEmpty() || email.isEmpty() || password.isEmpty())){
                     if(password.length() >=6){
                         registerUser();
                     }
                     else{
                        Toast.makeText(Registro.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                     }

                 }
                 else{
                     Toast.makeText(Registro.this,"Complete todos los campos", Toast.LENGTH_SHORT).show();
                 }
             }
         });
    }
    private void registerUser(){



        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Map<String, Object> map = new HashMap<>();
                    map.put("name", name);
                    map.put("email", email);
                    map.put("password", password);



                    String id = mAuth.getCurrentUser().getUid();


                    mDatabase.child("users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                startActivity(new Intent(Registro.this, MainActivity.class));
                                finish();
                            }
                            else{
                                Toast.makeText(Registro.this, "No se logró registrar usuario", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
                else{
                    Toast.makeText(Registro.this, "No se logró registrar usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}