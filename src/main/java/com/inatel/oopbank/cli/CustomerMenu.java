package com.inatel.oopbank.cli;

import com.inatel.oopbank.model.customer.Customer;
import com.inatel.oopbank.model.customer.address.Address;
import com.inatel.oopbank.model.customerdocument.CustomerDocument;
import com.inatel.oopbank.model.customerdocument.DocumentType;
import com.inatel.oopbank.service.CustomerDocumentService;
import com.inatel.oopbank.service.CustomerService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.UUID;

@RequiredArgsConstructor
public class CustomerMenu {

    private final CustomerService customerService;
    private final CustomerDocumentService documentService;

    public void show(Scanner scanner) {
        while (true) {
            System.out.println("\n=== Customer Menu ===");
            System.out.println("1 - Create Customer");
            System.out.println("2 - Find Customer by ID");
            System.out.println("3 - List All Customers");
            System.out.println("4 - Update Customer");
            System.out.println("5 - Delete Customer by ID");
            System.out.println("6 - Manage Customer Documents");
            System.out.println("0 - Back to Main Menu");
            System.out.print("Option: ");
            String option = scanner.nextLine();

            try {
                switch (option) {
                    case "1" -> createCustomer(scanner);
                    case "2" -> findCustomer(scanner);
                    case "3" -> listCustomers();
                    case "4" -> updateCustomer(scanner);
                    case "5" -> deleteCustomer(scanner);
                    case "6" -> manageDocuments(scanner);
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

    private void createCustomer(Scanner scanner) {
        System.out.print("Full Name: ");
        String fullName = scanner.nextLine();

        System.out.print("Date of Birth (yyyy-mm-dd): ");
        LocalDate dob = LocalDate.parse(scanner.nextLine());

        System.out.print("Phone: ");
        String phone = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Occupation: ");
        String occupation = scanner.nextLine();

        System.out.println("=== Address ===");
        System.out.print("Street: ");
        String street = scanner.nextLine();
        System.out.print("Number: ");
        String number = scanner.nextLine();
        System.out.print("City: ");
        String city = scanner.nextLine();
        System.out.print("State: ");
        String state = scanner.nextLine();
        System.out.print("ZIP: ");
        String zip = scanner.nextLine();

        Address address = new Address(number, street, city, state, zip);

        Customer customer = new Customer(fullName, dob, phone, email, address, occupation);
        Customer saved = customerService.create(customer);
        System.out.println("✅ Customer created with ID: " + saved.getId());
    }

    private void manageDocuments(Scanner scanner) {
        System.out.print("Enter Customer ID to manage documents: ");
        UUID customerId = UUID.fromString(scanner.nextLine());
        var optionalCustomer = customerService.findById(customerId);
        if (optionalCustomer.isEmpty()) {
            System.out.println("❌ Customer not found.");
            return;
        }
        Customer customer = optionalCustomer.get();

        while (true) {
            System.out.println("\n--- Document Menu for " + customer.getFullName() + " ---");
            System.out.println("1 - List Documents");
            System.out.println("2 - Add Document");
            System.out.println("3 - Update Document");
            System.out.println("4 - Delete Document");
            System.out.println("0 - Back to Customer Menu");
            System.out.print("Option: ");
            String docOption = scanner.nextLine();

            switch (docOption) {
                case "1" -> {
                    var docs = documentService.findByCustomer(customer);
                    if (docs.isEmpty()) {
                        System.out.println("⚠️ No documents found.");
                    } else {
                        docs.forEach(d -> System.out.printf("- %s: %s (ID: %s)\n",
                                d.getDocumentType(), d.getDocumentNumber(), d.getId()));
                    }
                }
                case "2" -> {
                    System.out.print("Document Type (e.g., CPF, Passport): ");
                    String type = scanner.nextLine();
                    System.out.print("Document Number: ");
                    String number = scanner.nextLine();

                    CustomerDocument doc = new CustomerDocument();
                    doc.setCustomer(customer);
                    try {
                        doc.setDocumentType(DocumentType.valueOf(type.toUpperCase()));
                    } catch (IllegalArgumentException e) {
                        System.out.println("❌ Invalid document type.");
                        break;
                    }
                    doc.setDocumentNumber(number);
                    CustomerDocument saved = documentService.create(doc);
                    System.out.println("✅ Document added with ID: " + saved.getId());
                }
                case "3" -> {
                    System.out.print("Document ID to update: ");
                    UUID docId = UUID.fromString(scanner.nextLine());
                    var optionalDoc = documentService.findById(docId);
                    if (optionalDoc.isEmpty()) {
                        System.out.println("❌ Document not found.");
                        break;
                    }
                    CustomerDocument docToUpdate = optionalDoc.get();

                    System.out.println("Leave blank to keep current value.\n");

                    System.out.printf("Document Type (%s): ", docToUpdate.getDocumentType());
                    String newType = scanner.nextLine();
                    if (!newType.isBlank()) {
                        try {
                            docToUpdate.setDocumentType(DocumentType.valueOf(newType.toUpperCase()));
                        } catch (IllegalArgumentException e) {
                            System.out.println("❌ Invalid document type.");
                            break;
                        }
                    }

                    System.out.printf("Document Number (%s): ", docToUpdate.getDocumentNumber());
                    String newNumber = scanner.nextLine();
                    if (!newNumber.isBlank()) docToUpdate.setDocumentNumber(newNumber);

                    documentService.update(docToUpdate);
                    System.out.println("✅ Document updated.");
                }
                case "4" -> {
                    System.out.print("Document ID to delete: ");
                    UUID deleteId = UUID.fromString(scanner.nextLine());
                    documentService.deleteById(deleteId);
                    System.out.println("🗑️ Document deleted.");
                }
                case "0" -> { return; }
                default -> System.out.println("❌ Invalid option.");
            }
        }
    }

    private void updateCustomer(Scanner scanner) {
        System.out.print("Customer ID to update: ");
        UUID id = UUID.fromString(scanner.nextLine());

        var optionalCustomer = customerService.findById(id);
        if (optionalCustomer.isEmpty()) {
            System.out.println("❌ Customer not found.");
            return;
        }

        Customer customer = optionalCustomer.get();

        System.out.println("Leave blank to keep current value.\n");

        System.out.printf("Full Name (%s): ", customer.getFullName());
        String fullName = scanner.nextLine();
        if (!fullName.isBlank()) customer.setFullName(fullName);

        System.out.printf("Phone (%s): ", customer.getPhone());
        String phone = scanner.nextLine();
        if (!phone.isBlank()) customer.setPhone(phone);

        System.out.printf("Email (%s): ", customer.getEmail());
        String email = scanner.nextLine();
        if (!email.isBlank()) customer.setEmail(email);

        System.out.printf("Occupation (%s): ", customer.getOccupation());
        String occupation = scanner.nextLine();
        if (!occupation.isBlank()) customer.setOccupation(occupation);

        Address address = customer.getAddress();

        System.out.printf("Street (%s): ", address.getStreet());
        String street = scanner.nextLine();
        if (!street.isBlank()) address.setStreet(street);

        System.out.printf("Number (%s): ", address.getNumber());
        String number = scanner.nextLine();
        if (!number.isBlank()) address.setNumber(number);

        System.out.printf("City (%s): ", address.getCity());
        String city = scanner.nextLine();
        if (!city.isBlank()) address.setCity(city);

        System.out.printf("State (%s): ", address.getState());
        String state = scanner.nextLine();
        if (!state.isBlank()) address.setState(state);

        System.out.printf("ZIP (%s): ", address.getZip());
        String zip = scanner.nextLine();
        if (!zip.isBlank()) address.setZip(zip);

        customer.setAddress(address);
        customerService.update(customer);

        System.out.println("✅ Customer updated successfully.");
    }

    private void findCustomer(Scanner scanner) {
        System.out.print("Customer ID: ");
        UUID id = UUID.fromString(scanner.nextLine());

        customerService.findById(id).ifPresentOrElse(
                c -> {
                    System.out.println("🧾 Customer Info:");
                    System.out.println("- Name: " + c.getFullName());
                    System.out.println("- Email: " + c.getEmail());
                    System.out.println("- Phone: " + c.getPhone());
                    Address a = c.getAddress();
                    System.out.printf("- Address: %s %s, %s - %s (%s)\n",
                            a.getStreet(), a.getNumber(), a.getCity(), a.getState(), a.getZip());
                },
                () -> System.out.println("❌ Customer not found.")
        );
    }

    private void listCustomers() {
        var list = customerService.findAll();
        if (list.isEmpty()) {
            System.out.println("⚠️ No customers found.");
        } else {
            System.out.println("📋 Customer List:");
            list.forEach(c -> System.out.printf("- %s (%s)\n", c.getFullName(), c.getId()));
        }
    }

    private void deleteCustomer(Scanner scanner) {
        System.out.print("Customer ID to delete: ");
        UUID id = UUID.fromString(scanner.nextLine());

        customerService.deleteById(id);
        System.out.println("🗑️ Customer deleted.");
    }
}
