package crm.customer;
import crm.call.Call;
import crm.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.*;

@Entity
@Table (name = "Customer")
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NonNull
    @NotNull
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NonNull
    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NonNull
    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "creation_date")
    private Date creationDate;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn
    private Set<Product> products;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn
    private List<Call> calls; //TODO ask if this is the only way of creating relationships

    public void addProduct(Product product) {
        if (products == null){ //initialize product set if not exists
            products = new HashSet<>();
        }
        products.add(product);
    }
    public void addCall(Call call) {
        if (calls == null){
            calls = new ArrayList<>();
        }
        calls.add(call);
    }

}