package com.example.m9_uf2_05pracfinal.Model;

import android.app.Activity;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Conexion {

    public int notificacion;
    public static List<Mensaje> listaMensajeUsuario = new ArrayList<>();
    ;
    public static Mensaje mensajeAEnviar;
    public Boolean errorEnviarMensaje;
    public List<String> listNomFichero;
    public static String nombreFichero;
    public boolean ficheroCorrecto;
    public static int progresoPorcentaje = 0;
    public Object ficheroDatos;

    private static int user;
    private static String pass;

    public static boolean passCorrect = false;

    private DataInputStream dis;
    private DataOutputStream dos;

    private static Socket sk = null;

    public Conexion(int user, String pass, int orden, Runnable r, Activity act) {
        this.user = user;
        this.pass = pass;
        new Thread(new ClientThread(orden, r, act)).start();
    }

    public Conexion(int orden, Runnable r, Activity act) {
        new Thread(new ClientThread(orden, r, act)).start();
    }

    class ClientThread implements Runnable {
        int orden;
        Runnable r;
        Activity act;

        ClientThread(int orden, Runnable r, Activity act) {
            this.orden = orden;
            this.r = r;
            this.act = act;
        }

        @Override
        public void run() {
            try {
                String strPuerto = ConexionConfig.getPortIP();

                String ip = strPuerto.split(" 和")[0];
                int puerto = Integer.parseInt(strPuerto.split(" 和")[1]);

                sk = new Socket(ip, puerto);
                //OutputStream out = sk.getOutputStream();
                dis = new DataInputStream(sk.getInputStream());
                dos = new DataOutputStream(sk.getOutputStream());

                //Cod usuario MD5[numToMD5+Pass]
                long numToMD5 = dis.readLong();
                //https://examples.javacodegeeks.com/android/core/socket-core/android-socket-example/

                //Codifica contra
                MessageDigest md = null;
                try {
                    md = MessageDigest.getInstance("MD5");
                } catch (NoSuchAlgorithmException ex) {
                }

                //Login
                dos.writeInt(user);

                String passServer = pass + numToMD5;
                md.update(passServer.getBytes());

                passServer = toHex(md.digest());

                dos.writeUTF(passServer);

                switch (dis.readInt()) {
                    case 1:
                        passCorrect = true;
                        break;
                    case -1:
                        passCorrect = false;
                        break;
                }

                if (passCorrect) {
                    dos.writeInt(orden);

                    switch (orden) {
                        case 10:
                            //Lee Count Mensaje
                            notificacion = dis.readInt();
                            break;
                        case 11:
                            //Lee mensaje para uidUser
                            MensajeParaUsuario();

                            break;
                        case 20:
                            //Escribe Msg

                            MensajeEnviar();

                            break;
                        case 30:
                            //Recibir nombre de ficheros
                            FicheroLista();

                            break;
                        case 31:
                            FicheroPedir();
                            break;
                        case 40:
                            //EnviarIDLoged();
                            break;
                        case 0:
                            break;
                        default:
                            break;
                    }

                }


                //RunOnUIThread(r);
                //r thread del activity
                act.runOnUiThread(r);

            } catch (IOException e) {
                e.printStackTrace();
                try {
                    sk.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }

        private void MensajeParaUsuario() throws IOException {
            int numMensaje = dis.readInt();

            for (int i = 0; i < numMensaje; i++) {

                int origen = dis.readInt();
                String contingut = dis.readUTF();

                //get listMensaje
                listaMensajeUsuario.add(new Mensaje(origen, contingut));
            }
        }

        private void MensajeEnviar() throws IOException {
            dos.writeInt(mensajeAEnviar.getOrigen_Des());

            if (dis.readInt() == 0) {
                dos.writeUTF(mensajeAEnviar.getContingut());
                errorEnviarMensaje = false;
            } else {
                errorEnviarMensaje = true;
            }
        }

        private void FicheroLista() throws IOException {
            listNomFichero = new ArrayList<>();
            int numFichero = dis.readInt();

            for (int i = 0; i < numFichero; i++) {

                String contingut = dis.readUTF();

                //get listNomFichero
                listNomFichero.add(contingut);
            }
        }

        private void FicheroPedir() throws IOException {

            BufferedOutputStream bo = null;
            File fi = null;
            MessageDigest md = null;

            try {
                md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException ex) {

            }

            dos.writeUTF(nombreFichero);

            String nombreFichero = dis.readUTF();
            long longFichero = dis.readLong();
            int blocFichero = dis.readInt();

            fi = new File(nombreFichero);
            bo = new BufferedOutputStream(new FileOutputStream(fi));

            long veces = longFichero / blocFichero;
            int resta = (int) (longFichero % blocFichero);

            byte b[] = new byte[blocFichero];

            for (long i = 0; i < veces; i++) {
                dis.read(b); // enviamos lo leído
                bo.write(b);
                md.update(b);

                final int numBarra = (int) ((100 * i) / veces);

                /////////
                    progresoPorcentaje = numBarra;

            }
            //envia el resto del fichero
            if (resta > 0) {
                dis.read(b, 0, resta); // enviamos el resto del fichero
                bo.write(b, 0, resta); // lee el resto del fichero en b
                md.update(b, 0, resta);
            }
            //Verificación del archivo en MD5
            String md5 = dis.readUTF();

            bo.close();

            if (md5.equals(toHex(md.digest()))) {
                ficheroCorrecto = true;
            } else {
                ficheroCorrecto = false;
            }
        }
    }

    public static boolean conexionContinua() {
        return passCorrect;
    }

    public String toHex(byte[] datos) {
        StringBuilder bs = new StringBuilder(datos.length * 2);
        for (int i = 0; i < datos.length; i++) {
            int v = 0xFF & datos[i]; // per que sigui de 0 a 255
            if (v < 0x10) {
                bs.append('0'); //perque faltarà el primer 0
            }
            bs.append(Integer.toHexString(v));
        }
        return bs.toString();
    }
}
