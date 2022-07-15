package ru.sberjavaproject.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class ErrorsUtil {

    public static void ErrorReturning(BindingResult bindingResult) {
        StringBuilder errorMsg = new StringBuilder();

        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError fieldError : errors) {
            errorMsg.append(fieldError.getField()).append(" - ")
                    .append(fieldError.getDefaultMessage() == null ? fieldError.getCode() : fieldError.getDefaultMessage())
                    .append(";");
        }
        throw new InvalidRequestException(errorMsg.toString());
    }
}
