package eeet2582.week3.customer;

import java.util.List;
import java.util.Optional;

import eeet2582.week3.customer.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "http://localhost:3000")
/*This annotation allows cross-
origin requests from the specified origin (http://localhost:3000).
This is often used for frontend-backend communication when they are running on different ports or
domains.*/
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/all")
    ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        Optional<List<CustomerDTO>> customers = customerService.getAllCustomers();

        return customers.map(customerEntities -> new ResponseEntity<>(
                customerEntities, HttpStatus.OK
        )).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PostMapping("")
    CustomerDTO createCustomer(@RequestBody CustomerEntity customerData) {
        return customerService.createCustomer(customerData);
    }

    @PutMapping("update/{id}")
    ResponseEntity<CustomerDTO> updateCustomer(@RequestBody  CustomerEntity customerData, @PathVariable String id) {
        Optional<CustomerDTO> retrieve = customerService.updateCustomer(customerData,id);
        return retrieve.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<CustomerDTO> deleteCustomer(@PathVariable String id) {
        Optional<CustomerDTO> retrieve = customerService.deleteCustomer(id);
        return retrieve.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/page")
    ResponseEntity<Page<CustomerDTO>> getCustomerPage(@RequestParam(defaultValue="0") int pageNo,
                                                         @RequestParam(defaultValue = "7") int pageSize){
        return ResponseEntity.ok(
                this.customerService.getCustomerPage(PageRequest.of(pageNo,pageSize))
        );
    }

    @GetMapping("/search/{searchText}")
    ResponseEntity<List<CustomerDTO>> searchCustomers(@PathVariable String searchText){
        return  ResponseEntity.of(
                customerService.searchCustomers(searchText)
        );
    }

//    @GetMapping("/defaultAll")
//    ResponseEntity<List<CustomerEntity>> getAllCustomersDefault() {
//        Optional<List<CustomerEntity>> customers = customerService.getAllCustomersDefault();
//
//        return customers.map(customerEntities -> new ResponseEntity<>(
//                customerEntities, HttpStatus.OK
//        )).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//
//    }

//    @GetMapping("/balance/{email}")
//    CustomerBalance getCustomerBalanceByEmail(@PathVariable String email) {
//        return customerService.getCustomerBalance(email);
//    }

}