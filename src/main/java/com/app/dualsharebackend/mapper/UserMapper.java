package com.app.dualsharebackend.mapper;

import com.app.dualsharebackend.dto.UserRequestDTO;
import com.app.dualsharebackend.dto.UserResponseDTO;
import com.app.dualsharebackend.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequestDTO  userRequestDTO);
    UserResponseDTO toDTO(User user);

}
