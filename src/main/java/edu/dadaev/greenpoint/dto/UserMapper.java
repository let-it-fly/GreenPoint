package edu.dadaev.greenpoint.dto;

import edu.dadaev.greenpoint.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequestDTO userRequestDTO);

    UserResponseDTO toDto(User user);


}