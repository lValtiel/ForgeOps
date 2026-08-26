package com.valtiel.forgeOps.service.authService;

import com.valtiel.forgeOps.dto.authDTO.AuthRequestDTO;
import com.valtiel.forgeOps.dto.authDTO.AuthResponseDTO;
import com.valtiel.forgeOps.exception.InvalidCredentialsException;
import com.valtiel.forgeOps.security.CustomUserDetailsService;
import com.valtiel.forgeOps.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponseDTO login(AuthRequestDTO authRequestDTO) {

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequestDTO.username(),
                            authRequestDTO.password()
                    )
            );

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequestDTO.username());
            String token = jwtService.generateToken(userDetails);
            return new AuthResponseDTO(token);

        }catch (AuthenticationException authenticationException) {
            throw new InvalidCredentialsException();
        }
    }
}
