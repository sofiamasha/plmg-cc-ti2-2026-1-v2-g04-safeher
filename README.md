# 🌸 SafeHer: Rede de Proteção e Avaliação de Empresas para Mulheres 🎀

<p align="center">
  <img src="https://img.shields.io/github/license/ICEI-PUC-Minas-CC-TI/plmg-cc-ti2-2026-1-v2-g04-safeher?color=ff69b4&style=for-the-badge" alt="License">
  <img src="https://img.shields.io/github/v/release/ICEI-PUC-Minas-CC-TI/plmg-cc-ti2-2026-1-v2-g04-safeher?color=ffb6c1&style=for-the-badge" alt="Release">
  <img src="https://img.shields.io/badge/maintained-yes-ff1493.svg?style=for-the-badge" alt="Maintained">
</p>

---

## 💗 Visão Geral do Projeto

[cite_start]O **SafeHer** é uma plataforma digital inovadora desenvolvida com o propósito de transformar o ambiente corporativo num espaço mais seguro, justo e acolhedor para as mulheres. [cite_start]A aplicação funciona como uma rede de apoio e fiscalização ativa, permitindo o gerenciamento completo de registos e o controlo de fluxos críticos de segurança[cite: 1, 3]. [cite_start]O sistema viabiliza que as usuárias relatem situações de assédio e discriminação vividas no ambiente de trabalho.

[cite_start]Além disso, através de dados estruturados e de uma API pública, a plataforma consolida indicadores de boas práticas e um **score/índice de segurança** para pontuar o comprometimento das organizações com a igualdade de género, promovendo total transparência no mercado de trabalho[cite: 4, 118].

💕 *Tecnologia e Dados a serviço da Segurança Feminina.* 💕

---

