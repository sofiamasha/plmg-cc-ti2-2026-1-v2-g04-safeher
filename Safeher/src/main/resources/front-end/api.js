const API = "http://localhost:8080";

//usuario 

/*cadastra novo */
async function cadastrarUsuario(usuario) {
    const res = await fetch(`${API}/usuarios`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(usuario)
    });
    return res.json();
}

/*atualiza usuario */
async function atualizarUsuario(id, usuario) {
    const res = await fetch(`${API}/usuarios/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(usuario)
    });
    return res.json();
}

/*remove usuario */
async function removerUsuario(id) {
    const res = await fetch(`${API}/usuarios/${id}`, { method: "DELETE" });
    return res.json();
}

/*busca usuario */
async function getUsuario(id) {
    const res = await fetch(`${API}/usuarios/${id}`);
    return res.json();
}

/*lista usuarios*/
async function listarUsuarios() {
    const res = await fetch(`${API}/usuarios`);
    return res.json();
}

// empresa

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

// avaliacao

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

// denuncia

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

/* lista avaliações feitas por um usuário específico */
async function listarDenunciasPorUsuario(usuarioId) {
    const res = await fetch(`${API}/denuncias/usuario/${usuarioId}`);
    return res.json();
}

/* lista avaliações de uma empresa específica */
async function listarAvaliacoesPorEmpresa(empresaId) {
    const res = await fetch(`${API}/avaliacoes/empresa/${empresaId}`);
    return res.json();
}

/* lista denúncias de uma empresa específica */
async function listarDenunciasPorEmpresa(empresaId) {
    const res = await fetch(`${API}/denuncias/empresa/${empresaId}`);
    return res.json();
}

// vagas

async function cadastrarVaga(vaga) {
    const res = await fetch(`${API}/vagas`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(vaga)
    });
    return res.json();
}

async function listarVagas() {
    const res = await fetch(`${API}/vagas`);
    return res.json();
}

async function listarVagasPorEmpresa(empresaId) {
    const res = await fetch(`${API}/vagas/empresa/${empresaId}`);
    return res.json();
}

async function removerVaga(id) {
    const res = await fetch(`${API}/vagas/${id}`, { method: "DELETE" });
    return res.json();
}

// responder avaliacao

async function responderAvaliacao(avaliacaoId, resposta) {
    const res = await fetch(`${API}/avaliacoes/${avaliacaoId}/resposta`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ resposta: resposta })
    });
    return res.json();
}

async function excluirRespostaAvaliacao(avaliacaoId) {
    const res = await fetch(`${API}/avaliacoes/${avaliacaoId}/resposta`, {
        method: "DELETE"
    });
    return res.json();
}

// candidaturas

async function cadastrarCandidatura(candidatura) {
    const res = await fetch(`${API}/candidaturas`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(candidatura)
    });
    return res.json();
}

async function listarCandidaturasPorVaga(vagaId) {
    const res = await fetch(`${API}/candidaturas/vaga/${vagaId}`);
    if (!res.ok) {
        const errorData = await res.json().catch(() => ({}));
        throw new Error(errorData.erro || `Erro de autorização (Status ${res.status}).`);
    }
    return res.json();
}

