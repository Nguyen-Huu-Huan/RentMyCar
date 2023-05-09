import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Customer {
    public Customer() {
    }

    private String givenName;
    private String surName;
    private String email;
    private Vehicle rentedVehicle;
    private String pickupDate;
    private String returnDate;
    private int noOfPassengers;

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = Customer.this.surName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = Customer.this.givenName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private double totalRental;

    public int getNoOfPassengers() {
        return noOfPassengers;
    }

    public void setNoOfPassengers(int noOfPassengers) {
        this.noOfPassengers = noOfPassengers;
    }

    public double getTotalRental() {
        return totalRental;
    }

    public void setTotalRental(double totalRental) {
        this.totalRental = totalRental;
    }

    public Vehicle getRentedVehicle() {
        return rentedVehicle;
    }

    public void setRentedVehicle(Vehicle rentedVehicle) {
        this.rentedVehicle = rentedVehicle;
    }

    public Customer(String name, String email) {
        this.givenName = name;
        this.email = email;
    }

    public void getCustomerInformation() {
        // Ask for the customer's information (given name, surname, email address, and the desired number of passenger)
        MenuHelper menuHelper = new MenuHelper();
        ValidationHelper validationHelper = new ValidationHelper();
        menuHelper.printMenuInstruction("Provide personal information");
        System.out.print("Please provide your given name: ");
        this.givenName = validationHelper.validateStringInput();
        System.out.print("Please provide your surname: ");
        this.surName = validationHelper.validateStringInput();
        System.out.print("Please provide your email address: ");
        this.email = validationHelper.validateEmail();
        System.out.print("Please provide your number of passengers: ");
        // Custom error message when the input number of passengers is out of range
        String errorMessage = "The number of passengers should be at least 1 and less than the car's capacity (" + this.rentedVehicle.getNoOfSeats() + " passengers)";
        this.noOfPassengers = validationHelper.validateIntegerWithRange(1, this.rentedVehicle.getNoOfSeats() - 1, errorMessage);
    }

    public void showCustomerInformation() {
        // Default printing format with 40 characters in the first column
        String format = "%-40s%s%n";
        // Linked hash map is used to store the vehicle information with its field name and field value
        LinkedHashMap<String, String> vehicleInfo = new LinkedHashMap<String, String>();
        vehicleInfo.put("Name:", this.givenName + " " + this.surName);
        vehicleInfo.put("Email:", this.email);
        vehicleInfo.put("Your vehicle:", this.rentedVehicle.getVehicleInfo().split(" - ")[1]);
        vehicleInfo.put("Number of passengers:", String.valueOf(this.noOfPassengers));
        vehicleInfo.put("Pick-up date:", this.pickupDate);
        vehicleInfo.put("Return date:", this.returnDate);
        vehicleInfo.put("Total payment:", String.format("$%.2f", this.totalRental));
        // Loop through each key in the linked hash map and print out the result
        for (String key : vehicleInfo.keySet()) {
            System.out.printf(format, key, vehicleInfo.get(key));
        }
    }

    public int rentalPeriod() {
        // Ask the customer for the pickup date
        MenuHelper menuHelper = new MenuHelper();
        menuHelper.printMenuInstruction("Provide dates");
        System.out.print("Please provide pick-up date (dd/mm/yyyy): ");
        ValidationHelper validationHelper = new ValidationHelper();
        this.pickupDate = validationHelper.validatePickupDate();
        // Parse the pickup date (from string to date)
        Date pickupDateReformat;
        try {
            pickupDateReformat = new SimpleDateFormat("dd/MM/yyyy").parse(this.pickupDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        // Ask the customer for the return date
        System.out.print("Please provide return date (dd/mm/yyyy): ");
        this.returnDate = validationHelper.validateReturnDate(pickupDateReformat);
        // Parse the return date (from string to date)
        Date returnDateReformat;
        try {
            returnDateReformat = new SimpleDateFormat("dd/MM/yyyy").parse(this.returnDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        // Calculate the difference in milliseconds and return the difference in days
        long diffInMillies = returnDateReformat.getTime() - pickupDateReformat.getTime();
        return (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) + 1;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getReturnDate() {
        return returnDate;
    }
}
