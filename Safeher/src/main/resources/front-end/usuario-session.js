(function injectToast() {
    if (document.getElementById('toast-container')) return;
    const el = document.createElement('div');
    el.id = 'toast-container';
    document.body.appendChild(el);
})();

(function redirectLogin() {
    if (window.location.pathname.endsWith('/login.html') && !window.location.href.startsWith('http://127.0.0.1:5500/')) {
        window.location.href = 'http://127.0.0.1:5500/Safeher/src/main/resources/front-end/login.html';
    }
})();

/**
 * exibe uma notificação toast.
 * @param {string} mensagem - Texto da mensagem
 * @param {'success'|'error'|'info'} tipo - Tipo visual
 * @param {number} duracao - Duração em ms (padrão 3500)
 */
function showToast(mensagem, tipo = 'success', duracao = 3500) {
    const container = document.getElementById('toast-container');
    if (!container) { alert(mensagem); return; }

    const icons = { success: 'fa-circle-check', error: 'fa-circle-xmark', info: 'fa-circle-info' };
    const toast = document.createElement('div');
    toast.className = `toast ${tipo}`;
    toast.innerHTML = `<i class="fa-solid ${icons[tipo] || icons.info}"></i><span>${mensagem}</span>`;
    container.appendChild(toast);

    setTimeout(() => {
        toast.classList.add('toast-out');
        setTimeout(() => toast.remove(), 320);
    }, duracao);
}

/* hamburguer menor */
document.addEventListener('DOMContentLoaded', function () {
    const header = document.querySelector('.sh-header');
    const nav    = document.querySelector('.sh-nav');
    if (!header || !nav) return;

    // toggle button
    const btn = document.createElement('button');
    btn.className = 'nav-toggle';
    btn.setAttribute('aria-label', 'Abrir menu');
    btn.innerHTML = '<span></span><span></span><span></span>';
    header.appendChild(btn);

    btn.addEventListener('click', function () {
        const open = nav.classList.toggle('open');
        btn.classList.toggle('open', open);
        btn.setAttribute('aria-label', open ? 'Fechar menu' : 'Abrir menu');
    });

    // fecha ao clicar fora
    document.addEventListener('click', function (e) {
        if (!header.contains(e.target)) {
            nav.classList.remove('open');
            btn.classList.remove('open');
        }
    });

    // fecha ao clicar em link
    nav.querySelectorAll('a').forEach(a => {
        a.addEventListener('click', () => {
            nav.classList.remove('open');
            btn.classList.remove('open');
        });
    });
});

/* sessao */
function salvarSessao(usuario) {
    localStorage.setItem('safeher_usuario', JSON.stringify(usuario));
}

function getSessao() {
    const data = localStorage.getItem('safeher_usuario');
    return data ? JSON.parse(data) : null;
}

function logout() {
    localStorage.removeItem('safeher_usuario');
    localStorage.removeItem('safeher_tipo');
    window.location.href = 'index.html';
}

function getTipoSessao() {
    return localStorage.getItem('safeher_tipo') || 'mulher';
}

