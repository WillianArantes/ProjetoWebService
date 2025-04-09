package br.unipar.hellows.appweb.dto;

import br.unipar.hellows.appweb.domain.Medico;
import br.unipar.hellows.appweb.domain.Paciente;

import java.util.Date;

public class ConsultaDTO {
    private Integer id;
    private Paciente paciente;
    private Medico medico;
    private Date dataCadastro;
    private String horaCadastro;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHoraCadastro() {
        return horaCadastro;
    }

    public void setHoraCadastro(String horaCadastro) {
        this.horaCadastro = horaCadastro;
    }

    public java.sql.Date getDataCadastro() {
        return (java.sql.Date) dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }


}
