-- Crear el esquema 'broersdb'
CREATE SCHEMA IF NOT EXISTS broersdb;

-- Crear tablas dentro del esquema broersdb
SET search_path TO broersdb;

-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255),
    active BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tipo enumerado para tipos de token
CREATE TYPE token_type AS ENUM ('REGISTRATION', 'PASSWORD_RESET');

-- Tabla de tokens
CREATE TABLE IF NOT EXISTS tokens (
    id SERIAL PRIMARY KEY,
    value VARCHAR(255) NOT NULL,
    user_id INT NOT NULL,
    type token_type NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    used BOOLEAN DEFAULT false,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Índices
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_tokens_value ON tokens(value);