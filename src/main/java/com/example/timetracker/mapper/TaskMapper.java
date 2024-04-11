package com.example.timetracker.mapper;

import com.example.timetracker.dto.TaskCreateDTO;
import com.example.timetracker.dto.TaskDTO;
import com.example.timetracker.dto.TaskUpdateDTO;
import com.example.timetracker.model.Task;
import org.mapstruct.*;

@Mapper(
        uses = { JsonNullableMapper.class, ReferenceMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class TaskMapper {
    @Mapping(target = "assigneeId", source = "assignee.id")
    public abstract TaskDTO map(Task model);
    @Mapping(target = "assignee", source = "assigneeId")
    public abstract Task map(TaskCreateDTO dto);
    @Mapping(target = "assignee", source = "assigneeId")
    public abstract void update(TaskUpdateDTO dto, @MappingTarget Task model);
}
