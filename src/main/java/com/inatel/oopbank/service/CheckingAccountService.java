package com.inatel.oopbank.service;

import com.inatel.oopbank.model.checkingaccount.CheckingAccount;
import com.inatel.oopbank.repository.CheckingAccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckingAccountService {

    private final CheckingAccountRepository checkingAccountRepository;

    public CheckingAccount create(CheckingAccount checkingAccount) {
        return checkingAccountRepository.save(checkingAccount);
    }

    public Optional<CheckingAccount> findById(UUID id) {
        return checkingAccountRepository.findById(id);
    }

    public List<CheckingAccount> findAll() {
        return checkingAccountRepository.findAll();
    }

    public void update(CheckingAccount checkingAccount) {
        if (!checkingAccountRepository.existsById(checkingAccount.getId())) {
            throw new IllegalArgumentException("Checking account not found");
        }
        checkingAccountRepository.save(checkingAccount);
    }

    public void deleteById(UUID id) {
        if (!checkingAccountRepository.existsById(id)) {
            throw new IllegalArgumentException("Checking account not found");
        }
        checkingAccountRepository.deleteById(id);
    }
}
