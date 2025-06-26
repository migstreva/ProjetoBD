package com.inatel.oopbank.cli;

import com.inatel.oopbank.model.checkingaccount.CheckingAccount;
import com.inatel.oopbank.model.customer.Customer;
import com.inatel.oopbank.service.CheckingAccountService;
import com.inatel.oopbank.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

@RequiredArgsConstructor
public class CheckingAccountMenu {

    private final CheckingAccountService checkingAccountService;
    private final CustomerService customerService;

    public void show(Scanner scanner) {
        while (true) {
            System.out.println("\n=== Checking Account Menu ===");
            System.out.println("1 - Create Checking Account");
            System.out.println("2 - Find Checking Account by ID");
            System.out.println("3 - List All Checking Accounts");
            System.out.println("4 - Update Checking Account");
            System.out.println("5 - Delete Checking Account by ID");
            System.out.println("0 - Back to Main Menu");
            System.out.print("Option: ");
            String option = scanner.nextLine();

            try {
                switch (option) {
                    case "1" -> createCheckingAccount(scanner);
                    case "2" -> findCheckingAccountById(scanner);
                    case "3" -> listCheckingAccounts();
                    case "4" -> updateCheckingAccount(scanner);
                    case "5" -> deleteCheckingAccount(scanner);
                    case "0" -> {
                        return; // Back to main menu
                    }
                    default -> System.out.println("❌ Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }
    }

    private void createCheckingAccount(Scanner scanner) {
        System.out.print("Customer ID for this account: ");
        UUID customerId = UUID.fromString(scanner.nextLine());
        Optional<Customer> optionalCustomer = customerService.findById(customerId);
        if (optionalCustomer.isEmpty()) {
            System.out.println("❌ Customer not found.");
            return;
        }

        System.out.print("Account Number: ");
        Long accountNumber = Long.parseLong(scanner.nextLine());

        System.out.print("Bank Branch: ");
        Integer bankBranch = Integer.parseInt(scanner.nextLine());

        System.out.print("Is Active (true/false): ");
        Boolean isActive = Boolean.parseBoolean(scanner.nextLine());

        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setCustomer(optionalCustomer.get());
        checkingAccount.setAccountNumber(accountNumber);
        checkingAccount.setBankBranch(bankBranch);
        checkingAccount.setIsActive(isActive);
        checkingAccount.setBalance(BigDecimal.ZERO);
        checkingAccount.setCreatedAt(LocalDateTime.now());
        checkingAccount.setInvestmentAccount(null);

        CheckingAccount saved = checkingAccountService.create(checkingAccount);
        System.out.println("✅ Checking Account created with ID: " + saved.getId());
    }

    private void findCheckingAccountById(Scanner scanner) {
        System.out.print("Checking Account ID: ");
        UUID id = UUID.fromString(scanner.nextLine());

        checkingAccountService.findById(id).ifPresentOrElse(
                acc -> {
                    System.out.println("🧾 Checking Account Info:");
                    System.out.println("- Account Number: " + acc.getAccountNumber());
                    System.out.println("- Bank Branch: " + acc.getBankBranch());
                    System.out.println("- Active: " + acc.getIsActive());
                    System.out.println("- Balance: " + acc.getBalance());
                    System.out.println("- Created At: " + acc.getCreatedAt());
                    System.out.println("- Customer Full Name: " + acc.getCustomer().getFullName());
                },
                () -> System.out.println("❌ Checking Account not found.")
        );
    }

    private void listCheckingAccounts() {
        var list = checkingAccountService.findAll();
        if (list.isEmpty()) {
            System.out.println("⚠️ No checking accounts found.");
        } else {
            System.out.println("📋 Checking Account List:");
            list.forEach(acc -> System.out.printf("- %s (ID: %s, Customer Full Name: %s)\n",
                    acc.getAccountNumber(), acc.getId(), acc.getCustomer().getFullName()));
        }
    }

    private void updateCheckingAccount(Scanner scanner) {
        System.out.print("Checking Account ID to update: ");
        UUID id = UUID.fromString(scanner.nextLine());

        Optional<CheckingAccount> optionalAccount = checkingAccountService.findById(id);
        if (optionalAccount.isEmpty()) {
            System.out.println("❌ Checking Account not found.");
            return;
        }

        CheckingAccount account = optionalAccount.get();

        System.out.println("Leave blank to keep current value.\n");

        System.out.printf("Account Number (%s): ", account.getAccountNumber());
        String accountNumberStr = scanner.nextLine();
        if (!accountNumberStr.isBlank()) account.setAccountNumber(Long.parseLong(accountNumberStr));

        System.out.printf("Bank Branch (%s): ", account.getBankBranch());
        String bankBranchStr = scanner.nextLine();
        if (!bankBranchStr.isBlank()) account.setBankBranch(Integer.parseInt(bankBranchStr));

        System.out.printf("Is Active (%s): ", account.getIsActive());
        String isActiveStr = scanner.nextLine();
        if (!isActiveStr.isBlank()) account.setIsActive(Boolean.parseBoolean(isActiveStr));

        checkingAccountService.update(account);
        System.out.println("✅ Checking Account updated successfully.");
    }

    private void deleteCheckingAccount(Scanner scanner) {
        System.out.print("Checking Account ID to delete: ");
        UUID id = UUID.fromString(scanner.nextLine());

        checkingAccountService.deleteById(id);
        System.out.println("🗑️ Checking Account deleted.");
    }
}
