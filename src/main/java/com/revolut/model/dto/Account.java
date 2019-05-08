package com.revolut.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Account {

    @JsonProperty("id")
    private UUID id;
    @JsonProperty("owner")
    private String owner;
    @JsonProperty("balance")
    private BigDecimal balance;
    @JsonProperty("outTransactions")
    private List<Transaction> outTransactions = Collections.emptyList();
    @JsonProperty("inTransactions")
    private List<Transaction> inTransactions = Collections.emptyList();

    public Account(UUID id, String owner, BigDecimal balance, List<Transaction> outTransactions, List<Transaction> inTransactions) {
        this.id = id;
        this.owner = owner;
        this.balance = balance;
        this.outTransactions = outTransactions;
        this.inTransactions = inTransactions;
    }

    public Account(String owner, BigDecimal balance) {
        this.owner = owner;
        this.balance = balance;
    }
}
