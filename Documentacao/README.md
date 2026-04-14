# Relatório de Documentação do Projeto - SafeHer

## 1. Identificação do Projeto
* **Título do Projeto:** SafeHer - Plataforma de Segurança para Mulheres
* **Equipe Desenvolvedora:** Kamilly Santos, Lanna Silva, Maria Fernanda Maro e Sofia de Abreu
* **Área de Atuação:** Tecnologia, Impacto Social e Igualdade de Gênero (Alinhado ao ODS 5 da ONU)

---

## 2. Contextualização e Justificativa
Milhões de mulheres enfrentam assédio, discriminação e insegurança no ambiente profissional diariamente. O atual cenário corporativo apresenta barreiras significativas à informação, incluindo:
* **Incerteza:** Falta de dados reais sobre quais empresas são de fato seguras.
* **Silêncio:** Dificuldade em encontrar canais de denúncia que sejam confiáveis e preservem o anonimato.
* **Opacidade:** Baixa transparência corporativa no que tange às políticas de igualdade de gênero.

Frequentemente, o assédio e a desigualdade ocorrem nas sombras da estrutura corporativa. O projeto SafeHer surge para transformar essa insegurança em empoderamento digital através de um ambiente protegido, inteligente e confiável.

---

## 3. Objetivos do Projeto
O objetivo central da plataforma web SafeHer é oferecer um canal seguro de apoio, denúncia e avaliação corporativa, centrado na voz feminina. 

**Objetivos Específicos:**
* Fornecer um **Índice de Segurança** das empresas, permitindo consultas antes de candidaturas.
* Criar um canal de denúncias seguro, com anonimato total.
* Oferecer um espaço para funcionárias avaliarem o ambiente e a cultura organizacional.
* Disponibilizar conteúdos educativos sobre direitos trabalhistas e prevenção ao assédio.
* Exibir **Vagas Éticas**, priorizando oportunidades em empresas com políticas reais de igualdade de gênero.

---

## 4. Público-Alvo e Impacto Social
**Público Direto:**
* Mulheres ativas no mercado de trabalho.
* Mulheres em busca de emprego ou transição de carreira.
* Vítimas de assédio laboral que necessitam de apoio e orientação.

**Beneficiados Indiretos:**
* Empresas Éticas que buscam atrair talentos qualificados e melhorar sua cultura interna.
* ONGs e instituições focadas no suporte jurídico, psicológico e impacto social.

---

## 5. Arquitetura Tecnológica e Funcionalidades (Sprints 1 e 2)
A plataforma integra Inteligência Artificial (**Algoritmo SafeWork IA**) para transformar dados complexos em indicadores de segurança, mitigando vieses através de Processamento de Linguagem Natural (NLP) e análise preditiva.

**Funcionalidades de Inteligência Artificial implementadas:**
1. **Chat com IA:** Atendimento automático para orientação imediata a mulheres em situação de risco, permitindo envio rápido de mensagem ou áudio.
2. **Validação de Provas:** Sistema para envio de prints, fotos ou boletins, com verificação de autenticidade e identificação de possíveis edições nos arquivos.
3. **Denúncia Inteligente:** Análise automática dos relatos enviados, identificação de palavras-chave e classificação do nível de gravidade do caso.

**Desenvolvimento do Front-End:**
Foram projetadas interfaces responsivas e intuitivas, contemplando:
* Tela inicial com acesso rápido a avaliações e denúncias.
* Página de Vagas Éticas com filtros para trabalho remoto, CLT e PJ.
* Sistema de cadastro fragmentado (modal de escolha para Empresa ou Mulher) e autenticação de usuários.
* **API Pública:** Rota projetada para que outros sistemas de RH e CRMs consultem o histórico das empresas e seu Índice de Segurança, mantendo a privacidade estrita dos relatos textuais e dados pessoais das usuárias fortemente criptografados.

---

## 6. Modelo de Negócio Sustentável (B2B SaaS)
A estrutura de monetização foi idealizada para manter a independência e a integridade da missão social do projeto:
* **Planos de Recrutamento:** Assinaturas para corporações divulgarem vagas segmentadas para talentos qualificados.
* **Selo "Empresa Segura":** Certificação anual fundamentada em auditoria de dados e Inteligência Artificial, atestando o compromisso organizacional real com a diversidade.
* **Vagas Patrocinadas (Ad Revenue):** Destaque prioritário em anúncios para organizações com altos índices de segurança.

---

## 7. Modelagem de Dados e Banco de Dados (PostgreSQL)
A arquitetura do banco de dados (esquema relacional e script SQL) foi estruturada no PostgreSQL para assegurar a integridade, a segurança e a transparência da informação.

**Entidades Principais (Esquema Relacional):**
* `USUARIA (id, nome, email, senha, data_cadastro, empresa_id)`: Centraliza o acesso e gerencia as credenciais das colaboradoras.
* `EMPRESA (id, nome, setor, local, indice_seg)`: Registra as corporações e armazena a métrica de segurança calculada pela IA.
* `AVALIACAO (id, nota, comentario, data, usuaria_id)`: Armazena os feedbacks de clima organizacional vinculados às usuárias.
* `DENUNCIA (id, descricao, anonima, data, avaliacao_id, empresa_id)`: Estrutura os relatos de abuso, garantindo suporte lógico para denúncias anônimas.

---

## 8. Conclusões e Trabalhos Futuros
Durante a execução das Sprints 1 e 2, foram consolidados os modelos de dados conceituais e lógicos, os diagramas relacionais, scripts SQL, a estruturação visual das telas (Front-End) e o fluxo inteligente de validação de provas e análise via IA.

Para as próximas iterações do desenvolvimento, estão previstos:
* A implementação final do **Chat com IA** para suporte de emergência imediato.
* A disponibilização técnica e documentação da **API Pública** para integração contínua de dados com o mercado.

O projeto SafeHer avança substancialmente em seu propósito de mitigar a desigualdade de gênero através da tecnologia e promover a segurança psicológica no trabalho, unindo tecnologia avançada e impacto social real.