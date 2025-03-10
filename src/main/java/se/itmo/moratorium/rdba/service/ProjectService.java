package se.itmo.moratorium.rdba.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.itmo.moratorium.rdba.dto.ProjectRequestDto;
import se.itmo.moratorium.rdba.dto.ProjectResponseDto;
import se.itmo.moratorium.rdba.mapper.ProjectMapper;
import se.itmo.moratorium.rdba.model.ProjectEntity;
import se.itmo.moratorium.rdba.repository.ProjectRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ProjectResponseDto createProject(ProjectRequestDto projectRequestDto) {
        ProjectEntity projectEntity = projectMapper.toEntity(projectRequestDto);
        ProjectEntity savedProject = projectRepository.save(projectEntity);
        return projectMapper.toDto(savedProject);
    }

    public ProjectResponseDto getProjectById(Long id) {
        ProjectEntity project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return projectMapper.toDto(project);
    }

    public List<ProjectResponseDto> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(projectMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProjectResponseDto updateProject(Long id, ProjectRequestDto projectRequestDto) {
        ProjectEntity projectEntity = projectMapper.toEntity(projectRequestDto);
        projectEntity.setId(id);
        ProjectEntity updatedProject = projectRepository.save(projectEntity);
        return projectMapper.toDto(updatedProject);
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}