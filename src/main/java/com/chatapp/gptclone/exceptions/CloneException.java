package com.chatapp.gptclone.exceptions;


import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CloneException extends Exception {

    private HttpStatus httpStatus;

    public CloneException(String message) {
        super(message);
    }

    public CloneException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public CloneException(String message, Throwable cause) {
        super(message, cause);
    }

    public CloneException(Throwable cause) {
        super(cause);
    }


    public CloneException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    // ← NEW: three-arg (message, status, cause)
    public CloneException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

}
