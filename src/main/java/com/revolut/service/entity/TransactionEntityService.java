package com.revolut.service.entity;

import com.revolut.DB;
import com.revolut.exceptions.TransactionException;
import com.revolut.exceptions.ValidationException;
import com.revolut.model.entities.AccountEntity;
import com.revolut.model.entities.TransactionEntity;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class TransactionEntityService extends AbstractEntityService<TransactionEntity> {


    public TransactionEntityService() {
        super(TransactionEntity.class);
    }


    public void makeTransaction(UUID payer, UUID receiver, BigDecimal amount) throws TransactionException, ValidationException {
        Session session = DB.getSessionFactory().getCurrentSession();

        try {
            session.getTransaction().begin();

            Optional<AccountEntity> optionalPayerAccount = Optional.ofNullable(session.get(AccountEntity.class, payer));
            Optional<AccountEntity> optionalReceiverAccount = Optional.ofNullable(session.get(AccountEntity.class, receiver));

            if (!optionalPayerAccount.isPresent())
                throw new ValidationException("Payer account not found by id: " + payer);
            if (!optionalReceiverAccount.isPresent())
                throw new ValidationException("Receiver account not found by id: " + receiver);

            AccountEntity payerAccount = optionalPayerAccount.get();
            payerAccount.setBalance(payerAccount.getBalance().subtract(amount));

            AccountEntity receiverAccount = optionalReceiverAccount.get();
            receiverAccount.setBalance(receiverAccount.getBalance().add(amount));

            TransactionEntity transaction = new TransactionEntity(
                    LocalDateTime.now(),
                    payerAccount,
                    receiverAccount,
                    amount
            );

            session.save(transaction);

            session.getTransaction().commit();
        } catch (ValidationException ve) {
            log.debug("Validation Error: ", ve);
            session.getTransaction().rollback();
            throw ve;
        } catch (Exception e) {
            log.error("Exception: ", e);
            session.getTransaction().rollback();
            throw new TransactionException("Something went wrong. Please try again later. Have a nice day");
        }
    }

}
