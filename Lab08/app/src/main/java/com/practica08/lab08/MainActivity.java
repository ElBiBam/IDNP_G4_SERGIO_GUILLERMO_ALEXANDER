package com.practica08.lab08;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener,AdapterView.OnItemSelectedListener {
    //Declaramos los elementos de la interfaz
    private Button btnCrear;
    private Button btnVer;
    private Button btnEliminar;
    private EditText editNombre;
    private EditText editApellido;
    private EditText editCorreo;
    private EditText editEdad;
    private EditText txtNombre;
    private EditText txtComentario;

    //Declaración del spinner y su Adapter
    private Spinner spinComentarios;
    private ArrayAdapter spinnerAdapter;

    //Lista de comentarios y comentario actual
    private ArrayList<Usuario> lista;
    private Usuario c;

    //Controlador de bases de datos
    private MyOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializamos los elementos de la interfaz
        editNombre=(EditText) findViewById(R.id.editNombre);
        editApellido=(EditText)findViewById(R.id.editApellido);
        editCorreo=(EditText)findViewById(R.id.editCorreo);
        editEdad=(EditText) findViewById(R.id.editEdad);
        txtNombre=(EditText) findViewById(R.id.txtNombre);
        txtComentario=(EditText)findViewById(R.id.txtComentario);

        //Los elementos del panel inferior no seran editables
        txtNombre.setEnabled(false);
        txtComentario.setEnabled(false);

        btnCrear=(Button)findViewById(R.id.btnCrear);
        btnVer=(Button)findViewById(R.id.btnVer);
        btnEliminar=(Button)findViewById(R.id.btnEliminar);

        btnCrear.setOnClickListener(this);
        btnVer.setOnClickListener(this);
        btnEliminar.setOnClickListener(this);

        //Iniciamos el controlador de la base de datos
        db=new MyOpenHelper(this);

        //Iniciamos el spinner y la lista de comentarios
        spinComentarios=(Spinner) findViewById(R.id.spinComentarios);
        lista=db.getComments();

        //Creamos el adapter y lo asociamos al spinner
        spinnerAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,lista);
        spinComentarios.setAdapter(spinnerAdapter);
        spinComentarios.setOnItemSelectedListener(this);


    }

    @Override
    public void onClick(View v) {
        //Acciones de cada boton
        switch(v.getId()){
            case R.id.btnCrear:
                //Insertamos un nuevo elemento en base de datos
                db.insertar(editNombre.getText().toString(),editApellido.getText().toString(),editCorreo.getText().toString(),Integer.parseInt(editEdad.getText().toString()));
                //Actualizamos la lista de comentarios
                lista=db.getComments();
                //Actualizamos el adapter y lo asociamos de nuevo al spinner
                spinnerAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,lista);
                spinComentarios.setAdapter(spinnerAdapter);
                //Limpiamos el formulario
                editNombre.setText("");
                editApellido.setText("");
                editCorreo.setText("");
                editEdad.setText("");
                break;

            case R.id.btnVer:
                //Si hay algun comentario seleccionado mostramos sus valores en la parte inferior
                if(c!=null) {
                    txtNombre.setText("Usuario: "+c.getNombre()+" "+c.getApellido());
                    txtComentario.setText("Correo: "+c.getCorreo()+"/n"+"Edad: "+c.getEdad()+" años");

                }
                break;
            case R.id.btnEliminar:
                //Si hay algun comentario seleccionado lo borramos de la base de datos y actualizamos el spinner
                if(c!=null) {
                    db.borrar(c.getId());
                    lista = db.getComments();
                    spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, lista);
                    spinComentarios.setAdapter(spinnerAdapter);
                    //Limpiamos los datos del panel inferior
                    txtNombre.setText("");
                    txtComentario.setText("");
                    //Eliminamos el Comentario actual puesto que ya no existe en base de datos
                    c=null;
                }
                break;

        }
    }

    //Controlador de elemento seleccionado en el spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinComentarios) {
            //Si hay elementos en la base de datos, establecemos el comentario actual a partir del
            //indice del elemento seleccionado en el spinner
            if(lista.size()>0) {
                c = lista.get(position);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
