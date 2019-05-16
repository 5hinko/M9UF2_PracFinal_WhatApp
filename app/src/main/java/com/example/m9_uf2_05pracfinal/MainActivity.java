package com.example.m9_uf2_05pracfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.m9_uf2_05pracfinal.Model.Conexion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    private EditText user;
    private EditText password;
    private Button btnConn;
    private Button btnConf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = findViewById(R.id.edtUser);
        password = findViewById(R.id.edtPass);
        btnConn = findViewById(R.id.btnConnect);
        btnConf = findViewById(R.id.btn_config);

    }

    private void btnEnable(boolean bolo) {
        btnConn.setEnabled(bolo);
        btnConf.setEnabled(bolo);
    }

    public void btnConnect_onClick(View view) {
        btnEnable(false);

        new Conexion(Integer.parseInt(user.getText().toString()), password.getText().toString(), 0, new ThreadVerify(), MainActivity.this);

        btnEnable(true);
    }

    class ThreadVerify implements Runnable {
        Boolean passCorrect;

        @Override
        public void run() {

            passCorrect = Conexion.passCorrect;

            if(passCorrect){
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);

            }else{
                Toast.makeText(MainActivity.this,"Login fail",Toast.LENGTH_LONG).show();
            }
        }
    }


    public void btnConfig_onClick(View view) {
        btnEnable(false);

        //Create Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        SettingPortActvity settingPortActvity = SettingPortActvity.newInstance();
        settingPortActvity.show(fragmentManager, "fragment_setting_port");

        btnEnable(true);
    }
}
