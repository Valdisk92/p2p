package com.revolut.validators.transaction;

import com.revolut.exceptions.ValidationException;
import com.revolut.model.dto.Transaction;
import com.revolut.validators.Validator;

import java.math.BigDecimal;

public class AmountHigherThanZeroValidator implements Validator<Transaction> {
    @Override
    public void validate(Transaction transaction) throws ValidationException {
        if (transaction.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Transaction amount must be higher than zero");
        }
    }
}
