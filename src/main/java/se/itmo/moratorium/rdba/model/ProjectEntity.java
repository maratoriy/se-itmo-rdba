package se.itmo.moratorium.rdba.model;

import lombok.*;
import jakarta.persistence.*;
import se.itmo.moratorium.rdba.model.TaskEntity;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "projects")
@NamedEntityGraph(
        name = "Project.withTasks",
        attributeNodes = {
                @NamedAttributeNode("tasks")
        }
)
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private Set<TaskEntity> tasks;
}
