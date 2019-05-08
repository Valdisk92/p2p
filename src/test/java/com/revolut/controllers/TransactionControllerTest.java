package com.revolut.controllers;

import com.revolut.model.dto.Account;
import com.revolut.model.dto.ErrorResponse;
import com.revolut.model.dto.Transaction;
import com.revolut.service.AccountService;
import com.revolut.service.entity.AccountEntityService;
import com.revolut.service.impl.DefaultAccountService;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.function.Function;

import static org.junit.Assert.*;

public class TransactionControllerTest extends JerseyTest {

    private Account miguel;
    private Account vladyslav;

    @Override
    protected Application configure() {
        ResourceConfig resourceConfig = new ResourceConfig()
                .register(new TransactionController())
                .register(new AccountController());

        return new ResourceConfig(resourceConfig);
    }

    @Before
    public void createTwoTestAccounts() {
        Account firstAccount = new Account("Miguel Follana", new BigDecimal("100500.54"));
        Account secondAccount = new Account("Vladyslav Korostel", new BigDecimal("985.43"));

        AccountService accountService = new DefaultAccountService(new AccountEntityService());

        this.miguel = accountService.create(firstAccount);
        this.vladyslav = accountService.create(secondAccount);
    }

    @Test
    public void shouldBeExceptionPayerNotFound() {
        Transaction transaction = new Transaction(
                UUID.randomUUID(),
                this.miguel.getId(),
                new BigDecimal("100.43")
        );

        testError(transaction, msg -> msg.startsWith("Payer account not found by id:"));
    }

    @Test
    public void shouldBeExceptionReceiverNotFound() {
        Transaction transaction = new Transaction(
                this.vladyslav.getId(),
                UUID.randomUUID(),
                new BigDecimal("100.43")
        );

        testError(transaction, msg -> msg.startsWith("Receiver account not found by id:"));
    }

    @Test
    public void shouldBeExceptionAmountMustBeNotNull() {
        Transaction transaction = new Transaction(
                this.vladyslav.getId(),
                UUID.randomUUID(),
                null
        );

        testError(transaction, msg -> msg.equals("Transaction amount must be exists"));
    }

    @Test
    public void shouldBeExceptionAmountMustNotBeZero() {
        Transaction transaction = new Transaction(
                UUID.randomUUID(),
                this.miguel.getId(),
                new BigDecimal("0")
        );

        testError(transaction, msg -> msg.startsWith("Transaction amount must not be zero"));
    }

     @Test
    public void shouldBeExceptionAmountMustHigherThanZero() {
        Transaction transaction = new Transaction(
                UUID.randomUUID(),
                this.miguel.getId(),
                new BigDecimal("-100")
        );

        testError(transaction, msg -> msg.startsWith("Transaction amount must be higher than zero"));
    }

    private void testError(Transaction transaction, Function<String, Boolean> checkFunc) {
        Response response = target("/v1/transaction")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(transaction, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());

        ErrorResponse errorResponse = response.readEntity(ErrorResponse.class);

        assertNotNull(errorResponse.getMessage());
        assertTrue(checkFunc.apply(errorResponse.getMessage()));
    }

    @Test
    public void shouldBeCompleteTransactionSuccessfully() {
        assertNotNull(this.miguel);
        assertNotNull(this.vladyslav);

        Transaction transaction = new Transaction(
                this.vladyslav.getId(),
                this.miguel.getId(),
                new BigDecimal("100.43")
        );

        Response response = target("/v1/transaction")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(transaction, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        Account miguelAfterTransaction = target("/v1/account/".concat(this.miguel.getId().toString()))
                .request()
                .get(Account.class);

        Account vladyslavAfterTransaction = target("/v1/account/".concat(this.vladyslav.getId().toString()))
                .request()
                .get(Account.class);

        assertEquals(1, miguelAfterTransaction.getInTransactions().size());
        assertEquals(1, vladyslavAfterTransaction.getOutTransactions().size());

        assertEquals(new BigDecimal("100600.97"), miguelAfterTransaction.getBalance());
        assertEquals(new BigDecimal("885.00"), vladyslavAfterTransaction.getBalance());
    }
}