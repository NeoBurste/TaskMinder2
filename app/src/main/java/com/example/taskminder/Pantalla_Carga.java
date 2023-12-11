package com.example.taskminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Pantalla_Carga extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_carga);

        firebaseAuth = firebaseAuth.getInstance();

        int tiempo=3000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                verificarUsuario();
            }
        }, tiempo);
    }

    private void verificarUsuario(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser == null){
            startActivity(new Intent(Pantalla_Carga.this, MainActivity.class));
            finish();
        }else {
            startActivity(new Intent(Pantalla_Carga.this, MenuPrincipal.class));
            finish();
        }
    }
}