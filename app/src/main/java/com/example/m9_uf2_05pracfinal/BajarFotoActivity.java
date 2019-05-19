package com.example.m9_uf2_05pracfinal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.m9_uf2_05pracfinal.Model.Conexion;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BajarFotoActivity extends AppCompatActivity {

    private String urlFichero;
    public static ProgressBar progressBar;
    private ImageView imageView;
    private Button btnSalir;
    Conexion conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bajar_foto);

        progressBar = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.imageView2);
        btnSalir = findViewById(R.id.btn_salir);

        btnSalir.setVisibility(View.INVISIBLE);

        progressBar.setProgress(0);

        urlFichero = getIntent().getStringExtra("element");

        if (Conexion.passCorrect) {
            Conexion.nombreFichero = urlFichero;
            //new ThreadBarraProgreso().run();

            conexion = new Conexion(31, new ThreadBajarFoto(), BajarFotoActivity.this);
        } else {
            finish();
        }

    }

    class ThreadBajarFoto implements Runnable {
        @Override
        public void run() {

            if (conexion.ficheroCorrecto) {
                File imgFile = new File(conexion.dirFichero);

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                //Bitmap myBitmap = BitmapFactory.decodeFile(getFilesDir() + "/"+imgFile.toString());
                Log.d("Conexion", imgFile.toString());
                Log.d("Conexion", imgFile.getAbsolutePath());

                imageView.setImageBitmap(myBitmap);
            } else {
                Toast.makeText(BajarFotoActivity.this, "Error en baixar el fitxer", Toast.LENGTH_LONG).show();
            }
        }
    }


}
