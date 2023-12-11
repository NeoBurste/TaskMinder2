package com.example.taskminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CrearNota extends AppCompatActivity {

    EditText crearTituloDeNota, crearContenidoNota;
    FloatingActionButton guardarNota;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    ProgressBar progressBarCrearNota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_nota);

        guardarNota = findViewById(R.id.guardarNota);
        crearTituloDeNota = findViewById(R.id.crearTituloDeNota);
        crearContenidoNota = findViewById(R.id.crearContenidoNota);
        progressBarCrearNota = findViewById(R.id.progressBarCrearNota);

        Toolbar toolbar=findViewById(R.id.barraCrearNotas);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();


        guardarNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = crearTituloDeNota.getText().toString();
                String contenido = crearContenidoNota.getText().toString();
                if(titulo.isEmpty() || contenido.isEmpty())
                {
                    Toast.makeText(CrearNota.this, "Los campos estan vacios", Toast.LENGTH_SHORT).show();
                }
                else{
                    progressBarCrearNota.setVisibility(View.VISIBLE);
                    DocumentReference documentReference=firebaseFirestore.collection("notas").document(firebaseUser.getUid()).collection("misMotas").document();
                    Map<String , Object> nota = new HashMap<>();
                    nota.put("titulo",titulo);
                    nota.put("contenido",contenido);

                    documentReference.set(nota).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(CrearNota.this, "Nota Creada con exito", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(CrearNota.this, MenuPrincipal.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CrearNota.this, "Error al crear nota", Toast.LENGTH_SHORT).show();
                            progressBarCrearNota.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });




    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home)
        {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}