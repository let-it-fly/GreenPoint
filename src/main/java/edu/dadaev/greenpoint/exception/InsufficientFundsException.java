package edu.dadaev.greenpoint.exception;

public class InsufficientFundsException extends RuntimeException{

    public InsufficientFundsException() {
        super("Недостаточно средств на балансе");
    }
}
