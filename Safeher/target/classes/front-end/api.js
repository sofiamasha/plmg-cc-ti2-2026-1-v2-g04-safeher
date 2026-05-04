/**
 * SafeHer - Funções de integração com o back-end
 * Inclua este script nas páginas HTML que precisam de dados do banco.
 *
 * Uso: <script src="api.js"></script>
 */

const API = "http://localhost:8080";

// =====================
// USUARIO
// =====================

/** Cadastra um novo usuário */
async function cadastrarUsuario(usuario) {
    const res = await fetch(`${API}/usuarios`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(usuario)
    });
    return res.json();
}

/** Atualiza um usuário pelo ID */
async function atualizarUsuario(id, usuario) {
    const res = await fetch(`${API}/usuarios/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(usuario)
    });
    return res.json();
}

/** Remove um usuário pelo ID */
async function removerUsuario(id) {
    const res = await fetch(`${API}/usuarios/${id}`, { method: "DELETE" });
    return res.json();
}

/** Busca um usuário pelo ID */
async function getUsuario(id) {
    const res = await fetch(`${API}/usuarios/${id}`);
    return res.json();
}

/** Lista todos os usuários */
async function listarUsuarios() {
    const res = await fetch(`${API}/usuarios`);
    return res.json();
}

// =====================
// EMPRESA
// =====================

async function cadastrarEmpresa(empresa) {
    const res = await fetch(`${API}/empresas`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(empresa)
    });
    return res.json();
}

async function atualizarEmpresa(id, empresa) {
    const res = await fetch(`${API}/empresas/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(empresa)
    });
    return res.json();
}

async function removerEmpresa(id) {
    const res = await fetch(`${API}/empresas/${id}`, { method: "DELETE" });
    return res.json();
}

async function getEmpresa(id) {
    const res = await fetch(`${API}/empresas/${id}`);
    return res.json();
}

async function listarEmpresas() {
    const res = await fetch(`${API}/empresas`);
    return res.json();
}

// =====================
// AVALIACAO
// =====================

async function cadastrarAvaliacao(avaliacao) {
    const res = await fetch(`${API}/avaliacoes`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(avaliacao)
    });
    return res.json();
}

async function atualizarAvaliacao(id, avaliacao) {
    const res = await fetch(`${API}/avaliacoes/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(avaliacao)
    });
    return res.json();
}

async function removerAvaliacao(id) {
    const res = await fetch(`${API}/avaliacoes/${id}`, { method: "DELETE" });
    return res.json();
}

async function getAvaliacao(id) {
    const res = await fetch(`${API}/avaliacoes/${id}`);
    return res.json();
}

async function listarAvaliacoes() {
    const res = await fetch(`${API}/avaliacoes`);
    return res.json();
}

// =====================
// DENUNCIA
// =====================

async function cadastrarDenuncia(denuncia) {
    const res = await fetch(`${API}/denuncias`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(denuncia)
    });
    return res.json();
}

async function atualizarDenuncia(id, denuncia) {
    const res = await fetch(`${API}/denuncias/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(denuncia)
    });
    return res.json();
}

async function removerDenuncia(id) {
    const res = await fetch(`${API}/denuncias/${id}`, { method: "DELETE" });
    return res.json();
}

async function getDenuncia(id) {
    const res = await fetch(`${API}/denuncias/${id}`);
    return res.json();
}

async function listarDenuncias() {
    const res = await fetch(`${API}/denuncias`);
    return res.json();
}

/** Lista denúncias de um usuário específico */
async function listarDenunciasPorUsuario(usuarioId) {
    const res = await fetch(`${API}/denuncias/usuario/${usuarioId}`);
    return res.json();
}
