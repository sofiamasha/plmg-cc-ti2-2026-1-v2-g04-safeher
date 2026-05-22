# 🌸 SafeHer: Rede de Proteção e Avaliação de Empresas para Mulheres 🎀

<p align="center">
  <img src="https://img.shields.io/github/license/ICEI-PUC-Minas-CC-TI/plmg-cc-ti2-2026-1-v2-g04-safeher?color=ff69b4&style=for-the-badge" alt="License">
  <img src="https://img.shields.io/github/v/release/ICEI-PUC-Minas-CC-TI/plmg-cc-ti2-2026-1-v2-g04-safeher?color=ffb6c1&style=for-the-badge" alt="Release">
  <img src="https://img.shields.io/badge/maintained-yes-ff1493.svg?style=for-the-badge" alt="Maintained">
</p>

---

## 💗 Visão Geral do Projeto

O **SafeHer** é uma plataforma digital inovadora desenvolvida com o propósito de transformar o ambiente corporativo num espaço mais seguro, justo e acolhedor para as mulheres. A aplicação funciona como uma rede de apoio e fiscalização ativa, permitindo o gerenciamento completo de registos e o controlo de fluxos críticos de segurança. O sistema viabiliza que as usuárias relatem situações de assédio e discriminação vividas no ambiente de trabalho.

Além disso, através de dados estruturados e de uma API pública, a plataforma consolida indicadores de boas práticas e um **score/índice de segurança** para pontuar o comprometimento das organizações com a igualdade de género, promovendo total transparência no mercado de trabalho.

💕 *Tecnologia e Dados a serviço da Segurança Feminina.* 💕

---

## 💮 Sumário
* [🎯 Visão Geral do Projeto](#-visão-geral-do-projeto)
* [✨ Principais Funcionalidades](#-principais-funcionalidades)
* [🛠️ Arquitetura e Tecnologias](#-arquitetura-e-tecnologias)
* [🗄️ Fluxo de Rotas](#%EF%B8%8F-fluxo-de-rotas-exemplo-avalia%C3%A7%C3%B5es)
* [⏱️ Histórico de Versões](#%EF%B8%8F-histórico-de-versões)
* [👥 Autores e Orientação](#-autores-e-orientação)
* [📄 Licença](#-licença)

---

## 🎯 O Problema & A Solução

### ❌ O Problema
* **Insegurança e Vulnerabilidade:** Milhões de mulheres enfrentam assédio e discriminação diariamente no ecossistema profissional.
* **A Barreira da Informação:** Falta de dados reais sobre quais empresas são de facto seguras para o público feminino.
* **Silêncio Corporativo:** Dificuldade em encontrar canais de denúncia externos fiáveis, uma vez que as estruturas tradicionais costumam falhar no anonimato.

### 💖 A Solução
* **Plataforma Web Inteligente:** Espaço digital protegido e focado no anonimato total da utilizadora.
* **Mapeamento e Transparência:** Consulta pública de indicadores e histórico corporativo de compliance.
* **Análise Avançada:** Integração de processos cognitivos para mapear padrões de comportamento e gerar um ecossistema seguro.

---

## ✨ Principais Funcionalidades

* 📑 **Canais de Denúncia Anónima:** Registo blindado de relatos com garantia de proteção e desvinculação de identidade.
* 🔍 **Índice de Confiabilidade:** Consulta rápida ao nível de segurança de organizações antes de processos seletivos.
* 📊 **API Pública Compartilhada:** Fornecimento de dados analíticos sobre scores organizacionais e métricas de igualdade de género.
* 🧠 **Processamento Inteligente:** Integração com o ecossistema de IA para transcrição avançada de áudio e análise de ocorrências.

---

## 🛠️ Arquitetura e Tecnologias

O ecossistema foi projetado priorizando estabilidade lógica, segurança da informação e tratamento rigoroso das regras de negócio no backend:

* 💻 **Front-end:** Desenvolvido em **React / JavaScript** integrado com HTML5 e CSS3 para interfaces ricas, responsivas e intuitivas.
* ⚙️ **Back-end:** Construído em **Java** utilizando o **Spark Framework**, responsável por uma orquestração robusta de APIs.
* 🧠 **Inteligência Artificial:** Integração com **OpenAI Whisper v2** para transcrição avançada e análise de áudios.
* 🗄️ **Banco de Dados:** **PostgreSQL** hospedado na nuvem da **Azure**, garantindo persistência altamente estável e escalável.

---

## 🗄️ Fluxo de Rotas (Exemplo: Avaliações)

A porta de entrada das requisições é orquestrada no controller por meio dos seguintes endpoints lógicos do Spark Framework:

* `POST /avaliacoes` -> Insere uma nova avaliação corporativa.
* `GET /avaliacoes/:id` -> Busca os detalhes de um registo específico.
* `GET /avaliacoes` -> Lista todos os registos do sistema.
* `GET /avaliacoes/empresa/:empresaId` -> Filtra os dados estatísticos por organização.

---

## ⏱️ Histórico de Versões

* **🌷 1.0.0 (2026-05-22):**
  * Definição da especificação lógica e arquitetura do ecossistema.
  * Implementação do banco de dados relacional PostgreSQL na Azure.
  * Integração das rotas do Spark Framework e motor de Inteligência Artificial OpenAI Whisper.

---

## 👥 Autores e Orientação

✨ **Informações de Citação e Integrantes:**

A lista completa das alunas contribuidoras que idealizaram este ecossistema e dos professores orientadores responsáveis pela tutela académica encontra-se integrada dinamicamente no arquivo de metadados do projeto.

Para visualizar os integrantes, detalhes académicos e atribuições oficiais, consulte a secção lateral direita da página deste repositório em **"Cite this repository"** ou aceda diretamente ao arquivo de configuração [CITATION.cff](CITATION.cff).

---

## 📄 Licença

Este projeto é protegido e distribuído sob os termos da **Licença MIT**. Para mais informações, consulte o arquivo `LICENSE` no repositório.
