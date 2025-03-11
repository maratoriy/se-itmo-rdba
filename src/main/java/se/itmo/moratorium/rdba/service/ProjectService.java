package se.itmo.moratorium.rdba.service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.itmo.moratorium.rdba.dto.ProjectRequestDto;
import se.itmo.moratorium.rdba.dto.ProjectResponseDto;
import se.itmo.moratorium.rdba.dto.UserResponseDto;
import se.itmo.moratorium.rdba.mapper.ProjectMapper;
import se.itmo.moratorium.rdba.mapper.UserMapper;
import se.itmo.moratorium.rdba.model.ProjectEntity;
import se.itmo.moratorium.rdba.model.UserEntity;
import se.itmo.moratorium.rdba.repository.ProjectRepository;
import se.itmo.moratorium.rdba.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserMapper userMapper;
    private final SecurityService securityService;
    private final UserRepository userRepository;

    public ProjectResponseDto createProject(ProjectRequestDto projectRequestDto) {
        ProjectEntity projectEntity = projectMapper.toEntity(projectRequestDto);
        UserEntity currentUser = securityService.getCurrentUser();
        projectEntity.getUsers().add(currentUser);
        ProjectEntity savedProject = projectRepository.save(projectEntity);
        return projectMapper.toDto(savedProject);
    }

    public ProjectResponseDto getProjectById(Long id) {
        ProjectEntity project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        verifyUserAccess(project);
        return projectMapper.toDto(project);
    }

    public List<ProjectResponseDto> getAllProjects() {
        UserEntity currentUser = securityService.getCurrentUser();
        return projectRepository.findByUsers_Id(currentUser.getId()).stream()
                .map(projectMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProjectResponseDto updateProject(Long id, ProjectRequestDto projectRequestDto) {
        ProjectEntity existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        verifyUserAccess(existingProject);
        existingProject.setName(projectRequestDto.getName());
        existingProject.setDescription(projectRequestDto.getDescription());
        ProjectEntity updatedProject = projectRepository.save(existingProject);
        return projectMapper.toDto(updatedProject);
    }

    public void deleteProject(Long id) {
        ProjectEntity project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        verifyUserAccess(project);
        projectRepository.deleteById(id);
    }

    public void addUserToProject(Long projectId, Long userId) {
        ProjectEntity project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        verifyUserAccess(project);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (project.getUsers().contains(user)) {
            throw new RuntimeException("User is already associated with this project");
        }

        project.getUsers().add(user);
        projectRepository.save(project);
    }

    public List<UserResponseDto> getUsersByProjectId(Long projectId) {
        ProjectEntity project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        verifyUserAccess(project);
        return project.getUsers().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    private void verifyUserAccess(ProjectEntity project) {
        UserEntity currentUser = securityService.getCurrentUser();
        if (!project.getUsers().contains(currentUser)) {
            throw new RuntimeException("Access denied");
        }
    }
}
