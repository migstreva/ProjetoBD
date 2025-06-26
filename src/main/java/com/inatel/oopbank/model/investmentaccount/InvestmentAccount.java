package com.inatel.oopbank.model.investmentaccount;

import com.inatel.oopbank.model.checkingaccount.CheckingAccount;
import com.inatel.oopbank.model.investment.Investment;
import com.inatel.oopbank.model.investmentaccount.riskprofile.RiskProfileType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "investment_account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvestmentAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_profile")
    private RiskProfileType riskProfileType;

    private BigDecimal balance;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "checking_account_id", nullable = false)
    private CheckingAccount checkingAccount;

    @OneToMany(mappedBy = "investmentAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Investment> investments = new ArrayList<>();

}
