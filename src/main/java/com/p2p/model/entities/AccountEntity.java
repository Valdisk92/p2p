package com.p2p.model.entities;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
public class AccountEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    private String owner;

    @Column(precision = 10, scale = 2)
    private BigDecimal balance;

    @OneToMany(mappedBy = "from", fetch = FetchType.EAGER)
    private List<TransactionEntity> myTransactions = new LinkedList<>();

    @OneToMany(mappedBy = "to", fetch = FetchType.EAGER)
    private List<TransactionEntity> transactionsToMe = new LinkedList<>();

    public AccountEntity(String owner, BigDecimal balance) {
        this.owner = owner;
        this.balance = balance;
    }
}
