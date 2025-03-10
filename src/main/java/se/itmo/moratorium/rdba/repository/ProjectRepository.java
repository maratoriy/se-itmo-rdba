package se.itmo.moratorium.rdba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.itmo.moratorium.rdba.model.ProjectEntity;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
}