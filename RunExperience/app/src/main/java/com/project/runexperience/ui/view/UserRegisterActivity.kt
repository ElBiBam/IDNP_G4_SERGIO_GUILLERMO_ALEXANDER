package com.project.runexperience.ui.view

import android.R
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity


class RegistrarUsuarioActivity : AppCompatActivity() {
    var op: Spinner? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_usuario)
        op = findViewById<View>(R.id.spinner) as Spinner
        val adapter =
            ArrayAdapter.createFromResource(this, R.array.opciones, R.layout.simple_spinner_item)
        op!!.adapter = adapter
    }
}