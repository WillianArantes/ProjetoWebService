package br.unipar.hellows.appweb.domain;

import br.unipar.hellows.appweb.dto.ConsultaDTO;

import java.time.LocalTime;
import java.util.Date;

public class Consulta {

    /*Consulta

    Atributos: id, paciente, medico (opcional), dataHora, ativo*/

    private Integer id;

    private Paciente paciente;
    private Medico medico;
    private Date dataCadastro;
    private LocalTime horaCadastro;

    public Consulta(ConsultaDTO consultaDTO){

    }

    public Consulta() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public LocalTime getHoraCadastro() {
        return horaCadastro;
    }

    public void setHoraCadastro(LocalTime horaCadastro) {
        this.horaCadastro = horaCadastro;
    }
}
