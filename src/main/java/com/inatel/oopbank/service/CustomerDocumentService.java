package com.inatel.oopbank.service;

import com.inatel.oopbank.model.customer.Customer;
import com.inatel.oopbank.model.customerdocument.CustomerDocument;
import com.inatel.oopbank.repository.CustomerDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerDocumentService {

    private final CustomerDocumentRepository documentRepository;

    public CustomerDocument create(CustomerDocument document) {
        return documentRepository.save(document);
    }

    public Optional<CustomerDocument> findById(UUID id) {
        return documentRepository.findById(id);
    }

    public List<CustomerDocument> findByCustomer(Customer customer) {
        return documentRepository.findByCustomer(customer);
    }

    public void update(CustomerDocument document) {
        if (!documentRepository.existsById(document.getId())) {
            throw new IllegalArgumentException("Document not found");
        }
        documentRepository.save(document);
    }

    public void deleteById(UUID id) {
        if (!documentRepository.existsById(id)) {
            throw new IllegalArgumentException("Document not found");
        }
        documentRepository.deleteById(id);
    }
}
