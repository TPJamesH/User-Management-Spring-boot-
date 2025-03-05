package eeet2582.week3.customer;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
@Document(collection = "customers")
/**
* Defines the data structure*/
public class CustomerEntity {
    @Id
    private String id;
  //  @Column(name = "firstName")
    private String firstName;
   // @Column(name = "lastName")
    private String lastName;
   // @Column(name = "email")
    private String email;
   // @Column(name = "balance")
    private double balance;

    public CustomerEntity() {}

    protected CustomerEntity(String firstName, String lastName, String email, double balance) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.balance = balance;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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

    

}
