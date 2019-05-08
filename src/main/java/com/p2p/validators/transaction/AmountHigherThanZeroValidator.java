package com.p2p.validators.transaction;

import com.p2p.exceptions.ValidationException;
import com.p2p.model.dto.Transaction;
import com.p2p.validators.Validator;

import java.math.BigDecimal;

public class AmountHigherThanZeroValidator implements Validator<Transaction> {
    @Override
    public void validate(Transaction transaction) throws ValidationException {
        if (transaction.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Transaction amount must be higher than zero");
        }
    }
}
