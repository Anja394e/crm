package crm.customer;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@RestController
@RequestMapping(path = "api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(path = "/{customerId}")
    public Optional<Customer> getCustomer(@PathVariable ("customerId") int customerId) {
        return customerService.getCustomer(customerId);
    }
    @GetMapping()
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping
    public void registerNewCustomer(@Valid @RequestBody Customer customer) {
        customerService.addNewCustomer(customer);
    }

    @DeleteMapping(path = "/{customerId}")
    public void deleteCustomer(@PathVariable("customerId") int customerId) {
        customerService.deleteCustomer(customerId);
    }

    @PutMapping(path = "/{customerId}")
    public void updateCustomer(
            @PathVariable("customerId") int customerId,
            @Valid @RequestBody Customer newCustomer){
        customerService.updateCustomer(customerId, newCustomer);
    }

    @PatchMapping(path = "/{customerId}")
    public void patchCustomer(
            @PathVariable("customerId") int customerId,
            @RequestBody Customer newCustomer){
        customerService.patchCustomer(customerId, newCustomer);
    }

    @GetMapping(path = "/{customerId}/bill")
    public Set getBill(@PathVariable ("customerId") int customerId) {
        return customerService.getCosts(customerId);
    }
}