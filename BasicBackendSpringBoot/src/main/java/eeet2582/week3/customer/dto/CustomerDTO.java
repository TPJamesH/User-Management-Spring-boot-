package eeet2582.week3.customer.dto;

import eeet2582.week3.customer.CustomerEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDTO  {
    private String firstName;
    private String lastName;
    private String email;
    private double balance;

    public CustomerDTO() {}
    public CustomerDTO(String firstName, String lastName, String email, double balance) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.balance = balance;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


    public List<CustomerDTO> toDTO(List<CustomerEntity> data) {
        List<CustomerDTO> result = new ArrayList<>();
        data.forEach(customer -> result.add(toDTO(customer)));
        return result;
    }

    public CustomerDTO toDTO(CustomerEntity customer) {
        return new CustomerDTO(customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getBalance());
    }

    public Optional<CustomerDTO> toDTO(Optional<CustomerEntity> customer) {
        return Optional.of(new CustomerDTO(customer.get().getFirstName(),
                customer.get().getLastName(),
                customer.get().getEmail(),
                customer.get().getBalance()));
    }
}
