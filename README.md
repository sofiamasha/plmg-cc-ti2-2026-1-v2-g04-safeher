# SafeHer: Rede de Proteção e Avaliação de Empresas para Mulheres

![GitHub license](https://img.shields.io/github/license/ICEI-PUC-Minas-CC-TI/plmg-cc-ti2-2026-1-v2-g04-safeher)
![GitHub release (latest by date)](https://img.shields.io/github/v/release/ICEI-PUC-Minas-CC-TI/plmg-cc-ti2-2026-1-v2-g04-safeher)
![Maintained](https://img.shields.io/badge/maintained-yes-green.svg)

O **SafeHer** é uma plataforma digital desenvolvida com o propósito de transformar o ambiente corporativo em um espaço mais seguro, justo e acolhedor para as mulheres. A aplicação funciona como uma rede de apoio e fiscalização, permitindo que usuárias relatem, de forma totalmente anônima, segura e auditável, situações de assédio e discriminação vividas no ambiente de trabalho, além de pontuar e avaliar o comprometimento de empresas com a igualdade de gênero.

---

## 📌 Sumário
* [Visão Geral do Projeto](#-visão-geral-do-projeto)
* [Principais Funcionalidades](#-principais-funcionalidades)
* [Arquitetura e Tecnologias](#-arquitetura-e-tecnologias)
* [Estrutura do Banco de Dados](#-estrutura-do-banco-de-dados)
* [Como Executar o Projeto](#-como-executar-o-projeto)
* [Histórico de Versões](#-histórico-de-versões)
* [Contribuidores](#-contribuidores)
* [Orientação](#-orientação)
* [Licença](#-licença)

---

## 🚀 Visão Geral do Projeto

Muitas vezes, canais de denúncia internos de grandes corporações falham em proteger a identidade da vítima, gerando medo de retaliação ou demissão. O SafeHer surge como uma solução descentralizada e externa. Reunindo dados estatísticos sobre a conduta das empresas, a plataforma gera um **índice de confiabilidade e segurança** público, auxiliando mulheres a tomarem decisões informadas sobre suas carreiras e incentivando as empresas a adotarem políticas eficazes de compliance e acolhimento.

### O Problema
* Subnotificação de casos de assédio moral e sexual no trabalho.
* Falta de transparência nos canais de denúncia tradicionais.
* Inexistência de um histórico público sobre a cultura de segurança de empresas para candidatas em processos seletivos.

### A Solução
* Canal de relatos blindado e anônimo.
* Mapeamento de recorrências por setores e CNPJ.
* Painel de monitoramento focado no impacto social e na segurança coletiva.

---

## ⚙️ Principais Funcionalidades

* **Relatos Blindados:** Sistema de cadastro seguro com criptografia de dados confidenciais, onde a identidade da usuária é completamente dissociada do relato público.
* **Busca e Avaliação de Empresas:** Consulta de histórico e notas de empresas com base em critérios de segurança, equidade e suporte à mulher.
* **Painel Estatístico (Dashboard):** Visualização de dados analíticos sobre incidências por setor de mercado para auditoria social.
* **Mecanismos de Moderação:** Filtros de conteúdo para garantir a veracidade, o respeito e a conformidade legal dos relatos publicados.

---

## 🛠 Arquitetura e Tecnologias

O ecossistema foi projetado priorizando estabilidade, segurança de dados e alta disponibilidade das regras de negócio.

* **Banco de Dados Relacional:** Estrutura relacional robusta garantindo integridade referencial com tabelas estruturadas de usuários, empresas, relatos e avaliações.
* **Autenticação:** Mecanismos de criptografia e hashing para proteção de dados confidenciais.
* **Ferramentas de Modelagem:** Abstração e automação de processos lógico-computacionais organizados de forma rigorosa.

---

## 🗄 Estrutura do Banco de Dados

A arquitetura de dados foi planejada para mitigar o cruzamento de informações que possam quebrar o anonimato da usuária, estruturando-se estritamente através das seguintes entidades fundamentais:
