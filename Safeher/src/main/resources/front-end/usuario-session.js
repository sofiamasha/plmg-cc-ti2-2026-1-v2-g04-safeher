/**
 * SafeHer - Gerenciamento de sessão do usuário
 * Inclua este script APÓS api.js nas páginas com header.
 *
 * Uso: <script src="api.js"></script>
 *      <script src="usuario-session.js"></script>
 */

/** Salva o usuário logado no localStorage */
function salvarSessao(usuario) {
    localStorage.setItem('safeher_usuario', JSON.stringify(usuario));
}

/** Recupera o usuário logado */
function getSessao() {
    const data = localStorage.getItem('safeher_usuario');
    return data ? JSON.parse(data) : null;
}

/** Remove a sessão (logout) */
function logout() {
    localStorage.removeItem('safeher_usuario');
    localStorage.removeItem('safeher_tipo');
    window.location.href = 'index.html';
}

/** Retorna o tipo de conta: 'mulher' ou 'empresa' */
function getTipoSessao() {
    return localStorage.getItem('safeher_tipo') || 'mulher';
}

/** Atualiza o header com o menu do usuário logado */
function atualizarHeader() {
    const usuario = getSessao();
    const nav = document.querySelector('.sh-nav');
    if (!nav) return;

    // Remove botões de Entrar/Criar conta
    const btnEntrar = nav.querySelector('a[href="login.html"]');
    const btnCriar = nav.querySelector('a[href="escolha-cadastro.html"]');

    if (usuario) {
        if (btnEntrar) btnEntrar.remove();
        if (btnCriar) btnCriar.remove();

        const tipo = getTipoSessao();

        // Empresa não faz denúncias e não vê vagas — esconder links na nav
        if (tipo === 'empresa') {
            const linkDenuncias = nav.querySelector('a[href="denuncias.html"]');
            if (linkDenuncias) linkDenuncias.remove();
            const linkVagas = nav.querySelector('a[href="vagas.html"]');
            if (linkVagas) linkVagas.remove();
        }

        // Pega a primeira letra do nome
        const inicial = usuario.nome ? usuario.nome.charAt(0).toUpperCase() : 'U';
        const labelTipo = tipo === 'empresa' ? 'Empresa' : 'Usuária';
        const menuDenuncias = tipo === 'mulher' ? `
                    <a href="minhas-denuncias.html" class="user-dropdown-item">
                        <i class="fa-solid fa-file-lines"></i> Minhas Denúncias
                    </a>` : '';

        const menuHTML = `
            <div class="user-menu">
                <button class="user-menu-btn" onclick="toggleUserMenu()">
                    <span class="user-avatar">${inicial}</span>
                    <span class="user-name">${usuario.nome || labelTipo}</span>
                    <i class="fa-solid fa-chevron-down" style="font-size: 12px;"></i>
                </button>
                <div class="user-dropdown" id="userDropdown">
                    <div class="user-dropdown-header">
                        <span class="user-avatar" style="width:40px;height:40px;font-size:18px;">${inicial}</span>
                        <div>
                            <strong>${usuario.nome || labelTipo}</strong>
                            <p style="font-size:12px;color:#888;margin:0;">${usuario.email || ''}</p>
                            <p style="font-size:11px;color:var(--pink-primary);margin:2px 0 0;font-weight:600;">${labelTipo}</p>
                        </div>
                    </div>
                    <hr style="margin:8px 0;border:none;border-top:1px solid #eee;">${menuDenuncias}
                    <a href="#" class="user-dropdown-item" onclick="logout()">
                        <i class="fa-solid fa-right-from-bracket"></i> Sair da conta
                    </a>
                </div>
            </div>
        `;
        nav.insertAdjacentHTML('beforeend', menuHTML);
    }
}

/** Toggle do dropdown do usuário */
function toggleUserMenu() {
    const dropdown = document.getElementById('userDropdown');
    if (dropdown) {
        dropdown.classList.toggle('show');
    }
}

// Fecha o dropdown ao clicar fora
document.addEventListener('click', function(e) {
    const menu = document.querySelector('.user-menu');
    const dropdown = document.getElementById('userDropdown');
    if (dropdown && menu && !menu.contains(e.target)) {
        dropdown.classList.remove('show');
    }
});

// Executa ao carregar a página
document.addEventListener('DOMContentLoaded', atualizarHeader);
