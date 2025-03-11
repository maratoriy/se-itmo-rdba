package se.itmo.moratorium.rdba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.itmo.moratorium.rdba.model.TaskEntity;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findByAssignedUser_Id(Long userId);
    List<TaskEntity> findByProject_Id(Long projectId);
}