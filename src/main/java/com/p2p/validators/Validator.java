package com.p2p.validators;

import com.p2p.exceptions.ValidationException;

public interface Validator<T> {


    void validate(T object) throws ValidationException;


}
