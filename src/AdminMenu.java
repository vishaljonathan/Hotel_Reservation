import model.Customer;
import model.IRoom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private static final AdminResource adminResource = AdminResource.getInstance();
    private static final Scanner scanner = new Scanner(System.in);

    public static void displayAdminMenu() {
        String option;
        do {
            System.out.println("\nAdmin Menu");
            System.out.println("---------------------------------------------");
            System.out.println("1. See all Customers");
            System.out.println("2. See all Rooms");
            System.out.println("3. See all Reservations");
            System.out.println("4. Add a Room");
            System.out.println("5. Back to Main Menu");
            System.out.println("---------------------------------------------");
            System.out.print("Please select a number for the menu option: ");

            option = scanner.nextLine();

            switch (option) {
                case "1" -> seeAllCustomers();
                case "2" -> seeAllRooms();
                case "3" -> adminResource.displayAllReservations();
                case "4" -> addARoom();
                case "5" -> System.out.println("Returning to Main Menu...");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (!option.equals("5"));
    }

    private static void seeAllCustomers() {
        Collection<Customer> customers = adminResource.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            customers.forEach(System.out::println);
        }
    }

    private static void seeAllRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();
        if (rooms.isEmpty()) {
            System.out.println("No rooms available.");
        } else {
            rooms.forEach(System.out::println);
        }
    }

    private static void addARoom() {
        List<IRoom> newRooms = new ArrayList<>();
        String addAnother;

        do {
            System.out.print("Enter room number: ");
            String roomNumber = scanner.nextLine();

            System.out.print("Enter price per night: ");
            double price = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter room type (SINGLE/DOUBLE): ");
            String type = scanner.nextLine().toUpperCase();

            model.RoomType roomType = model.RoomType.valueOf(type);
            IRoom room = new model.Room(roomNumber, price, roomType);
            newRooms.add(room);

            System.out.print("Add another room? (y/n): ");
            addAnother = scanner.nextLine();
        } while (addAnother.equalsIgnoreCase("y"));

        adminResource.addRoom(newRooms);
        System.out.println("Room(s) added successfully!");
    }
}