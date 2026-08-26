package com.valtiel.forgeOps.service.authService;

import com.valtiel.forgeOps.dto.authDTO.AuthRequestDTO;
import com.valtiel.forgeOps.dto.authDTO.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO login(AuthRequestDTO authRequestDTO);
}
