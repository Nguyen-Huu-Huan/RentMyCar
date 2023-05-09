import java.awt.*;
import java.sql.Time;
import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class Vehicle {
    private String vehicleID;
    private String brand;
    private String model;
    private String type;
    private int yearOfManufacture;
    private int noOfSeats;
    private String color;
    private double rentalPerDay;
    private double insurancePerDay;
    private double serviceFee;
    private double discount;

    public Vehicle() {
    }

    public Vehicle(String vehicleID, String brand, String model, String type, String color) {
        // The constructor is used to save the columns with string type
        this.vehicleID = vehicleID;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.color = color;
    }

    public Vehicle(String vehicleID, String brand, String model, String type, int yearOfManufacture, int noOfSeats, String color, double rentalPerDay, double insurancePerDay, double serviceFee, double discount) {
        // The constructor is used to save all columns (when all data is valid)
        this.vehicleID = vehicleID;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.yearOfManufacture = yearOfManufacture;
        this.noOfSeats = noOfSeats;
        this.color = color;
        this.rentalPerDay = rentalPerDay;
        this.insurancePerDay = insurancePerDay;
        this.serviceFee = serviceFee;
        this.discount = discount;
    }

    public void showVehicleDetails(Customer customer) {
        // Calculate the difference between rental period (return date - pickup date)
        int numOfRentDay = customer.rentalPeriod();
        // Default printing format with 40 characters in the first column
        String format = "%-40s%s%n";
        // Linked hash map is used to store the vehicle information with its field name and field value
        LinkedHashMap<String, String> vehicleInfo = new LinkedHashMap<String, String>();
        vehicleInfo.put("Vehicle:", this.vehicleID);
        vehicleInfo.put("Brand:", this.brand);
        vehicleInfo.put("Model:", this.model);
        vehicleInfo.put("Type of vehicle:", this.type);
        // -1 for invalid data, therefore, print out N/A
        if (this.yearOfManufacture == -1)
            vehicleInfo.put("Year of manufacture:", "N/A");
        else
            vehicleInfo.put("Year of manufacture:", String.valueOf(this.yearOfManufacture));
        if (this.noOfSeats == -1)
            vehicleInfo.put("No. Of seats:", "N/A");
        else
            vehicleInfo.put("No. Of seats:", String.valueOf(this.noOfSeats));
        vehicleInfo.put("Colour:", this.color);
        // Calculate the total rental + insurance fees by multiplying the daily rate with the number of days
        double totalRentalFees = this.rentalPerDay * numOfRentDay;
        double totalInsuranceFees = this.insurancePerDay * numOfRentDay;
        double totalDiscount = 0;
        // Apply a discount when the rental period is more than 7 days
        if (numOfRentDay >= 7) {
            totalDiscount = this.discount * numOfRentDay;
        }
        vehicleInfo.put("Rental:", String.format("$%-10.2f($%.2f * %d days)", totalRentalFees, this.rentalPerDay, numOfRentDay));
        String discountFormat = String.format("$%-10.2f($%.2f * %d days)", totalRentalFees - totalDiscount, this.discount, numOfRentDay);
        // Display discount is not available when the total discount is equal to 0
        if (totalDiscount == 0) {
            discountFormat = String.format("$%-10.2f(Discount is not applicable)", totalRentalFees);
        }
        vehicleInfo.put("Discounted price:", discountFormat);
        vehicleInfo.put("Insurance:", String.format("$%-10.2f($%.2f * %d days)", totalInsuranceFees, this.insurancePerDay, numOfRentDay));
        vehicleInfo.put("Service fee:", String.format("$%.2f", this.serviceFee));
        // Calculate the total price to display
        double totalPrice = totalRentalFees - totalDiscount + totalInsuranceFees + this.serviceFee;
        customer.setTotalRental(totalPrice);
        vehicleInfo.put("Total:", String.format("$%.2f", totalPrice));
        new MenuHelper().printMenuInstruction("Show vehicle details");
        for (String key : vehicleInfo.keySet()) {
            System.out.printf(format, key, vehicleInfo.get(key));
        }
    }


    public String getVehicleInfo() {
        // The method is used to print out the vehicle information in the menu
        return this.vehicleID + " - " + this.brand + " " + this.model + " " + this.type + " with " + this.noOfSeats + " seats";
    }

    public double totalRentalFees(int noOfDays) {
        return this.rentalPerDay * noOfDays;
    }

    public double totalInsuranceFees(double noOfDays) {
        return this.insurancePerDay * noOfDays;
    }

    public String getVehicleID() {
        return this.vehicleID;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getYearOfManufacture() {
        return this.yearOfManufacture;
    }

    public void setYearOfManufacture(int yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

    public int getNoOfSeats() {
        return this.noOfSeats;
    }

    public void setNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getRentalPerDay() {
        return this.rentalPerDay;
    }

    public void setRentalPerDay(double rentalPerDay) {
        this.rentalPerDay = rentalPerDay;
    }

    public double getInsurancePerDay() {
        return this.insurancePerDay;
    }

    public void setInsurancePerDay(double insurancePerDay) {
        this.insurancePerDay = insurancePerDay;
    }

    public double getServiceFee() {
        return this.serviceFee;
    }

    public void setServiceFee(double serviceFee) {
        this.serviceFee = serviceFee;
    }

    public double getDiscount() {
        return this.discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

}
