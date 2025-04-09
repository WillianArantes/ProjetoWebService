package br.unipar.hellows.appweb.interfaces;

import br.unipar.hellows.appweb.domain.Paciente;
import br.unipar.hellows.appweb.dto.PacienteDTO;
import br.unipar.hellows.appweb.exceptions.BusinessException;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebService
public interface PacienteWS {

    @WebMethod
    Paciente cadastrarPaciente(PacienteDTO paciente) throws BusinessException, SQLException, NamingException;

    @WebMethod
    ArrayList<Paciente> buscarTodos() throws BusinessException, SQLException, NamingException;

    @WebMethod
    Paciente atualizarDadosPaciente(PacienteDTO paciente) throws BusinessException, SQLException, NamingException;

    @WebMethod
    Paciente excluirPaciente (PacienteDTO paciente) throws BusinessException, SQLException, NamingException;

}
