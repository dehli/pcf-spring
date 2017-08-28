package io.dehli.accounts.repos;

import io.dehli.accounts.models.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Integer> {}
