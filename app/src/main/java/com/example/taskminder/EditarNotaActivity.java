package com.example.taskminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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

public class EditarNotaActivity extends AppCompatActivity {
    Intent data;
    EditText EditarTituloDeNota,EditarContenidoNota;
    FloatingActionButton guardarEditarNota;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_nota);

        EditarContenidoNota = findViewById(R.id.EditarContenidoNota);
        EditarTituloDeNota = findViewById(R.id.EditarTituloDeNota);
        guardarEditarNota = findViewById(R.id.guardarEditarNota);
        data=getIntent();

        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();


        Toolbar toolbar=findViewById(R.id.barraEditarNota);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        guardarEditarNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(EditarNotaActivity.this, "Guardar boton click", Toast.LENGTH_SHORT).show();
                String nuevotitulo=EditarTituloDeNota.getText().toString();
                String nuevocontenido=EditarContenidoNota.getText().toString();

                if(nuevotitulo.isEmpty()||nuevocontenido.isEmpty())
                {
                    Toast.makeText(EditarNotaActivity.this, "Complete todo los campos", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    DocumentReference documentReference=firebaseFirestore.collection("notas").document(firebaseUser.getUid()).collection("misMotas").document(data.getStringExtra("noteId"));
                    Map<String,Object> note = new HashMap<>();
                    note.put("titulo",nuevotitulo);
                    note.put("contenido",nuevocontenido);
                    documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(EditarNotaActivity.this, "Nota actualizada", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EditarNotaActivity.this,MenuPrincipal.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditarNotaActivity.this, "Error al actualizar nota", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        String notetitulo = data.getStringExtra("titulo");
        String notecontenido = data.getStringExtra("contenido");
        EditarContenidoNota.setText(notecontenido);
        EditarTituloDeNota.setText(notetitulo);
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