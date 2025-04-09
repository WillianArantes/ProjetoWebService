package br.unipar.hellows.appweb.dto;

import java.io.Serializable;

class CancelamentoConsultaDTO implements Serializable {
    private Integer consultaId;
    private String motivo;

    public Integer getConsultaId() {
        return consultaId;
    }

    public void setConsultaId(Integer consultaId) {
        this.consultaId = consultaId;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
