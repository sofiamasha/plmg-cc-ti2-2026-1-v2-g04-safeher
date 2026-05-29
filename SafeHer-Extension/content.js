/**
  * SafeHer Extension - Content Script
  * Executado nas páginas da web abertas pelo usuário para detectar nomes de empresas.
  */

// Função inteligente de detecção de empresa baseada em seletores conhecidos
function detectCompanyName() {
  const hostname = window.location.hostname.toLowerCase();
  
  // 1. LINKEDIN
  if (hostname.includes("linkedin.com")) {
    const selectors = [
      ".job-details-jobs-unified-top-card__company-name a",
      ".jobs-unified-top-card__company-name a",
      ".jobs-details-top-card__company-url",
      ".job-card-container__company-name",
      ".jobs-post-shared-actor__company",
      ".pv-text-details__right-panel-item-text" // Página de perfil profissional
    ];
    for (const selector of selectors) {
      const el = document.querySelector(selector);
      if (el && el.innerText.trim()) {
        return el.innerText.trim();
      }
    }
  }
  
  // 2. GLASSDOOR
  if (hostname.includes("glassdoor.com")) {
    const selectors = [
      "div[data-test='employer-name']",
      ".employerName",
      ".css-164r41r",
      ".css-1vq45v5",
      "h3.employer-name"
    ];
    for (const selector of selectors) {
      const el = document.querySelector(selector);
      if (el && el.innerText.trim()) {
        // Limpa notas do Glassdoor que costumam vir juntas (ex: "Empresa X \n4.2 ★")
        const namePart = el.innerText.split("\n")[0];
        return namePart.replace(/[0-9\.\★\☆\-\s]/g, "").trim();
      }
    }
  }
  
  // 3. INDEED
  if (hostname.includes("indeed.com")) {
    const selectors = [
      ".jobsearch-JobInfoHeader-companyName",
      ".companyName",
      "div[class*='companyName']"
    ];
    for (const selector of selectors) {
      const el = document.querySelector(selector);
      if (el && el.innerText.trim()) {
        return el.innerText.trim();
      }
    }
  }
  
  // 4. GUPY (Muito comum no Brasil)
  if (hostname.includes("gupy.io")) {
    const selectors = [
      ".company-name",
      "[data-testid='company-name']",
      "h2.css-1f9e2k",
      ".sc-bdnxRM.jXyUro"
    ];
    for (const selector of selectors) {
      const el = document.querySelector(selector);
      if (el && el.innerText.trim()) {
        return el.innerText.trim();
      }
    }
  }

  // 5. CATHO
  if (hostname.includes("catho.com.br")) {
    const selectors = [
      "a.company-name",
      ".job-company",
      "h2.company-title"
    ];
    for (const selector of selectors) {
      const el = document.querySelector(selector);
      if (el && el.innerText.trim()) {
        return el.innerText.trim();
      }
    }
  }

  // 6. DETECÇÃO GENÉRICA DE TÍTULO DA PÁGINA
  // Exemplos de títulos comuns: "Vaga de Emprego na SafeHer - SP", "Trabalhar na Google"
  const title = document.title;
  if (title.includes(" na ") || title.includes(" em ")) {
    const markers = [" na ", " em "];
    for (const marker of markers) {
      if (title.includes(marker)) {
        const parts = title.split(marker);
        if (parts.length > 1) {
          const candidate = parts[parts.length - 1].split("|")[0].split("-")[0].split(":")[0].trim();
          if (candidate && candidate.length < 40) {
            return candidate;
          }
        }
      }
    }
  }

  return "";
}

// Escuta requisições enviadas pelo Popup da extensão
chrome.runtime.onMessage.addListener((request, sender, sendResponse) => {
  if (request.action === "detectCompany") {
    // Tenta ler o texto que o usuário selecionou ativamente com o mouse
    const selected = window.getSelection().toString().trim();
    
    // Tenta ler o nome da empresa estruturado na página
    const detected = detectCompanyName();
    
    sendResponse({
      selectedText: selected,
      detectedCompanyName: detected
    });
  }
  return true; // Mantém a porta de resposta aberta de forma assíncrona
});
