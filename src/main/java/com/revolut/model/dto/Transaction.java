package com.revolut.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Transaction {

    @JsonProperty("from")
    private UUID from;

    @JsonProperty("to")
    private UUID to;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("date")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime date;

    public Transaction(UUID from, UUID to, BigDecimal amount, LocalDateTime date) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.date = date;
    }

    public Transaction(UUID from, UUID to, BigDecimal amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }
}
