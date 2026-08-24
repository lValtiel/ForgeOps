package com.valtiel.forgeOps.mapper.roleMapper;

import com.valtiel.forgeOps.dto.roleDTO.RoleResponseDTO;
import com.valtiel.forgeOps.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleResponseDTO entityToDTO(Role role);
}
