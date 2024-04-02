package com.example.timetracker.mapper;

import com.example.timetracker.dto.UserCreateDTO;
import com.example.timetracker.dto.UserDTO;
import com.example.timetracker.dto.UserUpdateDto;
import com.example.timetracker.model.User;
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
public abstract class UserMapper {
    public abstract UserDTO map(User model);

    public abstract User map(UserCreateDTO dto);

    public abstract void update(UserUpdateDto dto, @MappingTarget User model);
}
