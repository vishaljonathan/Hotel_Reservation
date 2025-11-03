package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {
    private static final Map<String,IRoom>  roomList = new HashMap<>();
    private static final Set<Reservation> reservationList = new HashSet<>();
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
        //Null values not accepted
        if(room == null){
            throw new IllegalArgumentException("Room number cannot be null");
        }
        if(roomList.containsKey(room.getRoomNumber())){
            //No duplicate entries allowed
            System.out.println("Room with the number " + room.getRoomNumber() + " already exists");
            return;
        }
        roomList.put(room.getRoomNumber(), room);
    }

    public IRoom getARoom(String roomId){
        return roomList.get(roomId);
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found");
        }
        if (room == null) {
            throw new IllegalArgumentException("Room not found");
        }
        if (checkInDate == null || checkOutDate == null) {
            throw new IllegalArgumentException("Dates cannot be null");
        }
        if (!checkInDate.before(checkOutDate)) {
            throw new IllegalArgumentException("Check-in must be before check-out");
        }

        //Check if the user entered room number exists
        IRoom storedRoom = getARoom(room.getRoomNumber());
        if (storedRoom == null) {
            throw new IllegalArgumentException("Room number not registered in system: " + room.getRoomNumber());
        }

        //Checking availability of the room
        Collection<IRoom> available = findRooms(checkInDate, checkOutDate);
        if (!available.contains(storedRoom)) {
            throw new IllegalStateException("Room " + room.getRoomNumber() + " is not available for the selected dates.");
        }

        //Prevent double booking for the same room
        for(Reservation r : reservationList){
            if(r.iRoom().getRoomNumber().equals(storedRoom)){
                Date reservedIn = r.getCheckInDate();
                Date reservedOut = r.getCheckOutDate();
                boolean validCheckIn = checkInDate.before(reservedOut) && checkOutDate.after(reservedIn);
                if(validCheckIn){
                    throw new IllegalStateException("Room already reserved for overlapping dates.");
                }
            }
        }

        Reservation reservation = new Reservation(customer,room,checkInDate,checkOutDate);
        reservationList.add(reservation);
        return reservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate){
        //Null dates means all rooms
        if (checkInDate == null || checkOutDate == null) {
            return new ArrayList<>(roomList.values());
        }

        List<IRoom> availableRooms = new ArrayList<>(roomList.values());

        for(Reservation reservation: reservationList){
            Date reservedCheckIn = reservation.getCheckInDate();
            Date reservedCheckOut = reservation.getCheckOutDate();
            boolean validCheckIn = checkInDate.before(reservedCheckOut) && checkOutDate.after(reservedCheckIn);
            if(validCheckIn){
                availableRooms.remove(reservation.iRoom().getRoomNumber());
            }
        }

        if (!availableRooms.isEmpty()) {
            return availableRooms;
        }

        //If room not available in the specified check in and check out dates, +7 days search
        Calendar cal = Calendar.getInstance();
        cal.setTime(checkInDate);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        Date recCheckIn = cal.getTime();

        cal.setTime(checkOutDate);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        Date recCheckOut = cal.getTime();

        List<IRoom> recommended = new ArrayList<>(roomList.values());
        for (Reservation reservation : reservationList) {
            Date reservedCheckIn = reservation.getCheckInDate();
            Date reservedCheckOut = reservation.getCheckOutDate();
            boolean overlap = recCheckIn.before(reservedCheckOut) && recCheckOut.after(reservedCheckIn);
            if (overlap) {
                recommended.remove(reservation.iRoom().getRoomNumber());
            }
        }

        if (!recommended.isEmpty()) {
            System.out.println("No rooms available for requested dates. Showing recommended rooms for dates: " + recCheckIn + " to " + recCheckOut);
            return recommended;
        }

        return Collections.emptyList();
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

    public Collection<IRoom> getAllRooms(){
        return roomList.values();
    }
}
