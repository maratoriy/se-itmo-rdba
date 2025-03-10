--liquibase formatted sql

--changeset moratorium:create_tasks_table
CREATE TABLE IF NOT EXISTS tasks (
    id BIGSERIAL PRIMARY KEY,
    project_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    status VARCHAR(20) NOT NULL,
    priority VARCHAR(50) NOT NULL,
    assigned_user_id BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP

    , CONSTRAINT fk_project_task FOREIGN KEY (project_id)
          REFERENCES projects (id) ON DELETE CASCADE
    , CONSTRAINT fk_assigned_user FOREIGN KEY (assigned_user_id)
          REFERENCES users (id) ON DELETE SET NULL
);

--changeset moratorium:comments_for_tasks runOnChange:true
COMMENT ON TABLE tasks IS 'Таблица, содержащая задачи, относящиеся к проектам';
COMMENT ON COLUMN tasks.id IS 'Уникальный идентификатор задачи';
COMMENT ON COLUMN tasks.project_id IS 'Внешний ключ на projects';
COMMENT ON COLUMN tasks.title IS 'Краткое название (заголовок) задачи';
COMMENT ON COLUMN tasks.description IS 'Подробное описание задачи';
COMMENT ON COLUMN tasks.status IS 'Текущий статус задачи';
COMMENT ON COLUMN tasks.priority IS 'Приоритет задачи';
COMMENT ON COLUMN tasks.assigned_user_id IS 'Ссылка на пользователя-исполнителя';
COMMENT ON COLUMN tasks.created_at IS 'Дата и время создания записи';
COMMENT ON COLUMN tasks.updated_at IS 'Дата и время последнего обновления записи';

--changeset moratorium:index_tasks_project_id
CREATE INDEX IF NOT EXISTS idx_tasks_project_id
    ON tasks(project_id);

--changeset moratorium:index_tasks_assigned_user_id
CREATE INDEX IF NOT EXISTS idx_tasks_assigned_user_id
    ON tasks(assigned_user_id);