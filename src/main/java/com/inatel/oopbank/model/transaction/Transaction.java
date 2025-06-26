package com.inatel.oopbank.model.transaction;

import com.inatel.oopbank.model.checkingaccount.CheckingAccount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
// Transaction may be a reserved word
// in some databases.
@Table(name = "transaction")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "related_account_id")
    private UUID relatedAccountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checking_account_id", nullable = false)
    private CheckingAccount checkingAccount;

    @PrePersist
    public void prePersist() {
        this.timestamp = LocalDateTime.now();
    }
}
