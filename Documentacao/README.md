# Documentação Técnica - SafeHer

## 1. Introdução

O SafeHer é uma plataforma web desenvolvida para promover maior segurança, transparência e suporte às mulheres no ambiente de trabalho. O sistema permite o registro de denúncias anônimas, avaliações corporativas e consulta de informações relacionadas à segurança e reputação de empresas.

Esta documentação apresenta a arquitetura da solução, os componentes do sistema, as tecnologias utilizadas e os principais fluxos de funcionamento da aplicação.

---

# 2. Arquitetura do Sistema

O SafeHer foi desenvolvido utilizando uma arquitetura em camadas, garantindo separação de responsabilidades, organização do código e facilidade de manutenção.

## 2.1 Camada de Apresentação

Responsável pela interação com os usuários da plataforma.

### Funcionalidades

- Cadastro de usuárias
- Login e autenticação
- Registro de denúncias
- Cadastro de avaliações
- Consulta de empresas
- Navegação geral do sistema

### Tecnologias

- React
- JavaScript
- HTML
- CSS

---

## 2.2 Camada de Negócio

Responsável pelo processamento das regras de negócio da aplicação.

### Responsabilidades

- Gerenciamento de usuários
- Gerenciamento de empresas
- Processamento de avaliações
- Processamento de denúncias
- Controle de autenticação
- Integração com serviços externos

### Tecnologias

- Java

---

## 2.3 Camada de Persistência

Responsável pelo armazenamento e recuperação dos dados do sistema.

### Banco de Dados

- PostgreSQL

### Principais Entidades

#### Usuária

Armazena informações de autenticação e perfil.

#### Empresa

Armazena informações corporativas e indicadores de avaliação.

#### Avaliação

Armazena feedbacks e experiências compartilhadas pelas usuárias.

#### Denúncia

Armazena relatos anônimos e evidências associadas.

---

# 3. Funcionalidades do Sistema

## 3.1 Gestão de Usuárias

Permite o gerenciamento das informações das usuárias.

### Operações

- Cadastro
- Login
- Atualização de perfil
- Recuperação de acesso

## 3.2 Gestão de Empresas

Permite o cadastro e consulta de informações corporativas.

### Operações

- Cadastro de empresa
- Consulta de empresas
- Visualização de avaliações

## 3.3 Avaliações

Permite que usuárias compartilhem experiências relacionadas ao ambiente de trabalho.

### Operações

- Cadastro de avaliação
- Consulta de avaliações
- Filtragem por empresa

## 3.4 Denúncias

Permite o registro seguro e anônimo de ocorrências.

### Operações

- Registro de denúncia
- Anexação de evidências
- Consulta de relatos

---

# 4. Integração com Inteligência Artificial

A plataforma utiliza recursos de Inteligência Artificial para auxiliar no processamento e análise de denúncias.

## 4.1 Transcrição de Áudio

Utiliza o Azure Speech para converter arquivos de áudio em texto e capturar a emoção pela voz.

### Fluxo

1. Upload do arquivo de áudio.
2. Envio para processamento.
3. Transcrição automática.
4. Armazenamento do texto gerado.

## 4.2 Classificação de Denúncias

Após o processamento do conteúdo, o sistema pode realizar análises para identificar padrões e classificar ocorrências.

### Objetivos

- Identificação de padrões de comportamento.
- Classificação da gravidade dos relatos.
- Apoio à análise de informações.

---

# 5. Segurança

A aplicação foi projetada considerando privacidade e proteção de dados.

## Medidas Implementadas

- Autenticação de usuários.
- Controle de acesso.
- Validação de entradas.
- Proteção de rotas.
- Preservação do anonimato das denunciantes.
- Armazenamento seguro de informações.

---

# 6. Fluxos Principais

## Fluxo de Denúncias

1. A usuária realiza login na plataforma.
2. A denúncia é registrada.
3. Evidências podem ser anexadas.
4. Os dados são validados pelo backend.
5. O conteúdo é processado pela camada de IA.
6. A denúncia é armazenada no banco de dados.
7. As informações ficam disponíveis para consulta.


## Fluxo de Avaliações

1. A usuária seleciona uma empresa.
2. Uma avaliação é cadastrada.
3. O sistema valida os dados enviados.
4. As informações são armazenadas.
5. Os indicadores da empresa são atualizados.

---

# 7. Tecnologias Utilizadas

## Front-end

- JavaScript
- HTML
- CSS

## Back-end

- Java

## Banco de Dados

- PostgreSQL

## Inteligência Artificial

- Azure Speech

## Controle de Versão

- Git
- GitHub

---

# 8. Considerações Finais

O SafeHer foi desenvolvido para oferecer uma plataforma segura e confiável para mulheres compartilharem experiências profissionais, registrarem denúncias e consultarem informações sobre empresas.

A arquitetura adotada permite futuras expansões da aplicação, integração com novos serviços e evolução contínua dos mecanismos de segurança e análise de dados.
