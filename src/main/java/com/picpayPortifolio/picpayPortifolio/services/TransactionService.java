package com.picpayPortifolio.picpayPortifolio.services;

import com.picpayPortifolio.picpayPortifolio.domain.transaction.Transaction;
import com.picpayPortifolio.picpayPortifolio.domain.user.User;
import com.picpayPortifolio.picpayPortifolio.dtos.TransactionDTO;
import com.picpayPortifolio.picpayPortifolio.repositories.TransactionReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private TransactionReposity repository;

    @Autowired
    private NotificationService notificationService;

    public Transaction createTransaction(TransactionDTO transaction) throws Exception {
        User sender = this.userService.findUserById(transaction.senderId());
        User receiver = this.userService.findUserById((transaction.receiverId()));

        userService.validateTransaction(sender, transaction.value());

        boolean isAuthorized = !this.authorizeTransaction(sender, transaction.value());
        if (isAuthorized) {
            throw new Exception("Transação não Autorizada");
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));

        this.repository.save(new Transaction());
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);


        this.notificationService.sendNotification(sender,"Transação realizada com sucesso");
        this.notificationService.sendNotification(receiver,"Transação realizada com sucesso");


        return newTransaction;
    }


    public boolean authorizeTransaction(User sender, BigDecimal value) {
        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc", Map.class);
        if (authorizationResponse.getStatusCode() == HttpStatus.OK) {
            String message = (String) authorizationResponse.getBody().get("message");
            return "Autorizado".equalsIgnoreCase(message);
        } else return false;
    }

}
