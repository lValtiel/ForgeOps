package com.valtiel.forgeOps.service.userService;

import com.valtiel.forgeOps.dto.userDTO.CreateUserDTO;
import com.valtiel.forgeOps.dto.userDTO.SimpleUserDTO;
import com.valtiel.forgeOps.dto.userDTO.UpdateUserDTO;
import com.valtiel.forgeOps.dto.userDTO.UserWithRoles;
import com.valtiel.forgeOps.entity.Role;
import com.valtiel.forgeOps.entity.User;
import com.valtiel.forgeOps.exception.DuplicateResourceException;
import com.valtiel.forgeOps.exception.ResourceNotFoundException;
import com.valtiel.forgeOps.mapper.userMapper.UserMapper;
import com.valtiel.forgeOps.repository.RoleRepository;
import com.valtiel.forgeOps.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    @Override
    public List<SimpleUserDTO> getUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toListSimpleUserDTO(users);
    }

    @Override
    public List<UserWithRoles> getUsersWithRoles() {
        List<User> users = userRepository.findAll();
        return userMapper.toListUserWithRoles(users);
    }

    @Override
    public SimpleUserDTO getUser(Long user_id) {
        User user = findUserById(user_id);
        return userMapper.entityToSimpleUserDTO(user);
    }

    @Override
    public UserWithRoles getUserWithRoles(Long user_id) {
        User user = findUserById(user_id);
        return userMapper.entitytoUserWithRolesDTO(user);
    }

    @Override
    public void registerUser(CreateUserDTO createUserDTO) {

        User existsUser = userRepository.findByUsername(createUserDTO.username());

        if(existsUser != null) {
            throw new DuplicateResourceException("El nombre de usuario ya existe. Intenta uno nuevo");
        }

        Set<Role> roles = new HashSet<>(roleRepository.findAllById(createUserDTO.roles_id()));

        if(roles.size() != createUserDTO.roles_id().size()) {
            throw new ResourceNotFoundException("Uno o más roles no existen");
        }

        User user = User.builder()
                .username(createUserDTO.username())
                .password(passwordEncoder.encode(createUserDTO.password()))
                .email(createUserDTO.email())
                .roles(roles)
                .build();

        userRepository.save(user);
    }

    @Override
    public void updateUser(Long user_id, UpdateUserDTO updateUserDTO) {

        User user = findUserById(user_id);

        user.setEmail(updateUserDTO.email());
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long user_id) {
        User user = findUserById(user_id);
        userRepository.delete(user);
    }

    @Override
    public User findUserById(Long user_id) {
        User user = userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        return user;
    }
}
