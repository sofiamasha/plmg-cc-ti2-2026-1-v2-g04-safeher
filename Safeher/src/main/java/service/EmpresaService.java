package service;

import com.google.gson.Gson;
import dao.EmpresaDAO;
import model.Empresa;
import spark.Request;
import spark.Response;

import java.sql.*;
import java.util.List;

public class EmpresaService {

    private EmpresaDAO empresaDAO;
    private Gson gson;

    public EmpresaService() {
        this.empresaDAO = new EmpresaDAO();
        this.gson = new Gson();
    }

    public String insert(Request req, Response res) {
        res.type("application/json");
        try {
            Empresa empresa = gson.fromJson(req.body(), Empresa.class);
            empresaDAO.insert(empresa);
            return gson.toJson("{\"msg\":\"Empresa inserida com sucesso\"}");
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }

    public String update(Request req, Response res) {
        res.type("application/json");
        try {
            Empresa empresa = gson.fromJson(req.body(), Empresa.class);
            empresa.setId(Integer.parseInt(req.params(":id")));
            empresaDAO.update(empresa);
            return gson.toJson("{\"msg\":\"Empresa atualizada com sucesso\"}");
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }

    public String remove(Request req, Response res) {
        res.type("application/json");
        try {
            empresaDAO.remove(Integer.parseInt(req.params(":id")));
            return gson.toJson("{\"msg\":\"Empresa removida com sucesso\"}");
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }

    public String get(Request req, Response res) {
        res.type("application/json");
        try {
            Empresa empresa = empresaDAO.get(Integer.parseInt(req.params(":id")));
            return gson.toJson(empresa);
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }

    public String listar(Request req, Response res) {
        res.type("application/json");
        try {
            return gson.toJson(empresaDAO.listar());
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }

    public String obterScorePublico(spark.Request req, spark.Response res) {
        res.type("application/json");
        try {
            String nome = req.queryParams("nome");
            if (nome == null || nome.trim().isEmpty()) {
                res.status(400);
                return "{\"erro\":\"Parâmetro 'nome' é obrigatório\"}";
            }

            Empresa empresa = empresaDAO.buscarPorNome(nome);
            if (empresa == null) {
                res.status(404);
                return "{\"erro\":\"Empresa sem dados no SafeHer\",\"encontrada\":false,\"nome\":\"" + nome.replace("\"", "\\\"") + "\"}";
            }

            int totalAvaliacoes = 0;
            double somaNotas = 0.0;
            int totalDenuncias = 0;

            String sqlAval = "SELECT COUNT(*) as total, SUM(nota) as soma FROM Avaliacao WHERE empresa_id = ? OR (nome_empresa ILIKE ? AND (empresa_id IS NULL OR empresa_id = 0))";
            String sqlDenuncia = "SELECT COUNT(*) as total FROM Denuncia WHERE Empresa_id = ?";

            java.sql.Connection conn = null;
            try {
                empresaDAO.abrirConexao();
                conn = empresaDAO.getConexao();

                PreparedStatement stmtA = conn.prepareStatement(sqlAval);
                stmtA.setInt(1, empresa.getId());
                stmtA.setString(2, empresa.getNome());
                ResultSet rsA = stmtA.executeQuery();
                if (rsA.next()) {
                    totalAvaliacoes = rsA.getInt("total");
                    somaNotas = rsA.getDouble("soma");
                }
                rsA.close();
                stmtA.close();

                PreparedStatement stmtD = conn.prepareStatement(sqlDenuncia);
                stmtD.setInt(1, empresa.getId());
                ResultSet rsD = stmtD.executeQuery();
                if (rsD.next()) {
                    totalDenuncias = rsD.getInt("total");
                }
                rsD.close();
                stmtD.close();

            } finally {
                empresaDAO.fecharConexao();
            }

            double mediaAvaliacoes = totalAvaliacoes > 0 ? somaNotas / totalAvaliacoes : 0.0;
            double baseScore = totalAvaliacoes > 0 ? mediaAvaliacoes * 20.0 : 100.0;
            double penalidade = totalDenuncias * 10.0;
            int scoreFinal = (int) Math.max(0, Math.min(100, baseScore - penalidade));

            String status = "Segura";
            String resumo = "Excelente reputação. Ambiente de trabalho altamente seguro e recomendado.";
            if (scoreFinal < 40) {
                status = "Crítica";
                resumo = "Nível de segurança muito baixo. Presença de denúncias graves e avaliações negativas.";
            } else if (scoreFinal < 70) {
                status = "Atenção";
                resumo = "Ambiente corporativo requer cuidados. Algumas reclamações ou notas medianas registradas.";
            }

            java.util.Map<String, Object> payload = new java.util.HashMap<>();
            payload.put("id", empresa.getId());
            payload.put("nome", empresa.getNome());
            payload.put("score", scoreFinal);
            payload.put("mediaAvaliacoes", mediaAvaliacoes);
            payload.put("totalAvaliacoes", totalAvaliacoes);
            payload.put("denuncias", totalDenuncias);
            payload.put("status", status);
            payload.put("resumo", resumo);
            payload.put("encontrada", true);

            return gson.toJson(payload);

        } catch (Exception e) {
            res.status(500);
            return "{\"erro\":\"" + e.getMessage().replace("\"", "\\\"") + "\"}";
        }
    }

    public String atualizarAssinatura(Request req, Response res) {
        res.type("application/json");
        try {
            java.util.Map<String, Object> body = gson.fromJson(req.body(), java.util.Map.class);
            Object idObj = body.get("empresaId");
            if (idObj == null) {
                res.status(400);
                return "{\"erro\":\"empresaId e plano são obrigatórios\"}";
            }
            int empresaId;
            if (idObj instanceof Double) {
                empresaId = ((Double) idObj).intValue();
            } else if (idObj instanceof Integer) {
                empresaId = (Integer) idObj;
            } else {
                empresaId = Integer.parseInt(idObj.toString().trim());
            }
            String plano = (String) body.get("plano");
            
            if (!plano.equals("Free") && !plano.equals("Premium") && !plano.equals("Plus")) {
                res.status(400);
                return "{\"erro\":\"Plano inválido\"}";
            }
            
            empresaDAO.atualizarPlano(empresaId, plano);
            
            Empresa empresa = empresaDAO.get(empresaId);
            return gson.toJson(empresa);
        } catch (Exception e) {
            res.status(500);
            return "{\"erro\":\"" + e.getMessage().replace("\"", "\\\"") + "\"}";
        }
    }
}
