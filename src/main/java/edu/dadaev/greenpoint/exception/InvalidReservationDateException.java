package edu.dadaev.greenpoint.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidReservationDateException extends RuntimeException{

    public InvalidReservationDateException() {
        super("неверная дата бронирования");
    }
}
