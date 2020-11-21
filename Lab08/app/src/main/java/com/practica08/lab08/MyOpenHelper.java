package com.practica08.lab08;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyOpenHelper extends SQLiteOpenHelper {
    private static final String COMMENTS_TABLE_CREATE = "CREATE TABLE usuario(_id INTEGER PRIMARY KEY AUTOINCREMENT, nombres TEXT, apellidos TEXT, correo TEXT, edad INTEGER)";
    private static final String DB_NAME = "running.sqlite";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase db;

    public MyOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db=this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(COMMENTS_TABLE_CREATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Insertar un nuevo comentario
    public void insertar(String nombre,String apellido, String correo, int edad){
        ContentValues cv = new ContentValues();
        cv.put("nombres", nombre);
        cv.put("apellidos", apellido);
        cv.put("correo", correo);
        cv.put("edad", edad);
        db.insert("usuario", null, cv);
    }

    //Borrar un comentario a partir de su id
    public void borrar(int id){
        String[] args = new String[]{String.valueOf(id)};
        db.delete("usuario", "_id=?", args);
    }

    //Obtener la lista de comentarios en la base de datos
    public ArrayList<Usuario> getComments(){
        //Creamos el cursor
        ArrayList<Usuario> lista=new ArrayList<Usuario>();
        Cursor c = db.rawQuery("select _id,nombres,apellidos,correo,edad  from usuario", null);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
                //Asignamos el valor en nuestras variables para crear un nuevo objeto Comentario
                String nombre = c.getString(c.getColumnIndex("nombres"));
                String apellido = c.getString(c.getColumnIndex("apellidos"));
                String correo = c.getString(c.getColumnIndex("correo"));
                int edad = Integer.parseInt(c.getString(c.getColumnIndex("edad")));
                int id=c.getInt(c.getColumnIndex("_id"));
                Usuario com =new Usuario(id,nombre,apellido,correo,edad);
                //Añadimos el comentario a la lista
                lista.add(com);
            } while (c.moveToNext());
        }

        //Cerramos el cursor
        c.close();
        return lista;
    }
}