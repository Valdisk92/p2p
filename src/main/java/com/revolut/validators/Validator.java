package com.revolut.validators;

import com.revolut.exceptions.ValidationException;

public interface Validator<T> {


    void validate(T object) throws ValidationException;


}
