package com.practica04.student.viewmodel;

import android.util.Log;
import android.widget.ListView;

import com.practica04.student.model.StudentModel;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StudentViewModel extends ViewModel {
    private MutableLiveData<List<StudentModel>> students;
    public LiveData<List<StudentModel>> getStudents() {
        if (students == null) {
            students = new MutableLiveData<List<StudentModel>>();
            //loadStudents();
        }
        return students;
    }
    public void setValue(List<StudentModel> list) {
        students.setValue(list);
    }

    private void loadStudents() {
        // Do an asynchronous operation to fetch users.
    }
}
