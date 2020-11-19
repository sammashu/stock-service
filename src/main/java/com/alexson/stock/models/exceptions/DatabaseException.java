package com.alexson.stock.models.exceptions;

import com.alexson.stock.models.ResponseError;

public class DatabaseException extends RuntimeException {
    public DatabaseException(ResponseError responseError){
        super(String.format(responseError.getValue() + " " + responseError.getMsg()));
    }
}
