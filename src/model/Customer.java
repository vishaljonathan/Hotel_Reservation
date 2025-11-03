package model;

import java.util.regex.Pattern;

public class Customer {

    //Declare variables
    private String firstName;
    private String lastName;
    private String email;

    //Implement constructor method
    public Customer(String firstName, String lastName, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        String emailRegex = "^(.+)@(.+).(.+)$";

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
