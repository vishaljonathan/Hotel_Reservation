package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {
    private static Map<String,IRoom>  roomList = new HashMap<>();
    private static Set<Reservation> reservationList = new HashSet<>();
    private static ReservationService reservationService = null;

    private ReservationService(){

    }

    public static ReservationService getInstance(){
        if (reservationService == null){
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    public void addRoom(IRoom room){
        roomList.put(room.getRoomNumber(), room);
    }

    public IRoom getARoom(String roomId){
        return roomList.get(roomId);
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        Reservation reservation = new Reservation(customer,room,checkInDate,checkOutDate);
        reservationList.add(reservation);
        return reservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate){
        List<IRoom> availableRooms = new ArrayList<>(roomList.values());
        for(Reservation reservation: reservationList){
            Date reservedCheckIn = reservation.getCheckInDate();
            Date reservedCheckOut = reservation.getCheckOutDate();
            boolean validCheckIn = checkInDate.before(reservedCheckOut) && checkOutDate.after(reservedCheckIn);
            if(validCheckIn){
                availableRooms.remove(reservation.iRoom().getRoomNumber());
            }
        }
        return availableRooms;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer){
        List<Reservation> customerReservation = new ArrayList<>();
        for(Reservation reservation: reservationList){
            if(reservation.getCustomer().equals(customer)){
                customerReservation.add(reservation);
            }
        }
        return customerReservation;
    }

    public void printAllReservation(){
        if(reservationList.isEmpty()){
            System.out.println("No reservations found");
        } else {
            for (Reservation reservation: reservationList){
                System.out.println(reservation);
                System.out.println("----------------------------------");
            }
        }
    }

}
