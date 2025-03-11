package se.itmo.moratorium.rdba.service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.itmo.moratorium.rdba.dto.ProjectRequestDto;
import se.itmo.moratorium.rdba.dto.ProjectResponseDto;
import se.itmo.moratorium.rdba.mapper.ProjectMapper;
import se.itmo.moratorium.rdba.model.ProjectEntity;
import se.itmo.moratorium.rdba.model.UserEntity;
import se.itmo.moratorium.rdba.repository.ProjectRepository;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final SecurityService securityService;

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
        if (!project.getUsers().contains(securityService.getCurrentUser())) {
            throw new RuntimeException("Access denied");
        }
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

        if (!existingProject.getUsers().contains(securityService.getCurrentUser())) {
            throw new RuntimeException("Access denied");
        }

        existingProject.setName(projectRequestDto.getName());
        existingProject.setDescription(projectRequestDto.getDescription());

        ProjectEntity updatedProject = projectRepository.save(existingProject);
        return projectMapper.toDto(updatedProject);
    }

    public void deleteProject(Long id) {
        ProjectEntity project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        if (!project.getUsers().contains(securityService.getCurrentUser())) {
            throw new RuntimeException("Access denied");
        }
        projectRepository.deleteById(id);
    }
}
