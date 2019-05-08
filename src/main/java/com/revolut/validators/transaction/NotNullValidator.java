package com.revolut.validators.transaction;

import com.revolut.exceptions.ValidationException;
import com.revolut.model.dto.Transaction;
import com.revolut.validators.Validator;

public class NotNullValidator implements Validator<Transaction> {

    @Override
    public void validate(Transaction transaction) throws ValidationException {
        if (transaction == null) {
            throw new ValidationException("You have to pass a filled transaction object");
        }
    }

}
