# 📄 Relatório de Documentação — SafeHer

## 1. Identificação do Projeto

- **Projeto:** SafeHer — Plataforma de Segurança para Mulheres no Ambiente de Trabalho  
- **Equipe:** Kamilly Santos, Lanna Silva, Maria Fernanda Maro e Sofia de Abreu  
- **Área:** Tecnologia com impacto social (ODS 5 — Igualdade de Gênero)

---

## 2. Contextualização

O ambiente de trabalho ainda apresenta altos índices de assédio, desigualdade e subnotificação de denúncias, principalmente pela falta de canais seguros e anônimos.

O **SafeHer** surge como uma plataforma digital que busca centralizar relatos de forma segura, promovendo mais transparência sobre ambientes corporativos e contribuindo para decisões profissionais mais informadas.

---

## 3. Objetivos

### Objetivo geral
Criar uma plataforma de apoio, denúncia e avaliação de empresas com foco em segurança e anonimato.

### Objetivos específicos
- Permitir denúncias anônimas de assédio no ambiente de trabalho  
- Disponibilizar avaliações de empresas baseadas em experiências reais  
- Oferecer conteúdo informativo sobre direitos e segurança no trabalho  
- Apoiar a tomada de decisão de mulheres na escolha de ambientes profissionais  

---

## 4. Público-alvo

- Mulheres inseridas ou ingressando no mercado de trabalho  
- Profissionais em transição de carreira  
- Pessoas impactadas por situações de assédio ou ambientes inseguros  

---

## 5. Arquitetura e funcionalidades

A plataforma foi estruturada como uma aplicação web com foco em usabilidade, anonimato e organização de dados sensíveis.

### Principais funcionalidades
- Registro de denúncias anônimas  
- Avaliação de empresas por usuárias  
- Envio de evidências (imagens e arquivos)  
- Organização e consulta de relatos  

### Estrutura do front-end
- Telas de cadastro, login e navegação principal  
- Fluxo separado para empresa e usuária  
- Interface focada em simplicidade e acessibilidade  

### API
- Estrutura preparada para integração com sistemas externos  
- Foco em consulta de dados de empresas e avaliações  

---

## 6. Modelagem de dados

O banco de dados foi estruturado em modelo relacional para garantir organização e integridade das informações.

### Entidades principais
- **Usuária:** dados de autenticação e vínculo com empresa  
- **Empresa:** informações corporativas e índice de avaliação  
- **Avaliação:** feedbacks sobre o ambiente de trabalho  
- **Denúncia:** registros anônimos vinculados a relatos  

---

## 7. Conclusão e próximos passos

Durante as primeiras sprints, foram desenvolvidos os principais artefatos do sistema, incluindo modelagem de dados, estrutura do front-end e definição dos fluxos principais da aplicação.

### Próximas melhorias previstas
- Aprimoramento do fluxo de denúncias e validação de dados  
- Evolução da API para integração externa  
- Expansão da experiência de usuário (UX) e acessibilidade  