package com.app.dualsharebackend.exception;

public class CloudinaryServiceException extends RuntimeException {
    public CloudinaryServiceException() {
        super("Error en el servicio de Cloudinary");
    }
}
