package com.example.practica;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnGuardar, btnBuscar, btnActualizar, btnBorrar,BLista;
    EditText etId, etNombres, etTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGuardar = (Button)findViewById(R.id.btnGuardar);
        btnBuscar = (Button)findViewById(R.id.btnBuscar);
        btnBorrar = (Button)findViewById(R.id.btnBorrar);
        btnActualizar  = (Button)findViewById(R.id.btnActulizar);
        BLista=(Button)findViewById(R.id. blista);

        etId = (EditText)findViewById(R.id.etId);
        etNombres = (EditText)findViewById(R.id.etNombres);
        etTelefono = (EditText)findViewById(R.id.etTelefono);

        final DesarrolloBD desarrolloBD = new DesarrolloBD(getApplicationContext());

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = desarrolloBD.getWritableDatabase();
                ContentValues valores = new ContentValues();
                valores.put(DesarrolloBD.DatosTabla.COLUMNA_ID,etId.getText().toString());
                valores.put(DesarrolloBD.DatosTabla.COLUMNA_NOMBRES, etNombres.getText().toString());
                valores.put(DesarrolloBD.DatosTabla.COLUMNA_TELEFONOS, etTelefono.getText().toString());

                Long IdGuardado = db.insert(DesarrolloBD.DatosTabla.NOMBRE_TABLA, DesarrolloBD.DatosTabla.COLUMNA_ID, valores);
                Toast.makeText(getApplicationContext(), "SE GUARDO CORRECTAMENTE: "+IdGuardado, Toast.LENGTH_LONG).show();

            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = desarrolloBD.getReadableDatabase();
                String[] argsel = {etId.getText().toString()};
                String[] projection = {DesarrolloBD.DatosTabla.COLUMNA_NOMBRES, DesarrolloBD.DatosTabla.COLUMNA_TELEFONOS};
                Cursor c = db.query(DesarrolloBD.DatosTabla.NOMBRE_TABLA, projection, DesarrolloBD.DatosTabla.COLUMNA_ID+"=?",argsel,null,null,null);

                c.moveToFirst();
                etNombres.setText(c.getString(0));
                etTelefono.setText(c.getString(1));

            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = desarrolloBD.getWritableDatabase();
                String Selection = DesarrolloBD.DatosTabla.COLUMNA_ID+"=?";
                String[] argsel = {etId.getText().toString()};

                db.delete(DesarrolloBD.DatosTabla.NOMBRE_TABLA,Selection,argsel);

            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = desarrolloBD.getWritableDatabase();
                ContentValues valores = new ContentValues();
                valores.put(DesarrolloBD.DatosTabla.COLUMNA_NOMBRES, etNombres.getText().toString());
                valores.put(DesarrolloBD.DatosTabla.COLUMNA_TELEFONOS, etTelefono.getText().toString());
                String[] argsel = {etId.getText().toString()};
                String Selection = DesarrolloBD.DatosTabla.COLUMNA_ID+"=?";

                int count = db.update(DesarrolloBD.DatosTabla.NOMBRE_TABLA,valores,Selection,argsel);

            }
        });
    }
}
