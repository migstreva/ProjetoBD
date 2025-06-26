package com.inatel.oopbank.model.investment.variableincome;

import com.inatel.oopbank.model.investment.Investment;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("VARIABLE")
@Getter
@Setter
@NoArgsConstructor
public class VariableIncome extends Investment {

    @Column(name = "ticker", length = 10, nullable = false)
    private String tickerSymbol;

    @Column(name = "purchase_price", nullable = false)
    private BigDecimal purchasePrice;

    @Override
    public BigDecimal calculateYield(LocalDateTime toDateTime) {
        try {
            Stock stock = YahooFinance.get(tickerSymbol);

            if (stock == null || stock.getQuote().getPrice() == null) {
                throw new IllegalStateException("Could not retrieve price for " + tickerSymbol);
            }

            BigDecimal currentPrice = stock.getQuote().getPrice();

            return currentPrice.subtract(purchasePrice)
                    .divide(purchasePrice, 2, RoundingMode.HALF_UP)
                    .multiply(getAmount());

        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch data for " + tickerSymbol, e);
        }
    }
}
