package com.picpay.challenge.repository;

import com.picpay.challenge.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRespository extends JpaRepository<Transaction,Long> {
}
