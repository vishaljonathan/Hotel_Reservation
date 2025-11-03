package model;

public class Room implements IRoom{

    //Add Variables
    private String roomNumber;
    private Double price;
    private RoomType roomType;

    //Implement Constructor
    public Room(String roomNumber, Double price, RoomType roomType){
        super();
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

    //Overriding
    public String toString(){
        return "Room Number: " + roomNumber + " " + "Price: " + price + " " + "RoomType: " + roomType;
    }
}
