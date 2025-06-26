package com.inatel.oopbank;

import com.inatel.oopbank.cli.MainMenu;
import com.inatel.oopbank.service.CheckingAccountService;
import com.inatel.oopbank.service.CustomerDocumentService;
import com.inatel.oopbank.service.CustomerService;
import com.inatel.oopbank.service.InvestmentAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class OopbankApplication implements CommandLineRunner {

	private final CustomerService customerService;
	private final CustomerDocumentService documentService;
	private final CheckingAccountService checkingAccountService;
	private final InvestmentAccountService investmentAccountService;

	public static void main(String[] args) {
		SpringApplication.run(OopbankApplication.class, args);
	}

	@Override
	public void run(String... args) {
		new MainMenu(customerService, documentService, checkingAccountService, investmentAccountService).show();
	}
}
