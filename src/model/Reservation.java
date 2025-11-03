
package model;

import java.util.Date;

public class Reservation {

    //Adding variables
    private final Customer customer;
    private final IRoom room;
    private final Date checkInDate;
    private final Date checkOutDate;

    //Implement constructor
    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Customer getCustomer(){
        return customer;
    }

    public IRoom iRoom(){
        return room;
    }

    public Date getCheckInDate(){
        return checkInDate;
    }

    public Date getCheckOutDate(){
        return checkOutDate;
    }

    @Override
    public String toString() {
        return "Customer Name: " + customer.getFirstName() + " " + customer.getLastName() +
                "\nEmail: " + customer.getEmail() +
                "\nRoom Number: " + room.getRoomNumber() +
                "\nPrice: " + room.getRoomPrice() +
                "\nRoom Type: " + room.getRoomType() +
                "\nCheck-In Date: " + checkInDate +
                "\nCheck-Out Date: " + checkOutDate;
    }
}
