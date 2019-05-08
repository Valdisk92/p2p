package com.p2p.validators.transaction;

import com.p2p.exceptions.ValidationException;
import com.p2p.model.dto.Transaction;
import com.p2p.validators.Validator;

public class AmountNotNullValidator implements Validator<Transaction> {

    @Override
    public void validate(Transaction transaction) throws ValidationException {
        if (transaction.getAmount() == null) {
            throw new ValidationException("Transaction amount must be exists");
        }
    }
}
