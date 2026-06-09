# Arquitetura do Sistema - SafeHer

## Visão Geral

O SafeHer utiliza arquitetura em camadas para garantir separação de responsabilidades, escalabilidade e facilidade de manutenção.

## Estrutura Geral

Frontend
↓
Backend
↓
Banco de Dados
↓
Serviços de IA

## Frontend

Responsável pela interface com a usuária.

### Tecnologias

* HTML
* CSS
* JavaScript

### Responsabilidades

* Cadastro
* Login
* Avaliações
* Denúncias
* Navegação

## Backend

Responsável pelas regras de negócio.

### Tecnologias

* Java
* Spring Boot

### Responsabilidades

* Autenticação
* Validações
* Processamento de denúncias
* Integração com IA

## Banco de Dados

### Tecnologia

* PostgreSQL

### Entidades Principais

* Usuária
* Empresa
* Avaliação
* Denúncia

## Serviços Externos

### Azure Speech

* Transcrição de áudio
* Análise de emoção por voz
