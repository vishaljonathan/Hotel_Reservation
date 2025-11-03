package model;

public class FreeRoom extends Room{

    //Change the constructor and set the price to 0
    public FreeRoom(String roomNumber, RoomType roomType){
        super(roomNumber,0.0,roomType);
    }

    public String toString(){
        return "Free Room: " + super.toString();
    }
}
