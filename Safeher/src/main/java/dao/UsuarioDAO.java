package dao;

import model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO extends ConexaoDAO {

    public void insert(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO Usuario (id, nome, email, senha, data) VALUES (?, ?, ?, ?, ?)";
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setInt(1, usuario.getId());
            stmt.setString(2, usuario.getNome());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getSenha());
            stmt.setDate(5, Date.valueOf(usuario.getData()));
            stmt.executeUpdate();
            stmt.close();
        } finally {
            fecharConexao();
        }
    }

    public void update(Usuario usuario) throws SQLException {
        String sql = "UPDATE Usuario SET nome=?, email=?, senha=?, data=? WHERE id=?";
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setDate(4, Date.valueOf(usuario.getData()));
            stmt.setInt(5, usuario.getId());
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
