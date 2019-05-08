package com.p2p.service.impl;

import com.p2p.exceptions.TransactionException;
import com.p2p.exceptions.ValidationException;
import com.p2p.model.dto.Transaction;
import com.p2p.service.TransactionService;
import com.p2p.service.entity.TransactionEntityService;
import com.p2p.validators.Validator;
import com.p2p.validators.transaction.AmountHigherThanZeroValidator;
import com.p2p.validators.transaction.AmountNotNullValidator;
import com.p2p.validators.transaction.AmountNotZeroValidator;
import com.p2p.validators.transaction.NotNullValidator;

import java.util.LinkedList;
import java.util.Queue;

public class DefaultTransactionService implements TransactionService {

    private final TransactionEntityService transactionEntityService;

    private Queue<Validator<Transaction>> validatorsQueue;

    public DefaultTransactionService(TransactionEntityService transactionEntityService) {
        this.transactionEntityService = transactionEntityService;

        this.validatorsQueue = new LinkedList<>();
        this.validatorsQueue.add(new NotNullValidator());
        this.validatorsQueue.add(new AmountNotNullValidator());
        this.validatorsQueue.add(new AmountNotZeroValidator());
        this.validatorsQueue.add(new AmountHigherThanZeroValidator());

    }

    @Override
    public void makeTransaction(Transaction transaction) throws ValidationException, TransactionException {
        validate(transaction);

        transactionEntityService.makeTransaction(transaction.getFrom(), transaction.getTo(), transaction.getAmount());
    }

    private void validate(Transaction transaction) throws ValidationException {
        for (Validator<Transaction> validator : validatorsQueue) {
            validator.validate(transaction);
        }
    }

}
