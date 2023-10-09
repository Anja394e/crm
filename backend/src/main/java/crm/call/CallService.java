package crm.call;

import crm.customer.Customer;
import crm.customer.CustomerRepository;
import crm.product.Product;
import org.springframework.stereotype.Service;

import java.util.*;

import static crm.call.Call.Type.END;
import static crm.call.Call.Type.START;
import static crm.product.Product.ProductType.TELEPHONY;

@Service
public class CallService {

    private final CallRepository callRepository;
    private final CustomerRepository customerRepository;


    public CallService(CallRepository callRepository, CustomerRepository customerRepository) {
        this.callRepository = callRepository;
        this.customerRepository = customerRepository;
    }
    
    public List<Call> getCalls(int customerId) {

        Customer customer = customerRepository.findById(customerId).orElseThrow(()
                -> new IllegalStateException("customer with id " + customerId + " does not exist."));

        return customer.getCalls();
    }

    public void deleteCall(int callId) {
        boolean exists = callRepository.existsById(callId);
        if (!exists) {
            throw new IllegalStateException("Call with id " + callId + " does not exist.");
        }
        callRepository.deleteById(callId);
    }

    public void addStart(int customerId) {

        Customer customer = customerRepository.findById(customerId).orElseThrow(()
                -> new IllegalStateException("customer with id " + customerId + " does not exist."));
        Set<Product> products = customer.getProducts();
        boolean contains = false;
        for (Product product : products){
            if (product.getProductType() == TELEPHONY){
                contains = true;
            }
        }

        if(contains){
            List<Call> oldCalls = customer.getCalls(); //get last Call (with the highest Id)
            Call lastCall = new Call();


            if (oldCalls.isEmpty()){
                lastCall.setType(END);
            }
            else {
                for (Call oldCall: oldCalls){
                    if (oldCall.getId() > lastCall.getId()){
                        lastCall=oldCall;
                    }
                }
            }

            if(lastCall.getType() == END){

                Call call = new Call();
                call.setType(START);
                call.setTimestamp(new Date());
                customer.addCall(call);
                customerRepository.save(customer);
            }
        }




    }

    public void addEnd(int customerId, Call call) {

        Customer customer = customerRepository.findById(customerId).orElseThrow(()
                -> new IllegalStateException("customer with id " + customerId + " does not exist."));

        Set<Product> products = customer.getProducts();
        boolean contains = false;
        for (Product product : products){
            if (product.getProductType() == TELEPHONY){
                contains = true;
            }
        }

        if(contains){
            List<Call> oldCalls = customer.getCalls(); //get last Call (with highest Id)
            Call lastCall = new Call();
            if (!oldCalls.isEmpty()){
                for (Call oldCall: oldCalls){
                    if (oldCall.getId() > lastCall.getId()){
                        lastCall=oldCall;
                    }
                }
            }
            else {lastCall.setType(END);}

            if(lastCall.getType() == START) {
                call.setType(END);
                call.setTimestamp(new Date());

                customer.addCall(call);

                customerRepository.save(customer);
            }
        }
    }
}
