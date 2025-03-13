package eeet2582.week3.customer;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

/**
 * CustomerRepository
 * Provides methods to interact with the database to store and retrieve
 * entities.
 */
public interface CustomerRepository extends MongoRepository<CustomerEntity, String> {

    Optional<CustomerEntity> findById(String id);

    @Query("{ $or: [ " +
            "{ 'firstName': { $regex: ?0, $options: 'i' } }, " +
            "{ 'lastName': { $regex: ?0, $options: 'i' } }, " +
            "{ 'email': { $regex: ?0, $options: 'i' } }, " +
            "{ $expr:  {$regexMatch:  {input: {$concat: ['$firstName', ' ','$lastName']}, regex:  ?0, options:  'i'}}}"
            +
            "] }")

    List<CustomerEntity> findCustomerEntityBySearchText(@Param("searchText") String searchText);
    //    List<Product> findAllByPrice(double price, Pageable pageable);
    List<CustomerEntity> findCustomerEntityBySearchTextPaginated(Pageable pageable,String searchText);

    List<CustomerEntity> findByFirstName(String firstName);

    List<CustomerEntity> findByLastName(String lastName);

    List<CustomerEntity> findByEmail(String email);

    List<CustomerEntity> findByBalance(double balance);

}
