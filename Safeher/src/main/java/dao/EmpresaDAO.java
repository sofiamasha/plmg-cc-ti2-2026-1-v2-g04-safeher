package dao;

import model.Empresa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDAO extends ConexaoDAO {

    public void insert(Empresa empresa) throws SQLException {
        String sql = "INSERT INTO Empresa (id, nome, indice, cnpj, cep, endereco, email, telefone, senha) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setInt(1, empresa.getId());
            stmt.setString(2, empresa.getNome());
            stmt.setBigDecimal(3, empresa.getIndice());
            stmt.setString(4, empresa.getCnpj());
            stmt.setString(5, empresa.getCep());
            stmt.setString(6, empresa.getEndereco());
            stmt.setString(7, empresa.getEmail());
            stmt.setString(8, empresa.getTelefone());
            stmt.setString(9, empresa.getSenha());
            stmt.executeUpdate();
            stmt.close();
        } finally {
            fecharConexao();
        }
    }

    public void update(Empresa empresa) throws SQLException {
        String sql = "UPDATE Empresa SET nome=?, indice=?, cnpj=?, cep=?, endereco=?, email=?, telefone=? WHERE id=?";
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setString(1, empresa.getNome());
            stmt.setBigDecimal(2, empresa.getIndice());
            stmt.setString(3, empresa.getCnpj());
            stmt.setString(4, empresa.getCep());
            stmt.setString(5, empresa.getEndereco());
            stmt.setString(6, empresa.getEmail());
            stmt.setString(7, empresa.getTelefone());
            stmt.setInt(8, empresa.getId());
            stmt.executeUpdate();
            stmt.close();
        } finally {
            fecharConexao();
        }
    }

    public void remove(int id) throws SQLException {
        String sql = "DELETE FROM Empresa WHERE id=?";
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

    public Empresa get(int id) throws SQLException {
        String sql = "SELECT * FROM Empresa WHERE id=?";
        Empresa empresa = null;
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                empresa = new Empresa();
                empresa.setId(rs.getInt("id"));
                empresa.setNome(rs.getString("nome"));
                empresa.setIndice(rs.getBigDecimal("indice"));
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setCep(rs.getString("cep"));
                empresa.setEndereco(rs.getString("endereco"));
                empresa.setEmail(rs.getString("email"));
                empresa.setTelefone(rs.getString("telefone"));
                empresa.setSenha(rs.getString("senha"));
            }
            rs.close();
            stmt.close();
        } finally {
            fecharConexao();
        }
        return empresa;
    }

    public List<Empresa> listar() throws SQLException {
        String sql = "SELECT * FROM Empresa";
        List<Empresa> lista = new ArrayList<>();
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Empresa empresa = new Empresa();
                empresa.setId(rs.getInt("id"));
                empresa.setNome(rs.getString("nome"));
                empresa.setIndice(rs.getBigDecimal("indice"));
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setCep(rs.getString("cep"));
                empresa.setEndereco(rs.getString("endereco"));
                empresa.setEmail(rs.getString("email"));
                empresa.setTelefone(rs.getString("telefone"));
                empresa.setSenha(rs.getString("senha"));
                lista.add(empresa);
            }
            rs.close();
            stmt.close();
        } finally {
            fecharConexao();
        }
        return lista;
    }

    public Empresa buscarPorNome(String nome) throws SQLException {
        String sql = "SELECT id, nome FROM Empresa WHERE nome ILIKE ? LIMIT 1";
        Empresa empresa = null;
        try {
            abrirConexao();
            PreparedStatement stmt = getConexao().prepareStatement(sql);
            stmt.setString(1, "%" + nome.trim() + "%");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                empresa = new Empresa();
                empresa.setId(rs.getInt("id"));
                empresa.setNome(rs.getString("nome"));
            }
            rs.close();
            stmt.close();
        } finally {
            fecharConexao();
        }
        return empresa;
    }
}
