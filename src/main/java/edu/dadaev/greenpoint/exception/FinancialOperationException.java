package edu.dadaev.greenpoint.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FinancialOperationException extends RuntimeException{
    public FinancialOperationException() {
        super("не удалось выполнить списание средств");
    }
}
