package org.learn.aws.start.repository;

import org.learn.aws.start.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
}
