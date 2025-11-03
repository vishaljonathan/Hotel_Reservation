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
        String addAnother = null;

        do {
            try {
                System.out.print("Enter room number: ");
                String roomNumber = scanner.nextLine();
                if (roomNumber == null || roomNumber.isBlank()) {
                    System.out.println("Room number cannot be empty.");
                    continue;
                }

                System.out.print("Enter price per night: ");
                String priceInput = scanner.nextLine();
                double price;
                try {
                    price = Double.parseDouble(priceInput);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid price. Please enter a numeric value.");
                    continue;
                }
                if (price < 0.0) {
                    System.out.println("Price cannot be negative.");
                    continue;
                }

                System.out.print("Enter room type (SINGLE/DOUBLE): ");
                String type = scanner.nextLine().toUpperCase();
                model.RoomType roomType;
                try {
                    roomType = model.RoomType.valueOf(type);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid room type. Use SINGLE or DOUBLE.");
                    continue;
                }

                IRoom room = new model.Room(roomNumber, price, roomType);
                newRooms.add(room);

                System.out.print("Add another room? (y/n): ");
                addAnother = scanner.nextLine();
            } catch (Exception ex) {
                System.out.println("Error adding room: " + ex.getMessage());
                addAnother = "n";
            }
        } while (addAnother != null && addAnother.equalsIgnoreCase("y"));

        adminResource.addRoom(newRooms);
        System.out.println("Room(s) added successfully!");
    }
}