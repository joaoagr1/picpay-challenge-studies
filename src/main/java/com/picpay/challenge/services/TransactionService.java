package com.picpay.challenge.services;

import com.picpay.challenge.domain.transaction.Transaction;
import com.picpay.challenge.domain.user.User;
import com.picpay.challenge.dtos.TransactionDTO;
import com.picpay.challenge.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository respository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AuthorizarionService authorizarionService;


 public  Transaction createTransaction(TransactionDTO transaction) throws Exception {

     User sender = this.userService.findUserById(transaction.senderId());
     User receiver = this.userService.findUserById(transaction.receiverId());

     userService.validateTransaction(sender,transaction.value());

     boolean isAutorized = this.authorizarionService.authorizeTransaction(sender,transaction.value());

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

    this.notificationService.sendNotification(sender, "Transação realizada com sucesso");
    this.notificationService.sendNotification(receiver,"Transação recebida com sucesso");

     return newTransaction;

 }



}
