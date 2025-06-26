package com.inatel.oopbank.repository;

import com.inatel.oopbank.model.investment.Investment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InvestmentRepository extends JpaRepository<Investment, UUID> {
}
