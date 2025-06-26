package com.inatel.oopbank.repository;

import com.inatel.oopbank.model.investmentaccount.InvestmentAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InvestmentAccountRepository extends JpaRepository<InvestmentAccount, UUID> {
}
