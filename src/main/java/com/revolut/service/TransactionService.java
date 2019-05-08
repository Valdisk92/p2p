package com.revolut.service;

import com.revolut.exceptions.TransactionException;
import com.revolut.exceptions.ValidationException;
import com.revolut.model.dto.Transaction;

public interface TransactionService {
    void makeTransaction(Transaction transaction) throws ValidationException, TransactionException;
}
