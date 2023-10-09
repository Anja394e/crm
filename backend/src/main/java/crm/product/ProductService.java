package crm.product;

import crm.customer.Customer;
import crm.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductService {


    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;


    public ProductService(ProductRepository productRepository, CustomerRepository customerRepository) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    public Set<Product> getProducts() {
        Set<Product> productSet = new HashSet<>();
        List<Customer> customerList = customerRepository.findAll();

        for (Customer customer : customerList){
            productSet.addAll(customer.getProducts());
        }

        return productSet;
    }

    public Set<Product> getProducts (Product.ProductType productType){
        Set<Product> allProducts = getProducts();
        Set<Product> typeProducts = new HashSet<>();
        for (Product product : allProducts){
            if (product.getProductType() == productType){
                typeProducts.add(product);
            }
        }
        return typeProducts;
    }

    public void addNewProduct(int customerId, Product product) {

        Customer customer = customerRepository.findById(customerId).orElseThrow(()
                -> new IllegalStateException("customer with id " + customerId + " does not exist."));

        Set<Product> existingProducts = customer.getProducts();
        Set<Product.ProductType> types = new HashSet<>();
        for (Product customersProduct : existingProducts){
            types.add(customersProduct.getProductType());
        }

        if (!types.contains(product.getProductType())){
            customer.addProduct(product);
            customerRepository.save(customer);
        }
    }

    public void deleteProducts(int customerId, Product.ProductType type) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()
                -> new IllegalStateException("customer with id " + customerId + " does not exist."));

        Set<Product> customersProducts = customer.getProducts();
        if (customersProducts == null) {
            customersProducts = new HashSet<>();
        }

        for (Product product : customersProducts) {
            if (product.getProductType() == type) {
                productRepository.deleteById(product.getId());
            }

        }
    }
}
