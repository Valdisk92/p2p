package com.p2p.controllers;

import com.p2p.model.dto.Account;
import com.p2p.service.AccountService;
import com.p2p.service.impl.DefaultAccountService;
import com.p2p.service.entity.AccountEntityService;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Path("v1/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController() {
        this.accountService = new DefaultAccountService(
                new AccountEntityService()
        );
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findOne(@PathParam("id") UUID id) {
        Optional<Account> optionalAccount = accountService.findOne(id);

        return optionalAccount
                .map(account -> Response.ok(account).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        List<Account> accounts = this.accountService.findAll();

        return Response.ok(accounts)
                .build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Account account) {
        Account createdAccount = this.accountService.create(account);

        return Response.ok(createdAccount)
                .build();
    }

}
