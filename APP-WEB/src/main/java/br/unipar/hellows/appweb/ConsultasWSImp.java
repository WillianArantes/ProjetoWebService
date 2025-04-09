package br.unipar.hellows.appweb;

import br.unipar.hellows.appweb.dto.ConsultaDTO;
import br.unipar.hellows.appweb.exceptions.BusinessException;
import br.unipar.hellows.appweb.interfaces.ConsultasWS;
import br.unipar.hellows.appweb.services.ConsultaService;
import jakarta.jws.WebService;

import javax.naming.NamingException;
import java.sql.SQLException;

@WebService
public class ConsultasWSImp implements ConsultasWS {

    private final ConsultaService consultaService = new ConsultaService();

    @Override
    public void agendamentoConsulta(ConsultaDTO consultaDTO) throws BusinessException, SQLException, NamingException {
        try {
            consultaService.agendarConsulta(consultaDTO);
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Erro ao agendar consulta: " + e.getMessage());
        }
    }

    @Override
    public void cancelamentoConsulta(Integer consultaId, String motivo) throws BusinessException, SQLException,
            NamingException {
        try {
            consultaService.cancelarConsulta(consultaId, motivo);
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Erro ao cancelar consulta: " + e.getMessage());
        }
    }
}
