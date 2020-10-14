package com.practica04.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NewStudentActivity extends AppCompatActivity {
    Intent mainActivityIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_student);

        Button btn_accept = (Button)findViewById(R.id.button_accept);
        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                EditText et_cui = findViewById(R.id.editText_cui);
                EditText et_lastName = findViewById(R.id.editText_lastName);
                EditText et_name = findViewById(R.id.editText_name);
                resultIntent.putExtra("cui", et_cui.getText().toString());
                resultIntent.putExtra("lastname", et_lastName.getText().toString());
                resultIntent.putExtra("name", et_name.getText().toString());
                setResult(1, resultIntent);
                finish();
            }
        });

        Button btn_cancel = (Button)findViewById(R.id.button_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                setResult(0, resultIntent);
                finish();
            }
        });
    }
}