/* header dinamico  */
function atualizarHeader() {
    const usuario = getSessao();
    const nav = document.querySelector('.sh-nav');
    if (!nav) return;

    const btnEntrar = nav.querySelector('a[href="login.html"]') || nav.querySelector('a[href="http://127.0.0.1:5500/Safeher/src/main/resources/front-end/login.html"]');
    const btnCriar  = nav.querySelector('a[href="escolha-cadastro.html"]');

    if (usuario) {
        if (btnEntrar) btnEntrar.remove();
        if (btnCriar)  btnCriar.remove();

        const tipo = getTipoSessao();

        if (tipo === 'empresa') {
            const page = window.location.pathname.split('/').pop() || 'inicio-empresa.html';
            const links = `
                <a href="inicio-empresa.html" class="${page === 'inicio-empresa.html' ? 'active' : ''}">Início</a>
                <a href="empresa-dashboard.html" class="${page === 'empresa-dashboard.html' ? 'active' : ''}">Dashboard</a>
                <a href="empresa-compliance.html" class="${page === 'empresa-compliance.html' ? 'active' : ''}"><i class="fa-solid fa-scale-balanced" style="margin-right:4px;"></i> Compliance</a>
                <a href="empresa-ouvidoria.html" class="${page === 'empresa-ouvidoria.html' ? 'active' : ''}">Ouvidoria</a>
                <a href="empresa-treinamentos.html" class="${page === 'empresa-treinamentos.html' ? 'active' : ''}">Treinamentos</a>
                <a href="empresa-vagas.html" class="${page === 'empresa-vagas.html' ? 'active' : ''}">Vagas</a>
                <a href="planos.html" style="font-weight: 700; color: var(--pink-primary);"><i class="fa-solid fa-gem"></i> Planos</a>
            `;
            nav.innerHTML = links;
        } else if (tipo === 'mulher') {
            const page = window.location.pathname.split('/').pop() || 'inicio-mulher.html';
            const links = `
                <a href="inicio-mulher.html" class="${page === 'inicio-mulher.html' ? 'active' : ''}">Início</a>
                <a href="denuncias.html" class="${page === 'denuncias.html' ? 'active' : ''}">Denúncias</a>
                <a href="avaliacoes.html" class="${page === 'avaliacoes.html' ? 'active' : ''}">Avaliações</a>
                <a href="conteudos.html" class="${page === 'conteudos.html' ? 'active' : ''}">Conteúdos</a>
                <a href="vagas.html" class="${page === 'vagas.html' ? 'active' : ''}">Vagas</a>
            `;
            nav.innerHTML = links;
        }

        const inicial   = usuario.nome ? usuario.nome.charAt(0).toUpperCase() : 'U';
        const labelTipo = tipo === 'empresa' ? 'Empresa' : 'Usuária';
        const avatarHtml = usuario.foto
            ? `<img src="${usuario.foto}" style="width:100%; height:100%; border-radius:50%; object-fit:cover; display:block;">`
            : inicial;

        const menuDenuncias = tipo === 'mulher' ? `
            <a href="minhas-denuncias.html" class="user-dropdown-item">
                <i class="fa-solid fa-file-lines"></i> Minhas Denúncias
            </a>` : '';

        const menuPerfil = `
            <a href="perfil.html" class="user-dropdown-item">
                <i class="fa-solid fa-user-gear"></i> Meu Perfil
            </a>`;

        const menuHTML = `
            <div class="user-menu">
                <button class="user-menu-btn" onclick="toggleUserMenu()">
                    <span class="user-avatar" style="overflow:hidden; display:flex; align-items:center; justify-content:center;">${avatarHtml}</span>
                    <span class="user-name">${usuario.nome || labelTipo}</span>
                    <i class="fa-solid fa-chevron-down" style="font-size:11px;"></i>
                </button>
                <div class="user-dropdown" id="userDropdown">
                    <div class="user-dropdown-header">
                        <span class="user-avatar" style="width:40px;height:40px;font-size:18px; overflow:hidden; display:flex; align-items:center; justify-content:center;">${avatarHtml}</span>
                        <div>
                            <strong style="font-size:14px;">${usuario.nome || labelTipo}</strong>
                            <p style="font-size:12px;color:#888;margin:2px 0 0;">${usuario.email || ''}</p>
                            <p style="font-size:11px;color:var(--pink-primary);margin:2px 0 0;font-weight:700;">${labelTipo}</p>
                        </div>
                    </div>
                    ${menuPerfil}
                    ${menuDenuncias}
                    <button class="user-dropdown-item" onclick="logout()">
                        <i class="fa-solid fa-right-from-bracket"></i> Sair da conta
                    </button>
                </div>
            </div>`;
        nav.insertAdjacentHTML('beforeend', menuHTML);
    } else {
        const page = window.location.pathname.split('/').pop() || 'index.html';
        const isParceirosActive = page === 'parceiros.html';
        const isInicioActive = page === 'index.html' || page === '';
        const links = `
            <a href="index.html" class="${isInicioActive ? 'active' : ''}">Início</a>
            <a href="parceiros.html" class="${isParceirosActive ? 'active' : ''}">Para Parceiros</a>
            <a href="http://127.0.0.1:5500/Safeher/src/main/resources/front-end/login.html" class="sh-btn" style="color: white;">Entrar</a>
            <a href="escolha-cadastro.html" class="sh-btn-outline">Criar conta</a>
        `;
        nav.innerHTML = links;
    }
}

function toggleUserMenu() {
    const dropdown = document.getElementById('userDropdown');
    if (dropdown) dropdown.classList.toggle('show');
}

document.addEventListener('click', function (e) {
    const menu     = document.querySelector('.user-menu');
    const dropdown = document.getElementById('userDropdown');
    if (dropdown && menu && !menu.contains(e.target)) {
        dropdown.classList.remove('show');
    }
});

document.addEventListener('DOMContentLoaded', atualizarHeader);
