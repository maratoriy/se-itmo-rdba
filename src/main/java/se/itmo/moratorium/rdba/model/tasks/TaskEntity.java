package se.itmo.moratorium.rdba.model.tasks;

import lombok.*;
import jakarta.persistence.*;
import se.itmo.moratorium.rdba.model.projects.ProjectEntity;
import se.itmo.moratorium.rdba.model.users.UserEntity;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String status;
    private String priority;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assigned_user_id")
    private UserEntity assignedUser;
}