package com.example.m9_uf2_05pracfinal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.m9_uf2_05pracfinal.Model.Conexion;

import java.io.IOException;

public class MenuActivity extends AppCompatActivity {

    TextView txtPendiente;

    Button btnListado;
    Button btnDescarga;

    Conexion conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        txtPendiente = findViewById(R.id.txt_notificacion);
        btnListado = findViewById(R.id.btn_irMensajes);
        btnDescarga = findViewById(R.id.btn_irRecibir);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (Conexion.conexionContinua()) {
            conexion = new  Conexion( 10, new ThreadVerNoti(), MenuActivity.this);
        } else {
            finish();
        }
    }

    /*
        Error con conexion.notificacion
         */
    class ThreadVerNoti implements Runnable {

        Handler handler = new Handler();
        @Override
        public void run() {
            //handler.post(new Runnable(){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txtPendiente.setText(conexion.notificacion+"");
                }
            });
        }
    }

    private void accionBtnEnable(boolean bool) {
        btnListado.setEnabled(bool);
        btnDescarga.setEnabled(bool);
    }

    public void btnClick_mensajes(View view) {
        accionBtnEnable(false);
        startActivity(new Intent(this,MenuMensajeActivity.class));
        //new MenuMensajeActivity();
        accionBtnEnable(true);
    }

    public void btnClick_ficheros(View view) {
        accionBtnEnable(false);
        startActivity(new Intent(this,MenuFicheroActivity.class));
        //new MenuFicheroActivity();
        accionBtnEnable(true);
    }
}
