package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonPrimitive;
import dao.UsuarioDAO;
import model.Usuario;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class UsuarioService {

    private UsuarioDAO usuarioDAO;
    private Gson gson;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
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
            Usuario usuario = gson.fromJson(req.body(), Usuario.class);
            usuarioDAO.insert(usuario);
            return gson.toJson("{\"msg\":\"Usuario inserido com sucesso\"}");
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }

    public String update(Request req, Response res) {
        res.type("application/json");
        try {
            Usuario usuario = gson.fromJson(req.body(), Usuario.class);
            usuario.setId(Integer.parseInt(req.params(":id")));
            usuarioDAO.update(usuario);
            return gson.toJson("{\"msg\":\"Usuario atualizado com sucesso\"}");
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }

    public String remove(Request req, Response res) {
        res.type("application/json");
        try {
            usuarioDAO.remove(Integer.parseInt(req.params(":id")));
            return gson.toJson("{\"msg\":\"Usuario removido com sucesso\"}");
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }

    public String get(Request req, Response res) {
        res.type("application/json");
        try {
            Usuario usuario = usuarioDAO.get(Integer.parseInt(req.params(":id")));
            return gson.toJson(usuario);
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }

    public String listar(Request req, Response res) {
        res.type("application/json");
        try {
            return gson.toJson(usuarioDAO.listar());
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }
}
