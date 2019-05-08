package com.revolut.service;

import com.revolut.model.dto.Account;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountService {
    Optional<Account> findOne(UUID id);

    List<Account> findAll();

    Account create(Account account);
}
