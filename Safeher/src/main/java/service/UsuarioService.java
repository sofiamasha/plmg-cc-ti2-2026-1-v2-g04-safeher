package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonPrimitive;
import dao.UsuarioDAO;
import model.Usuario;
import org.mindrot.jbcrypt.BCrypt;
import spark.Request;
import spark.Response;

import java.time.LocalDate;

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
            // nunca salva senha em texto puro
            usuario.setSenha(BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt()));
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

            // se a senha nao veio no corpo, mantem a atual (ja com hash);
            // se veio, gera um novo hash.
            if (usuario.getSenha() == null || usuario.getSenha().isBlank()) {
                usuario.setSenha(usuarioDAO.buscarSenhaAtual(usuario.getId()));
            } else {
                usuario.setSenha(BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt()));
            }

            usuarioDAO.update(usuario);
            return gson.toJson("{\"msg\":\"Usuario atualizado com sucesso\"}");
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }

    public String login(Request req, Response res) {
        res.type("application/json");
        try {
            Usuario dados = gson.fromJson(req.body(), Usuario.class);
            Usuario usuario = usuarioDAO.buscarPorEmail(dados.getEmail());

            boolean ok = false;
            if (usuario != null && usuario.getSenha() != null) {
                try {
                    ok = BCrypt.checkpw(dados.getSenha(), usuario.getSenha());
                } catch (IllegalArgumentException ex) {
                    ok = false; // hash invalido (ex.: usuario antigo em texto puro)
                }
            }

            if (ok) {
                usuario.setSenha(null); // nunca devolve a senha
                return gson.toJson(usuario);
            }

            res.status(401);
            return gson.toJson("{\"erro\":\"Email ou senha invalidos\"}");
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