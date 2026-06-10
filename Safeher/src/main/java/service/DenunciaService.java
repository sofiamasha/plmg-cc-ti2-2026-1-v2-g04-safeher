package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonPrimitive;
import dao.DenunciaDAO;
import dao.EmpresaDAO;
import model.Denuncia;
import model.Empresa;
import com.google.gson.JsonObject;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DenunciaService {

    private DenunciaDAO denunciaDAO;
    private Gson gson;

    public DenunciaService() {
        this.denunciaDAO = new DenunciaDAO();
        this.gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class,
                (JsonSerializer<LocalDate>) (date, type, ctx) ->
                    new JsonPrimitive(date.toString()))
            .registerTypeAdapter(LocalDate.class,
                (JsonDeserializer<LocalDate>) (json, type, ctx) ->
                    LocalDate.parse(json.getAsString()))
            .create();
    }

    
public String insert(Request req, Response res) {
        res.type("application/json");
        try {
            Denuncia denuncia = gson.fromJson(req.body(), Denuncia.class);

            // Auto-cadastro: se a denuncia nao tem empresa vinculada mas veio um nome,
            // procura uma empresa existente; se nao houver, cria um registro minimo.
            if (denuncia.getEmpresaId() <= 0
                    && denuncia.getEmpresaNome() != null
                    && !denuncia.getEmpresaNome().isBlank()) {
                EmpresaDAO empresaDAO = new EmpresaDAO();
                Empresa existente = empresaDAO.buscarPorNomeExato(denuncia.getEmpresaNome());
                if (existente != null) {
                    denuncia.setEmpresaId(existente.getId());
                } else {
                    int novoId = empresaDAO.criarMinima(denuncia.getEmpresaNome());
                    denuncia.setEmpresaId(novoId);
                }
            }

            ScoreService scoreService = new ScoreService();
            denuncia.setScore(scoreService.calcularScore(denuncia.getDescricao()));
            if (denuncia.getStatus() == null || denuncia.getStatus().isBlank()) {
                denuncia.setStatus("Aberta");
            }
            denunciaDAO.insert(denuncia);
            return gson.toJson("{\"msg\":\"Denuncia inserida com sucesso\"}");
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }
    public String update(Request req, Response res) {
        res.type("application/json");
        try {
            Denuncia denuncia = gson.fromJson(req.body(), Denuncia.class);
            denuncia.setId(Integer.parseInt(req.params(":id")));
            
            // Validar plano da empresa para atualizacoes de denuncia (Compliance)
            EmpresaDAO empresaDAO = new EmpresaDAO();
            Empresa empresa = empresaDAO.get(denuncia.getEmpresaId());
            if (empresa == null || empresa.getPlano() == null || !empresa.getPlano().equalsIgnoreCase("Premium")) {
                res.status(403);
                return gson.toJson("{\"erro\":\"Acesso negado: Recursos de tratativas de denúncias são exclusivos do plano Premium.\"}");
            }
            
            ScoreService scoreService = new ScoreService();
            denuncia.setScore(scoreService.calcularScore(denuncia.getDescricao()));
            denunciaDAO.update(denuncia);
            return gson.toJson("{\"msg\":\"Denuncia atualizada com sucesso\"}");
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }

    public String remove(Request req, Response res) {
        res.type("application/json");
        try {
            denunciaDAO.remove(Integer.parseInt(req.params(":id")));
            return gson.toJson("{\"msg\":\"Denuncia removida com sucesso\"}");
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }

    public String get(Request req, Response res) {
        res.type("application/json");
        try {
            Denuncia denuncia = denunciaDAO.get(Integer.parseInt(req.params(":id")));
            return gson.toJson(denuncia);
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }

    public String listar(Request req, Response res) {
        res.type("application/json");
        try {
            return gson.toJson(denunciaDAO.listar());
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }

    public String listarPorUsuario(Request req, Response res) {
        res.type("application/json");
        try {
            return gson.toJson(denunciaDAO.listarPorUsuario(Integer.parseInt(req.params(":usuarioId"))));
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }

    public String listarPorEmpresa(Request req, Response res) {
        res.type("application/json");
        try {
            int empresaId = Integer.parseInt(req.params(":empresaId"));
            
            // Validar plano da empresa para listar denúncias (Removido para permitir que qualquer empresa veja as denúncias)
            /*
            if (empresa.getPlano() == null || (!empresa.getPlano().equalsIgnoreCase("Premium") && !empresa.getPlano().equalsIgnoreCase("Plus"))) {
                res.status(403);
                return gson.toJson("{\"erro\":\"Acesso negado: O painel de denúncias corporativas é exclusivo dos planos Plus/Premium.\"}");
            }
            */
            
            return gson.toJson(denunciaDAO.listarPorEmpresa(empresaId));
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }

    public String listarPainelRisco(Request req, Response res) {
        res.type("application/json");
        try {
            return gson.toJson(denunciaDAO.listarOrdenadoPorRisco());
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }

    public String salvarResposta(Request req, Response res) {
        res.type("application/json");
        try {
            int id = Integer.parseInt(req.params(":id"));
            JsonObject body = gson.fromJson(req.body(), JsonObject.class);
            String resposta = body != null && body.has("resposta") && !body.get("resposta").isJsonNull()
                    ? body.get("resposta").getAsString().trim()
                    : "";
            String status = body != null && body.has("status") && !body.get("status").isJsonNull()
                    ? body.get("status").getAsString().trim()
                    : "Respondida";

            if (resposta.isBlank()) {
                res.status(400);
                return gson.toJson("{\"erro\":\"A resposta da empresa nao pode ficar vazia\"}");
            }

            if (!status.equals("Respondida") && !status.equals("Resolvida")) {
                status = "Respondida";
            }

            denunciaDAO.atualizarResposta(id, resposta, status);
            return gson.toJson("{\"msg\":\"Resposta da denuncia publicada com sucesso\"}");
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }

    public String excluirResposta(Request req, Response res) {
        res.type("application/json");
        try {
            int id = Integer.parseInt(req.params(":id"));
            denunciaDAO.atualizarResposta(id, "", "Aberta");
            return gson.toJson("{\"msg\":\"Resposta da denuncia removida com sucesso\"}");
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }
}
