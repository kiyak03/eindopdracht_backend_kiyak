package com.kiyak.eindopdracht_backend_kiyak.exception;

public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;


    public NotFoundException() {
        super("Cannot find the file!");
    }
}
