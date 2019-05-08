package com.revolut.validators.transaction;

import com.revolut.exceptions.ValidationException;
import com.revolut.model.dto.Transaction;
import com.revolut.validators.Validator;

public class AmountNotNullValidator implements Validator<Transaction> {

    @Override
    public void validate(Transaction transaction) throws ValidationException {
        if (transaction.getAmount() == null) {
            throw new ValidationException("Transaction amount must be exists");
        }
    }
}
