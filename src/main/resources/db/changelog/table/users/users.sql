--liquibase formatted sql

--changeset moratorium:create_users_table
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    login VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

--changeset moratorium:comments_for_users runOnChange:true
COMMENT ON TABLE users IS 'Таблица, содержащая учетные записи пользователей';
COMMENT ON COLUMN users.id IS 'Уникальный идентификатор пользователя';
COMMENT ON COLUMN users.login IS 'Уникальный логин пользователя';
COMMENT ON COLUMN users.email IS 'Уникальный адрес электронной почты';
COMMENT ON COLUMN users.password_hash IS 'Хэш пароля';
COMMENT ON COLUMN users.created_at IS 'Дата и время создания записи';
COMMENT ON COLUMN users.updated_at IS 'Дата и время последнего обновления записи';

--changeset moratorium:index_users_login
CREATE UNIQUE INDEX IF NOT EXISTS idx_users_login
    ON users(login);

--changeset moratorium:index_users_email
CREATE UNIQUE INDEX IF NOT EXISTS idx_users_email
    ON users(email);