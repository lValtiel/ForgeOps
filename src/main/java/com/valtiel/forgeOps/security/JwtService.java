package com.valtiel.forgeOps.security;

import com.valtiel.forgeOps.exception.JwtAuthenticationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${JWT_SECRET_KEY}")
    private String SECRET_KEY;
    private final long EXPIRATION_TIME = 86400000;

    private SecretKey getSigning() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigning())
                .compact();
    }

    public Claims extractClaims(String token) {

        try {

            return Jwts.parser()
                    .verifyWith(getSigning())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        }catch (ExpiredJwtException exception) {
            throw new JwtAuthenticationException("El token ha expirado");
        }catch (MalformedJwtException exception) {
            throw new JwtAuthenticationException("El token tiene un formato inválido");
        }catch (SignatureException exception) {
            throw new JwtAuthenticationException("La firma del token es inválida");
        }catch (UnsupportedJwtException exception) {
            throw new JwtAuthenticationException("El tipo de token no es soportado");
        }catch (IllegalArgumentException exception) {
            throw new JwtAuthenticationException("El token está vacío");
        }
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public void validateToken(String token, String username) {

        Claims claims = extractClaims(token);

        if(!claims.getSubject().equals(username)) {
            throw new JwtAuthenticationException("El token no pertenece al usuario");
        }
    }
}
