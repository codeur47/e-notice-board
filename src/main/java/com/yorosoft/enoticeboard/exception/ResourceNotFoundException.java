package com.yorosoft.enoticeboard.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {
        super("Could not find resource(s).");
    }
    public ResourceNotFoundException(Long id) {
        super("Could not find resource width " + id + ".");
    }
}
