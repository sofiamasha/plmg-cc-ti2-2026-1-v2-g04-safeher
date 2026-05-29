const API_BASE_URL = "http://localhost:8080";
const FRONTEND_BASE_URL = "http://localhost:8080";

document.addEventListener("DOMContentLoaded", () => {
  const searchInput = document.getElementById("company-search-input");
  const searchBtn = document.getElementById("search-btn");
  const detectedIndicator = document.getElementById("detected-indicator");
  
  const loader = document.getElementById("loader");
  const resultCard = document.getElementById("result-card");
  const noDataCard = document.getElementById("no-data-card");
  
  const btnEvaluate = document.getElementById("btn-evaluate");
  const btnComplain = document.getElementById("btn-complain");
  const btnViewSite = document.getElementById("btn-view-site");

  // Variáveis globais de controle para guardar o contexto atual da busca
  let currentCompanyName = "";
  let currentCompanyId = null;
  let companyExists = false;

  // Inicia detectando automaticamente o nome da empresa na página atual
  autoDetectPageCompany();

  // Escuta clique no botão de busca manual
  searchBtn.addEventListener("click", () => {
    const query = searchInput.value.trim();
    if (query) {
      fetchCompanyScore(query);
    }
  });

// 🚀 LOGICA DE REDIRECIONAMENTO TOTALMENTE BLINDADA
  btnEvaluate.addEventListener("click", (e) => {
    e.preventDefault();
    
    // Pega o nome da empresa: primeiro tenta a variável global, se estiver vazia pega o que está digitado no input
    const nomeEmpresa = currentCompanyName || searchInput.value.trim();
    
    if (nomeEmpresa) {
      const encodedName = encodeURIComponent(nomeEmpresa);
      chrome.tabs.create({ url: `${FRONTEND_BASE_URL}/avaliacao.html?empresa=${encodedName}` });
    } else {
      // Se não tiver nada digitado nem detectado, abre a página de avaliação limpa
      chrome.tabs.create({ url: `${FRONTEND_BASE_URL}/avaliacao.html` });
    }
  });

  btnComplain.addEventListener("click", (e) => {
    e.preventDefault();
    
    // Pega o nome da empresa da variável global ou do input
    const nomeEmpresa = currentCompanyName || searchInput.value.trim();
    
    if (nomeEmpresa) {
      const encodedName = encodeURIComponent(nomeEmpresa);
      chrome.tabs.create({ url: `${FRONTEND_BASE_URL}/denuncias.html?empresa=${encodedName}` });
    } else {
      // Se não tiver nada digitado nem detectado, abre a página de denúncias limpa
      chrome.tabs.create({ url: `${FRONTEND_BASE_URL}/denuncias.html` });
    }
  });

  btnViewSite.addEventListener("click", (e) => {
    e.preventDefault();
    if (companyExists && currentCompanyId) {
      chrome.tabs.create({ url: `${FRONTEND_BASE_URL}/avaliacoes.html?id=${currentCompanyId}` });
    } else {
      chrome.tabs.create({ url: `${FRONTEND_BASE_URL}/index.html` });
    }
  });
  btnViewSite.addEventListener("click", () => {
    if (companyExists && currentCompanyId) {
      chrome.tabs.create({ url: `${FRONTEND_BASE_URL}/avaliacoes.html?id=${currentCompanyId}` });
    } else {
      chrome.tabs.create({ url: `${FRONTEND_BASE_URL}/index.html` });
    }
  });

  /**
   * Tenta detectar a empresa na aba ativa do navegador
   */
  async function autoDetectPageCompany() {
    try {
      showLoading(true);
      
      // Obtém a aba ativa
      const [tab] = await chrome.tabs.query({ active: true, currentWindow: true });
      if (!tab) {
        showLoading(false);
        return;
      }

      // Envia mensagem para o content.js injetado na página
      chrome.tabs.sendMessage(tab.id, { action: "detectCompany" }, (response) => {
        showLoading(false);
        
        if (chrome.runtime.lastError) {
          console.warn("Não foi possível injetar content script nesta página:", chrome.runtime.lastError);
          return;
        }

        if (response) {
          const { selectedText, detectedCompanyName } = response;
          
          // Prioriza o texto ativamente selecionado pelo mouse
          if (selectedText && selectedText.length < 50) {
            searchInput.value = selectedText;
            showDetectedIndicator("Seleção detectada na página");
            fetchCompanyScore(selectedText);
          } 
          // Caso contrário, usa o nome extraído estruturalmente da página
          else if (detectedCompanyName) {
            searchInput.value = detectedCompanyName;
            showDetectedIndicator(`Empresa identificada: ${detectedCompanyName}`);
            fetchCompanyScore(detectedCompanyName);
          }
        }
      });
    } catch (err) {
      console.error("Erro na autodetecção:", err);
      showLoading(false);
    }
  }

  /**
   * Exibe indicador de detecção automática
   */
  function showDetectedIndicator(text) {
    detectedIndicator.querySelector("span").textContent = text;
    detectedIndicator.classList.remove("hidden");
    setTimeout(() => {
      detectedIndicator.classList.add("hidden");
    }, 4000);
  }

  /**
   * Consulta a API do SafeHer para obter os scores
   */
  async function fetchCompanyScore(companyName) {
    showLoading(true);
    hideCards();

    // Atualiza o estado global com o nome buscado preventivamente
    currentCompanyName = companyName;
    companyExists = false;
    currentCompanyId = null;

    try {
      const url = `${API_BASE_URL}/empresa/score?nome=${encodeURIComponent(companyName)}`;
      const res = await fetch(url);
      
      showLoading(false);

      if (res.ok) {
        const data = await res.json();
        renderResult(data);
      } else if (res.status === 404) {
        renderNoData(companyName);
      } else {
        throw new Error("Erro na requisição à API");
      }
    } catch (err) {
      console.error("Erro ao buscar dados:", err);
      showLoading(false);
      renderError(companyName);
    }
  }

  /**
   * Renderiza a empresa quando cadastrada no banco
   */
  function renderResult(data) {
    resultCard.classList.remove("hidden");
    
    // Atualiza estados globais para os cliques dos botões
    currentCompanyName = data.nome;
    currentCompanyId = data.id;
    companyExists = true;

    // Atualiza nome da empresa e avatar
    document.getElementById("company-name").textContent = data.nome;
    
    const avatar = document.getElementById("company-avatar");
    avatar.innerHTML = data.nome.charAt(0).toUpperCase();

    // Atualiza o Badge de Status
    const statusBadge = document.getElementById("status-badge");
    statusBadge.textContent = data.status;
    statusBadge.className = "badge"; // Reseta classes
    
    if (data.status === "Segura") {
      statusBadge.classList.add("badge-success");
    } else if (data.status === "Atenção") {
      statusBadge.classList.add("badge-warning");
    } else {
      statusBadge.classList.add("badge-danger");
    }

    // Atualiza o Score e o Círculo de Progresso
    updateScoreCircle(data.score);

    // Resumo de reputação
    document.getElementById("reputation-summary").textContent = data.resumo;

    // Média de estrelas
    updateStars(data.mediaAvaliacoes);
    document.getElementById("rating-avg").textContent = data.mediaAvaliacoes.toFixed(1);
    document.getElementById("total-reviews").textContent = data.totalAvaliacoes;

    // Número de denúncias
    document.getElementById("total-complaints").textContent = data.denuncias;
  }

  /**
   * Renderiza caso a empresa não exista no banco
   */
  function renderNoData(companyName) {
    noDataCard.classList.remove("hidden");
    document.getElementById("no-data-company-name-display").textContent = `"${companyName}"`;
    
    currentCompanyName = companyName;
    companyExists = false;
    currentCompanyId = null;
  }

  /**
   * Renderiza erro de conexão (caso o backend esteja desligado)
   */
  function renderError(companyName) {
    noDataCard.classList.remove("hidden");
    document.getElementById("no-data-company-name-display").textContent = "Conexão Offline";
    
    const desc = noDataCard.querySelector(".no-data-desc");
    desc.innerHTML = `<span style="color: var(--red-danger); font-weight: 600;">Não foi possível conectar ao SafeHer.</span><br>Certifique-se de que o servidor back-end está rodando em <strong>${API_BASE_URL}</strong>!`;
    
    currentCompanyName = companyName;
    companyExists = false;
    currentCompanyId = null;
  }

  /**
   * Atualiza visualmente o círculo de progresso do score
   */
  function updateScoreCircle(score) {
    const circle = document.getElementById("score-ring-progress");
    const valueDisplay = document.getElementById("score-value");
    
    if (!circle || !valueDisplay) return;
    
    valueDisplay.textContent = score;
    
    const radius = 40;
    const circumference = 2 * Math.PI * radius;
    
    circle.style.strokeDasharray = `${circumference} ${circumference}`;
    const offset = circumference - (score / 100) * circumference;
    
    setTimeout(() => {
      circle.style.strokeDashoffset = offset;
    }, 50);

    if (score >= 70) {
      circle.style.stroke = "var(--green-safe)";
    } else if (score >= 40) {
      circle.style.stroke = "var(--yellow-warning)";
    } else {
      circle.style.stroke = "var(--red-danger)";
    }
  }

  /**
   * Desenha estrelas dinamicamente
   */
  function updateStars(rating) {
    const container = document.getElementById("star-rating");
    if (!container) return;
    
    container.innerHTML = "";
    
    const fullStars = Math.floor(rating);
    const hasHalf = (rating % 1) >= 0.4;
    
    for (let i = 1; i <= 5; i++) {
      const star = document.createElement("i");
      if (i <= fullStars) {
        star.className = "fa-solid fa-star fill";
      } else if (i === fullStars + 1 && hasHalf) {
        star.className = "fa-solid fa-star-half-stroke fill";
      } else {
        star.className = "fa-regular fa-star";
      }
      container.appendChild(star);
    }
  }

  function showLoading(show) {
    if (show) {
      loader.classList.remove("hidden");
    } else {
      loader.classList.add("hidden");
    }
  }

  function hideCards() {
    resultCard.classList.add("hidden");
    noDataCard.classList.add("hidden");
  }
});