package com.app.incidentmanagement.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{

    // Made more dynamic to handle broader searches in the future
    //String Column - Field in SQL being searched on
    //String Search - Value in SQL being searched for
    public NotFoundException(String column, String search) {
        super(column + " not found with provided " + search);
    }
}
