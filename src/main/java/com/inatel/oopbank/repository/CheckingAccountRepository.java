package com.inatel.oopbank.repository;

import com.inatel.oopbank.model.checkingaccount.CheckingAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CheckingAccountRepository extends JpaRepository<CheckingAccount, UUID> {
}
