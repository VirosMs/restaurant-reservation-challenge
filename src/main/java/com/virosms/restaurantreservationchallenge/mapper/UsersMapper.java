package com.virosms.restaurantreservationchallenge.mapper;

import com.virosms.restaurantreservationchallenge.model.User.RegisterDTO;
import com.virosms.restaurantreservationchallenge.model.User.UserDTO;
import com.virosms.restaurantreservationchallenge.model.User.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = EmailMapper.class)
public interface UsersMapper {


    Users toRegisterDTO(RegisterDTO registerDTO);


    RegisterDTO toEntity(Users users);


    UserDTO toDto(Users user);


    Users toEntity(UserDTO userDTO);
}