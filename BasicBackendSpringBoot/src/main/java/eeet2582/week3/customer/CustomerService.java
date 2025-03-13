package eeet2582.week3.customer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import eeet2582.week3.customer.dto.CustomerDTO;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Leverages the repository to perform business logic operations, such as
 * creating, updating, deleting, and retrieving entities
 */
@Component
class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    private CustomerDTO converter = new CustomerDTO();

    // In-memory cache for the token-to-ID map
    private final Map<String, String> tokenCache = new ConcurrentHashMap<>();
    // Temporary token-to-ID map
    private final Map<String, String> tokenToIdMap = new ConcurrentHashMap<>();

    List<CustomerDTO> getCustomerByEmail(String email) {
        List<CustomerEntity> data = customerRepository.findByEmail(email);
        return converter.toDTO(data);
    }

    CustomerDTO createCustomer(CustomerEntity customerEntity) {
        customerRepository.save(customerEntity);
        return converter.toDTO(customerEntity);
    }

    // CustomerBalance getCustomerBalance(String email) {
    // return customerRepository.getCustomerEntityByEmail(email);
    // }

    public Map<String, Object> getAllCustomers() {
        tokenCache.clear();
        tokenToIdMap.clear();
        List<CustomerEntity> customers = customerRepository.findAll();

        List<CustomerDTO> customerDTOs = customers.stream().map(converter::toDTO).collect(Collectors.toList());

        // Generate tokens
        
        List<String> tokens = customers.stream().map(
                customer -> {
                    String token = UUID.randomUUID().toString(); // Generate token
                    tokenCache.put(token, customer.getId());
                    tokenToIdMap.put(token, customer.getId()); // Map token
                    return token;
                }).collect(Collectors.toList());

        // Return both DTOs and tokens as a response
        Map<String, Object> response = new HashMap<>();
        response.put("dto", customerDTOs);
        response.put("tokens", tokens);
        return response;
    }

    public Map<String, Object> searchCustomers(String searchText) {
        tokenCache.clear();
        tokenToIdMap.clear();
        List<CustomerEntity> customers = customerRepository.findCustomerEntityBySearchText(searchText);

        List<CustomerDTO> customerDTOs = customers.stream().map(converter::toDTO).collect(Collectors.toList());

        // Generate tokens
        List<String> tokens = customers.stream().map(
                customer -> {
                    String token = UUID.randomUUID().toString(); // Generate token
                    tokenCache.put(token, customer.getId());
                    tokenToIdMap.put(token, customer.getId()); // Map token
                    return token;
                }).collect(Collectors.toList());

        // Return both DTOs and tokens as a response
        Map<String, Object> response = new HashMap<>();
        response.put("dto", customerDTOs);
        response.put("tokens", tokens);
        return response;

    }

    public Optional<List<CustomerDTO>> getFilterFirstName(String firstName) {
        return Optional.of(converter.toDTO(customerRepository.findByFirstName(firstName)));
    }

    public Optional<List<CustomerDTO>> getFilterLastName(String lastName) {
        return Optional.of(converter.toDTO(customerRepository.findByLastName(lastName)));
    }

    public Optional<List<CustomerDTO>> getFilterBalance(double balance) {
        return Optional.of(converter.toDTO(customerRepository.findByBalance(balance)));
    }

    public Optional<CustomerDTO> updateCustomer(CustomerEntity customer, String id) {
        Optional<CustomerEntity> old = this.customerRepository.findById(tokenCache.get(id));
        Optional<CustomerEntity> updated = this.customerRepository.findById(tokenCache.get(id));
        if (old.isPresent()) {
            updated.get().setFirstName(customer.getFirstName());
            updated.get().setLastName(customer.getLastName());
            updated.get().setEmail(customer.getEmail());
            updated.get().setBalance(customer.getBalance());
            this.customerRepository.save(updated.get());
            return converter.toDTO(updated);
        }
        return converter.toDTO(old);
    }

    public Optional<CustomerDTO> deleteCustomer(String id) {
        Optional<CustomerEntity> deleteItem = this.customerRepository.findById(tokenCache.get(id));
        deleteItem.ifPresent(customer -> this.customerRepository.delete(customer));

        return converter.toDTO(deleteItem);
    }

    public Map<String, Object> getCustomerPage(Pageable pageable) {
        tokenCache.clear();
        tokenToIdMap.clear();
        List<CustomerEntity> customers = this.customerRepository.findAll(pageable).getContent();

        List<CustomerDTO> customerDTOs = customers.stream().map(converter::toDTO).collect(Collectors.toList());

        // Generate tokens
        List<String> tokens = customers.stream().map(
                customer -> {
                    String token = UUID.randomUUID().toString(); // Generate token
                    tokenCache.put(token, customer.getId());
                    tokenToIdMap.put(token, customer.getId()); // Map token
                    return token;
                }).collect(Collectors.toList());

        // Return both DTOs and tokens as a response
        Map<String, Object> response = new HashMap<>();
        response.put("dto", new PageImpl<>(customerDTOs));
        response.put("tokens", tokens);
        return response;
        // return new PageImpl<>(
        // converter.toDTO(
        // this.customerRepository.findAll(pageable).getContent()));
    }

    public Map<String,Object> getPagination_Search(Pageable pageable,String searchText){
        tokenCache.clear();
        tokenToIdMap.clear();
        List<CustomerEntity> customers = customerRepository.findCustomerEntityBySearchTextPaginated(pageable,searchText);
        List<CustomerDTO> customerDTOs = customers.stream().map(converter::toDTO).collect(Collectors.toList());

        // Generate tokens
        List<String> tokens = customers.stream().map(
                customer -> {
                    String token = UUID.randomUUID().toString(); // Generate token
                    tokenCache.put(token, customer.getId());
                    tokenToIdMap.put(token, customer.getId()); // Map token
                    return token;
                }).collect(Collectors.toList());

        // Return both DTOs and tokens as a response
        Map<String, Object> response = new HashMap<>();
        response.put("dto", new PageImpl<>(customerDTOs));
        response.put("tokens", tokens);
        return response;

    }
}
