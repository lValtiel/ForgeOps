package com.valtiel.forgeOps.service.userService;

import com.valtiel.forgeOps.dto.userDTO.CreateUserDTO;
import com.valtiel.forgeOps.dto.userDTO.SimpleUserDTO;
import com.valtiel.forgeOps.dto.userDTO.UpdateUserDTO;
import com.valtiel.forgeOps.dto.userDTO.UserWithRoles;
import com.valtiel.forgeOps.entity.User;

import java.util.List;

public interface UserService {

    List<SimpleUserDTO> getUsers();
    List<UserWithRoles> getUsersWithRoles();
    SimpleUserDTO getUser(Long user_id);
    UserWithRoles getUserWithRoles(Long user_id);
    void registerUser(CreateUserDTO createUserDTO);
    void updateUser(Long user_id, UpdateUserDTO updateUserDTO);
    void deleteUser(Long user_id);
    User findUserById(Long user_id);
}
