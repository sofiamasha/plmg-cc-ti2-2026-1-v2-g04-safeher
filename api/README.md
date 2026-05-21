# 🌸 SafeHer API

API backend em **Node.js + Express** para integração com a extensão de navegador SafeHer.

---

## Início Rápido

```bash
# 1. Entre na pasta da API
cd api

# 2. Instale as dependências (já feito)
npm install

# 3. Configure o .env (já criado a partir do .env.example)
# Edite api/.env se precisar alterar porta, banco, JWT_SECRET, etc.

# 4. Inicie o servidor
npm run dev
```

O servidor sobe em **http://localhost:3000**.

> ⚠️ O PostgreSQL precisa estar rodando com o banco `safeher` criado.

---

## Estrutura do Projeto

```
api/
├── server.js                          # Ponto de entrada
├── src/
│   ├── app.js                         # Configuração Express, CORS, middlewares
│   ├── config/
│   │   └── database.js                # Pool de conexão PostgreSQL
│   ├── controllers/
│   │   ├── auth.controller.js         # Cadastro + Login
│   │   ├── empresas.controller.js     # CRUD empresas + busca + score
│   │   └── avaliacoes.controller.js   # CRUD avaliações
│   ├── middlewares/
│   │   ├── auth.middleware.js         # Validação JWT
│   │   └── error.middleware.js        # Handler global de erros
│   └── routes/
│       ├── index.js                   # Agregador de rotas
│       ├── auth.routes.js             # /api/auth/*
│       ├── empresas.routes.js         # /api/empresas/*
│       └── avaliacoes.routes.js       # /api/avaliacoes/*
├── .env                               # Variáveis de ambiente (não commitado)
├── .env.example                       # Template de variáveis
├── .gitignore
└── package.json
```

---

## Endpoints da API

### 🔓 Autenticação (sem token)

| Método | Rota                | Body                          | Descrição            |
|--------|---------------------|-------------------------------|----------------------|
| POST   | `/api/auth/cadastro` | `{ nome, email, senha }`     | Cria conta, retorna JWT |
| POST   | `/api/auth/login`    | `{ email, senha }`           | Login, retorna JWT      |

### 🏢 Empresas (requer token)

| Método | Rota                        | Descrição                              |
|--------|-----------------------------|----------------------------------------|
| GET    | `/api/empresas`             | Lista todas                            |
| GET    | `/api/empresas/buscar?nome=X` | Busca por nome (extensão usa esta)   |
| GET    | `/api/empresas/:id`         | Busca por ID                           |
| GET    | `/api/empresas/:id/score`   | Score + motivos (formato da extensão)  |
| POST   | `/api/empresas`             | Cadastra empresa                       |
| PUT    | `/api/empresas/:id`         | Atualiza empresa                       |
| DELETE | `/api/empresas/:id`         | Remove empresa                         |

### ⭐ Avaliações (requer token)

| Método | Rota                                 | Descrição                         |
|--------|--------------------------------------|-----------------------------------|
| GET    | `/api/avaliacoes`                    | Lista todas                       |
| GET    | `/api/avaliacoes/:id`                | Busca por ID                      |
| GET    | `/api/avaliacoes/empresa/:empresaId` | Avaliações de uma empresa         |
| POST   | `/api/avaliacoes`                    | Adiciona avaliação                |
| PUT    | `/api/avaliacoes/:id`                | Edita (apenas autora)             |
| DELETE | `/api/avaliacoes/:id`                | Remove (apenas autora)            |

### 🩺 Health Check

| Método | Rota      | Descrição                     |
|--------|-----------|-------------------------------|
| GET    | `/health` | Status do servidor            |

---

## Autenticação

Todas as rotas (exceto `/api/auth/*` e `/health`) exigem token JWT.

Envie o token no header:
```
Authorization: Bearer <seu_token_aqui>
```

---

## Exemplo de Uso (extensão)

```
1. Extensão faz POST /api/auth/login → recebe token
2. Extensão detecta nome da empresa na página de emprego
3. Extensão faz GET /api/empresas/buscar?nome=EmpresaX (com token)
4. API retorna score, avaliações e detalhes
5. Extensão exibe os dados no overlay
```

---

## Variáveis de Ambiente (.env)

| Variável       | Padrão     | Descrição                        |
|----------------|------------|----------------------------------|
| `PORT`         | `3000`     | Porta do servidor                |
| `DB_HOST`      | `localhost`| Host do PostgreSQL               |
| `DB_PORT`      | `5432`     | Porta do PostgreSQL              |
| `DB_NAME`      | `safeher`  | Nome do banco                    |
| `DB_USER`      | `postgres` | Usuário do banco                 |
| `DB_PASSWORD`  | `postgres` | Senha do banco                   |
| `JWT_SECRET`   | —          | Chave secreta para JWT           |
| `JWT_EXPIRES_IN`| `7d`      | Tempo de expiração do token      |
| `CORS_ORIGINS` | `*`        | Origens permitidas (CORS)        |
