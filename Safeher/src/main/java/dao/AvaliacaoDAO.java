package dao;

import model.Avaliacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoDAO extends ConexaoDAO {

    public void insert(Avaliacao avaliacao) throws SQLException {
        // REMOVIDO o 'id' do INSERT para o PostgreSQL gerar sozinho (SERIAL)
        String sql = "INSERT INTO Avaliacao (comentario, nota, usuario_id, empresa_id, nome_empresa) VALUES (?, ?, ?, ?, ?)";
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
            stmt.executeUpdate();
            stmt.close();
        } finally {
            fecharConexao();
        }
    }

    public void update(Avaliacao avaliacao) throws SQLException {
        String sql = "UPDATE Avaliacao SET comentario=?, nota=?, usuario_id=?, empresa_id=?, nome_empresa=? WHERE id=?";
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
                avaliacao.setUsuarioId(rs.getInt("usuario_id"));
                avaliacao.setEmpresaId(rs.getInt("empresa_id"));
                avaliacao.setNomeEmpresa(rs.getString("nome_empresa"));
            }
            rs.close();
            stmt.close();
        } finally {
            fecharConexao();
        }
        return avaliacao;
    }

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
                avaliacao.setUsuarioId(rs.getInt("usuario_id"));
                avaliacao.setEmpresaId(rs.getInt("empresa_id"));
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
        String sql = "SELECT * FROM Avaliacao WHERE empresa_id=?"; 
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
                avaliacao.setUsuarioId(rs.getInt("usuario_id"));
                avaliacao.setEmpresaId(rs.getInt("empresa_id"));
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