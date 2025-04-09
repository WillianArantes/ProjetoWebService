package br.unipar.hellows.appweb.repositories;


import br.unipar.hellows.appweb.domain.Endereco;
import br.unipar.hellows.appweb.domain.Medico;
import br.unipar.hellows.appweb.infraestructure.ConnectionFactory;

import javax.naming.NamingException;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MedicoRepository {

    private static final String CADASTRAR =
            "INSERT INTO medico (nome, email, telefone, crm, especialidade, logradouro, numero, complemento, bairro, ativo) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, TRUE)";

    private static final String LISTAR =
            "SELECT nome, email, crm, especialidade FROM medico WHERE ativo = TRUE ORDER BY nome ASC";

    private static final String BUSCAR_DADOS_POR_ID = "SELECT * FROM medico WHERE id = ?";

    private static final String UPDATE = "UPDATE medico SET nome = ?, telefone = ?, logradouro = ?, numero = ?," +
            " complemento = ?, bairro = ? WHERE id = ?";



    private static final String DESATIVAR =
            "UPDATE medico SET ativo = FALSE WHERE id = ?";
    private static final String BUSCAR_MEDICOS_DISPONIVEIS = """
        SELECT m.* FROM medico m
        WHERE m.ativo = TRUE
        AND NOT EXISTS (
            SELECT 1 FROM consultas c
            WHERE c.medico_id = m.id
            AND c.data_cadastro = ?
            AND c.hora_cadastro = ?
        )
    """;

    public void cadastrarMedico(Medico medico) throws SQLException, NamingException {


        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();
            pstmt = conn.prepareStatement(CADASTRAR, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, medico.getNome());
            pstmt.setString(2, medico.getEmail());
            pstmt.setString(3, medico.getTelefone());
            pstmt.setString(4, medico.getCrm());
            pstmt.setString(5, medico.getEspecialidade());
            pstmt.setString(6, medico.getEndereco().getLogradouro());
            pstmt.setString(7, medico.getEndereco().getNumero());
            pstmt.setString(8, medico.getEndereco().getComplemento());
            pstmt.setString(9, medico.getEndereco().getBairro());

            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                medico.setId(rs.getInt(1));
            }
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    public Medico buscarDadosPorId(int id) throws SQLException, NamingException {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Medico medico = null;

        try {
            conn = new ConnectionFactory().getConnection();
            pstmt = conn.prepareStatement(BUSCAR_DADOS_POR_ID);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                medico = new Medico();
                medico.setId(rs.getInt("id"));
                medico.setNome(rs.getString("nome"));
                medico.setEmail(rs.getString("email"));
                medico.setTelefone(rs.getString("telefone"));
                medico.setCrm(rs.getString("crm"));
                medico.setEspecialidade(rs.getString("especialidade"));

                Endereco endereco = new Endereco();
                endereco.setLogradouro(rs.getString("logradouro"));
                endereco.setNumero(rs.getString("numero"));
                endereco.setComplemento(rs.getString("complemento"));
                endereco.setBairro(rs.getString("bairro"));
                medico.setEndereco(endereco);
            }
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }

        return medico;
    }

    public List<Medico> buscarMedicosDisponiveis(Date data, String hora) throws SQLException, NamingException {
        List<Medico> medicos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();
            pstmt = conn.prepareStatement(BUSCAR_MEDICOS_DISPONIVEIS);

            pstmt.setDate(1, new java.sql.Date(data.getTime()));
            pstmt.setTime(2, java.sql.Time.valueOf(LocalTime.parse(hora))); // Converte a String "HH:mm"

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Medico medico = new Medico();
                medico.setId(rs.getInt("id"));
                medico.setNome(rs.getString("nome"));
                medico.setEmail(rs.getString("email"));
                medico.setTelefone(rs.getString("telefone"));
                medico.setCrm(rs.getString("crm"));
                medico.setEspecialidade(rs.getString("especialidade"));
                medico.setAtivo(rs.getBoolean("ativo"));

                // Se quiser popular endereço, coloque aqui

                medicos.add(medico);
            }

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }

        return medicos;
    }


    public void atualizar(Medico medico) throws SQLException, NamingException {
    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
        conn = new ConnectionFactory().getConnection();
        pstmt = conn.prepareStatement(
                UPDATE);


        pstmt.setString(1, medico.getNome());
        pstmt.setString(2, medico.getTelefone());
        pstmt.setString(3, medico.getEndereco().getLogradouro());
        pstmt.setString(4, medico.getEndereco().getNumero());
        pstmt.setString(5, medico.getEndereco().getComplemento());
        pstmt.setString(6, medico.getEndereco().getBairro());
        pstmt.setInt(7, medico.getId());

        pstmt.executeUpdate();
    } finally {
        if (pstmt != null) pstmt.close();
        if (conn != null) conn.close();
    }
}

    public List<Medico> listarMedicos () throws SQLException, NamingException {


        List<Medico> medicos = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();
            pstmt = conn.prepareStatement(LISTAR);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Medico medico = new Medico();
                medico.setNome(rs.getString("nome"));
                medico.setEmail(rs.getString("email"));
                medico.setCrm(rs.getString("crm"));
                medico.setEspecialidade(rs.getString("especialidade"));
                medicos.add(medico);
            }
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
        return medicos;

    }

    public void desativarMedico ( int id) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = new ConnectionFactory().getConnection();
            pstmt = conn.prepareStatement(DESATIVAR);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }

    }
}
