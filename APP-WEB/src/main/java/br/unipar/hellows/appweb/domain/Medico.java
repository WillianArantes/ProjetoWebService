package br.unipar.hellows.appweb.domain;

import br.unipar.hellows.appweb.dto.EnderecoDTO;
import br.unipar.hellows.appweb.dto.MedicoDTO;

public class Medico {
    private Integer id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;
    private String especialidade;
    private Endereco endereco;
    private boolean ativo; //

    public Medico(MedicoDTO medicoDTO) {
    }

    public Medico() {
    }

    public Medico(Integer id, String nome, String email, String telefone, String crm, String especialidade,
                  Endereco endereco, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.crm = crm;
        this.especialidade = especialidade;
        this.endereco = endereco;
        this.ativo = ativo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
// ...demais getters e setters existentes...
}
