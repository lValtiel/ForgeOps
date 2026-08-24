package com.valtiel.forgeOps.dto.userDTO;

import com.valtiel.forgeOps.entity.Role;

import java.util.Set;

public record UserWithRoles(
        String username,
        String email,
        Set<Role> roles
) {}