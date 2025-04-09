package br.unipar.hellows.appweb.services;

import br.unipar.hellows.appweb.domain.Medico;
import br.unipar.hellows.appweb.exceptions.BusinessException;
import br.unipar.hellows.appweb.repositories.MedicoRepository;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

public class MedicoService {

    private MedicoRepository medicoRepository;

    public MedicoService() {
        this.medicoRepository = new MedicoRepository();
    }

    public Medico cadastrarMedico (Medico medico) throws BusinessException{

        if(medico.getNome() == null || medico.getNome().isEmpty()){
            throw new BusinessException("Preenchimento do campo nome obrigatório!");
        }
        if(medico.getEmail() == null || medico.getEmail().isEmpty()){
            throw new BusinessException("Preenchimento do campo email obrigatório!");
        }
        if(medico.getEndereco().getLogradouro() == null || medico.getEndereco().getLogradouro().isEmpty()){
            throw new BusinessException("Preenchimento do campo logradouro do endereço é obrigatório!");
        }
        if(medico.getEndereco().getBairro() == null || medico.getEndereco().getBairro().isEmpty()){
            throw new BusinessException("Preenchimento do campo bairro do endereço é obrigatório!");
        }

    return medico;
    }


    public Medico atualizarDados(int id, Medico dadosAtualizados) throws BusinessException,
            SQLException, NamingException {

        Medico medicoExistente = medicoRepository.buscarDadosPorId(id);

        if (medicoExistente == null) {
            throw new IllegalArgumentException("Médico não encontrado");
        }

        if (!medicoExistente.getEmail().equals(dadosAtualizados.getEmail()) ||
                !medicoExistente.getCrm().equals(dadosAtualizados.getCrm()) ||
                !medicoExistente.getEspecialidade().equals(dadosAtualizados.getEspecialidade())) {
            throw new IllegalArgumentException("E-mail, CRM e Especialidade não podem ser alterados.");
        }

        medicoExistente.setNome(dadosAtualizados.getNome());
        medicoExistente.setTelefone(dadosAtualizados.getTelefone());
        medicoExistente.setEndereco(dadosAtualizados.getEndereco());

        medicoRepository.atualizar(medicoExistente);
     return medicoExistente;
    }


    public List<Medico> listarPacientes() throws SQLException, NamingException {
        return medicoRepository.listarMedicos();
    }

    public Medico desativarMedico(int id) throws SQLException, NamingException {
         medicoRepository.desativarMedico(id);
        return null;
    }

}
