package com.example.m9_uf2_05pracfinal.Model;

public class Mensaje {

    private int origen_Des;
    private String contingut;
    private boolean read;

    public Mensaje(int origen, String contingut){
        this.origen_Des = origen;
        this.contingut = contingut;
        read = false;
    }

    public int getOrigen_Des(){
        return origen_Des;
    }

    public String getContingut(){
        return contingut;
    }

    public boolean getRead(){
        return read;
    }

    public void isRead(){
        read = true;
    }

}
