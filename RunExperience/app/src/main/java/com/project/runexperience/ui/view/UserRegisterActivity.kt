package com.project.runexperience.ui.view

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.project.runexperience.R


class UserRegisterActivity : AppCompatActivity() {
    var op: Spinner? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_register)
        op = findViewById<View>(R.id.spinner) as Spinner
        val adapter =
            ArrayAdapter.createFromResource(this, R.array.opciones, android.R.layout.simple_spinner_item)
        op!!.adapter = adapter
    }
}