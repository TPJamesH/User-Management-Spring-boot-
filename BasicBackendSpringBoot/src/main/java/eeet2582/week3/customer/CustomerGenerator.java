package eeet2582.week3.customer;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

import java.util.List;

@Component
public class CustomerGenerator {

    private final CustomerRepository customerRepository;

    public CustomerGenerator(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostConstruct
    public void generateCustomers() {
        this.customerRepository.deleteAll();
        List<CustomerEntity> customers = List.of(
                new CustomerEntity("John", "Doe", "john.doe@example.com", 11),
                new CustomerEntity("Jane", "Smith", "jane.smith@example.com", 60),
                new CustomerEntity("Michael", "Johnson", "michael.johnson@example.com", 50),
                new CustomerEntity("Emily", "Davis", "emily.davis@example.com", 70),
                new CustomerEntity("David", "Wilson", "david.wilson@example.com", 50));
        customerRepository.saveAll(customers);
    }
}
