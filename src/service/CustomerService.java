package service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {
    private static final Map<String,Customer> customerList = new HashMap<>();
    private static CustomerService customerService = null;

    private CustomerService(){

    }

    public static CustomerService getInstance(){
        if (customerService == null){
            customerService = new CustomerService();
        }
        return customerService;
    }

    public void addCustomer(String email, String firstName, String lastName){
        Customer customer = new Customer(firstName, lastName, email);
        customerList.put(email,customer);
    }

    public Customer getCustomer(String customerEmail){
        return customerList.get(customerEmail);
    }

    public Collection<Customer> getAllCustomers(){
        return customerList.values();
    }
}
