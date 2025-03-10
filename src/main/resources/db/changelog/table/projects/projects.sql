--liquibase formatted sql

--changeset moratorium:create_projects_table
CREATE TABLE IF NOT EXISTS projects (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

--changeset moratorium:comments_for_projects runOnChange:true
COMMENT ON TABLE projects IS 'Таблица, содержащая информацию о проектах';
COMMENT ON COLUMN projects.id IS 'Уникальный идентификатор проекта';
COMMENT ON COLUMN projects.name IS 'Название проекта';
COMMENT ON COLUMN projects.description IS 'Описание проекта';
COMMENT ON COLUMN projects.created_at IS 'Дата и время создания записи';
COMMENT ON COLUMN projects.updated_at IS 'Дата и время последнего обновления записи';

--changeset moratorium:index_projects_name
CREATE INDEX IF NOT EXISTS idx_projects_name
    ON projects(name);