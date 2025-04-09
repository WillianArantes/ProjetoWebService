package br.unipar.hellows.appweb.services;

import br.unipar.hellows.appweb.domain.Paciente;
import br.unipar.hellows.appweb.repositories.PacienteRepository;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

public class PacienteService {

    private PacienteRepository pacienteRepository = new PacienteRepository();


    public Paciente cadastrarPaciente(Paciente paciente) throws SQLException, NamingException {
        validarCamposObrigatorios(paciente);
        pacienteRepository.cadastrarPaciente(paciente);
        return paciente;
    }


    public List<Paciente> listarPacientes() throws SQLException, NamingException {
        return pacienteRepository.listar();
    }



    public Paciente atualizarPaciente(Paciente novoPaciente) throws SQLException, NamingException {
        // Buscar paciente atual no banco
        Paciente existente = pacienteRepository.buscarPorId(novoPaciente.getId());

        if (existente == null) {
            throw new IllegalArgumentException("Paciente não encontrado para atualização.");
        }

        // Regras de negócio
        if (!existente.getEmail().equals(novoPaciente.getEmail())) {
            throw new IllegalArgumentException("Não é permitido alterar o e-mail do paciente.");
        }

        if (!existente.getCpf().equals(novoPaciente.getCpf())) {
            throw new IllegalArgumentException("Não é permitido alterar o CPF do paciente.");
        }

        // Chamar repositório para atualizar
        pacienteRepository.atualizar(novoPaciente);
        return existente;
    }



    public Paciente desativarPaciente(Integer idPaciente) throws SQLException, NamingException {
        pacienteRepository.desativarPaciente(idPaciente);
        return null;
    }

    // Validação de campos obrigatórios no cadastro
    private void validarCamposObrigatorios(Paciente paciente) {
        if (isNullOrEmpty(paciente.getNome()) ||
                isNullOrEmpty(paciente.getEmail()) ||
                isNullOrEmpty(paciente.getTelefone()) ||
                isNullOrEmpty(paciente.getCpf()) ||
                isNullOrEmpty(paciente.getEndereco().getLogradouro()) ||
                isNullOrEmpty(paciente.getEndereco().getBairro()) ||
                isNullOrEmpty(paciente.getEndereco().getCidade()) ||
                isNullOrEmpty(paciente.getEndereco().getUf()) ||
                isNullOrEmpty(paciente.getEndereco().getCep())) {
            throw new IllegalArgumentException("Todos os campos obrigatórios devem ser preenchidos.");
        }
    }

    private boolean isNullOrEmpty(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}
