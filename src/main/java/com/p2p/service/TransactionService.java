package com.p2p.service;

import com.p2p.exceptions.TransactionException;
import com.p2p.exceptions.ValidationException;
import com.p2p.model.dto.Transaction;

public interface TransactionService {
    void makeTransaction(Transaction transaction) throws ValidationException, TransactionException;
}
