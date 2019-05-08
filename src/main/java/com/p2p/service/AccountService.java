package com.p2p.service;

import com.p2p.model.dto.Account;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountService {
    Optional<Account> findOne(UUID id);

    List<Account> findAll();

    Account create(Account account);
}
