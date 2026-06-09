# 🌸 SafeHer: Rede de Proteção e Avaliação de Empresas para Mulheres

---

## Visão Geral do Projeto

O **SafeHer** é uma plataforma digital desenvolvida com o propósito de promover ambientes corporativos mais seguros, transparentes e inclusivos para as mulheres. A solução foi concebida para atuar como uma rede de apoio, informação e conscientização, permitindo que usuárias compartilhem experiências, registrem avaliações e relatem situações de assédio, discriminação ou outras ocorrências relacionadas ao ambiente de trabalho.

A plataforma busca reduzir a assimetria de informações existente no mercado de trabalho, oferecendo indicadores que auxiliam mulheres na avaliação de organizações antes de processos seletivos ou oportunidades profissionais. Ao mesmo tempo, incentiva empresas a adotarem práticas mais responsáveis, fortalecendo uma cultura organizacional baseada em respeito, equidade e segurança.

Por meio da integração entre banco de dados estruturado, recursos de computação em nuvem e serviços de Inteligência Artificial, o sistema consolida informações relevantes para a geração de um **Índice de Segurança Corporativa**, promovendo maior transparência sobre as condições oferecidas pelas organizações às mulheres.

Além disso, a aplicação disponibiliza dados por meio de uma API pública, contribuindo para futuras integrações, análises estatísticas e iniciativas voltadas à promoção da igualdade de gênero no ambiente profissional.

---

## Sumário

