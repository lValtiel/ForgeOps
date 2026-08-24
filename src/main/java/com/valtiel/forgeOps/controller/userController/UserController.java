package com.valtiel.forgeOps.controller.userController;

import com.valtiel.forgeOps.dto.userDTO.CreateUserDTO;
import com.valtiel.forgeOps.dto.userDTO.SimpleUserDTO;
import com.valtiel.forgeOps.dto.userDTO.UpdateUserDTO;
import com.valtiel.forgeOps.dto.userDTO.UserWithRoles;
import com.valtiel.forgeOps.service.userService.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<SimpleUserDTO>> getUsers() {
        List<SimpleUserDTO> usersDTO = userService.getUsers();
        return ResponseEntity.ok(usersDTO);
    }

    @GetMapping("/with-roles")
    public ResponseEntity<List<UserWithRoles>> getUsersWithRoles() {
        List<UserWithRoles> usersDTO = userService.getUsersWithRoles();
        return ResponseEntity.ok(usersDTO);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<SimpleUserDTO> getUser(@PathVariable Long user_id) {
        SimpleUserDTO simpleUserDTO = userService.getUser(user_id);
        return ResponseEntity.ok(simpleUserDTO);
    }

    @GetMapping("/with-roles/{user_id}")
    public ResponseEntity<UserWithRoles> getUserWithRoles(@PathVariable Long user_id) {
        UserWithRoles userWithRoles = userService.getUserWithRoles(user_id);
        return ResponseEntity.ok(userWithRoles);
    }

    @PostMapping
    public ResponseEntity<String> registerUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        userService.registerUser(createUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado");
    }

    @PutMapping("/{user_id}")
    public ResponseEntity<String> updateUser(@PathVariable Long user_id, @Valid @RequestBody UpdateUserDTO updateUserDTO) {
        userService.updateUser(user_id, updateUserDTO);
        return ResponseEntity.ok("Usuario actualizado");
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long user_id) {
        userService.deleteUser(user_id);
        return ResponseEntity.ok("Usuario eliminado");
    }
}
