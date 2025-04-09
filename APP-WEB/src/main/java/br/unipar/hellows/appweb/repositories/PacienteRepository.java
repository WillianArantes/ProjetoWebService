package br.unipar.hellows.appweb.repositories;

import br.unipar.hellows.appweb.domain.Endereco;
import br.unipar.hellows.appweb.domain.Paciente;
import br.unipar.hellows.appweb.infraestructure.ConnectionFactory;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteRepository {

    private static final String CADASTRAR =
            "INSERT INTO paciente (nome, email, telefone, cpf, logradouro, numero, complemento, bairro, cidade," +
                    " uf, cep, ativo) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, TRUE)";

    private static final String LISTAR =
            "SELECT nome, email, cpf FROM paciente WHERE ativo = TRUE ORDER BY nome ASC";

    private static final String BUSCAR_POR_ID = "SELECT * FROM paciente WHERE id = ?";

    private static final String ATUALIZAR =
            "UPDATE paciente SET nome = ?, telefone = ?, logradouro = ?, numero = ?, complemento = ?, bairro = ?, " +
                    "cidade = ?, uf = ?, cep = ? WHERE id = ?";

    public void cadastrarPaciente(Paciente paciente) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();

            pstmt = conn.prepareStatement(CADASTRAR, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, paciente.getNome());
            pstmt.setString(2, paciente.getEmail());
            pstmt.setString(3, paciente.getTelefone());
            pstmt.setString(4, paciente.getCpf());
            pstmt.setString(5, paciente.getEndereco().getLogradouro());
            pstmt.setString(6, paciente.getEndereco().getNumero());
            pstmt.setString(7, paciente.getEndereco().getComplemento());
            pstmt.setString(8, paciente.getEndereco().getBairro());
            pstmt.setString(9, paciente.getEndereco().getCidade());
            pstmt.setString(10, paciente.getEndereco().getCep());

            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                paciente.setId(rs.getInt(1));
            }
        } finally {
            if (pstmt != null) pstmt.close();
            if (rs != null) rs.close();
            if (conn != null) conn.close();
        }
    }

    public List<Paciente> listar() throws SQLException, NamingException {
        List<Paciente> pacientes = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();
            pstmt = conn.prepareStatement(LISTAR);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Paciente paciente = new Paciente();
                paciente.setNome(rs.getString("nome"));
                paciente.setEmail(rs.getString("email"));
                paciente.setCpf(rs.getString("cpf"));
                pacientes.add(paciente);
            }
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }

        return pacientes;
    }

    public Paciente buscarPorId(int id) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Paciente paciente = null;

        try {
            conn = new ConnectionFactory().getConnection();
            pstmt = conn.prepareStatement(BUSCAR_POR_ID);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                paciente = new Paciente();
                paciente.setId(rs.getInt("id"));
                paciente.setNome(rs.getString("nome"));
                paciente.setEmail(rs.getString("email"));
                paciente.setTelefone(rs.getString("telefone"));
                paciente.setCpf(rs.getString("cpf"));

                Endereco endereco = new Endereco();
                endereco.setLogradouro(rs.getString("logradouro"));
                endereco.setNumero(rs.getString("numero"));
                endereco.setComplemento(rs.getString("complemento"));
                endereco.setBairro(rs.getString("bairro"));
                endereco.setCidade(rs.getString("cidade"));
                endereco.setUf(rs.getString("uf"));
                endereco.setCep(rs.getString("cep"));
                paciente.setEndereco(endereco);
            }
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }

        return paciente;
    }
    public void atualizar(Paciente paciente) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = new ConnectionFactory().getConnection();
            pstmt = conn.prepareStatement(ATUALIZAR);
            pstmt.setString(1, paciente.getNome());
            pstmt.setString(2, paciente.getTelefone());
            pstmt.setString(3, paciente.getEndereco().getLogradouro());
            pstmt.setString(4, paciente.getEndereco().getNumero());
            pstmt.setString(5, paciente.getEndereco().getComplemento());
            pstmt.setString(6, paciente.getEndereco().getBairro());
            pstmt.setString(7, paciente.getEndereco().getCidade());
            pstmt.setString(8, paciente.getEndereco().getUf());
            pstmt.setString(9, paciente.getEndereco().getCep());
            pstmt.setInt(10, paciente.getId());

            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }

    }
    public void desativarPaciente(int id) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = new ConnectionFactory().getConnection();
            pstmt = conn.prepareStatement("UPDATE paciente SET ativo = FALSE WHERE id = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }
}
