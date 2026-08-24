package com.valtiel.forgeOps.mapper.userMapper;

import com.valtiel.forgeOps.dto.userDTO.SimpleUserDTO;
import com.valtiel.forgeOps.dto.userDTO.UserWithRoles;
import com.valtiel.forgeOps.entity.User;
import com.valtiel.forgeOps.mapper.roleMapper.RoleMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {

    List<SimpleUserDTO> toListSimpleUserDTO(List<User> users);
    List<UserWithRoles> toListUserWithRoles(List<User> users);
    SimpleUserDTO entityToSimpleUserDTO(User user);
    UserWithRoles entitytoUserWithRolesDTO(User user);
}
