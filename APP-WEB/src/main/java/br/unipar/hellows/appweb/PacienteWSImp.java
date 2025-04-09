package br.unipar.hellows.appweb;

import br.unipar.hellows.appweb.domain.Paciente;
import br.unipar.hellows.appweb.dto.PacienteDTO;
import br.unipar.hellows.appweb.exceptions.BusinessException;
import br.unipar.hellows.appweb.interfaces.PacienteWS;
import br.unipar.hellows.appweb.services.PacienteService;
import jakarta.jws.WebService;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebService
public class PacienteWSImp implements PacienteWS {

    @Override
    public Paciente cadastrarPaciente(PacienteDTO pacienteDTO) throws BusinessException, SQLException, NamingException {
        Paciente paciente = new Paciente(pacienteDTO);
        PacienteService pacienteService = new PacienteService();
        return pacienteService.cadastrarPaciente(paciente);
    }

    @Override
    public ArrayList<Paciente> buscarTodos() throws BusinessException, SQLException, NamingException {
        PacienteService pacienteService = new PacienteService();
        return (ArrayList<Paciente>) pacienteService.listarPacientes();
    }

    @Override
    public Paciente atualizarDadosPaciente(PacienteDTO pacienteDTO) throws BusinessException, SQLException,
            NamingException {
        Paciente paciente = new Paciente(pacienteDTO);
        PacienteService pacienteService = new PacienteService();
        return pacienteService.atualizarPaciente(paciente);
    }

    @Override
    public Paciente excluirPaciente(PacienteDTO pacienteDTO) throws BusinessException, SQLException, NamingException {
        PacienteService pacienteService = new PacienteService();
        return pacienteService.desativarPaciente(pacienteDTO.getId());
    }
}
