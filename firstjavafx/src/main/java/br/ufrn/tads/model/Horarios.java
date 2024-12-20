package br.ufrn.tads.model;

import java.sql.Time;

public class Horarios {

    private int id;
    private Time hora;
    private String diaDele;
    private String AgendadoPor;
    public String getAgendadoPor() {
        return AgendadoPor;
    }

    public void setAgendadoPor(String agendadoPor) {
        AgendadoPor = agendadoPor;
    }

    public String getDiaDele() {
        return diaDele;
    }

    public void setDiaDele(String diaDele) {
        this.diaDele = diaDele;
    }

    public Horarios() {
   
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
 

    
}
