package com.p2p.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ErrorResponse {

    @Getter
    @JsonProperty("message")
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
}
