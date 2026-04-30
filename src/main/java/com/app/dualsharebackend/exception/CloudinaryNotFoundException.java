package com.app.dualsharebackend.exception;

public class CloudinaryNotFoundException extends RuntimeException {

    public CloudinaryNotFoundException() {
        super("The file does not exist in Cloudinary");
    }
}
