package com.inatel.oopbank.model.investment;

import com.inatel.oopbank.model.investmentaccount.InvestmentAccount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "investment")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "investment_type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Investment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    private BigDecimal yield;

    @Column(name = "current_value", nullable = false)
    private BigDecimal currentValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "investment_account_id", nullable = false)
    private InvestmentAccount investmentAccount;

    public abstract BigDecimal calculateYield(LocalDateTime toDateTime);

    public void updateFinancials() {
        BigDecimal yield = calculateYield(LocalDateTime.now());
        setYield(yield);
        setCurrentValue(getAmount().add(yield));
    }
}
