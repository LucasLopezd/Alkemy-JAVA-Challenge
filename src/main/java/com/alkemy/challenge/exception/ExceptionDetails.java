package com.alkemy.challenge.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionDetails {

    private final String exception;
    private final String message;
    private final String path;

    public ExceptionDetails(Exception exception, String path){
        this.exception = exception.getClass().getSimpleName();
        this.message = exception.getMessage();
        this.path = path;
    }

    @Override
    public String toString() {
        return "{" +
            " exception='" + exception + "/" +
            ", message='" + message + "/" +
            ", path='" + path + "/" +
            "}";
    }
}

    
