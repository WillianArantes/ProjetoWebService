package br.unipar.hellows.appweb.interfaces;

import br.unipar.hellows.appweb.domain.Medico;
import br.unipar.hellows.appweb.dto.MedicoDTO;
import br.unipar.hellows.appweb.exceptions.BusinessException;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebService
public interface MedicoWS {

    @WebMethod
    Medico cadastrarMedico (MedicoDTO medico) throws BusinessException;

    @WebMethod
    ArrayList<Medico> buscarTodos() throws BusinessException, SQLException, NamingException;

    @WebMethod
    Medico atualizarDadosMedico (MedicoDTO medico) throws BusinessException, SQLException, NamingException;

    @WebMethod
    Medico excluirMedico (MedicoDTO medico) throws BusinessException, SQLException, NamingException;


}
