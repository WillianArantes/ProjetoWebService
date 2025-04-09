package br.unipar.hellows.appweb.dto;

import java.io.Serializable;

class AtualizacaoPacienteDTO implements Serializable {
    private Integer id;
    private String nome;
    private String telefone;
    private EnderecoDTO endereco;
}
