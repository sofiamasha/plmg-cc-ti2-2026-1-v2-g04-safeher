-- Patch: sistema de API keys publicas (parceiros)

CREATE TABLE IF NOT EXISTS api_key (
    id          SERIAL PRIMARY KEY,
    chave       VARCHAR(80) UNIQUE NOT NULL,
    nome_app    VARCHAR(120) NOT NULL,
    email       VARCHAR(150) NOT NULL,
    ativa       BOOLEAN NOT NULL DEFAULT TRUE,
    limite_dia  INT NOT NULL DEFAULT 1000,
    criada_em   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS api_key_uso (
    id          SERIAL PRIMARY KEY,
    api_key_id  INT NOT NULL REFERENCES api_key(id) ON DELETE CASCADE,
    endpoint    VARCHAR(120) NOT NULL,
    ip          VARCHAR(45),
    data        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_api_key_uso_key_data ON api_key_uso(api_key_id, data);

-- Chave de demonstracao (idempotente)
INSERT INTO api_key (chave, nome_app, email, limite_dia)
VALUES ('sh_demo_publica_123', 'Demo Publica', 'demo@safeher.local', 5000)
ON CONFLICT (chave) DO NOTHING;
