package com.alkemy.challenge.exception;

public class MalformedHeaderException extends BadRequestException{
    
    private static final String DESCRIPTION = "Token whit wrong format";

    public MalformedHeaderException(String detail){
        super(DESCRIPTION + ", " + detail);
    }
}
