package se.itmo.moratorium.rdba.mapper;

import org.mapstruct.Mapper;
import se.itmo.moratorium.rdba.dto.ProjectRequestDto;
import se.itmo.moratorium.rdba.dto.ProjectResponseDto;
import se.itmo.moratorium.rdba.model.ProjectEntity;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    ProjectResponseDto toDto(ProjectEntity projectEntity);
    ProjectEntity toEntity(ProjectRequestDto projectRequestDto);
}