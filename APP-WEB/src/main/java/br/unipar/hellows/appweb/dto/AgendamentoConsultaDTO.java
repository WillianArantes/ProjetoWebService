package br.unipar.hellows.appweb.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

class AgendamentoConsultaDTO implements Serializable {
    private Integer pacienteId;
    private Integer medicoId;
    private String dataHora;

    public Integer getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Integer pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Integer getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(Integer medicoId) {
        this.medicoId = medicoId;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }
}