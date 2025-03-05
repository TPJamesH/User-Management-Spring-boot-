package eeet2582.week3.customer;

import java.util.List;
import java.util.Map;
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
/*
 * This annotation allows cross-
 * origin requests from the specified origin (http://localhost:3000).
 * This is often used for frontend-backend communication when they are running
 * on different ports or
 * domains.
 */
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/all")
    ResponseEntity<Map<String, Object>> getAllCustomers() {
        Map<String, Object> customers = customerService.getAllCustomers();

        if (customers == null || customers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);

    }

    @PostMapping("")
    CustomerDTO createCustomer(@RequestBody CustomerEntity customerData) {
        return customerService.createCustomer(customerData);
    }

    @PutMapping("update/{id}")
    ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerEntity customerData, @PathVariable String id) {
        Optional<CustomerDTO> retrieve = customerService.updateCustomer(customerData, id);
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
    ResponseEntity<Map<String, Object>> getCustomerPage(@RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "7") int pageSize) {
        Map<String, Object> customers = customerService.getCustomerPage(PageRequest.of(pageNo, pageSize));

        if (customers == null || customers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);

    }

    @GetMapping("/search/{searchText}")
    ResponseEntity<Map<String,Object>> searchCustomers(@PathVariable String searchText) {
        Map<String, Object> customers =   customerService.searchCustomers(searchText);

        if (customers == null || customers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
      
    }

    // @GetMapping("/defaultAll")
    // ResponseEntity<List<CustomerEntity>> getAllCustomersDefault() {
    // Optional<List<CustomerEntity>> customers =
    // customerService.getAllCustomersDefault();
    //
    // return customers.map(customerEntities -> new ResponseEntity<>(
    // customerEntities, HttpStatus.OK
    // )).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    //
    // }

    // @GetMapping("/balance/{email}")
    // CustomerBalance getCustomerBalanceByEmail(@PathVariable String email) {
    // return customerService.getCustomerBalance(email);
    // }

}