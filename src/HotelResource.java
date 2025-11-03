import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {
    private static HotelResource hotelResource = null;
    private static final CustomerService customerService = CustomerService.getInstance();
    private static final ReservationService reservationService = ReservationService.getInstance();

    //Static reference
    private HotelResource(){

    }

    public static HotelResource getInstance(){
        if (hotelResource == null){
            hotelResource = new HotelResource();
        }
        return hotelResource;
    }

    public Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName){
        customerService.addCustomer(email,firstName,lastName);
    }

    public IRoom getRoom(String roomNumber){
        return reservationService.getARoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date CheckOutDate){
        Customer customer = customerService.getCustomer(customerEmail);
        if(customer == null){
            throw new IllegalArgumentException("User not found: " + customerEmail);
        }
        return reservationService.reserveARoom(customer,room,checkInDate,CheckOutDate);
    }

    public Collection <Reservation> getCustomersReservation(String customerEmail){
        Customer customer = customerService.getCustomer(customerEmail);
        if(customer == null){
            throw new IllegalArgumentException("User not found: " + customerEmail);
        }
        return reservationService.getCustomersReservation(customer);
    }

    public Collection <IRoom> findARoom(Date checkIn, Date checkOut){
        return reservationService.findRooms(checkIn,checkOut);
    }
}
