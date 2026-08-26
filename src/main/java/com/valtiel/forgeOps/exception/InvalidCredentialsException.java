package com.valtiel.forgeOps.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Nombre de usuario y/o contraseña incorrectos. Vuelve a iniciar sesión");
    }
}
