import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {
    private static final HotelResource hotelResource = HotelResource.getInstance();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        displayMainMenu();
    }

    public static void displayMainMenu() {
        String option;
        do {
            System.out.println("\nWelcome to the Hotel Reservation Application");
            System.out.println("---------------------------------------------");
            System.out.println("1. Find and reserve a room");
            System.out.println("2. See my reservations");
            System.out.println("3. Create an account");
            System.out.println("4. Admin");
            System.out.println("5. Exit");
            System.out.println("---------------------------------------------");
            System.out.print("Please select a number for the menu option: ");

            option = scanner.nextLine();

            switch (option) {
                case "1" -> findAndReserveRoom();
                case "2" -> seeMyReservations();
                case "3" -> createAccount();
                case "4" -> AdminMenu.displayAdminMenu();
                case "5" -> System.out.println("Thank you for using our system!");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (!option.equals("5"));
    }

    private static void findAndReserveRoom() {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        Customer customer = hotelResource.getCustomer(email);
        if (customer == null) {
            System.out.println("No account found with this email. Please create an account first.");
            return;
        }

        System.out.print("Enter check-in date (yyyy-mm-dd): ");
        String checkInStr = scanner.nextLine();
        System.out.print("Enter check-out date (yyyy-mm-dd): ");
        String checkOutStr = scanner.nextLine();

        try {
            Date checkIn = java.sql.Date.valueOf(checkInStr);
            Date checkOut = java.sql.Date.valueOf(checkOutStr);

            Collection<IRoom> availableRooms = hotelResource.findARoom(checkIn, checkOut);

            if (availableRooms.isEmpty()) {
                System.out.println("No rooms available for the selected dates.");
            } else {
                System.out.println("Available Rooms:");
                availableRooms.forEach(System.out::println);

                System.out.print("Enter room number to reserve: ");
                String roomNumber = scanner.nextLine();
                IRoom room = hotelResource.getRoom(roomNumber);

                if (room != null) {
                    Reservation reservation = hotelResource.bookARoom(email, room, checkIn, checkOut);
                    System.out.println("Reservation confirmed: " + reservation);
                } else {
                    System.out.println("Invalid room number.");
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid date format or input.");
        }
    }

    private static void seeMyReservations() {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        Collection<Reservation> reservations = hotelResource.getCustomersReservation(email);
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            reservations.forEach(System.out::println);
        }
    }

    private static void createAccount() {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        try {
            hotelResource.createACustomer(email, firstName, lastName);
            System.out.println("Account created successfully!");
        } catch (Exception e) {
            System.out.println("Error creating account: " + e.getMessage());
        }
    }
}