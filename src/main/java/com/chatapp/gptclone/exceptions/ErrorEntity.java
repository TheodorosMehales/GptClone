package com.chatapp.gptclone.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorEntity {

    private String message;
    private Integer errorCode;
    private String errorDescription;

}
