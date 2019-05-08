package com.p2p.model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "transactions")
@NoArgsConstructor
public class TransactionEntity {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    
    private LocalDateTime date;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "from_account")
    private AccountEntity from;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "to_account")
    private AccountEntity to;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal amount;

    public TransactionEntity(LocalDateTime date, AccountEntity from, AccountEntity to, BigDecimal amount) {
        this.date = date;
        this.from = from;
        this.to = to;
        this.amount = amount;
    }
}
