package se.itmo.moratorium.rdba.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.itmo.moratorium.rdba.model.TaskPriority;
import se.itmo.moratorium.rdba.model.TaskStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestDto {
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private Long projectId;
    private Long assignedUserId;
}
