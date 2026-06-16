package edu.dadaev.greenpoint.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class ResourceBookedException extends RuntimeException {

    public ResourceBookedException() {
        super("выбранный ресурс уже забронирован на указанные даты");
    }
}
