package crm.product;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequestMapping(path = "api/v1")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(path = "/customers/{customerId}/products")
    public void registerNewProduct(@PathVariable("customerId") int customerId, @Valid @RequestBody Product product) {
        productService.addNewProduct(customerId, product);
    }

    @DeleteMapping(path = "/customers/{customerId}/products/{type}")
    public void removeProducts(@PathVariable("customerId") int customerId ,@PathVariable("type") Product.ProductType productType) {
        productService.deleteProducts(customerId, productType);
    }

    @GetMapping(path = "/products")
    public Set<Product> getProducts(){
        return productService.getProducts();
    }

    @GetMapping(path = "/products/{ProductType}")
    public Set<Product> getProducts(@PathVariable ("ProductType") Product.ProductType productType){
        return productService.getProducts(productType);
    }
}