import java.util.HashMap;
import java.util.Map;

class CustomerManager {
    private Map<String, Customer> customers = new HashMap<>();

    public void addCustomer(Customer customer) {
        customers.put(customer.getNic(), customer);
    }

    public Customer getCustomer(String nic) {
        return customers.get(nic);
    }
}