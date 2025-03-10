--liquibase formatted sql

--changeset moratorium:create_project_user_table
CREATE TABLE IF NOT EXISTS project_user (
    id BIGSERIAL PRIMARY KEY,
    project_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL

    , CONSTRAINT fk_project FOREIGN KEY (project_id)
          REFERENCES projects (id) ON DELETE CASCADE
    , CONSTRAINT fk_user FOREIGN KEY (user_id)
          REFERENCES users (id) ON DELETE CASCADE
);

--changeset moratorium:comments_for_project_user runOnChange:true
COMMENT ON TABLE project_user IS 'Таблица связи проектов и пользователей (M:N)';
COMMENT ON COLUMN project_user.id IS 'Уникальный идентификатор записи в связующей таблице';
COMMENT ON COLUMN project_user.project_id IS 'Внешний ключ на projects';
COMMENT ON COLUMN project_user.user_id IS 'Внешний ключ на users';

--changeset moratorium:index_project_user_project_id
CREATE INDEX IF NOT EXISTS idx_project_user_project_id
    ON project_user(project_id);

--changeset moratorium:index_project_user_user_id
CREATE INDEX IF NOT EXISTS idx_project_user_user_id
    ON project_user(user_id);
