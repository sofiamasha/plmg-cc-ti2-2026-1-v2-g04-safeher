package dao;

import model.Avaliacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoDAO extends ConexaoDAO {

    public AvaliacaoDAO() {
        executarMigracao();
    }

    private void executarMigracao() {
        String sql = "ALTER TABLE Avaliacao ADD COLUMN IF NOT EXISTS resposta TEXT";
        try {
            abrirConexao();
            Statement stmt = getConexao().createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            System.err.println("Erro ao rodar migracao da tabela Avaliacao: " + e.getMessage());
        } finally {
            try {
                fecharConexao();
            } catch (Exception ex) {}
        }
    }

    public void insert(Avaliacao avaliacao) throws SQLException {
        // REMOVIDO o 'id' do INSERT para o PostgreSQL gerar sozinho (SERIAL)
        String sql = "INSERT INTO Avaliacao (comentario, nota, usuario_id, empresa_id, nome_empresa, resposta) VALUES (?, ?, ?, ?, ?, ?)";
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
            stmt.setString(6, avaliacao.getResposta());
            stmt.executeUpdate();
            stmt.close();
        } finally {
            fecharConexao();
        }
    }

    public void update(Avaliacao avaliacao) throws SQLException {
        String sql = "UPDATE Avaliacao SET comentario=?, nota=?, usuario_id=?, empresa_id=?, nome_empresa=?, resposta=? WHERE id=?";
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
            stmt.setString(6, avaliacao.getResposta());
            stmt.setInt(7, avaliacao.getId());
            stmt.executeUpdate();
            stmt.close();
        } finally {
            fecharConexao();
        }
    }

    public void atualizarResposta(int id, String resposta) throws SQLException {
        String sql = "UPDATE Avaliacao SET resposta=? WHERE id=?";
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setString(1, resposta);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            stmt.close();
        } finally {
            fecharConexao();
        }
    }

    public void associarEmpresaIdPorNome(int empresaId, String nome) throws SQLException {
        String sql = "UPDATE Avaliacao SET empresa_id=? WHERE (empresa_id IS NULL OR empresa_id = 0) AND nome_empresa ILIKE ?";
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setInt(1, empresaId);
            stmt.setString(2, nome.trim());
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
                avaliacao.setResposta(rs.getString("resposta"));
            }
            rs.close();
            stmt.close();
        } finally {
            fecharConexao();
        }
        return avaliacao;
    }

    public List<Avaliacao> listar() throws SQLException {
        String sql = "SELECT * FROM Avaliacao ORDER BY id DESC";
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
                avaliacao.setResposta(rs.getString("resposta"));
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
                avaliacao.setResposta(rs.getString("resposta"));
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