package dao;

import model.Denuncia;

import java.sql.*;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class DenunciaDAO extends ConexaoDAO {

    public DenunciaDAO() {
        executarMigracao();
    }

    private void executarMigracao() {
        try {
            abrirConexao();
            Statement stmt = getConexao().createStatement();
            stmt.executeUpdate("ALTER TABLE Denuncia ADD COLUMN IF NOT EXISTS status VARCHAR(50) DEFAULT 'Pendente'");
            stmt.executeUpdate("ALTER TABLE Denuncia ADD COLUMN IF NOT EXISTS planoAcao TEXT DEFAULT ''");
            stmt.close();
        } catch (Exception e) {
            System.err.println("Erro ao rodar migracao da tabela Denuncia: " + e.getMessage());
        } finally {
            try {
                fecharConexao();
            } catch (Exception ex) {}
        }
    }

    public void insert(Denuncia denuncia) throws SQLException {
        String sql = "INSERT INTO Denuncia (descricao, data, anonima, Usuario_id, Empresa_id, score, status, planoAcao) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setString(1, denuncia.getDescricao());
            stmt.setDate(2, Date.valueOf(denuncia.getData()));
            stmt.setBoolean(3, denuncia.isAnonima());
            stmt.setInt(4, denuncia.getUsuarioId());
            if (denuncia.getEmpresaId() == 0) {
                stmt.setNull(5, Types.INTEGER);
            } else {
                stmt.setInt(5, denuncia.getEmpresaId());
            }
            stmt.setInt(6, denuncia.getScore());
            stmt.setString(7, denuncia.getStatus() != null ? denuncia.getStatus() : "Pendente");
            stmt.setString(8, denuncia.getPlanoAcao() != null ? denuncia.getPlanoAcao() : "");
            stmt.executeUpdate();
            stmt.close();
        } finally {
            fecharConexao();
        }
    }

    public void update(Denuncia denuncia) throws SQLException {
        String sql = "UPDATE Denuncia SET descricao=?, data=?, anonima=?, Usuario_id=?, Empresa_id=?, score=?, status=?, planoAcao=? WHERE id=?";
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setString(1, denuncia.getDescricao());
            stmt.setDate(2, Date.valueOf(denuncia.getData()));
            stmt.setBoolean(3, denuncia.isAnonima());
            stmt.setInt(4, denuncia.getUsuarioId());
            stmt.setInt(5, denuncia.getEmpresaId());
            stmt.setInt(6, denuncia.getScore());
            stmt.setString(7, denuncia.getStatus() != null ? denuncia.getStatus() : "Pendente");
            stmt.setString(8, denuncia.getPlanoAcao() != null ? denuncia.getPlanoAcao() : "");
            stmt.setInt(9, denuncia.getId());
            stmt.executeUpdate();
            stmt.close();
        } finally {
            fecharConexao();
        }
    }

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
                denuncia.setScore(rs.getInt("score"));
                denuncia.setStatus(rs.getString("status"));
                denuncia.setPlanoAcao(rs.getString("planoAcao"));
            }
            rs.close();
            stmt.close();
        } finally {
            fecharConexao();
        }
        return denuncia;
    }

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
                denuncia.setScore(rs.getInt("score"));
                denuncia.setStatus(rs.getString("status"));
                denuncia.setPlanoAcao(rs.getString("planoAcao"));
                lista.add(denuncia);
            }
            rs.close();
            stmt.close();
        } finally {
            fecharConexao();
        }
        return lista;
    }

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
                denuncia.setScore(rs.getInt("score"));
                denuncia.setStatus(rs.getString("status"));
                denuncia.setPlanoAcao(rs.getString("planoAcao"));
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
                denuncia.setScore(rs.getInt("score"));
                denuncia.setStatus(rs.getString("status"));
                denuncia.setPlanoAcao(rs.getString("planoAcao"));
                lista.add(denuncia);
            }
            rs.close();
            stmt.close();
        } finally {
            fecharConexao();
        }
        return lista;
    }

    public List<Denuncia> listarOrdenadoPorRisco() throws SQLException {
        String sql = "SELECT * FROM Denuncia ORDER BY score DESC NULLS LAST, data DESC";
        List<Denuncia> lista = new ArrayList<>();
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Denuncia denuncia = new Denuncia();
                denuncia.setId(rs.getInt("id"));
                denuncia.setDescricao(rs.getString("descricao"));
                denuncia.setData(rs.getDate("data") != null ? rs.getDate("data").toLocalDate() : null);
                denuncia.setAnonima(rs.getBoolean("anonima"));
                denuncia.setUsuarioId(rs.getInt("Usuario_id"));
                denuncia.setEmpresaId(rs.getInt("Empresa_id"));
                denuncia.setScore(rs.getInt("score"));
                denuncia.setStatus(rs.getString("status"));
                denuncia.setPlanoAcao(rs.getString("planoAcao"));
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
