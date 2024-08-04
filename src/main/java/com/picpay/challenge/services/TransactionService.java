package com.picpay.challenge.services;

import com.picpay.challenge.domain.transaction.Transaction;
import com.picpay.challenge.domain.user.User;
import com.picpay.challenge.dtos.TransactionDTO;
import com.picpay.challenge.repository.TransactionRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRespository respository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;


 public  Transaction createTransaction(TransactionDTO transaction) throws Exception {

     User sender = this.userService.findUserById(transaction.senderId());
     User receiver = this.userService.findUserById(transaction.receiverId());

     userService.validateTransaction(sender,transaction.value());

     boolean isAutorized = this.authorizeTransaction(sender,transaction.value());

     if (!isAutorized){
         throw new Exception("Transação não autorizada.");
     }

     Transaction newTransaction = new Transaction();

     newTransaction.setAmount(transaction.value());
     newTransaction.setSender(sender);
     newTransaction.setReceiver(receiver);

     sender.setBalance(sender.getBalance().subtract(transaction.value()));
     receiver.setBalance(receiver.getBalance().add(transaction.value()));

     this.respository.save(newTransaction);
     this.userService.saveUser(sender);
     this.userService.saveUser(receiver);

    this.notificationService.sendNotififcation(sender, "Transação realizada com sucesso");
    this.notificationService.sendNotififcation(receiver,"Transação recebida com sucesso");

     return newTransaction;

 }

 public boolean authorizeTransaction (User sender, BigDecimal value){

    ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity(
            "https://util.devi.tools/api/v2/authorize", Map.class);

    if(authorizationResponse.getStatusCode() == HttpStatus.OK){

        return true;

    }

        return false;

 }

}
