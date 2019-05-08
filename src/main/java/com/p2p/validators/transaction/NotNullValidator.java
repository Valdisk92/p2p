package com.p2p.validators.transaction;

import com.p2p.exceptions.ValidationException;
import com.p2p.model.dto.Transaction;
import com.p2p.validators.Validator;

public class NotNullValidator implements Validator<Transaction> {

    @Override
    public void validate(Transaction transaction) throws ValidationException {
        if (transaction == null) {
            throw new ValidationException("You have to pass a filled transaction object");
        }
    }

}
