package com.revolut.exceptions;

public class TransactionException extends Exception {
    public TransactionException(String message) {
        super(message);
    }
}
