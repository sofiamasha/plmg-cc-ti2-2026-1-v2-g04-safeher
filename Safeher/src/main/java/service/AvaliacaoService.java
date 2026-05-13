package service;

import com.google.gson.Gson;
import dao.AvaliacaoDAO;
import model.Avaliacao;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.List;

public class AvaliacaoService {

    private AvaliacaoDAO avaliacaoDAO;
    private Gson gson;

    public AvaliacaoService() {
        this.avaliacaoDAO = new AvaliacaoDAO();
        this.gson = new Gson();
    }

    public String insert(Request req, Response res) {
        res.type("application/json");
        try {
            Avaliacao avaliacao = gson.fromJson(req.body(), Avaliacao.class);
            avaliacaoDAO.insert(avaliacao);
            return gson.toJson("{\"msg\":\"Avaliacao inserida com sucesso\"}");
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }

    public String update(Request req, Response res) {
        res.type("application/json");
        try {
            Avaliacao avaliacao = gson.fromJson(req.body(), Avaliacao.class);
            avaliacao.setId(Integer.parseInt(req.params(":id")));
            avaliacaoDAO.update(avaliacao);
            return gson.toJson("{\"msg\":\"Avaliacao atualizada com sucesso\"}");
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }

    public String remove(Request req, Response res) {
        res.type("application/json");
        try {
            avaliacaoDAO.remove(Integer.parseInt(req.params(":id")));
            return gson.toJson("{\"msg\":\"Avaliacao removida com sucesso\"}");
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }

    public String get(Request req, Response res) {
        res.type("application/json");
        try {
            Avaliacao avaliacao = avaliacaoDAO.get(Integer.parseInt(req.params(":id")));
            return gson.toJson(avaliacao);
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }

    public String listar(Request req, Response res) {
        res.type("application/json");
        try {
            return gson.toJson(avaliacaoDAO.listar());
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }

    public String listarPorEmpresa(Request req, Response res) {
        res.type("application/json");
        try {
            return gson.toJson(avaliacaoDAO.listarPorEmpresa(Integer.parseInt(req.params(":empresaId"))));
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }
}
