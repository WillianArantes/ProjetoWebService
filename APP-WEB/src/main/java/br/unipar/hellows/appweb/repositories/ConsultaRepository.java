package br.unipar.hellows.appweb.repositories;

import br.unipar.hellows.appweb.domain.Consulta;
import br.unipar.hellows.appweb.infraestructure.ConnectionFactory;

import javax.naming.NamingException;
import java.sql.*;
import java.time.LocalTime;
    /*
    @WebMethod
    Medico cadastrarMedico (MedicoDTO medico) throws BusinessException;

    @WebMethod
    ArrayList<Medico> buscarTodos() throws BusinessException;

    @WebMethod
    Medico atualizarDadosMedico (MedicoDTO medico) throws BusinessException;

    @WebMethod
    Medico excluirMedico (MedicoDTO medico) throws BusinessException

      private Integer id;
    private Paciente paciente;
    private Medico medico;
    private Date dataCadastro;
    private LocalTime horaCadastro;
*/

public class ConsultaRepository {

    private static final String INSERIR = "INSERT INTO consultas (paciente_id, medico_id, data_cadastro, hora_cadastro) " +
            "VALUES (?, ?, ?, ?)";
    private static final String CONSULTA_EXISTENTE_PACIENTE = "SELECT COUNT(*) FROM consultas WHERE paciente_id = ?" +
            " AND data_cadastro = ?";
    private static final String CONSULTA_EXISTENTE_MEDICO = "SELECT COUNT(*) FROM consultas WHERE medico_id = ? " +
            "AND data_cadastro = ? AND hora_cadastro = ?";
    private static final String CANCELAR = "UPDATE consultas SET motivo_cancelamento = ? WHERE id = ?";

    public void agendarConsulta(Consulta consulta) throws SQLException, NamingException {
        try (Connection conn = new ConnectionFactory().getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERIR)) {

            stmt.setInt(1, consulta.getPaciente().getId());
            stmt.setInt(2, consulta.getMedico().getId());
            stmt.setDate(3, new java.sql.Date(consulta.getDataCadastro().getTime()));
            stmt.setTime(4, java.sql.Time.valueOf(consulta.getHoraCadastro()));

            stmt.executeUpdate();
        }
    }

    public boolean existeConsultaPacienteNoDia(int pacienteId, Date data) throws SQLException, NamingException {
        try (Connection conn = new ConnectionFactory().getConnection();
             PreparedStatement stmt = conn.prepareStatement(CONSULTA_EXISTENTE_PACIENTE)) {

            stmt.setInt(1, pacienteId);
            stmt.setDate(2, new java.sql.Date(data.getTime()));

            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }

    public boolean medicoOcupado(int medicoId, Date data, String hora) throws SQLException, NamingException {
        try (Connection conn = new ConnectionFactory().getConnection();
             PreparedStatement stmt = conn.prepareStatement(CONSULTA_EXISTENTE_MEDICO)) {

            stmt.setInt(1, medicoId);
            stmt.setDate(2, new java.sql.Date(data.getTime()));
            stmt.setTime(3, java.sql.Time.valueOf(LocalTime.parse(hora))); // Converte a string para LocalTime, depois para Time

            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }

    public void cancelarConsulta(int consultaId, String motivo) throws SQLException, NamingException {
        try (Connection conn = new ConnectionFactory().getConnection();
             PreparedStatement stmt = conn.prepareStatement(CANCELAR)) {

            stmt.setString(1, motivo);
            stmt.setInt(2, consultaId);

            stmt.executeUpdate();
        }
    }
}
