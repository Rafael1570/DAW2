package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.exceptions;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    private final String resource;
    private final String field;
    private final Object value;
    public ResourceNotFoundException(String resource,String field,Object value) {
        super("Resource not found: "+resource+" ("+field+"="+value+")");
        this.resource=resource; this.field=field; this.value=value;
    }
}
