package model;

import java.util.regex.Pattern;

public class Customer {

    //Declare variables
    private final String firstName;
    private final String lastName;
    private final String email;

    //Implement constructor method
    public Customer(String firstName, String lastName, String email){
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name cannot be empty");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        String emailRegex = "^(.+)@(.+)\\.(com|org|net|co)$";

        if(!Pattern.compile(emailRegex).matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format: " + email);
        }

        this.email = email;
    }

    //Getter function for firstname
    public String getFirstName(){
        return firstName;
    }

    //Getter function for lastname
    public String getLastName(){
        return lastName;
    }

    //Getter function for email
    public String getEmail(){
        return email;
    }

    public String toString(){
        return "Customer Name: " + firstName + " " + lastName + "\nEmail ID: " + email;
    }
}
