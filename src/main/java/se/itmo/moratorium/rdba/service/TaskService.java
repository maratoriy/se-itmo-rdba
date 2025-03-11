package se.itmo.moratorium.rdba.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.itmo.moratorium.rdba.dto.TaskRequestDto;
import se.itmo.moratorium.rdba.dto.TaskResponseDto;
import se.itmo.moratorium.rdba.mapper.TaskMapper;
import se.itmo.moratorium.rdba.model.TaskEntity;
import se.itmo.moratorium.rdba.model.ProjectEntity;
import se.itmo.moratorium.rdba.model.UserEntity;
import se.itmo.moratorium.rdba.repository.TaskRepository;
import se.itmo.moratorium.rdba.repository.ProjectRepository;
import se.itmo.moratorium.rdba.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TaskMapper taskMapper;
    private final SecurityService securityService;

    public TaskResponseDto createTask(TaskRequestDto taskRequestDto) {
        ProjectEntity project = projectRepository.findById(taskRequestDto.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));
        UserEntity user = Optional.ofNullable(taskRequestDto.getAssignedUserId())
                        .map(userRepository::findById)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .orElse(null);


        verifyAccessToProject(project);

        TaskEntity taskEntity = taskMapper.toEntity(taskRequestDto);
        taskEntity.setProject(project);
        taskEntity.setAssignedUser(user);
        TaskEntity savedTask = taskRepository.save(taskEntity);

        return taskMapper.toDto(savedTask);
    }

    public TaskResponseDto getTaskById(Long id) {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        verifyAccessToProject(task.getProject());

        return taskMapper.toDto(task);
    }

    public List<TaskResponseDto> getTasksByProjectId(Long projectId) {
        return taskRepository.findByProject_Id(projectId).stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }


    public List<TaskResponseDto> getAllTasks() {
        UserEntity currentUser = securityService.getCurrentUser();
        return taskRepository.findByAssignedUser_Id(currentUser.getId()).stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    public TaskResponseDto updateTask(Long id, TaskRequestDto taskRequestDto) {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        UserEntity user = Optional.ofNullable(taskRequestDto.getAssignedUserId())
                .map(userRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .orElse(null);

        verifyAccessToProject(task.getProject());

        task.setAssignedUser(user);
        task.setTitle(taskRequestDto.getTitle());
        task.setDescription(taskRequestDto.getDescription());
        task.setStatus(taskRequestDto.getStatus());
        task.setPriority(taskRequestDto.getPriority());

        TaskEntity updatedTask = taskRepository.save(task);
        return taskMapper.toDto(updatedTask);
    }

    public void deleteTask(Long id) {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        verifyAccessToProject(task.getProject());

        taskRepository.deleteById(id);
    }

    private void verifyAccessToProject(ProjectEntity project) {
        UserEntity currentUser = securityService.getCurrentUser();
        if (!project.getUsers().contains(currentUser)) {
            throw new RuntimeException("Access denied");
        }
    }
}
