package com.example.m9_uf2_05pracfinal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.m9_uf2_05pracfinal.Model.Conexion;
import com.example.m9_uf2_05pracfinal.Model.Mensaje;

public class MensajeCreateActivity extends AppCompatActivity {

    private EditText edtDesti;
    private EditText edtContingut;
    private Button btnEnvia;
    private Button btnCancelar;
    Conexion conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_create_mensaje);

        edtDesti = findViewById(R.id.edt_destiID);
        edtContingut = findViewById(R.id.edt_contingut);
        btnEnvia = findViewById(R.id.btn_enviar);
        btnCancelar = findViewById(R.id.btn_cancelar);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeLayout();
            }
        });

        btnEnvia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Conexion.conexionContinua()) {
                   if(edtDesti.getText().toString().length()>0){
                       Conexion.mensajeAEnviar = new Mensaje(Integer.parseInt(edtDesti.getText().toString()), edtContingut.getText().toString());

                       conexion = new Conexion(20, new ThreadEnviaMensaje(), MensajeCreateActivity.this);
                   }else{
                       Toast.makeText(getApplicationContext(),"No hay destinatario",Toast.LENGTH_LONG).show();
                   }
                } else {
                    closeLayout();
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        Toast.makeText(getApplicationContext(),"AAAAAAAAa", Toast.LENGTH_LONG).show();
        finish();
        return;
    }

    class ThreadEnviaMensaje implements Runnable {

        @Override
        public void run() {
            if(!conexion.errorEnviarMensaje){
                closeLayout();
            }else{
                Toast.makeText(MensajeCreateActivity.this,"Error no existeix aquesta direcció",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void closeLayout() {
        //getAcivity().onBackPressed();
        finish();
    }
}
