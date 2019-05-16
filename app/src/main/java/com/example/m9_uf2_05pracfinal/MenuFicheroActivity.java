package com.example.m9_uf2_05pracfinal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.m9_uf2_05pracfinal.Model.Conexion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuFicheroActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private FicheroItemList mAdapter;
    Conexion conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_fichero);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(Conexion.conexionContinua()){
            conexion = new Conexion(30,new ThreadVerNoti(),MenuFicheroActivity.this);
        }else{
            finish();
        }
    }

    class ThreadVerNoti implements Runnable {

        @Override
        public void run() {
            mAdapter = new FicheroItemList(MenuFicheroActivity.this, conexion.listNomFichero);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(MenuFicheroActivity.this));
        }
    }
}
