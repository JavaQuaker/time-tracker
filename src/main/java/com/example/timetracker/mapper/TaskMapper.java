package com.example.timetracker.mapper;

import com.example.timetracker.dto.TaskCreateDTO;
import com.example.timetracker.dto.TaskDTO;
import com.example.timetracker.dto.TaskUpdateDTO;
import com.example.timetracker.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.MappingTarget;

@Mapper(
        uses = { JsonNullableMapper.class, ReferenceMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class TaskMapper {
    public abstract TaskDTO map(Task model);
    public abstract Task map(TaskCreateDTO dto);
    public abstract void update(TaskUpdateDTO dto, @MappingTarget Task model);
}
