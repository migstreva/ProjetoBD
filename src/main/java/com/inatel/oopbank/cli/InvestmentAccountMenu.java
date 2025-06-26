package com.inatel.oopbank.cli;

import com.inatel.oopbank.model.investmentaccount.InvestmentAccount;
import com.inatel.oopbank.service.CheckingAccountService;
import com.inatel.oopbank.service.InvestmentAccountService;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.UUID;

@RequiredArgsConstructor
public class InvestmentAccountMenu {

    private final InvestmentAccountService investmentAccountService;
    private final CheckingAccountService checkingAccountService;

    public void show(Scanner scanner) {
        while (true) {
            System.out.println("\n=== Investment Account Menu ===");
            System.out.println("1 - Create Investment Account");
            System.out.println("2 - Find Investment Account by ID");
            System.out.println("3 - List All Investment Accounts");
            System.out.println("4 - Delete Investment Account");
            System.out.println("0 - Back to Main Menu");
            System.out.print("Option: ");
            String option = scanner.nextLine();

            try {
                switch (option) {
                    case "1" -> createAccount(scanner);
                    case "2" -> findAccountById(scanner);
                    case "3" -> listAccounts();
                    case "4" -> deleteAccount(scanner);
                    case "0" -> { return; }
                    default -> System.out.println("❌ Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }
    }

    private void createAccount(Scanner scanner) {
        System.out.print("Checking Account ID: ");
        UUID checkingAccountId = UUID.fromString(scanner.nextLine());

        var optionalChecking = checkingAccountService.findById(checkingAccountId);
        if (optionalChecking.isEmpty()) {
            System.out.println("❌ Checking account not found.");
            return;
        }

        var checkingAccount = optionalChecking.get();

        InvestmentAccount account = new InvestmentAccount();
        account.setCheckingAccount(checkingAccount);
        account.setBalance(BigDecimal.ZERO);

        InvestmentAccount saved = investmentAccountService.create(account);
        System.out.println("✅ Investment account created with ID: " + saved.getId());
    }

    private void findAccountById(Scanner scanner) {
        System.out.print("Account ID: ");
        UUID id = UUID.fromString(scanner.nextLine());

        investmentAccountService.findById(id).ifPresentOrElse(
                acc -> {
                    System.out.println("📄 Account Info:");
                    System.out.println("- ID: " + acc.getId());
                    System.out.println("- Customer: " + acc.getCheckingAccount().getCustomer().getFullName());
                    System.out.println("- Balance: " + acc.getBalance());
                },
                () -> System.out.println("❌ Account not found.")
        );
    }

    private void listAccounts() {
        var list = investmentAccountService.findAll();
        if (list.isEmpty()) {
            System.out.println("⚠️ No investment accounts found.");
        } else {
            System.out.println("📋 Investment Accounts:");
            list.forEach(acc -> System.out.printf("- ID: %s | Customer: %s | Balance: %.2f\n",
                    acc.getId(), acc.getCheckingAccount().getCustomer().getFullName(), acc.getBalance()));
        }
    }

    private void deleteAccount(Scanner scanner) {
        System.out.print("Account ID to delete: ");
        UUID id = UUID.fromString(scanner.nextLine());

        investmentAccountService.deleteById(id);
        System.out.println("🗑️ Investment account deleted.");
    }
}
