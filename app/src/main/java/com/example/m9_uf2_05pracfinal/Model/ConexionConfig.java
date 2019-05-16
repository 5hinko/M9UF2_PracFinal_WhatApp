package com.example.m9_uf2_05pracfinal.Model;

public class ConexionConfig {
    private static String ip = "192.168.137.1"; //localhost,... 127.0.0.1
    private static int puerto = 12345; //puerto del servidor

    public static void setPortIP(String _ip, int _puerto) {
        ip = _ip;
        puerto = _puerto;
    }

    public static String getPortIP(){
        return ip + " 和" + puerto;
    }
}
