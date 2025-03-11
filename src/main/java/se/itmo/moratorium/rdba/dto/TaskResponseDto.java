package se.itmo.moratorium.rdba.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.itmo.moratorium.rdba.model.TaskPriority;
import se.itmo.moratorium.rdba.model.TaskStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDto {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private UserResponseDto assignedUser;
    private Long projectId;
    private TaskPriority priority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
