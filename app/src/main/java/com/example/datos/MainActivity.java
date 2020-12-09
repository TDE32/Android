package com.example.datos;

import  androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.datos.Modelo.Personas;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    EditText nomP, appP, correoP;
    ListView LV_datos;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        nomP = findViewById(R.id.Nombre);
        appP = findViewById(R.id.Apellidos);
        correoP = findViewById(R.id.Email);

        LV_datos = findViewById(R.id.lv_Datos);
        incializarFirebase();

    }

    private void incializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String nombre = nomP.getText().toString();
        String apellido = appP.getText().toString();
        String correo = correoP.getText().toString();
        String emailAddress = correoP.getText().toString().trim();
        switch (item.getItemId()){
            case R.id.icon_add:{
                if (nombre.equals("")||correo.equals("")||apellido.equals("")){
                    Toast.makeText(this,"Campos vacios",Toast.LENGTH_LONG).show();
                    validacion();
                }
                else{
                    Personas p = new Personas();
                    p.setUid(UUID.randomUUID().toString());
                    p.setNombre(nombre);
                    p.setApellidos(apellido);
                    if (android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
                        p.setCorreo(correo);
                        databaseReference.child("Personas").child(p.getUid()).setValue(p);
                        Toast.makeText(this,"Agregar", Toast.LENGTH_LONG).show();
                        limpiarcajas();
                    }
                    else{
                        Toast.makeText(this, "Correo invalido", Toast.LENGTH_LONG);
                        validacion();
                    }

                }
                break;
            }
            case R.id.icon_del:{
                Toast.makeText(this,"Eliminar", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.icon_safe:{
                Toast.makeText(this,"Guardar", Toast.LENGTH_LONG).show();

            }
            default:break;
        }
        return true;
    }

    private void limpiarcajas() {
        nomP.setText("");
        appP.setText("");
        correoP.setText("");
    }
    private void validacion(){
        String nombre = nomP.getText().toString();
        String apellido = appP.getText().toString();
        String correo = correoP.getText().toString();

        if (nombre.equals("")){
            nomP.setError("Campo Obligatorio");
        }
        if (apellido.equals("")){
            appP.setError("Campo Obligatorio");
        }
        if (correo.equals("")){
            correoP.setError("Campo Obligatorio");
        }
    }

    }
