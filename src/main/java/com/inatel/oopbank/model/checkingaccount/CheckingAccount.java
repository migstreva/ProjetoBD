package com.inatel.oopbank.model.checkingaccount;

import com.inatel.oopbank.model.customer.Customer;
import com.inatel.oopbank.model.investmentaccount.InvestmentAccount;
import com.inatel.oopbank.model.transaction.Transaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "checking_account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckingAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "account_number", nullable = false)
    private Long accountNumber;

    @Column(name = "bank_branch", nullable = false)
    private Integer bankBranch;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    private BigDecimal balance;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false, unique = true)
    private Customer customer;

    @OneToOne(mappedBy = "checkingAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private InvestmentAccount investmentAccount;

    @OneToMany(mappedBy = "checkingAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();

}
