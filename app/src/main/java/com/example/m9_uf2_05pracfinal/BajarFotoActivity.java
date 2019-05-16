package com.example.m9_uf2_05pracfinal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    private ProgressBar progressBar;
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
            new ThreadBarraProgreso().run();

            conexion = new Conexion(31, new ThreadBajarFoto(), BajarFotoActivity.this);
        } else {
            finish();
        }

    }

    class ThreadBarraProgreso implements Runnable{

        @Override
        public void run() {
            progressBar.post(new Runnable() {
                @Override
                public void run() {

                        while(Conexion.progresoPorcentaje < 100){
                            progressBar.setProgress(Conexion.progresoPorcentaje);
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                }
            });
        }
    }


    class ThreadBajarFoto implements Runnable {
        @Override
        public void run() {/*
            BufferedOutputStream bo = null;
            File fi = null;
            MessageDigest md = null;
            try {
                try {
                    md = MessageDigest.getInstance("MD5");
                } catch (NoSuchAlgorithmException ex) {

                }

                conexion.escribe().writeUTF(urlFichero);

                String nombreFichero = conexion.lee().readUTF();
                long longFichero = conexion.lee().readLong();
                int blocFichero = conexion.lee().readInt();

                fi = new File(nombreFichero);
                bo = new BufferedOutputStream(new FileOutputStream(fi));

                long veces = longFichero / blocFichero;
                int resta = (int) (longFichero % blocFichero);

                byte b[] = new byte[blocFichero];

                for (long i = 0; i < veces; i++) {
                    conexion.lee().read(b); // enviamos lo leído
                    bo.write(b);
                    md.update(b);

                    final int numBarra = (int) ((100 * i) / veces);

                    progressBar.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(numBarra);
                        }
                    });

                }
                //envia el resto del fichero
                if (resta > 0) {
                    conexion.lee().read(b, 0, resta); // enviamos el resto del fichero
                    bo.write(b, 0, resta); // lee el resto del fichero en b
                    md.update(b, 0, resta);
                }
                //Verificación del archivo en MD5
                String md5 = conexion.lee().readUTF();

                bo.close();

                if (md5.equals(conexion.toHex(md.digest()))) {
                    File imgFile = new File(nombreFichero);

                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    imageView.setImageBitmap(myBitmap);
                } else {
                    Toast.makeText(BajarFotoActivity.this, "Error en baixar el fitxer", Toast.LENGTH_LONG).show();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }*/

            if (conexion.ficheroCorrecto) {
                File imgFile = new File(conexion.nombreFichero);

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imageView.setImageBitmap(myBitmap);
            } else {
                Toast.makeText(BajarFotoActivity.this, "Error en baixar el fitxer", Toast.LENGTH_LONG).show();
            }


        }
    }


}
