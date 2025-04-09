package br.unipar.hellows.appweb.domain;

import br.unipar.hellows.appweb.dto.PacienteDTO;

public class Paciente {

    /*Nome
    E-mail
    Telefone
    CPF
    Endereço completo (logradouro, número, complemento, bairro, cidade, UF e CEP)
    Todas as informações são de preenchimento obrigatório, exceto o número e o
    complemento do endereço.*/
    private Integer id;
    private String nome;
    private String telefone;
    private String email;
    private String cpf;
    private Endereco endereco;
    private boolean ativo;

    public Paciente(PacienteDTO pacienteDTO) {



    }
    public Paciente() {
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
