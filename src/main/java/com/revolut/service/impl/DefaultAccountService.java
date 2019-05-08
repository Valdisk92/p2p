package com.revolut.service.impl;

import com.revolut.model.dto.Account;
import com.revolut.model.dto.Transaction;
import com.revolut.model.entities.AccountEntity;
import com.revolut.model.entities.TransactionEntity;
import com.revolut.service.AccountService;
import com.revolut.service.entity.AccountEntityService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DefaultAccountService implements AccountService {

    private final AccountEntityService accountEntityService;

    private Function<TransactionEntity, Transaction> transactionEntityToTransactionFunction = transactionEntity -> {
        return new Transaction(
                transactionEntity.getFrom().getId(),
                transactionEntity.getTo().getId(),
                transactionEntity.getAmount(),
                transactionEntity.getDate()
        );
    };

    private Function<AccountEntity, Account> accountEntityToAccountFunction = accountEntity -> {
        return new Account(
                accountEntity.getId(),
                accountEntity.getOwner(),
                accountEntity.getBalance(),
                accountEntity.getMyTransactions().stream().map(transactionEntityToTransactionFunction).collect(Collectors.toList()),
                accountEntity.getTransactionsToMe().stream().map(transactionEntityToTransactionFunction).collect(Collectors.toList())
        );
    };


    public DefaultAccountService(AccountEntityService accountEntityService) {
        this.accountEntityService = accountEntityService;
    }

    @Override
    public Optional<Account> findOne(UUID id) {
        return this.accountEntityService.findOne(id)
                .map(accountEntityToAccountFunction);
    }

    @Override
    public List<Account> findAll() {
        return accountEntityService.findAll().stream()
                .map(accountEntityToAccountFunction)
                .collect(Collectors.toList());
    }


    @Override
    public Account create(Account account) {
        AccountEntity accountEntity = new AccountEntity(
                account.getOwner(),
                account.getBalance()
        );

        AccountEntity createdAccount = accountEntityService.save(accountEntity);

        return accountEntityToAccountFunction.apply(createdAccount);
    }

}
