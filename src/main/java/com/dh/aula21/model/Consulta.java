package com.dh.aula21.model;

import java.util.Date;

public class Consulta {

    private Integer id;
    private Paciente paciente;
    private Dentista dentista;
    private Usuario usuario;
    private Date dataCad;
    private Date dataAtend;

    public Consulta() {
    }
    public Consulta(Paciente paciente, Dentista dentista, Usuario usuario, Date dataCad, Date dataAtend) {
        this.paciente = paciente;
        this.dentista = dentista;
        this.usuario = usuario;
        this.dataCad = dataCad;
        this.dataAtend = dataAtend;
    }
    public Consulta(Integer id, Paciente paciente, Dentista dentista, Usuario usuario, Date dataCad, Date dataAtend) {
        this.id = id;
        this.paciente = paciente;
        this.dentista = dentista;
        this.usuario = usuario;
        this.dataCad = dataCad;
        this.dataAtend = dataAtend;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Date getDataCad() {
        return dataCad;
    }
    public void setDataCad(Date dataCad) {
        this.dataCad = dataCad;
    }
    public Date getDataAtend() {
        return dataAtend;
    }
    public void setDataAtend(Date dataAtend) {
        this.dataAtend = dataAtend;
    }
    public Paciente getPaciente() {
        return paciente;
    }
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
    public Dentista getDentista() {
        return dentista;
    }
    public void setDentista(Dentista dentista) {
        this.dentista = dentista;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Consultas{" +
                "id=" + id +
                ", dataCad=" + dataCad +
                ", dataAtend=" + dataAtend +
                ", paciente=" + paciente +
                ", dentista=" + dentista +
                ", usuario=" + usuario +
                '}';
    }
}
