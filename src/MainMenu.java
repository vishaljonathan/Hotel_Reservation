import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.Calendar;
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
        try {
            System.out.print("Enter your email: ");
            String email = scanner.nextLine();

            Customer customer = hotelResource.getCustomer(email);
            if (customer == null) {
                System.out.println("No account found with this email. Please create an account first.");
                return;
            }

            System.out.print("Enter check-in date (yyyy-mm-dd): ");
            Date checkIn = java.sql.Date.valueOf(scanner.nextLine());
            System.out.print("Enter check-out date (yyyy-mm-dd): ");
            Date checkOut = java.sql.Date.valueOf(scanner.nextLine());

            if (!checkIn.before(checkOut)) {
                System.out.println("Check-in date must be before check-out date.");
                return;
            }

            // First search attempt
            Collection<IRoom> availableRooms = hotelResource.findARoom(checkIn, checkOut);

            Date recCheckIn = null;
            Date recCheckOut = null;
            boolean usingRecommended = false;

            if (availableRooms.isEmpty()) {
                // If none found, suggest +7 day recommended dates
                Calendar cal = Calendar.getInstance();
                cal.setTime(checkIn);
                cal.add(Calendar.DAY_OF_MONTH, 7);
                recCheckIn = new Date(cal.getTimeInMillis());

                cal.setTime(checkOut);
                cal.add(Calendar.DAY_OF_MONTH, 7);
                recCheckOut = new Date(cal.getTimeInMillis());

                System.out.println("\nNo rooms available for selected dates.");
                System.out.println("Recommended rooms for new dates: " + recCheckIn + " to " + recCheckOut);

                Collection<IRoom> recommendedRooms = hotelResource.findARoom(recCheckIn, recCheckOut);

                if (recommendedRooms.isEmpty()) {
                    System.out.println("No rooms available for the recommended dates either.");
                    return;
                } else {
                    recommendedRooms.forEach(System.out::println);

                    System.out.print("Would you like to book for the recommended dates? (y/n): ");
                    String confirm = scanner.nextLine();
                    if (confirm.equalsIgnoreCase("y")) {
                        availableRooms = recommendedRooms;
                        usingRecommended = true;
                    } else {
                        return;
                    }
                }
            }

            // Proceed with available rooms (either original or recommended)
            System.out.println("\nAvailable Rooms:");
            availableRooms.forEach(System.out::println);

            System.out.print("Enter room number to reserve: ");
            String roomNumber = scanner.nextLine();
            IRoom room = hotelResource.getRoom(roomNumber);

            if (room == null) {
                System.out.println("Invalid room number.");
                return;
            }

            Date finalCheckIn = usingRecommended ? recCheckIn : checkIn;
            Date finalCheckOut = usingRecommended ? recCheckOut : checkOut;

            try {
                Reservation reservation = hotelResource.bookARoom(email, room, finalCheckIn, finalCheckOut);
                System.out.println("\nReservation confirmed: " + reservation);
            } catch (Exception e) {
                System.out.println("Could not book room: " + e.getMessage());
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid date format or input.");
        }
    }

    private static void seeMyReservations() {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        try {
            Collection<Reservation> reservations = hotelResource.getCustomersReservation(email);
            if (reservations.isEmpty()) {
                System.out.println("No reservations found.");
            } else {
                reservations.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Error retrieving reservations: " + e.getMessage());
        }
    }

    private static void createAccount() {
        try {
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter first name: ");
            String firstName = scanner.nextLine();
            System.out.print("Enter last name: ");
            String lastName = scanner.nextLine();

            hotelResource.createACustomer(email, firstName, lastName);
            System.out.println("Account created successfully!");
        } catch (Exception e) {
            System.out.println("Error creating account: " + e.getMessage());
        }
    }
}