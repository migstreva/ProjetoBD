package com.inatel.oopbank.repository;

import com.inatel.oopbank.model.customer.Customer;
import com.inatel.oopbank.model.customerdocument.CustomerDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CustomerDocumentRepository extends JpaRepository<CustomerDocument, UUID> {
    List<CustomerDocument> findByCustomer(Customer customer);
}
