package com.inatel.oopbank.cli;

import com.inatel.oopbank.service.CheckingAccountService;
import com.inatel.oopbank.service.CustomerDocumentService;
import com.inatel.oopbank.service.CustomerService;
import com.inatel.oopbank.service.InvestmentAccountService;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

@RequiredArgsConstructor
public class MainMenu {

    private final CustomerService customerService;
    private final CustomerDocumentService documentService;
    private final CheckingAccountService checkingAccountService;
    private final InvestmentAccountService investmentAccountService;

    public void show() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n===== OopBank Main Menu =====");
                System.out.println("1 - Manage Customers");
                System.out.println("2 - Manage Checking Accounts (Delete method not working!)");
                System.out.println("3 - Manage Investment Accounts (Delete method may not work!)");
                System.out.println("0 - Exit");
                System.out.print("Option: ");
                String option = scanner.nextLine();

                switch (option) {
                    case "1" -> {
                        CustomerMenu customerMenu = new CustomerMenu(customerService, documentService);
                        customerMenu.show(scanner);
                    }
                    case "2" -> {
                        CheckingAccountMenu checkingAccountMenu = new CheckingAccountMenu(checkingAccountService, customerService);
                        checkingAccountMenu.show(scanner);
                    }
                    case "3" -> {
                        InvestmentAccountMenu investmentAccountMenu = new InvestmentAccountMenu(investmentAccountService, checkingAccountService);
                        investmentAccountMenu.show(scanner);
                    }
                    case "0" -> {
                        System.out.println("👋 Exiting. Goodbye!");
                        return;
                    }
                    default -> System.out.println("❌ Invalid option.");
                }
            }
        }
    }
}