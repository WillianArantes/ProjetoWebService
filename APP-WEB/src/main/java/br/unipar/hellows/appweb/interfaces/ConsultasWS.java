package br.unipar.hellows.appweb.interfaces;

import br.unipar.hellows.appweb.dto.ConsultaDTO;
import br.unipar.hellows.appweb.exceptions.BusinessException;
import jakarta.jws.WebMethod;

import javax.naming.NamingException;
import java.sql.SQLException;

public interface ConsultasWS {

    @WebMethod
    void agendamentoConsulta(ConsultaDTO consultaDTO) throws BusinessException, SQLException, NamingException;

    @WebMethod
    void cancelamentoConsulta(Integer consultaId, String motivo) throws BusinessException, SQLException, NamingException;


}
