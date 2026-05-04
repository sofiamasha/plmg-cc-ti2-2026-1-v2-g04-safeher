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
    window.location.href = 'index.html';
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

        // Pega a primeira letra do nome
        const inicial = usuario.nome ? usuario.nome.charAt(0).toUpperCase() : 'U';

        const menuHTML = `
            <div class="user-menu">
                <button class="user-menu-btn" onclick="toggleUserMenu()">
                    <span class="user-avatar">${inicial}</span>
                    <span class="user-name">${usuario.nome || 'Usuária'}</span>
                    <i class="fa-solid fa-chevron-down" style="font-size: 12px;"></i>
                </button>
                <div class="user-dropdown" id="userDropdown">
                    <div class="user-dropdown-header">
                        <span class="user-avatar" style="width:40px;height:40px;font-size:18px;">${inicial}</span>
                        <div>
                            <strong>${usuario.nome || 'Usuária'}</strong>
                            <p style="font-size:12px;color:#888;margin:0;">${usuario.email || ''}</p>
                        </div>
                    </div>
                    <hr style="margin:8px 0;border:none;border-top:1px solid #eee;">
                    <a href="minhas-denuncias.html" class="user-dropdown-item">
                        <i class="fa-solid fa-file-lines"></i> Minhas Denúncias
                    </a>
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