## 💮 Sumário
* [🎯 Visão Geral do Projeto](#-visão-geral-do-projeto)
* [✨ Principais Funcionalidades](#-principais-funcionalidades)
* [🛠️ Arquitetura e Tecnologias](#-arquitetura-e-tecnologias)
* [🗄️ Estrutura do Banco de Dados](#%EF%B8%8F-estrutura-do-banco-de-dados)
* [💻 Como Executar o Projeto](#-como-executar-o-projeto)
* [⏱️ Histórico de Versões](#%EF%B8%8F-histórico-de-versões)
* [👩‍💻 Contribuidores](#-contribuidores)
* [🧑‍🏫 Orientação](#-orientação)
* [📄 Licença](#-licença)

---

## 🎯 O Problema & A Solução

### [cite_start]❌ O Problema 
* [cite_start]**Insegurança e Vulnerabilidade:** Milhões de mulheres enfrentam assédio e discriminação diariamente no ecossistema profissional.
* [cite_start]**A Barreira da Informação:** Falta de dados reais sobre quais empresas são de facto seguras para o público feminino[cite: 119].
* [cite_start]**Silêncio Corporativo:** Dificuldade em encontrar canais de denúncia externos fiáveis, uma vez que as estruturas tradicionais costumam falhar no anonimato[cite: 120].

### [cite_start]💖 A Solução [cite: 2, 129]
* [cite_start]**Plataforma Web Inteligente:** Espaço digital protegido e focado no anonimato total da utilizadora[cite: 127, 129].
* [cite_start]**Mapeamento e Transparência:** Consulta pública de indicadores e histórico corporativo de compliance[cite: 4, 144].
* [cite_start]**Análise Avançada:** Integração de processos cognitivos para mapear padrões de comportamento e gerar um ecossistema seguro[cite: 2, 137].

---

## ✨ Principais Funcionalidades

* [cite_start]📑 **Canais de Denúncia Anónima:** Registo blindado de relatos com garantia de proteção e desvinculação de identidade[cite: 131, 147].
* [cite_start]🔍 **Índice de Confiabilidade:** Consulta rápida ao nível de segurança de organizações antes de processos seletivos[cite: 130].
* [cite_start]📊 **API Pública Compartilhada:** Fornecimento de dados analíticos sobre scores organizacionais e métricas de igualdade de género[cite: 4].
* [cite_start]🧠 **Processamento Inteligente:** Algoritmo proprietário (*SafeWork IA*) focado em analisar recorrências e mitigar vieses informacionais[cite: 136].

---

## 🛠️ Arquitetura e Tecnologias

[cite_start]A arquitetura do SafeHer foi desenhada em camadas isoladas para garantir escalabilidade, segurança e excelente divisão de responsabilidades:

* [cite_start]💻 **Front-end:** Desenvolvido em **React / JavaScript** integrado com HTML5 e CSS3 para interfaces ricas, responsivas e intuitivas[cite: 6, 7].
* [cite_start]⚙️ **Back-end:** Construído em **Java** utilizando o **Spark Framework**, responsável por uma orquestração robusta de APIs[cite: 6, 7].
* [cite_start]🧠 **Inteligência Artificial:** Integração com **OpenAI Whisper v2** para transcrição avançada e análise cognitiva/emocional de áudios[cite: 8, 9].
* [cite_start]🗄️ **Banco de Dados:** **PostgreSQL** hospedado na nuvem da **Azure**, garantindo persistência altamente estável e escalável.

---

## 🗄️ Estrutura do Projeto (Modularidade Java)

[cite_start]O código backend segue a organização padrão por camadas (Controller, Service, DAO e Model)[cite: 9, 23]:

src/├── app/       # Classe principal e inicialização (Main)├── service/   # Camada de Negócio (regras do sistema e validações)├── dao/       # Camada de Dados (comunicação direta via SQL/JDBC)├── model/     # Modelagem de Objetos estruturados└── public/    # Assets e páginas do Front-end
---

## 🗄️ Fluxo de Rotas (Exemplo: Avaliações)

A porta de entrada das requisições é orquestrada no controller por meio dos seguintes endpoints lógicos do Spark Framework[cite: 16, 18]:

* `POST /avaliacoes` -> Insere uma nova avaliação corporativa[cite: 16].
* `GET /avaliacoes/:id` -> Busca os detalhes de um registo específico[cite: 17].
* `GET /avaliacoes` -> Lista todos os registos do sistema[cite: 17].
* `GET /avaliacoes/empresa/:empresaId` -> Filtra os dados estatísticos por organização[cite: 18].

---

## 💻 Como Executar o Projeto

### 🎀 Pré-requisitos
Antes de começar, certifique-se de que tem instalado na sua máquina:
* Git
* Java JDK (compatível com Spark Framework)
* SGBD PostgreSQL (local ou acesso à Azure)

### 🌸 Passo a Passo
```bash
# 1. Clone o repositório oficial do grupo
$ git clone [https://github.com/ICEI-PUC-Minas-CC-TI/plmg-cc-ti2-2026-1-v2-g04-safeher.git](https://github.com/ICEI-PUC-Minas-CC-TI/plmg-cc-ti2-2026-1-v2-g04-safeher.git)

# 2. Acesse o diretório raiz do projeto
$ cd plmg-cc-ti2-2026-1-v2-g04-safeher

# 3. Configure a string de conexão do PostgreSQL em suas variáveis locais

# 4. Execute os scripts SQL de migração para estruturar as tabelas (Usuario, Empresa, Avaliacao)

# 5. Inicie a aplicação Java através do seu terminal ou IDE de preferência
⏱️ Histórico de Versões🌷 1.0.0 (2026-05-22):Definição da especificação lógica e arquitetura do ecossistema.  Implementação do banco de dados relacional PostgreSQL na Azure.  Integração das rotas do Spark Framework e motor de Inteligência Artificial OpenAI Whisper.  👩‍💻 ContribuidoresAgradecemos imensamente às desenvolvedoras que planejaram, modelaram e construíram este ecossistema de proteção:  Kamilly Santos 🌸Lanna Silva 🌸Maria Fernanda Oliveira Maro 🌸Sofia de Abreu 🌸🧑‍🏫 OrientaçãoO projeto foi rigorosamente estruturado e desenvolvido sob a tutela académica dos professores:Prof. Humberto Torres Marques Neto 👔Prof. Ilo Amy Saldanha Rivero 👔📄 LicençaEste projeto é protegido e distribuído sob os termos da Licença MIT. Para mais informações, consulte o arquivo LICENSE no repositório.
