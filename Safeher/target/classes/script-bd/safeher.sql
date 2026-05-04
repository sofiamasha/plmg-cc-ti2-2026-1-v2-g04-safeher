-- Banco SafeHer

-- tabela dos usuários
CREATE TABLE Usuario (
    id INT PRIMARY KEY,      -- chave primaria (id unico do usuario)
    nome VARCHAR(100),       -- nome do usuario
    email VARCHAR(100),      -- email do usuario
    senha VARCHAR(100),      -- senha
    data DATE                -- data de registro
);

-- tabela das empresas
CREATE TABLE Empresa (
    id INT PRIMARY KEY,      -- chave primaria (id unico da empresa)
    nome VARCHAR(100),       -- nome da empresa
    indice DECIMAL(5,2)      -- indice da empresa
);

-- tabela de avaliações
CREATE TABLE Avaliacao (
    id INT PRIMARY KEY,      -- chave primaria (id da avaliacao)
    comentario TEXT,         -- comentario da avaliacao
    nota INT,                -- nota
    Usuario_id INT,          -- referencia ao usuario
    FOREIGN KEY (Usuario_id) REFERENCES Usuario(id)
);

-- tabela de denúncias
CREATE TABLE Denuncia (
    id INT PRIMARY KEY,      -- chave primaria (id da denuncia)
    descricao TEXT,          -- descricao da denuncia
    data DATE,               -- data da denuncia
    anonima BOOLEAN,         -- se é anonima
    Usuario_id INT,          -- referencia ao usuario
    Empresa_id INT,          -- referencia a empresa
    FOREIGN KEY (Usuario_id) REFERENCES Usuario(id),
    FOREIGN KEY (Empresa_id) REFERENCES Empresa(id)
);