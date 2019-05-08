package com.p2p.controllers;

import com.p2p.model.dto.Account;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AccountControllerTest extends JerseyTest {

    @Override
    protected Application configure() {
        ResourceConfig resourceConfig = new ResourceConfig().register(new AccountController());

        return new ResourceConfig(resourceConfig);
    }

    @Test
    public void shouldCreateAccount() {
        Account account = new Account("asddf", new BigDecimal(1000));

        Account response = target("/v1/account")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(account, MediaType.APPLICATION_JSON_TYPE), Account.class);

        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(response.getOwner(), account.getOwner());
        assertEquals(response.getBalance(), account.getBalance());
    }


    @Test
    public void shouldNotFound() {
        Response response = target("/v1/account/".concat(String.valueOf(UUID.randomUUID()))).request()
                .get();

        assertNotNull(response);
        assertEquals(response.getStatus(), Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void shouldFound() {
        Account account = new Account("Miguel Follana", new BigDecimal(1000));

        Account createdAccount = target("/v1/account")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(account, MediaType.APPLICATION_JSON_TYPE), Account.class);

        assertNotNull(createdAccount);
        assertNotNull(createdAccount.getId());

        Account response = target("/v1/account/".concat(String.valueOf(createdAccount.getId()))).request()
                .get(Account.class);

        assertNotNull(response);
        assertEquals(account.getOwner(), response.getOwner());
    }


}