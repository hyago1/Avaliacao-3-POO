package br.ufrn.tads.model;

import java.sql.Time;

public class Horarios {

    private int id;
    private Time hora;

    private Boolean vago;

    public Horarios() {
   
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public Boolean getVago() {
        return vago;
    }

    public void setVago(Boolean vago) {
        this.vago = vago;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
 

    
}
