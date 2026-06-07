package dao;

import model.Vaga;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VagaDAO extends ConexaoDAO {

    public VagaDAO() {
        executarMigracao();
    }

    private void executarMigracao() {
        String sql = "CREATE TABLE IF NOT EXISTS Vaga (" +
                     "    id SERIAL PRIMARY KEY," +
                     "    titulo VARCHAR(150)," +
                     "    empresa VARCHAR(150)," +
                     "    local VARCHAR(100)," +
                     "    tipo VARCHAR(50)," +
                     "    salario VARCHAR(100)," +
                     "    descricao TEXT," +
                     "    tags VARCHAR(200)," +
                     "    selo BOOLEAN DEFAULT false," +
                     "    empresa_id INT," +
                     "    FOREIGN KEY (empresa_id) REFERENCES Empresa(id) ON DELETE CASCADE" +
                     ")";
        try {
            abrirConexao();
            Statement stmt = getConexao().createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            System.err.println("Erro ao rodar migracao da tabela Vaga: " + e.getMessage());
        } finally {
            try {
                fecharConexao();
            } catch (Exception ex) {}
        }
    }

    public void insert(Vaga vaga) throws SQLException {
        String sql = "INSERT INTO Vaga (titulo, empresa, local, tipo, salario, descricao, tags, selo, empresa_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setString(1, vaga.getTitulo());
            stmt.setString(2, vaga.getEmpresa());
            stmt.setString(3, vaga.getLocal());
            stmt.setString(4, vaga.getTipo());
            stmt.setString(5, vaga.getSalario());
            stmt.setString(6, vaga.getDescricao());
            stmt.setString(7, vaga.getTags());
            stmt.setBoolean(8, vaga.isSelo());
            stmt.setInt(9, vaga.getEmpresaId());
            stmt.executeUpdate();
            stmt.close();
        } finally {
            fecharConexao();
        }
    }

    public void remove(int id) throws SQLException {
        String sql = "DELETE FROM Vaga WHERE id=?";
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

    public Vaga get(int id) throws SQLException {
        String sql = "SELECT * FROM Vaga WHERE id=?";
        Vaga vaga = null;
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                vaga = new Vaga();
                vaga.setId(rs.getInt("id"));
                vaga.setTitulo(rs.getString("titulo"));
                vaga.setEmpresa(rs.getString("empresa"));
                vaga.setLocal(rs.getString("local"));
                vaga.setTipo(rs.getString("tipo"));
                vaga.setSalario(rs.getString("salario"));
                vaga.setDescricao(rs.getString("descricao"));
                vaga.setTags(rs.getString("tags"));
                vaga.setSelo(rs.getBoolean("selo"));
                vaga.setEmpresaId(rs.getInt("empresa_id"));
            }
            rs.close();
            stmt.close();
        } finally {
            fecharConexao();
        }
        return vaga;
    }

    public List<Vaga> listar() throws SQLException {
        String sql = "SELECT * FROM Vaga ORDER BY id DESC";
        List<Vaga> lista = new ArrayList<>();
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Vaga vaga = new Vaga();
                vaga.setId(rs.getInt("id"));
                vaga.setTitulo(rs.getString("titulo"));
                vaga.setEmpresa(rs.getString("empresa"));
                vaga.setLocal(rs.getString("local"));
                vaga.setTipo(rs.getString("tipo"));
                vaga.setSalario(rs.getString("salario"));
                vaga.setDescricao(rs.getString("descricao"));
                vaga.setTags(rs.getString("tags"));
                vaga.setSelo(rs.getBoolean("selo"));
                vaga.setEmpresaId(rs.getInt("empresa_id"));
                lista.add(vaga);
            }
            rs.close();
            stmt.close();
        } finally {
            fecharConexao();
        }
        return lista;
    }

    public List<Vaga> listarPorEmpresa(int empresaId) throws SQLException {
        String sql = "SELECT * FROM Vaga WHERE empresa_id=? ORDER BY id DESC";
        List<Vaga> lista = new ArrayList<>();
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setInt(1, empresaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Vaga vaga = new Vaga();
                vaga.setId(rs.getInt("id"));
                vaga.setTitulo(rs.getString("titulo"));
                vaga.setEmpresa(rs.getString("empresa"));
                vaga.setLocal(rs.getString("local"));
                vaga.setTipo(rs.getString("tipo"));
                vaga.setSalario(rs.getString("salario"));
                vaga.setDescricao(rs.getString("descricao"));
                vaga.setTags(rs.getString("tags"));
                vaga.setSelo(rs.getBoolean("selo"));
                vaga.setEmpresaId(rs.getInt("empresa_id"));
                lista.add(vaga);
            }
            rs.close();
            stmt.close();
        } finally {
            fecharConexao();
        }
        return lista;
    }
}
