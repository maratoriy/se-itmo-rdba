package se.itmo.moratorium.rdba.mapper;

import org.mapstruct.Mapper;
import se.itmo.moratorium.rdba.dto.TaskRequestDto;
import se.itmo.moratorium.rdba.dto.TaskResponseDto;
import se.itmo.moratorium.rdba.model.TaskEntity;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskEntity toEntity(TaskRequestDto taskRequestDto);

    TaskResponseDto toDto(TaskEntity taskEntity);
}
