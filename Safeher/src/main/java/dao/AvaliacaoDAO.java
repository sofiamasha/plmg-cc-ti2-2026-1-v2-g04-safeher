package dao;

import model.Avaliacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAO responsável pelas operações de banco de dados da entidade Avaliacao.
 */
public class AvaliacaoDAO extends ConexaoDAO {

    /**
     * Insere uma nova avaliação no banco de dados.
     *
     * @param avaliacao objeto Avaliacao a ser inserido
     * @throws SQLException em caso de erro no banco
     */
    public void insert(Avaliacao avaliacao) throws SQLException {
        String sql = "INSERT INTO Avaliacao (id, comentario, nota, Usuario_id, Empresa_id, nome_empresa) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setInt(1, avaliacao.getId());
            stmt.setString(2, avaliacao.getComentario());
            stmt.setInt(3, avaliacao.getNota());
            stmt.setInt(4, avaliacao.getUsuarioId());
            if (avaliacao.getEmpresaId() == 0) {
                stmt.setNull(5, Types.INTEGER);
            } else {
                stmt.setInt(5, avaliacao.getEmpresaId());
            }
            stmt.setString(6, avaliacao.getNomeEmpresa());
            stmt.executeUpdate();
            stmt.close();
        } finally {
            fecharConexao();
        }
    }

    /**
     * Atualiza os dados de uma avaliação existente.
     *
     * @param avaliacao objeto Avaliacao com os dados atualizados
     * @throws SQLException em caso de erro no banco
     */
    public void update(Avaliacao avaliacao) throws SQLException {
        String sql = "UPDATE Avaliacao SET comentario=?, nota=?, Usuario_id=?, Empresa_id=?, nome_empresa=? WHERE id=?";
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setString(1, avaliacao.getComentario());
            stmt.setInt(2, avaliacao.getNota());
            stmt.setInt(3, avaliacao.getUsuarioId());
            if (avaliacao.getEmpresaId() == 0) {
                stmt.setNull(4, Types.INTEGER);
            } else {
                stmt.setInt(4, avaliacao.getEmpresaId());
            }
            stmt.setString(5, avaliacao.getNomeEmpresa());
            stmt.setInt(6, avaliacao.getId());
            stmt.executeUpdate();
            stmt.close();
        } finally {
            fecharConexao();
        }
    }

    /**
     * Remove uma avaliação pelo ID.
     *
     * @param id identificador da avaliação a ser removida
     * @throws SQLException em caso de erro no banco
     */
    public void remove(int id) throws SQLException {
        String sql = "DELETE FROM Avaliacao WHERE id=?";
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();
        } finally {
            fecharConexao();
        }
    }

    /**
     * Busca uma avaliação pelo ID.
     *
     * @param id identificador da avaliação
     * @return Avaliacao encontrada ou null
     * @throws SQLException em caso de erro no banco
     */
    public Avaliacao get(int id) throws SQLException {
        String sql = "SELECT * FROM Avaliacao WHERE id=?";
        Avaliacao avaliacao = null;
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                avaliacao = new Avaliacao();
                avaliacao.setId(rs.getInt("id"));
                avaliacao.setComentario(rs.getString("comentario"));
                avaliacao.setNota(rs.getInt("nota"));
                avaliacao.setUsuarioId(rs.getInt("Usuario_id"));
                avaliacao.setEmpresaId(rs.getInt("Empresa_id"));
                avaliacao.setNomeEmpresa(rs.getString("nome_empresa"));
            }
            rs.close();
            stmt.close();
        } finally {
            fecharConexao();
        }
        return avaliacao;
    }

    /**
     * Lista todas as avaliações cadastradas.
     *
     * @return List com todas as avaliações
     * @throws SQLException em caso de erro no banco
     */
    public List<Avaliacao> listar() throws SQLException {
        String sql = "SELECT * FROM Avaliacao";
        List<Avaliacao> lista = new ArrayList<>();
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Avaliacao avaliacao = new Avaliacao();
                avaliacao.setId(rs.getInt("id"));
                avaliacao.setComentario(rs.getString("comentario"));
                avaliacao.setNota(rs.getInt("nota"));
                avaliacao.setUsuarioId(rs.getInt("Usuario_id"));
                avaliacao.setEmpresaId(rs.getInt("Empresa_id"));
                avaliacao.setNomeEmpresa(rs.getString("nome_empresa"));
                lista.add(avaliacao);
            }
            rs.close();
            stmt.close();
        } finally {
            fecharConexao();
        }
        return lista;
    }

    public List<Avaliacao> listarPorEmpresa(int empresaId) throws SQLException {
        String sql = "SELECT * FROM Avaliacao WHERE Empresa_id=?";
        List<Avaliacao> lista = new ArrayList<>();
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setInt(1, empresaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Avaliacao avaliacao = new Avaliacao();
                avaliacao.setId(rs.getInt("id"));
                avaliacao.setComentario(rs.getString("comentario"));
                avaliacao.setNota(rs.getInt("nota"));
                avaliacao.setUsuarioId(rs.getInt("Usuario_id"));
                avaliacao.setEmpresaId(rs.getInt("Empresa_id"));
                avaliacao.setNomeEmpresa(rs.getString("nome_empresa"));
                lista.add(avaliacao);
            }
            rs.close();
            stmt.close();
        } finally {
            fecharConexao();
        }
        return lista;
    }
}
