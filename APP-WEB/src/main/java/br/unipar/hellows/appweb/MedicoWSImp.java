package br.unipar.hellows.appweb;

import br.unipar.hellows.appweb.domain.Medico;
import br.unipar.hellows.appweb.dto.MedicoDTO;
import br.unipar.hellows.appweb.exceptions.BusinessException;
import br.unipar.hellows.appweb.interfaces.MedicoWS;
import br.unipar.hellows.appweb.services.MedicoService;
import jakarta.jws.WebService;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.ArrayList;


@WebService
public class MedicoWSImp implements MedicoWS {

    @Override
    public Medico cadastrarMedico(MedicoDTO medicoDTO) throws BusinessException {
        Medico medico = new Medico(medicoDTO);
        MedicoService medicoService = new MedicoService();
        return medicoService.cadastrarMedico(medico);
    }

    @Override
    public ArrayList<Medico> buscarTodos() throws BusinessException, SQLException, NamingException {
        MedicoService medicoService = new MedicoService();
        return (ArrayList<Medico>) medicoService.listarPacientes();
    }

    @Override
    public Medico atualizarDadosMedico(MedicoDTO medicoDTO) throws BusinessException, SQLException, NamingException {
        Medico medico = new Medico(medicoDTO);
        MedicoService medicoService = new MedicoService();
        return medicoService.atualizarDados(medico.getId(), medico);
    }

    @Override
    public Medico excluirMedico(MedicoDTO medicoDTO) throws BusinessException, SQLException, NamingException {
        MedicoService medicoService = new MedicoService();
        return medicoService.desativarMedico(medicoDTO.getId());
    }
}
