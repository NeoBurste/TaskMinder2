package com.example.taskminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NotaDetalles extends AppCompatActivity {

    private TextView DetallesTituloDeNota, DetallesContenidoNota;
    FloatingActionButton iraDetallesNota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota_detalles);

        DetallesTituloDeNota = findViewById(R.id.DetallesTituloDeNota);
        DetallesContenidoNota = findViewById(R.id.DetallesContenidoNota);
        iraDetallesNota = findViewById(R.id.iraDetallesNota);
        Toolbar toolbar=findViewById(R.id.barraDetalleNotas);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent data=getIntent();

        iraDetallesNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),EditarNotaActivity.class);
                intent.putExtra("titulo",data.getStringExtra("titulo"));
                intent.putExtra("contenido",data.getStringExtra("contenido"));
                intent.putExtra("noteId",data.getStringExtra("noteId"));
                v.getContext().startActivity(intent);
            }
        });

        DetallesContenidoNota.setText(data.getStringExtra("contenido"));
        DetallesTituloDeNota.setText(data.getStringExtra("titulo"));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Cambia esta línea
            finish();  // Finaliza la actividad actual
            return true;
        }
        return super.onOptionsItemSelected(item);
    }






}