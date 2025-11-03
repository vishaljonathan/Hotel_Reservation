
package model;

import java.util.Date;
import java.util.Objects;

public class Reservation {

    //Adding variables
    private final Customer customer;
    private final IRoom room;
    private final Date checkInDate;
    private final Date checkOutDate;

    //Implement constructor
    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }
        if (checkInDate == null || checkOutDate == null) {
            throw new IllegalArgumentException("Check-in and check-out dates cannot be null");
        }
        if (!checkInDate.before(checkOutDate)) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation)) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(customer, that.customer) &&
                Objects.equals(room, that.room) &&
                Objects.equals(checkInDate, that.checkInDate) &&
                Objects.equals(checkOutDate, that.checkOutDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, room, checkInDate, checkOutDate);
    }
}
