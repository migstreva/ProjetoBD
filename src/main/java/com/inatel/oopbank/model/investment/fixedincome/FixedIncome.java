package com.inatel.oopbank.model.investment.fixedincome;

import com.inatel.oopbank.model.investment.Investment;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("FIXED")
public class FixedIncome extends Investment {

    @Enumerated(EnumType.STRING)
    @Column(name = "fixed_income_type")
    private FixedIncomeType fixedIncomeType;

    @Column(name = "daily_interest_rate", nullable = false)
    private BigDecimal dailyInterestRate;

    @Column(name = "tax_rate", nullable = false)
    private BigDecimal taxRate;

    @Override
    public BigDecimal calculateYield(LocalDateTime toDateTime) {
        long daysElapsed = Duration.between(getStartDate(), toDateTime).toDays();

        if (daysElapsed <= 0) return BigDecimal.ZERO;

        double base = BigDecimal.ONE.add(dailyInterestRate).doubleValue(); // 1 + r
        double compoundFactor = Math.pow(base, daysElapsed);               // (1 + r)^t

        BigDecimal grossAmount = getAmount().multiply(BigDecimal.valueOf(compoundFactor));
        BigDecimal rawYield = grossAmount.subtract(getAmount());

        // Subtract tax
        BigDecimal netYield = rawYield.multiply(BigDecimal.ONE.subtract(taxRate));

        return netYield.setScale(2, RoundingMode.HALF_UP);
    }

}
