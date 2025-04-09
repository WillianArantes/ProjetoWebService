CREATE TABLE endereco (
    id SERIAL PRIMARY KEY,
    logradouro VARCHAR(255) NOT NULL,
    numero VARCHAR(20),
    complemento VARCHAR(100),
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    uf CHAR(2) NOT NULL,
    cep VARCHAR(20) NOT NULL
);
CREATE TABLE pacientes (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    endereco_id INTEGER NOT NULL REFERENCES endereco(id) ON DELETE CASCADE,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);
CREATE TABLE medicos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    crm VARCHAR(20) NOT NULL UNIQUE,
    especialidade VARCHAR(100) NOT NULL,
    endereco_id INTEGER NOT NULL REFERENCES endereco(id) ON DELETE CASCADE,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);
CREATE TABLE consultas (
    id SERIAL PRIMARY KEY,
    paciente_id INTEGER NOT NULL REFERENCES pacientes(id) ON DELETE CASCADE,
    medico_id INTEGER REFERENCES medicos(id) ON DELETE SET NULL,
    data_cadastro DATE NOT NULL,
    hora_cadastro TIME NOT NULL,
    motivo_cancelamento VARCHAR(255)
);