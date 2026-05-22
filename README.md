# 🌸 SafeHer: Rede de Proteção e Avaliação de Empresas para Mulheres 🎀

<p align="center">
  <img src="https://img.shields.io/github/license/ICEI-PUC-Minas-CC-TI/plmg-cc-ti2-2026-1-v2-g04-safeher?color=ff69b4&style=for-the-badge" alt="License">
  <img src="https://img.shields.io/github/v/release/ICEI-PUC-Minas-CC-TI/plmg-cc-ti2-2026-1-v2-g04-safeher?color=ffb6c1&style=for-the-badge" alt="Release">
  <img src="https://img.shields.io/badge/maintained-yes-ff1493.svg?style=for-the-badge" alt="Maintained">
</p>

---

## 💗 Visão Geral do Projeto

O **SafeHer** é uma plataforma digital inovadora e segura desenvolvida com o propósito de transformar o ambiente corporativo em um espaço mais seguro, justo e acolhedor para as mulheres. A aplicação funciona como uma rede de apoio e fiscalização, permitindo que as usuárias relatem, de forma totalmente anônima, segura e auditável, situações de assédio e discriminação vividas no ambiente de trabalho. 

Além disso, o sistema consolida dados estruturados para pontuar e avaliar o comprometimento de organizações com a igualdade de gênero e a integridade de suas colaboradoras.

💕 *Feito por mulheres, para proteger mulheres.* 💕

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

### ❌ O Problema
* Subnotificação crítica de casos de assédio moral e sexual no contexto corporativo.
* Canais de denúncia internos tradicionais que muitas vezes falham em blindar a identidade da vítima, gerando medo de retaliação ou demissão.
* Inexistência de um histórico público e transparente sobre a cultura de segurança das empresas.

### 💖 A Solução
* **Canal Descentralizado:** Um ambiente externo e seguro onde a identidade da usuária é totalmente protegida.
* **Mapeamento Inteligente:** Agrupamento de recorrências por setores de mercado e CNPJ.
* **Auditoria Social:** Geração de um índice de confiabilidade público para guiar mulheres em suas escolhas de carreira.

---

## ✨ Principais Funcionalidades

* 📑 **Relatos Blindados:** Sistema de cadastro seguro com hashing de dados sensíveis, onde os relatos públicos não possuem qualquer vínculo rastreável com a conta de origem da usuária.
* 🔍 **Busca e Avaliação de Empresas:** Consulta ao histórico corporativo com base em critérios rigorosos de segurança e suporte à mulher.
* 📊 **Painel Analítico:** Visualização estatística sobre a incidência de ocorrências em diferentes setores do mercado.
* 🛡️ **Mecanismos de Moderação:** Filtros lógicos estruturados para assegurar a conformidade, a integridade e o respeito nas publicações da plataforma.

---

## 🛠️ Arquitetura e Tecnologias

O ecossistema foi projetado priorizando estabilidade lógica, segurança da informação e tratamento rigoroso das regras de negócio no backend:

* 🗄️ **Banco de Dados Relacional:** Estrutura altamente íntegra focada no armazenamento seguro de dados de usuários, empresas, relatos e avaliações.
* 🔑 **Mecanismos de Segurança:** Aplicação de criptografia para a preservação completa do sigilo das usuárias.
* ⚙️ **Motores de Busca:** Lógica de processamento otimizada para a indexação de dados corporativos e estatísticos.

---

## 🗄️ Estrutura do Banco de Dados

A arquitetura lógica do banco de dados foi mapeada para separar rigidamente os dados de identificação dos relatos publicados, mantendo o ecossistema seguro e eficiente:

🌸 [Tabela: Usuario] 🌸
└── id (PK)
└── nome_completo
└── email
│
└── 1 : * 🌸 [Tabela: Relato] 🌸
└── id (PK)
└── texto_relato
└── data_registro
│
┌────────────────────┘ * : 1
│
🌸 [Tabela: Empresa] 🌸
└── id (PK)
└── cnpj
└── razao_social


---

## 💻 Como Executar o Projeto

### 🎀 Pré-requisitos
Antes de começar, certifique-se de ter instalado em sua máquina:
* Git
* Sistema Gerenciador de Banco de Dados Relacional (SGBD) de sua preferência.
* Ambiente de execução adequado para a lógica do backend.

### 🌸 Passo a Passo
```bash
# 1. Clone o repositório oficial do grupo
$ git clone [https://github.com/ICEI-PUC-Minas-CC-TI/plmg-cc-ti2-2026-1-v2-g04-safeher.git](https://github.com/ICEI-PUC-Minas-CC-TI/plmg-cc-ti2-2026-1-v2-g04-safeher.git)

# 2. Acesse o diretório raiz do projeto
$ cd plmg-cc-ti2-2026-1-v2-g04-safeher

# 3. Configure o seu arquivo de ambiente local com as credenciais do seu banco de dados

# 4. Execute o script SQL para a criação da estrutura de tabelas e relacionamentos

# 5. Inicie o sistema através do terminal de comandos
⏱️ Histórico de Versões
🌷 1.0.0 (2026-05-22):

Definição e especificação dos requisitos fundamentais do sistema.

Modelagem conceitual e script final do Banco de Dados Relacional.

Implementação dos motores e algoritmos para processamento de denúncias anônimas e catalogação de empresas.

👩‍💻 Contribuidores
Agradecemos imensamente às desenvolvedoras que planejaram, modelaram e construíram este ecossistema de proteção:

Kamilly Santos 🌸

Lanna Silva 🌸

Maria Fernanda Oliveira Maro 🌸

Sofia de Abreu 🌸

🧑‍🏫 Orientação
O projeto foi rigorosamente estruturado e desenvolvido sob a tutela acadêmica dos professores:

Prof. Humberto Torres Marques Neto 👔

Prof. Ilo Amy Saldanha Rivero 👔

📄 Licença
Este projeto é protegido e distribuído sob os termos da Licença MIT. Para maiores informações, consulte o arquivo LICENSE no repositório.
