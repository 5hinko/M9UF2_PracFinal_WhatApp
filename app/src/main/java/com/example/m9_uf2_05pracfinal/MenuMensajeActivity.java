package com.example.m9_uf2_05pracfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.m9_uf2_05pracfinal.Model.Conexion;
import com.example.m9_uf2_05pracfinal.Model.Mensaje;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuMensajeActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MensajeItemList mAdapter;
    Conexion conexion;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_mensaje);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                startActivity(new Intent(MenuMensajeActivity.this, MensajeCreateActivity.class));
                //new MensajeCreateActivity();
                //Envia un mensaje, llama a un fragment y dentro para poner id etc
            }
        });

        mRecyclerView = findViewById(R.id.recyclerView);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Conexion.conexionContinua()) {
            conexion = new  Conexion( 11, new ThreadListadoMensaje(), MenuMensajeActivity.this);
        } else {
            finish();
        }
    }

    class ThreadListadoMensaje implements Runnable {

        @Override
        public void run() {
            mAdapter = new MensajeItemList(MenuMensajeActivity.this, conexion.listaMensajeUsuario);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(MenuMensajeActivity.this));
        }
    }

}
