package com.valtiel.forgeOps.controller.authController;

import com.valtiel.forgeOps.dto.authDTO.AuthRequestDTO;
import com.valtiel.forgeOps.dto.authDTO.AuthResponseDTO;
import com.valtiel.forgeOps.service.authService.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequestDTO) {
        AuthResponseDTO token = authService.login(authRequestDTO);
        return ResponseEntity.ok(token);
    }
}
