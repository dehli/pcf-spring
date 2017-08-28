package io.dehli.transactions.repos;

import io.dehli.transactions.models.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {}
