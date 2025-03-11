package se.itmo.moratorium.rdba.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import se.itmo.moratorium.rdba.dto.TaskRequestDto;
import se.itmo.moratorium.rdba.dto.TaskResponseDto;
import se.itmo.moratorium.rdba.model.TaskEntity;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskEntity toEntity(TaskRequestDto taskRequestDto);

    @Mapping(target = "assignedUser", source = "assignedUser")
    @Mapping(target = "projectId", source = "project.id")
    TaskResponseDto toDto(TaskEntity taskEntity);
}
