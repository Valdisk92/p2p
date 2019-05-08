package com.revolut.service.impl;

import com.revolut.exceptions.TransactionException;
import com.revolut.exceptions.ValidationException;
import com.revolut.model.dto.Transaction;
import com.revolut.service.TransactionService;
import com.revolut.service.entity.TransactionEntityService;
import com.revolut.validators.Validator;
import com.revolut.validators.transaction.AmountHigherThanZeroValidator;
import com.revolut.validators.transaction.AmountNotNullValidator;
import com.revolut.validators.transaction.AmountNotZeroValidator;
import com.revolut.validators.transaction.NotNullValidator;

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
