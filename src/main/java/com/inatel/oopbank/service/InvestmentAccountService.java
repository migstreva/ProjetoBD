package com.inatel.oopbank.service;

import com.inatel.oopbank.model.investmentaccount.InvestmentAccount;
import com.inatel.oopbank.repository.InvestmentAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvestmentAccountService {

    private final InvestmentAccountRepository investmentAccountRepository;

    public InvestmentAccount create(InvestmentAccount investmentAccount) {
        return investmentAccountRepository.save(investmentAccount);
    }

    public Optional<InvestmentAccount> findById(UUID id) {
        return investmentAccountRepository.findById(id);
    }

    public List<InvestmentAccount> findAll() {
        return investmentAccountRepository.findAll();
    }

    public void update(InvestmentAccount investmentAccount) {
        if (!investmentAccountRepository.existsById(investmentAccount.getId())) {
            throw new IllegalArgumentException("Investment account not found");
        }
        investmentAccountRepository.save(investmentAccount);
    }

    public void deleteById(UUID id) {
        if (!investmentAccountRepository.existsById(id)) {
            throw new IllegalArgumentException("Investment account not found");
        }
        investmentAccountRepository.deleteById(id);
    }

}