* [Visão Geral do Projeto](#visão-geral-do-projeto)
* [O Problema e a Solução](#o-problema-e-a-solução)
* [Principais Funcionalidades](#principais-funcionalidades)
* [Arquitetura e Tecnologias](#arquitetura-e-tecnologias)
* [Fluxo de Rotas](#fluxo-de-rotas-exemplo-avaliações)
* [Histórico de Versões](#histórico-de-versões)
* [Autores e Orientação](#autores-e-orientação)
* [Considerações Finais](#considerações-finais)

---

## O Problema e a Solução

### O Problema

Apesar dos avanços relacionados à igualdade de gênero, milhares de mulheres ainda enfrentam situações de assédio moral, assédio sexual, discriminação e insegurança em seus ambientes de trabalho.

Além dos desafios cotidianos enfrentados dentro das organizações, existe uma dificuldade significativa em obter informações confiáveis sobre a cultura corporativa de empresas antes da participação em processos seletivos ou da aceitação de oportunidades profissionais.

Outro problema recorrente está relacionado à ausência de canais independentes que permitam a realização de denúncias de forma segura, protegendo a identidade das vítimas e incentivando o registro de ocorrências.

### A Solução

O SafeHer foi desenvolvido para atuar como uma plataforma centralizadora de informações, avaliações e denúncias relacionadas à experiência feminina no mercado de trabalho.

A solução oferece:

* Registro de denúncias e relatos;
* Avaliação de empresas com base em experiências reais;
* Consulta pública de indicadores organizacionais;
* Disponibilização de dados por meio de API pública;
* Recursos inteligentes para análise e processamento de informações;
* Acesso facilitado a conteúdos informativos relacionados aos direitos das mulheres.

Dessa forma, o sistema busca fortalecer a transparência corporativa, ampliar o acesso à informação e contribuir para a construção de ambientes profissionais mais seguros e inclusivos.

---

## Principais Funcionalidades

### Registro de Denúncias

Permite o cadastro de relatos relacionados a assédio, discriminação ou outras situações inadequadas ocorridas no ambiente corporativo, contribuindo para a geração de indicadores e para o monitoramento das organizações.

### Sistema de Avaliações

Possibilita que usuárias compartilhem experiências relacionadas às empresas, criando uma base colaborativa de informações sobre segurança, respeito e qualidade do ambiente de trabalho.

### Índice de Segurança Corporativa

Consolida informações provenientes das avaliações, denúncias e demais registros da plataforma para gerar indicadores que auxiliam na análise do comprometimento das organizações com boas práticas de inclusão e proteção às mulheres.

### API Pública

Disponibiliza dados e métricas produzidos pelo sistema para futuras integrações com aplicações externas, pesquisas acadêmicas e análises estatísticas.

### Processamento Inteligente

Integra recursos de Inteligência Artificial para transcrição e análise de conteúdos em áudio, apoiando a classificação e interpretação das informações registradas na plataforma.

### Conteúdos Informativos

Disponibiliza materiais e informações voltados à conscientização sobre direitos, segurança e inclusão feminina no ambiente corporativo.

---

## Arquitetura e Tecnologias

A arquitetura do SafeHer foi projetada com foco em organização, escalabilidade, manutenção e separação adequada de responsabilidades entre as camadas da aplicação.

### Front-End

Desenvolvido utilizando **React**, **JavaScript**, **HTML5** e **CSS3**, sendo responsável pela interface gráfica, experiência do usuário e comunicação com os serviços disponibilizados pelo backend.

### Back-End

Implementado em **Java** utilizando o **Spark Framework**, responsável pelo processamento das regras de negócio, validações, gerenciamento das rotas e comunicação com o banco de dados.

### Inteligência Artificial

Integração com **OpenAI Whisper v2**, utilizada para transcrição de áudios e apoio aos processos de análise das informações recebidas pela plataforma.

### Banco de Dados

Utilização do **PostgreSQL** para persistência dos dados da aplicação, garantindo integridade, organização e confiabilidade das informações armazenadas.

### Computação em Nuvem

Hospedagem e gerenciamento dos serviços em ambiente de nuvem **Microsoft Azure**, proporcionando disponibilidade, escalabilidade e suporte à infraestrutura da solução.

---

## Fluxo de Rotas (Exemplo: Avaliações)

Os serviços disponibilizados pela aplicação seguem uma arquitetura baseada em APIs REST, responsáveis pela comunicação entre frontend, backend e banco de dados.

### Endpoints Disponíveis

#### Cadastro de Avaliação

```http
POST /avaliacoes
```

Responsável pelo registro de uma nova avaliação no sistema.

#### Consulta de Avaliação por Identificador

```http
GET /avaliacoes/:id
```

Retorna os dados completos de uma avaliação específica.

#### Listagem de Avaliações

```http
GET /avaliacoes
```

Retorna todas as avaliações cadastradas na plataforma.

#### Consulta por Empresa

```http
GET /avaliacoes/empresa/:empresaId
```

Retorna avaliações e indicadores relacionados a uma empresa específica.

---

## Histórico de Versões

### Versão 1.0.0 (22/05/2026)

Principais entregas realizadas:

* Definição da proposta de solução;
* Levantamento dos requisitos do sistema;
* Estruturação das entidades de negócio;
* Planejamento da arquitetura geral da aplicação;
* Modelagem inicial do banco de dados;
* Definição dos recursos inteligentes e serviços externos.

### Versão 1.5.0 (30/05/2026)

Principais evoluções:

* Desenvolvimento das interfaces do usuário;
* Estruturação da navegação da aplicação;
* Refinamento das regras de negócio;
* Evolução da modelagem de dados;
* Construção dos primeiros fluxos operacionais do sistema.

### Versão 2.0.0 (09/06/2026)

Principais entregas realizadas:

* Integração entre frontend, backend e banco de dados;
* Implementação das funcionalidades previstas para a plataforma;
* Consolidação da arquitetura da aplicação;
* Integração dos recursos de Inteligência Artificial;
* Utilização de serviços em nuvem para suporte à solução;
* Refinamento das interfaces e da experiência do usuário;
* Melhorias estruturais na persistência e tratamento dos dados;
* Preparação da aplicação para demonstração e entrega final.

---

## Autores e Orientação

### Equipe de Desenvolvimento

O projeto **SafeHer** foi desenvolvido por alunas do curso de Ciência da Computação da Pontifícia Universidade Católica de Minas Gerais (PUC Minas), por meio de uma abordagem colaborativa envolvendo atividades de análise, modelagem, implementação, testes e documentação.

#### Integrantes da Equipe (ordem alfabética)

* Kamilly Oliveira
* Lanna da Silva
* Maria Fernanda
* Sofia

### Professores Orientadores

O desenvolvimento do projeto contou com a orientação acadêmica dos professores:

* Humberto
* Ilyo

### Instituição

**Pontifícia Universidade Católica de Minas Gerais (PUC Minas)**

Projeto desenvolvido no contexto da disciplina de Trabalho Interdisciplinar, contemplando atividades relacionadas a engenharia de software, banco de dados, desenvolvimento web, computação em nuvem e Inteligência Artificial.

---

## Considerações Finais

O SafeHer representa a aplicação prática de conhecimentos adquiridos ao longo da formação acadêmica, reunindo conceitos de desenvolvimento de software, banco de dados, computação em nuvem e Inteligência Artificial para abordar um problema social relevante.

A plataforma foi concebida com o objetivo de promover maior transparência no ambiente corporativo, fortalecer o acesso à informação e contribuir para a construção de espaços profissionais mais seguros para as mulheres.

Ao integrar tecnologia, análise de dados e participação colaborativa dos usuários, o projeto busca gerar impacto positivo tanto para profissionais quanto para organizações comprometidas com a promoção da igualdade e do respeito no ambiente de trabalho.

---

**Última atualização:** 09 de junho de 2026.
