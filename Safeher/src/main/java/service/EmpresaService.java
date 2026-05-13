package service;

import com.google.gson.Gson;
import dao.EmpresaDAO;
import model.Empresa;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
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
}
