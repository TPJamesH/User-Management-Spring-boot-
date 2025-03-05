package eeet2582.week3.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import eeet2582.week3.customer.dto.CustomerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eeet2582.week3.customer.dto.CustomerBalance;

/**
 *  Leverages the repository to perform business logic operations, such as
 * creating, updating, deleting, and retrieving entities
 */
@Component
class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    private CustomerDTO converter = new CustomerDTO();

    List<CustomerDTO> getCustomerByEmail(String email) {
        List<CustomerEntity> data =  customerRepository.findByEmail(email);
        return converter.toDTO(data);
    }

    CustomerDTO createCustomer(CustomerEntity customerEntity) {
        customerRepository.save(customerEntity);
        return converter.toDTO(customerEntity);
    }

//    CustomerBalance getCustomerBalance(String email) {
//        return customerRepository.getCustomerEntityByEmail(email);
//    }

    public Optional<List<CustomerDTO>> getAllCustomers() {
        return Optional.of(converter.toDTO(customerRepository.findAll()));
    }

//    public Optional<List<CustomerEntity>> getAllCustomersDefault() {
//        return Optional.of(customerRepository.findAll());
//    }

    public Optional<List<CustomerDTO>> searchCustomers(String searchText){

        return Optional.of(converter.toDTO(customerRepository.findCustomerEntityBySearchText(searchText)));
    }

    public Optional<List<CustomerDTO>> getFilterFirstName(String firstName){
        return Optional.of(converter.toDTO(customerRepository.findByFirstName(firstName)));
    }

    public Optional<List<CustomerDTO>> getFilterLastName(String lastName){
        return Optional.of(converter.toDTO(customerRepository.findByLastName(lastName)));
    }

    public Optional<List<CustomerDTO>> getFilterBalance(double balance){
        return Optional.of(converter.toDTO(customerRepository.findByBalance(balance)));
    }

    public Optional<CustomerDTO> updateCustomer(CustomerEntity customer,String id){
        Optional<CustomerEntity> old= this.customerRepository.findById(id);
        Optional<CustomerEntity> updated = this.customerRepository.findById(id);
        if(old.isPresent()){
            updated.get().setFirstName(customer.getFirstName());
            updated.get().setLastName(customer.getLastName());
            updated.get().setEmail(customer.getEmail());
            updated.get().setBalance(customer.getBalance());
            this.customerRepository.save(updated.get());
            return converter.toDTO(updated);
        }
        return converter.toDTO(old);
    }



    public Optional<CustomerDTO> deleteCustomer(String id){
        Optional<CustomerEntity> deleteItem= this.customerRepository.findById(id);
        deleteItem.ifPresent(customer -> this.customerRepository.delete(customer));

        return converter.toDTO(deleteItem);
    }

     public Page<CustomerDTO> getCustomerPage(Pageable pageable){
        return new PageImpl<>(
                converter.toDTO(
                        this.customerRepository.findAll(pageable).
                                getContent()));
     }







}
