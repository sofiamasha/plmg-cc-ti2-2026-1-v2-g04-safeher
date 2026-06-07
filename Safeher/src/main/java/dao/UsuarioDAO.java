package dao;

import model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO extends ConexaoDAO {

    public UsuarioDAO() {
        executarMigracao();
    }

    private void executarMigracao() {
        String sql = "ALTER TABLE Usuario ADD COLUMN IF NOT EXISTS foto TEXT";
        try {
            abrirConexao();
            Statement stmt = getConexao().createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            System.err.println("Erro ao rodar migracao da tabela Usuario: " + e.getMessage());
        } finally {
            try {
                fecharConexao();
            } catch (Exception ex) {}
        }
    }

    public void insert(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO Usuario (id, nome, email, senha, data, foto) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setInt(1, usuario.getId());
            stmt.setString(2, usuario.getNome());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getSenha());
            stmt.setDate(5, Date.valueOf(usuario.getData()));
            stmt.setString(6, usuario.getFoto());
            stmt.executeUpdate();
            stmt.close();
        } finally {
            fecharConexao();
        }
    }

    public void update(Usuario usuario) throws SQLException {
        String sql = "UPDATE Usuario SET nome=?, email=?, senha=?, data=?, foto=? WHERE id=?";
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setDate(4, Date.valueOf(usuario.getData()));
            stmt.setString(5, usuario.getFoto());
            stmt.setInt(6, usuario.getId());
            stmt.executeUpdate();
            stmt.close();
        } finally {
            fecharConexao();
        }
    }

    public void remove(int id) throws SQLException {
        String sql = "DELETE FROM Usuario WHERE id=?";
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

    public Usuario get(int id) throws SQLException {
        String sql = "SELECT * FROM Usuario WHERE id=?";
        Usuario usuario = null;
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setData(rs.getDate("data").toLocalDate());
                usuario.setFoto(rs.getString("foto"));
            }
            rs.close();
            stmt.close();
        } finally {
            fecharConexao();
        }
        return usuario;
    }

    public List<Usuario> listar() throws SQLException {
        String sql = "SELECT * FROM Usuario";
        List<Usuario> lista = new ArrayList<>();
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setData(rs.getDate("data").toLocalDate());
                usuario.setFoto(rs.getString("foto"));
                lista.add(usuario);
            }
            rs.close();
            stmt.close();
        } finally {
            fecharConexao();
        }
        return lista;
    }
}
