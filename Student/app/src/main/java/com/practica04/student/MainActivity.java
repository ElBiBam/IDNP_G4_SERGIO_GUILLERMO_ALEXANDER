package com.practica04.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.practica04.student.adapter.StudentAdapter;
import com.practica04.student.model.StudentModel;
//import com.practica04.student.viewmodel.StudentViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<StudentModel> students = new ArrayList<StudentModel>();
    private ArrayAdapter<StudentModel> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView_students = findViewById(R.id.listView_students);

        adapter = new StudentAdapter(this, students);
        listView_students.setAdapter(adapter);

        Button btn_newStudent = (Button)findViewById(R.id.button_newStudent);
        btn_newStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewStudentActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("mydebug", String.valueOf(requestCode));

        if (requestCode == 1) {
            if(resultCode == 1) {
                String cui = data.getStringExtra("cui");
                String lastname = data.getStringExtra("lastname");
                String name = data.getStringExtra("name");

                StudentModel model = new StudentModel(cui, lastname, name);
                students.add(model);

                adapter.notifyDataSetChanged();
            }else{

            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("studentmain", "stop");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("studentmain", "destroy");
    }
}