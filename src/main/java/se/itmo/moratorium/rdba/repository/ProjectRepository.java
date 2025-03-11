package se.itmo.moratorium.rdba.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import se.itmo.moratorium.rdba.model.ProjectEntity;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    List<ProjectEntity> findByUsers_Id(Long userId);
}