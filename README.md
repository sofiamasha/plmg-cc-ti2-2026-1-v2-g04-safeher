# 🌸 SafeHer: Rede de Proteção e Avaliação de Empresas para Mulheres

---

## Visão Geral do Projeto

O **SafeHer** é uma plataforma digital desenvolvida com o objetivo de promover ambientes corporativos mais seguros, transparentes e inclusivos para as mulheres. A solução atua como uma rede de apoio, informação e monitoramento, permitindo que usuárias registrem avaliações e relatem situações de assédio, discriminação ou outras experiências negativas vivenciadas no contexto profissional.

A proposta da plataforma é ampliar o acesso a informações confiáveis sobre organizações, fornecendo indicadores que auxiliem mulheres na tomada de decisões relacionadas ao mercado de trabalho e incentivem empresas a adotarem práticas mais responsáveis e alinhadas aos princípios de equidade e respeito.

Por meio da integração entre banco de dados estruturado, serviços inteligentes e mecanismos de análise de informações, o sistema consolida um **índice de segurança corporativa**, permitindo avaliar o comprometimento das organizações com a proteção e valorização das mulheres no ambiente profissional.

Além de oferecer canais de denúncia e avaliação, a plataforma disponibiliza dados por meio de uma API pública, contribuindo para a transparência e para a construção de um ecossistema corporativo mais seguro.

---

## Sumário

* [Visão Geral do Projeto](#visão-geral-do-projeto)
* [O Problema e a Solução](#o-problema-e-a-solução)
* [Principais Funcionalidades](#principais-funcionalidades)
* [Arquitetura e Tecnologias](#arquitetura-e-tecnologias)
* [Fluxo de Rotas](#fluxo-de-rotas-exemplo-avaliações)
* [Histórico de Versões](#histórico-de-versões)
* [Autores e Orientação](#autores-e-orientação)

---

## O Problema e a Solução

### O Problema

Apesar dos avanços relacionados à igualdade de gênero, milhares de mulheres ainda enfrentam situações de assédio moral, assédio sexual, discriminação e insegurança em seus ambientes de trabalho.

Além disso, existe uma dificuldade significativa em obter informações confiáveis sobre a cultura organizacional das empresas antes de ingressar em processos seletivos ou assumir novas oportunidades profissionais.

Outro desafio relevante é a ausência de canais externos independentes que permitam o relato seguro dessas situações, preservando o anonimato e a proteção das vítimas.

### A Solução

O SafeHer foi concebido para atuar como uma plataforma centralizadora de informações, avaliações e denúncias relacionadas à experiência feminina no mercado de trabalho.

A solução oferece:

* Registro seguro de denúncias e relatos;
* Consulta pública de indicadores organizacionais;
* Avaliação de empresas com base em experiências reais;
* Disponibilização de métricas por meio de API pública;
* Utilização de recursos inteligentes para processamento e análise de informações.

Dessa forma, o sistema busca fortalecer a transparência corporativa e contribuir para a construção de ambientes profissionais mais seguros e inclusivos.

---

## Principais Funcionalidades

### Registro de Denúncias

Permite o envio de relatos relacionados a assédio, discriminação ou outras situações inadequadas ocorridas no ambiente corporativo, preservando a segurança das usuárias e a integridade das informações registradas.

### Sistema de Avaliações

Possibilita que usuárias compartilhem experiências relacionadas às empresas, contribuindo para a formação de indicadores coletivos de confiabilidade e segurança.

### Índice de Segurança Corporativa

Consolida informações provenientes de avaliações, denúncias e demais registros da plataforma para gerar métricas que auxiliem na análise do comprometimento das organizações com boas práticas de inclusão e proteção às mulheres.

### API Pública

Disponibiliza dados e indicadores produzidos pelo sistema, permitindo futuras integrações e análises por aplicações externas.

### Processamento Inteligente

Integra recursos de inteligência artificial para transcrição e análise de conteúdos em áudio, apoiando a classificação e interpretação de informações registradas na plataforma.

---

## Arquitetura e Tecnologias

A arquitetura do SafeHer foi projetada com foco em organização, escalabilidade e separação adequada de responsabilidades entre as camadas da aplicação.

### Front-End

Desenvolvido utilizando **React**, **JavaScript**, **HTML5** e **CSS3**, sendo responsável pela interface gráfica, experiência do usuário e interação com os serviços disponibilizados pelo backend.

### Back-End

Implementado em **Java** utilizando o **Spark Framework**, responsável pelo processamento das regras de negócio, validações, gerenciamento das rotas e comunicação com o banco de dados.

### Inteligência Artificial

Integração com o modelo **OpenAI Whisper v2**, utilizado para transcrição de áudios e apoio aos processos de análise das informações recebidas pela plataforma.

### Banco de Dados

Utilização do **PostgreSQL** para persistência de dados, hospedado em infraestrutura de nuvem da **Microsoft Azure**, garantindo disponibilidade, confiabilidade e escalabilidade para a aplicação.

---

## Fluxo de Rotas (Exemplo: Avaliações)

Os serviços disponibilizados pela aplicação são organizados por meio de endpoints REST responsáveis pela comunicação entre frontend, backend e banco de dados.

### Endpoints Disponíveis

* `POST /avaliacoes`
  Responsável pelo cadastro de uma nova avaliação.

* `GET /avaliacoes/:id`
  Retorna os dados de uma avaliação específica.

* `GET /avaliacoes`
  Lista todas as avaliações registradas no sistema.

* `GET /avaliacoes/empresa/:empresaId`
  Retorna avaliações e indicadores associados a uma empresa específica.

---

## Histórico de Versões

### Versão 1.0.0 (22/05/2026)

Principais entregas realizadas:

* Definição da arquitetura geral do sistema;
* Estruturação das entidades de negócio;
* Implementação do banco de dados PostgreSQL;
* Configuração da infraestrutura em nuvem Azure;
* Desenvolvimento das rotas da API utilizando Spark Framework;
* Integração inicial dos serviços de Inteligência Artificial;
* Organização dos módulos de frontend e backend da aplicação.

---

## Autores e Orientação

### Integrantes e Professores Orientadores

As informações referentes às integrantes responsáveis pelo desenvolvimento do projeto, bem como aos docentes orientadores, encontram-se documentadas oficialmente nos metadados do repositório.

Para consultar a composição completa da equipe, atribuições acadêmicas e informações de citação, utilize a seção **"Cite this repository"** disponível na lateral do repositório ou acesse diretamente o arquivo:

📄 **[CITATION.cff](CITATION.cff)**

---

## Considerações Finais

O SafeHer representa a aplicação prática de conceitos de desenvolvimento de software, banco de dados, computação em nuvem e inteligência artificial para a resolução de um problema social relevante.

A plataforma busca combinar tecnologia, transparência e acessibilidade para contribuir com a construção de ambientes corporativos mais seguros, promovendo informação confiável e apoio às mulheres em sua trajetória profissional.
