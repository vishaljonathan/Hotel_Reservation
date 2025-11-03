import model.Customer;

public class Driver {
    public static void main(String[] args){
        Customer customer = new Customer("Jeff","Bezoz","jeff@example.com");
        System.out.println(customer);

        //Email validation
        Customer customer1 = new Customer("first","second","email");
        System.out.println(customer1);
    }
}