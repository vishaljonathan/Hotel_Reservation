package model;

import java.util.Objects;

public class Room implements IRoom{

    //Add Variables
    private final String roomNumber;
    private final Double price;
    private final RoomType roomType;

    //Implement Constructor
    public Room(String roomNumber, Double price, RoomType roomType){
        super();
        if (roomNumber == null || roomNumber.isBlank()) {
            throw new IllegalArgumentException("Room number cannot be empty");
        }
        if (price == null) {
            throw new IllegalArgumentException("Price cannot be null");
        }
        if (price < 0.0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (roomType == null) {
            throw new IllegalArgumentException("RoomType cannot be null");
        }
        this.roomNumber = roomNumber;
        this.price = price;
        this.roomType = roomType;
    }

    //Getter function for room number
    public String getRoomNumber(){
        return roomNumber;
    }

    //Getter function for price
    public Double getRoomPrice(){
        return price;
    }

    //Getter function for room type
    public RoomType getRoomType(){
        return roomType;
    }

    //Getter function for isFree
    public boolean isFree(){
        return this.price == 0.0;
    }

    @Override
    public String toString(){
        return "Room Number: " + roomNumber + " " + "Price: " + price + " " + "RoomType: " + roomType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return roomNumber.equals(room.roomNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber);
    }
}
