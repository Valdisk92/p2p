package com.revolut.controllers;

import com.revolut.exceptions.TransactionException;
import com.revolut.exceptions.ValidationException;
import com.revolut.model.dto.ErrorResponse;
import com.revolut.model.dto.Transaction;
import com.revolut.service.impl.DefaultTransactionService;
import com.revolut.service.TransactionService;
import com.revolut.service.entity.TransactionEntityService;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Slf4j
@Path("v1/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController() {
        this.transactionService = new DefaultTransactionService(
                new TransactionEntityService()
        );
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response transaction(Transaction transaction) {
        try {
            this.transactionService.makeTransaction(transaction);

            return Response.ok().build();
        } catch (TransactionException e) {
            log.error("Exception: ", e);

            return Response.serverError()
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        } catch (ValidationException e) {
            log.debug("ValidationException: ", e);

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }


}
