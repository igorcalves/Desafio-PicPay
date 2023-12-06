package com.picpayPortifolio.picpayPortifolio.repositories;

import com.picpayPortifolio.picpayPortifolio.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionReposity extends JpaRepository<Transaction, Long> {}