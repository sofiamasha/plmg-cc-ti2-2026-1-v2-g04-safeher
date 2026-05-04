package dao;

import model.Denuncia;

import java.sql.*;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAO responsável pelas operações de banco de dados da entidade Denuncia.
 */
public class DenunciaDAO extends ConexaoDAO {

    /**
     * Insere uma nova denúncia no banco de dados.
     *
     * @param denuncia objeto Denuncia a ser inserido
     * @throws SQLException em caso de erro no banco
     */
    public void insert(Denuncia denuncia) throws SQLException {
        String sql = "INSERT INTO Denuncia (id, descricao, data, anonima, Usuario_id, Empresa_id) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setInt(1, denuncia.getId());
            stmt.setString(2, denuncia.getDescricao());
            stmt.setDate(3, Date.valueOf(denuncia.getData()));
            stmt.setBoolean(4, denuncia.isAnonima());
            stmt.setInt(5, denuncia.getUsuarioId());
            if (denuncia.getEmpresaId() == 0) {
                stmt.setNull(6, Types.INTEGER);
            } else {
                stmt.setInt(6, denuncia.getEmpresaId());
            }
            stmt.executeUpdate();
            stmt.close();
        } finally {
            fecharConexao();
        }
    }

    /**
     * Atualiza os dados de uma denúncia existente.
     *
     * @param denuncia objeto Denuncia com os dados atualizados
     * @throws SQLException em caso de erro no banco
     */
    public void update(Denuncia denuncia) throws SQLException {
        String sql = "UPDATE Denuncia SET descricao=?, data=?, anonima=?, Usuario_id=?, Empresa_id=? WHERE id=?";
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setString(1, denuncia.getDescricao());
            stmt.setDate(2, Date.valueOf(denuncia.getData()));
            stmt.setBoolean(3, denuncia.isAnonima());
            stmt.setInt(4, denuncia.getUsuarioId());
            stmt.setInt(5, denuncia.getEmpresaId());
            stmt.setInt(6, denuncia.getId());
            stmt.executeUpdate();
            stmt.close();
        } finally {
            fecharConexao();
        }
    }

    /**
     * Remove uma denúncia pelo ID.
     *
     * @param id identificador da denúncia a ser removida
     * @throws SQLException em caso de erro no banco
     */
    public void remove(int id) throws SQLException {
        String sql = "DELETE FROM Denuncia WHERE id=?";
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
     * Busca uma denúncia pelo ID.
     *
     * @param id identificador da denúncia
     * @return Denuncia encontrada ou null
     * @throws SQLException em caso de erro no banco
     */
    public Denuncia get(int id) throws SQLException {
        String sql = "SELECT * FROM Denuncia WHERE id=?";
        Denuncia denuncia = null;
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                denuncia = new Denuncia();
                denuncia.setId(rs.getInt("id"));
                denuncia.setDescricao(rs.getString("descricao"));
                denuncia.setData(rs.getDate("data").toLocalDate());
                denuncia.setAnonima(rs.getBoolean("anonima"));
                denuncia.setUsuarioId(rs.getInt("Usuario_id"));
                denuncia.setEmpresaId(rs.getInt("Empresa_id"));
            }
            rs.close();
            stmt.close();
        } finally {
            fecharConexao();
        }
        return denuncia;
    }

    /**
     * Lista todas as denúncias cadastradas.
     *
     * @return List com todas as denúncias
     * @throws SQLException em caso de erro no banco
     */
    public List<Denuncia> listar() throws SQLException {
        String sql = "SELECT * FROM Denuncia";
        List<Denuncia> lista = new ArrayList<>();
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Denuncia denuncia = new Denuncia();
                denuncia.setId(rs.getInt("id"));
                denuncia.setDescricao(rs.getString("descricao"));
                denuncia.setData(rs.getDate("data").toLocalDate());
                denuncia.setAnonima(rs.getBoolean("anonima"));
                denuncia.setUsuarioId(rs.getInt("Usuario_id"));
                denuncia.setEmpresaId(rs.getInt("Empresa_id"));
                lista.add(denuncia);
            }
            rs.close();
            stmt.close();
        } finally {
            fecharConexao();
        }
        return lista;
    }

    /**
     * Lista denúncias de um usuário específico.
     *
     * @param usuarioId identificador do usuário
     * @return List com as denúncias do usuário
     * @throws SQLException em caso de erro no banco
     */
    public List<Denuncia> listarPorUsuario(int usuarioId) throws SQLException {
        String sql = "SELECT * FROM Denuncia WHERE Usuario_id=?";
        List<Denuncia> lista = new ArrayList<>();
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Denuncia denuncia = new Denuncia();
                denuncia.setId(rs.getInt("id"));
                denuncia.setDescricao(rs.getString("descricao"));
                denuncia.setData(rs.getDate("data").toLocalDate());
                denuncia.setAnonima(rs.getBoolean("anonima"));
                denuncia.setUsuarioId(rs.getInt("Usuario_id"));
                denuncia.setEmpresaId(rs.getInt("Empresa_id"));
                lista.add(denuncia);
            }
            rs.close();
            stmt.close();
        } finally {
            fecharConexao();
        }
        return lista;
    }

    public List<Denuncia> listarPorEmpresa(int empresaId) throws SQLException {
        String sql = "SELECT * FROM Denuncia WHERE Empresa_id=?";
        List<Denuncia> lista = new ArrayList<>();
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setInt(1, empresaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Denuncia denuncia = new Denuncia();
                denuncia.setId(rs.getInt("id"));
                denuncia.setDescricao(rs.getString("descricao"));
                denuncia.setData(rs.getDate("data").toLocalDate());
                denuncia.setAnonima(rs.getBoolean("anonima"));
                denuncia.setUsuarioId(rs.getInt("Usuario_id"));
                denuncia.setEmpresaId(rs.getInt("Empresa_id"));
                lista.add(denuncia);
            }
            rs.close();
            stmt.close();
        } finally {
            fecharConexao();
        }
        return lista;
    }
}
