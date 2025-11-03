import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {
    private static AdminResource adminResource = null;
    private static final CustomerService customerService = CustomerService.getInstance();
    private static final ReservationService reservationService = ReservationService.getInstance();

    //Static reference
    private AdminResource(){

    }

    public static AdminResource getInstance(){
        if (adminResource == null){
            adminResource = new AdminResource();
        }
        return adminResource;
    }

    public Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }

    public void addRoom(List<IRoom> rooms){
        for(IRoom room:rooms){
            reservationService.addRoom(room);
        }
    }

    public Collection<IRoom> getAllRooms(){
        return reservationService.findRooms(null,null);
    }

    public Collection<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    public void displayAllReservations(){
        reservationService.printAllReservation();
    }
}
