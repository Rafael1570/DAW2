package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.exceptions;

import lombok.Getter;

@Getter
public class InvalidFileException extends RuntimeException {
    private final String resource;
    private final String field;
    private final Object value;
    public InvalidFileException(String resource,String field,Object value) {
        this(resource,field,value,"Invalid file");
    }
    public InvalidFileException(String resource,String field,Object value,String detail) {
        super("Invalid file for "+resource+" ("+field+"="+value+"): "+detail);
        this.resource=resource; this.field=field; this.value=value;
    }
}
