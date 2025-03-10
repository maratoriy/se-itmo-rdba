package se.itmo.moratorium.rdba.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import se.itmo.moratorium.rdba.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByLogin(String login);
}