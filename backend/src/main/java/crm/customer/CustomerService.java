package crm.customer;

import crm.call.Call;
import crm.product.Product;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;


    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Optional<Customer> getCustomer(int customerId) {
        return customerRepository.findById(customerId);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public void addNewCustomer(Customer customer) {
        customer.setCreationDate(new Date());
        customerRepository.save(customer);
    }

    public void deleteCustomer(int customerId) {
        boolean exists = customerRepository.existsById(customerId);
        if (!exists) {
            throw new IllegalStateException("Customer with id " + customerId + " does not exist.");
        }
        customerRepository.deleteById(customerId);
    }

    public void updateCustomer(int customerid, Customer newCustomer) {
        Customer oldCustomer = customerRepository.findById(customerid).orElseThrow(() -> new IllegalStateException(
                        "customer with id " + customerid + " does not exist."
                )
        );

        oldCustomer.setFirstName(newCustomer.getFirstName());
        oldCustomer.setLastName(newCustomer.getLastName());
        oldCustomer.setEmail(newCustomer.getEmail());

        customerRepository.save(oldCustomer);

    }

    public void patchCustomer(int customerid, Customer newCustomer) {
        Customer oldCustomer = customerRepository.findById(customerid).orElseThrow(() -> new IllegalStateException(
                        "customer with id " + customerid + " does not exist."
                )
        );

        if (newCustomer.getFirstName() != null && !newCustomer.getFirstName().isEmpty()) {
            oldCustomer.setFirstName(newCustomer.getFirstName());
        }

        if (newCustomer.getLastName() != null && !newCustomer.getLastName().isEmpty()) {
            oldCustomer.setLastName(newCustomer.getLastName());
        }

        if (newCustomer.getEmail() != null && !newCustomer.getEmail().isEmpty()) {
            oldCustomer.setEmail(newCustomer.getEmail());
        }

        customerRepository.save(oldCustomer);

    }

    public Set getCosts(int customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new IllegalStateException(
                        "customer with id " + customerId + " does not exist."
                )
        );

        boolean isTV = false;
        boolean isTELEPHONY = false;
        boolean isINTERNET = false;

        Set<Product> products = customer.getProducts();
        for (Product product : products) {
            switch (product.getProductType()){
                case TV -> isTV=true;
                case TELEPHONY -> isTELEPHONY=true;
                case INTERNET -> isINTERNET=true;
            }
        }
        Set is = new HashSet<>();
        is.add(isTV);
        is.add(isTELEPHONY);
        is.add(isINTERNET);

        List<Call> calls = customer.getCalls();

        for (int i=0; i < calls.size()/2; i+=2){
            is.add(calls.get(i+1).getTimestamp().getMinutes() - calls.get(i).getTimestamp().getMinutes());
        }

        return is;
    }
}
