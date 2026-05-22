package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonPrimitive;
import dao.DenunciaDAO;
import model.Denuncia;
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
            return gson.toJson(denunciaDAO.listarPorEmpresa(Integer.parseInt(req.params(":empresaId"))));
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
}
