package com.example.timetracker.mapper;

import com.example.timetracker.dto.TimeTrackerCreateDTO;
import com.example.timetracker.dto.TimeTrackerDTO;
import com.example.timetracker.dto.TimeTrackerUpdateDTO;
import com.example.timetracker.model.TimeTracker;
import org.mapstruct.*;

@Mapper(
        uses = { JsonNullableMapper.class, ReferenceMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class TimeTrackerMapper {
    @Mapping(target = "nameTimeTrackerId", source = "nameTimeTracker.id")
    public abstract TimeTrackerDTO map(TimeTracker model);
    @Mapping(target = "nameTimeTracker", source = "nameTimeTrackerId")
    public abstract TimeTracker map(TimeTrackerCreateDTO dto);
//    @Mapping(target = "nameTimeTracker", source = "nameTimeTrackerId")
    public abstract void update(TimeTrackerUpdateDTO dto, @MappingTarget TimeTracker model);
}
